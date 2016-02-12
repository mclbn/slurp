package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IHttpRequestResponse;
import burp.IRequestInfo;
import burp.IResponseInfo;
import com.slurp.SlurpUtils;

import java.net.URL;

public class SlurpHelperAnalyzedRequestResponse {
    private IHttpRequestResponse originalRequestResponse;
    private byte [] reqAsByte;
    private byte [] resAsByte;
    private String resAsString;
    private String reqAsString;
    private IResponseInfo resInfo;
    private IRequestInfo reqInfo;
    private java.net.URL uri;

    public SlurpHelperAnalyzedRequestResponse(IHttpRequestResponse msg) {
        SlurpUtils utils = SlurpUtils.getInstance();

        this.originalRequestResponse = msg;
        this.reqAsByte = this.originalRequestResponse.getRequest();
        this.resAsByte = this.originalRequestResponse.getResponse();
        this.reqAsString = utils.getHelpers().bytesToString(reqAsByte);
        this.resAsString = utils.getHelpers().bytesToString(resAsByte);
        this.reqInfo = utils.getHelpers().analyzeRequest(originalRequestResponse.getHttpService(),
                originalRequestResponse.getRequest());
        this.resInfo = utils.getHelpers().analyzeResponse(originalRequestResponse.getResponse());
        this.uri = reqInfo.getUrl();
    }

    public IHttpRequestResponse getOriginalRequestResponse() {
        return this.originalRequestResponse;
    }

    public byte[] getReqAsByte() {
        return reqAsByte;
    }

    public byte[] getResAsByte() {
        return resAsByte;
    }

    public String getResAsString() {
        return resAsString;
    }

    public String getReqAsString() {
        return reqAsString;
    }

    public IResponseInfo getResInfo() {
        return resInfo;
    }

    public IRequestInfo getReqInfo() {
        return reqInfo;
    }

    public URL getUri() {
        return uri;
    }
}
