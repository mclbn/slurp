package com.slurp.helper.checks.passive.localIP;

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

public class SlurpHelperPassiveCheckLocalIP extends AbstractSlurpHelperPassiveCheck {
    private ArrayList<String> ipList = null;

    SlurpHelperPassiveCheckLocalIP(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) {
        doCheck();
    }

    private void addIP(String ip) {
        if (this.ipList == null) {
            this.ipList = new ArrayList<String>();
        }
        this.ipList.add(ip);
    }

    @Override
    public void doCheck() {
        int ipFound = 0;
        String response = this.getAnalyzedReqRes().getResAsString();
        Pattern pattern, localPattern;
        Matcher m;

        pattern = Pattern.compile("((([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5]))"
                , Pattern.DOTALL | Pattern.MULTILINE); // ipv4
        localPattern = null;
        m = pattern.matcher(response);

        while (m.find()) {
            if (localPattern == null)
                localPattern = Pattern.compile("((127\\.0\\.0\\.1)|(10\\..*)|(172\\.1[6-9]\\..*)|(172\\.2[0-9]\\..*)|(172\\.3[0-1]\\..*)|(192\\.168\\..*))"
                        , Pattern.DOTALL | Pattern.MULTILINE);
            Matcher tmpM = localPattern.matcher(m.group(1));
            while (tmpM.find()) {
                this.addIP(tmpM.group(1));
                ipFound++;
            }
        }

        if (ipFound > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return this.ipList;
    }
}
