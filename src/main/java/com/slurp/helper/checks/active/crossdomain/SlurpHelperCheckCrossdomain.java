package com.slurp.helper.checks.active.crossdomain;

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

public class SlurpHelperCheckCrossdomain extends AbstractSlurpHelperActiveCheck {
    private ArrayList<String> bodyList = null;

    SlurpHelperCheckCrossdomain(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    private void addBody(String comment) {
        if (this.bodyList == null) {
            this.bodyList = new ArrayList<String>();
        }
        this.bodyList.add(comment);
    }

    @Override
    public void doCheck() {
        ArrayList<Integer> codes = new ArrayList<Integer>();
        Integer code;
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req;
        byte [] reqAsByte;
        String newRequest;

        newRequest = "GET /crossdomain.xml HTTP/1.0\r\nHost: ".concat(this.getOriginalRequestResponse().getHttpService().getHost()).concat("\r\n\r\n");
        reqAsByte = utils.getHelpers().stringToBytes(newRequest);

        req = this.doScanRequest(this.getOriginalRequestResponse().getHttpService(), reqAsByte);
        code = utils.getCodeFromResponse(req.getResponse());

        codes.add(code);

        newRequest = "GET /clientaccesspolicy.xml HTTP/1.0\r\nHost: ".concat(this.getOriginalRequestResponse().getHttpService().getHost()).concat("\r\n\r\n");
        reqAsByte = utils.getHelpers().stringToBytes(newRequest);

        req = this.doScanRequest(this.getOriginalRequestResponse().getHttpService(), reqAsByte);
        code = utils.getCodeFromResponse(req.getResponse());

        codes.add(code);

        Integer count = 0;
        for (Integer elem : codes) {
            if (elem == 200) {
                addBody(SlurpUtils.getResponseBody(req));
                count++;
            }
        }

        if (count > 0) {
            this.markAsPositive();
        } else {
            this.markAsNegative();
        }
    }

    @Override
    public List<String> getData() {
        return bodyList;
    }
}
