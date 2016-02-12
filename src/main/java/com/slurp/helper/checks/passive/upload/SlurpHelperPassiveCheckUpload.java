package com.slurp.helper.checks.passive.upload;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IRequestInfo;
import burp.IResponseInfo;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpHelperPassiveCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlurpHelperPassiveCheckUpload extends AbstractSlurpHelperPassiveCheck {
    private List<String> listUploadKeywords;
    private List<String> listUploadUrn;
    private ArrayList<String> foundInfo;

    SlurpHelperPassiveCheckUpload(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.listUploadKeywords = Arrays.asList(
                "upload",
                "ckeditor"
        );
        this.listUploadUrn = Arrays.asList(
                "upload.htm",
                "upload.html",
                "upload.php",
                "upload.php4",
                "upload.php5",
                "upload.php3",
                "upload.asp",
                "upload.aspx",
                "upload.jsp",
                "upload.jsf",
                "upload.do",
                "upload.cgi",
                "upload.sh",
                "upload.sh",
                "wp-content/plugins/ckeditor-for-wordpress/includes/upload.php"
        );
    }

    private void addInfo(String info) {
        if (this.foundInfo == null) {
            this.foundInfo = new ArrayList<String>();
        }
        this.foundInfo.add(info);
    }

    @Override
    public void doDataCheck(List<String> data) {
        doCheck();
    }

    private int checkRequestUrl(java.net.URL url) {
        for (String keyword : this.listUploadKeywords) {
            if (url.toString().toLowerCase().contains(keyword)) {
                addInfo(keyword);
            }
        }
        return 0;
    }

    private int checkResponseContent(String response) {
        for (String urn : this.listUploadUrn) {
            if (response.contains(urn)) {
                addInfo(urn);
            }
        }
        return 0;
    }

    @Override
    public void doCheck() {
        int uploadFound = 0;
        IRequestInfo reqInfo = this.getAnalyzedReqRes().getReqInfo();
        IResponseInfo resInfo = this.getAnalyzedReqRes().getResInfo();
        String resAsString = this.getAnalyzedReqRes().getResAsString();

        if (reqInfo.getMethod().toLowerCase().equals("post")) // on check l'URI que sur les POST
            uploadFound += checkRequestUrl(reqInfo.getUrl());
        if (resInfo.getStatusCode() == 200) {
            String mimeType = resInfo.getInferredMimeType();
            if (mimeType.equals("HTML")
                    || mimeType.equals("XML")
                    || mimeType.toLowerCase().equals("script")
                    || mimeType.equals("CSS"))
                uploadFound += checkResponseContent(resAsString);// maybe we could check other mime-types...
        }
        if (uploadFound > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return foundInfo;
    }
}
