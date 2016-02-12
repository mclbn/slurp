package com.slurp.helper.checks.passive.comments.IPinComments;

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

public class SlurpHelperPassiveCheckIPInComments extends AbstractSlurpHelperPassiveCheck {
    private ArrayList<String> ipList = null;

    SlurpHelperPassiveCheckIPInComments(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    private void addIP(String ip) {
        if (this.ipList == null) {
            this.ipList = new ArrayList<String>();
        }
        this.ipList.add(ip);
    }

    public void doDataCheck(List<String> data) {
        int ipFound = 0;
        Pattern pattern;
        Matcher m;

        pattern = Pattern.compile("((([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5]))"
                , Pattern.DOTALL | Pattern.MULTILINE); // ipv4
//        pattern = Pattern.compile("([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}", Pattern.DOTALL | Pattern.MULTILINE); // ipv6

        for (String comment : data) {
            m = pattern.matcher(comment);
            while (m.find()) {
                this.addIP(m.group(1));
//            System.out.println(m.group());
                ipFound++;
            }
        }

        if (ipFound > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public void doCheck() {

    }

    @Override
    public List<String> getData() {
        return ipList;
    }
}
