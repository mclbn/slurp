package com.slurp.helper.checks.passive.cookies;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpHelperPassiveCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlurpHelperPassiveCheckCookies extends AbstractSlurpHelperPassiveCheck {
    private ArrayList<String> cookieList = null;

    SlurpHelperPassiveCheckCookies(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) {
        doCheck();
    }

    private void addCookie(String cookie) {
        if (this.cookieList == null) {
            this.cookieList = new ArrayList<String>();
        }
        this.cookieList.add(cookie);
    }

    @Override
    public void doCheck() {
        boolean isHttps = false;
        Pattern pattern;
        Matcher m;

        List<String> headers = this.getAnalyzedReqRes().getResInfo().getHeaders();
        if (this.getOriginalRequestResponse().getHttpService().getProtocol().toLowerCase().equals("https"))
            isHttps = true;

        this.markAsNegative();
        for (String header : headers) {
            boolean toAdd = false;

            if (header.toLowerCase().contains("set-cookie:")) {

                pattern = Pattern.compile(".*;\\s*HttpOnly.*", Pattern.CASE_INSENSITIVE);
                m = pattern.matcher(header);
                if (!m.find()) {
                    toAdd = true;
                    this.markAsPositive();
                }

                if (isHttps) {
                    pattern = Pattern.compile(".*;\\s*secure.*", Pattern.CASE_INSENSITIVE);
                    m = pattern.matcher(header);
                    if (!m.find()) {
                        toAdd = true;
                        this.markAsPositive();
                    }
                }
            }
            if (toAdd)
                addCookie(header);
        }
    }

    @Override
    public List<String> getData() {
        return cookieList;
    }
}
