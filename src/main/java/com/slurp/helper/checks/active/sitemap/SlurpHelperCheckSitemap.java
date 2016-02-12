package com.slurp.helper.checks.active.sitemap;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IHttpRequestResponse;
import com.slurp.SlurpUtils;
import com.slurp.helper.AbstractSlurpHelperActiveCheck;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.ArrayList;
import java.util.List;

public class SlurpHelperCheckSitemap extends AbstractSlurpHelperActiveCheck {
    private String data = null;

    public SlurpHelperCheckSitemap(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    @Override
    public void doCheck() {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req;
        byte [] reqAsByte;
        String newRequest;

        newRequest = "GET /sitemap.xml HTTP/1.0\r\nHost: ".concat(this.getOriginalRequestResponse().getHttpService().getHost()).concat("\r\n\r\n");
        reqAsByte = utils.getHelpers().stringToBytes(newRequest);

        req = this.doScanRequest(this.getOriginalRequestResponse().getHttpService(), reqAsByte);
        int code = utils.getCodeFromResponse(req.getResponse());

        if (code == 200) {
            data = SlurpUtils.getResponseBody(req);
            this.markAsPositive();
        } else {
            this.markAsNegative();
        }
    }

    @Override
    public List<String> getData() {
        if (data != null) {
            ArrayList<String> dataList = new ArrayList<String>();
            dataList.add(data);

            return dataList;
        } else
            return null;
    }
}
