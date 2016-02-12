package com.slurp.helper.checks.passive.headers;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IResponseInfo;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpHelperPassiveCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlurpHelperPassiveCheckHeaders extends AbstractSlurpHelperPassiveCheck {
    private List<String> basicHeaders;
    private ArrayList<String> headerList = null;

    SlurpHelperPassiveCheckHeaders(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.basicHeaders = Arrays.asList(
                "Age",
                "Allow",
                "Cache-Control",
                "Connection",
                "Content-Encoding",
                "Content-Language",
                "Content-Length",
                "Content-Location",
                "Content-MD5",
                "Content-Disposition",
                "Content-Range",
                "Content-Type",
                "Date",
//                "ETag", // kept because it might in some case be useful to note it
                "Expires",
                "Keep-Alive",
                "Last-Modified",
//                "Link", // useful too
                "Location",
//                "P3P", // idem
                "Pragma",
                "Proxy-Authenticate",
                "Refresh",
                "Retry-After",
//                "Server", // we WANT it
                "Set-Cookie",
                "Status",
                "Strict-Transport-Security",
                "Trailer",
                "Transfer-Encoding",
//                "Upgrade", // might be an interesting behaviour
                "Vary",
//                "Via", // might give useful info
//                "Warning", // idem
                "WWW-Authenticate",
                "X-Frame-Options");
    }

    @Override
    public void doDataCheck(List<String> data) {
        doCheck();
    }

    private void addHeader(String comment) {
        if (this.headerList == null) {
            this.headerList = new ArrayList<String>();
        }
        this.headerList.add(comment);
    }

    @Override
    public void doCheck() {
        int unknownHeaders = 0;
        boolean first = true;
        IResponseInfo analyzedResponse = this.getAnalyzedReqRes().getResInfo();
        List<String> headers = analyzedResponse.getHeaders();

        for (String header : headers) {
            if (first) {
                first = false;
                continue; // we skip the HTTP response code line
            }
            String[] parts = header.split(":");
            if (this.basicHeaders.contains(parts[0])) {
                continue;
            } else {
                addHeader(header);
                unknownHeaders++;
            }
        }

        if (unknownHeaders > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return headerList;
    }
}
