package com.slurp.helper.checks.passive.customRegex;

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

public class SlurpHelperPassiveCheckCustomRegex extends AbstractSlurpHelperPassiveCheck {
    private ArrayList<String> matchList = null;
    private boolean matchRequest = false;
    private String regex = "http://.*/*";

    public SlurpHelperPassiveCheckCustomRegex(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) {
        doCheck();
    }

    private void addMatch(String match) {
        if (this.matchList == null) {
            this.matchList = new ArrayList<String>();
        }
        this.matchList.add(match);
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public void matchRequest() {
        this.matchRequest = true;
    }

    public void doNotMatchRequest() {
        this.matchRequest = true;
    }

    @Override
    public void doCheck() {
        String patternString = ".*?(" + regex + ").*?";

        int matches = 0;
        String response = this.getAnalyzedReqRes().getResAsString();
        Pattern pattern;
        Matcher m;

        pattern = Pattern.compile(patternString, Pattern.DOTALL|Pattern.MULTILINE);
        if (matchRequest) {
            String request = this.getAnalyzedReqRes().getReqAsString();

            m = pattern.matcher(request);

            while (m.find()) {
                this.addMatch(m.group(1));
                matches++;
            }
        }

        m = pattern.matcher(response);

        while (m.find()) {
            this.addMatch(m.group(1));
            matches++;
        }

        if (matches > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return this.matchList;
    }
}
