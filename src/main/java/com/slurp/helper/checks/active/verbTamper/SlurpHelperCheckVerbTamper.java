package com.slurp.helper.checks.active.verbTamper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IHttpRequestResponse;
import burp.IHttpService;
import burp.IRequestInfo;
import burp.IResponseInfo;
import com.slurp.SlurpUtils;
import com.slurp.helper.AbstractSlurpHelperActiveCheck;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.Arrays;
import java.util.List;

public class SlurpHelperCheckVerbTamper extends AbstractSlurpHelperActiveCheck {
    private List<String> verbList;

    SlurpHelperCheckVerbTamper(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.verbList = Arrays.asList(
                "HEAD",
                "GET",
                "POST",
                "head",
                "get",
                "post",
                "TRACE");
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    @Override
    public void doCheck() {
        boolean gotBypass = false;
        boolean unsureBypass = false;
        SlurpUtils utils = SlurpUtils.getInstance();
        IResponseInfo resInfo = getAnalyzedReqRes().getResInfo();

        if (resInfo.getStatusCode() != 401) {
            this.markAsNegative();
            return; // only test URI that require authentication
        }

        IHttpService oldSrv = this.getOriginalRequestResponse().getHttpService();
        IRequestInfo reqInfo = getAnalyzedReqRes().getReqInfo();
        String oldReqAsString = getAnalyzedReqRes().getReqAsString();
        String oldMethod = reqInfo.getMethod();
        IHttpService newSrv = utils.getHelpers().buildHttpService(oldSrv.getHost(), oldSrv.getPort(), oldSrv.getProtocol());

        for (String newMethod : this.verbList) {
            if (newMethod.equals(oldMethod))
                continue; // skipping the method that returned 401, duh
            else {
                String newReqAsString = oldReqAsString.replaceFirst(oldMethod, newMethod);
                byte[] newReq = utils.getHelpers().stringToBytes(newReqAsString);
                IHttpRequestResponse reqRes = this.doScanRequest(newSrv, newReq);
                int code = utils.getCodeFromResponse(reqRes.getResponse());

                if (code != 401) {
                    if (code == 200) {
                        gotBypass = true;
                    } else {
                        unsureBypass = true;
                    }
                }
            }
        }

        if (gotBypass)
            this.markAsPositive();
        else if (unsureBypass)
            this.markAsUncertain();
        else
            this.markAsNegative();
    }

    @Override
    public List<String> getData() {
        return null;
    }
}
