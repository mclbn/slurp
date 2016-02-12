package com.slurp.helper.checks.active.dirFuzz;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IHttpRequestResponse;
import com.slurp.SlurpUtils;
import com.slurp.helper.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlurpHelperCheckDirFuzz extends AbstractSlurpHelperActiveCheck {
    private List<String> knownDirMsg;
    private ArrayList<String> dirList = null;
    private int issuedRequests = 0;

    public SlurpHelperCheckDirFuzz(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id , analyzed, parent);
        this.knownDirMsg = Arrays.asList(
                "Index of", // Apache, nginx ?
                "Directory Listing For", // GlassFish, JBoss, Tomcat
                "Directory Listing", // IIS
                "Parent Directory"
        );
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    private void addDir(String uri) {
        if (this.dirList == null) {
            this.dirList = new ArrayList<String>();
        }
        this.dirList.add(uri);
    }

    private List<String> decomposeUrn(String urn) {
        ArrayList<String> uriList = new ArrayList<String>();

        String [] tmp = urn.split("\\?"); // removing parameters
        urn = tmp[0];

        int endIndex = urn.lastIndexOf("/");

        while (endIndex != -1) {
            String newUri = urn.substring(0, endIndex);
            uriList.add(newUri + "/");
            endIndex = newUri.lastIndexOf("/");
        }
        return uriList;
    }

    private boolean hasKnownDirString(String resAsString) {
        for (String elem : knownDirMsg) {
            if (resAsString.contains(elem))
                return true;
        }
        return false;
    }

    public static IHttpRequestResponse doDirFuzzReq(AbstractSlurpHelperCheck originatingCheck, String urn) {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req;
        byte [] reqAsByte;
        String newRequest;
        String host = originatingCheck.getOriginalRequestResponse().getHttpService().getHost();


        newRequest = "GET " + urn + " HTTP/1.0\r\nHost: ".concat(host).concat("\r\n\r\n");
        reqAsByte = utils.getHelpers().stringToBytes(newRequest);


        if (SlurpHelperBlueprintActiveCheckDirFuzz.getInstance().dirInHistory(host, urn)) {
            return null;
        }
        SlurpHelperBlueprintActiveCheckDirFuzz.getInstance().appendDirToHistory(host, urn);

        req = originatingCheck.doScanRequest(originatingCheck.getOriginalRequestResponse().getHttpService(), reqAsByte);

        return req;
    }

    private void doDirCheck(String urn) {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req = SlurpHelperCheckDirFuzz.doDirFuzzReq(this, urn);
        if (req == null)
            return;
        int code = utils.getCodeFromResponse(req.getResponse());
        issuedRequests++;


        if (code == 200) {
            String resAsString = utils.getHelpers().bytesToString(req.getResponse());
            addDir(utils.getUriFromRequest(req));

            if (hasKnownDirString(resAsString))
                this.markAsPositive();
            else if (getStatus().equals("PENDING"))
                this.markAsUncertain();
        }
    }

    private void doSubsequentChecks(List<String> urnList) {
        SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();
        SlurpHelper helper = SlurpHelper.getInstance();

        AbstractSlurpHelperActiveCheck checkRepos = (AbstractSlurpHelperActiveCheck) factory.newCheck("ACTIVE_DIRFUZZREPO", this.getAnalyzedReqRes(), this, null, this);
        AbstractSlurpHelperActiveCheck checkFiles = (AbstractSlurpHelperActiveCheck) factory.newCheck("ACTIVE_DIRFUZZFILES", this.getAnalyzedReqRes(), this, null, this);
        AbstractSlurpHelperActiveCheck checkBackups = (AbstractSlurpHelperActiveCheck) factory.newCheck("ACTIVE_DIRFUZZBACKUP", this.getAnalyzedReqRes(), this, null, this);
        AbstractSlurpHelperActiveCheck checkWs = (AbstractSlurpHelperActiveCheck) factory.newCheck("ACTIVE_DIRFUZZWS", this.getAnalyzedReqRes(), this, null, this);

        helper.runCheck(checkRepos, urnList);
        helper.runCheck(checkFiles, urnList);
        helper.runCheck(checkBackups, urnList);
        helper.runCheck(checkWs, urnList);
    }

    @Override
    public void doCheck() {
        String urn = SlurpUtils.getInstance().getUrnFromRequest(getOriginalRequestResponse().getRequest());
        List<String> urnList = decomposeUrn(urn);

        for (String elem : urnList) {
            doDirCheck(elem);
        }

        if (issuedRequests == 0)
            this.markForDeletetion();

        if (getStatus().equals("PENDING")) // overkill?
            this.markAsNegative();

        doSubsequentChecks(urnList);
    }

    @Override
    public List<String> getData() {
        return dirList;
    }
}
