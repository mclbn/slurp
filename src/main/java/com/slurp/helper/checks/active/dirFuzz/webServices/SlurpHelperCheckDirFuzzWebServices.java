package com.slurp.helper.checks.active.dirFuzz.webServices;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IHttpRequestResponse;
import com.slurp.SlurpUtils;
import com.slurp.helper.AbstractSlurpHelperActiveCheck;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;
import com.slurp.helper.checks.active.dirFuzz.SlurpHelperCheckDirFuzz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlurpHelperCheckDirFuzzWebServices extends AbstractSlurpHelperActiveCheck {
    private List<String> knownWSFiles;
    private int issuedRequests = 0;
    private ArrayList<String> foundList = null;

    public SlurpHelperCheckDirFuzzWebServices(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.knownWSFiles = Arrays.asList(
                "?wsdl",
                "?WSDL"
        );
    }

    private void addFound(String urn) {
        if (this.foundList == null) {
            this.foundList = new ArrayList<String>();
        }
        this.foundList.add(urn);
    }

    private void doFuzzReq(String urn) {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req = SlurpHelperCheckDirFuzz.doDirFuzzReq(this, urn);
        if (req == null)
            return;
        issuedRequests++;
        int code = utils.getCodeFromResponse(req.getResponse());

        if (code == 200) {
            addFound(utils.getUriFromRequest(req));

            if (getAnalyzedReqRes().getResAsString().toLowerCase().contains("wsdl")) {
                this.markAsPositive();
            } else
                this.markAsUncertain();
        }
    }

    @Override
    public void doDataCheck(List<String> data) {
        for (String urn : data) {
            for (String repoUrn : knownWSFiles) {
                doFuzzReq(urn + repoUrn);
            }
        }

        if (issuedRequests == 0) {
            this.markForDeletetion();
        }

        if (getStatus().equals("PENDING")) {
            this.markAsNegative();
        }
    }

    @Override
    public void doCheck() {

    }

    @Override
    public List<String> getData() {
        return foundList;
    }
}
