package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IHttpRequestResponse;

public class SlurpHelperResultUrl {
    private AbstractSlurpHelperCheck relatedCheck;
    private IHttpRequestResponse URI;

    public SlurpHelperResultUrl(AbstractSlurpHelperCheck check, IHttpRequestResponse newURI) {
        relatedCheck = check;
        URI = newURI;
    }

    public IHttpRequestResponse getHttpRequestResponse() {
        return URI;
    }

    public AbstractSlurpHelperCheck getRelatedCheck() {
        return relatedCheck;
    }
}
