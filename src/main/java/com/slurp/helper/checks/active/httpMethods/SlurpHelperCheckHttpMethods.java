package com.slurp.helper.checks.active.httpMethods;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IHttpRequestResponse;
import com.slurp.SlurpUtils;
import com.slurp.helper.AbstractSlurpHelperActiveCheck;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.List;

public class SlurpHelperCheckHttpMethods extends AbstractSlurpHelperActiveCheck {
    SlurpHelperCheckHttpMethods(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    private IHttpRequestResponse issueOptions() {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req;
        byte [] reqAsByte;
        String newRequest;

        newRequest = "OPTIONS / HTTP/1.0\r\nHost: " + this.getOriginalRequestResponse().getHttpService().getHost() + "\r\n\r\n";
        reqAsByte = utils.getHelpers().stringToBytes(newRequest);
        req = this.doScanRequest(this.getOriginalRequestResponse().getHttpService(), reqAsByte);
        return req;
    }

    private IHttpRequestResponse issueTrace() {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req;
        byte [] reqAsByte;
        String newRequest;
        String cookie = utils.generateHash(10);

        newRequest = "TRACE / HTTP/1.0\r\nHost: " + this.getOriginalRequestResponse().getHttpService().getHost() + "\r\n" + "Cookie: " + cookie + "\r\n\r\n";
        reqAsByte = utils.getHelpers().stringToBytes(newRequest);
        req = this.doScanRequest(this.getOriginalRequestResponse().getHttpService(), reqAsByte);
        return req;
    }

    private IHttpRequestResponse issuePut() {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req;
        byte [] reqAsByte;
        String newRequest;
        String dataName = utils.generateHash(10);
        String data = utils.generateHash(10);

        newRequest = "PUT /" + dataName + ".txt HTTP/1.0\r\nHost: " + this.getOriginalRequestResponse().getHttpService().getHost() + "\r\n" + "Content-Length: 10\r\n\r\n" + data +  "\r\n\r\n";
        reqAsByte = utils.getHelpers().stringToBytes(newRequest);
        req = this.doScanRequest(this.getOriginalRequestResponse().getHttpService(), reqAsByte);
        return req;
    }

    private IHttpRequestResponse issueDebug() {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req;
        byte [] reqAsByte;
        String newRequest;

        newRequest = "DEBUG /Default.aspx HTTP/1.0\r\nHost: "
                + this.getOriginalRequestResponse().getHttpService().getHost() + "\r\n"
                + "Command: start-debug" + "\r\n\r\n";
        reqAsByte = utils.getHelpers().stringToBytes(newRequest);
        req = this.doScanRequest(this.getOriginalRequestResponse().getHttpService(), reqAsByte);
        return req;
    }

    private IHttpRequestResponse issueUnknown() {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req;
        byte [] reqAsByte;
        String newRequest;

        newRequest = "SLURP / HTTP/1.0\r\nHost: " + this.getOriginalRequestResponse().getHttpService().getHost() + "\r\n\r\n";
        reqAsByte = utils.getHelpers().stringToBytes(newRequest);
        req = this.doScanRequest(this.getOriginalRequestResponse().getHttpService(), reqAsByte);
        return req;
    }

    // todo : Webdav detection

    @Override
    public void doCheck() {
        SlurpUtils utils = SlurpUtils.getInstance();
        IHttpRequestResponse req;
        int code;

        this.markAsNegative();

        this.issueOptions();

        req  = this.issueTrace();
        code = utils.getCodeFromResponse(req.getResponse());

        if (code == 200) {
            this.markAsPositive();
        }

        req  = this.issueDebug();
        code = utils.getCodeFromResponse(req.getResponse());

        if (code == 200) {
            this.markAsPositive();
        }

        req  = this.issuePut();
        code = utils.getCodeFromResponse(req.getResponse());

        if (code == 200) {
            this.markAsPositive();
        }

        req  = this.issueUnknown();
        code = utils.getCodeFromResponse(req.getResponse());

        if (code == 200) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return null;
    }
}
