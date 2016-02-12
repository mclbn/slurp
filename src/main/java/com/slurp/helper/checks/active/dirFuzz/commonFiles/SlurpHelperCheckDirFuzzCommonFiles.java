package com.slurp.helper.checks.active.dirFuzz.commonFiles;

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

public class SlurpHelperCheckDirFuzzCommonFiles extends AbstractSlurpHelperActiveCheck {
    private List<String> knownFiles;
    private int issuedRequests = 0;
    private ArrayList<String> foundList = null;

    public SlurpHelperCheckDirFuzzCommonFiles(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.knownFiles = Arrays.asList(
                "cron.sh",
                "index.php.sample",
                "site.tar.gz",
                "site.tgz",
                "site.zip",
                "site.7z",
                "RELEASE_NOTES.txt",
                "LICENSE.txt",
                "LICENSE",
                "CHANGELOG.txt",
                "CHANGELOG",
                "UPGRADE.txt",
                "UPGRADE",
                "INSTALL.txt",
                "INSTALL",
                "README.txt",
                "README.md",
                "README",
                "INSTALL.mysql.txt",
                "INSTALL.sqlite.txt",
                "INSTALL.pgsql.txt",
                "MAINTAINERS.txt",
                "UPGRADE.txt",
                "Thumbs.db"
        );
    }

    private void addFound(String uri) {
        if (this.foundList == null) {
            this.foundList = new ArrayList<String>();
        }
        this.foundList.add(uri);
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
            this.markAsPositive();
        }
    }

    @Override
    public void doDataCheck(List<String> data) {
        for (String urn : data) {
            for (String repoUrn : knownFiles) {
                doFuzzReq(urn + repoUrn);
            }
        }

        if (issuedRequests == 0)
            this.markForDeletetion();

        if (getStatus().equals("PENDING"))
            this.markAsNegative();
    }

    @Override
    public void doCheck() {

    }

    @Override
    public List<String> getData() {
        return foundList;
    }
}
