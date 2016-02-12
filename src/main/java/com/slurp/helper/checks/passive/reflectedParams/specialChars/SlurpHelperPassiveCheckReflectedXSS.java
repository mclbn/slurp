package com.slurp.helper.checks.passive.reflectedParams.specialChars;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpHelperPassiveCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlurpHelperPassiveCheckReflectedXSS extends AbstractSlurpHelperPassiveCheck {
    private List<String> knownPatterns;
    private ArrayList<String> paramList = null;

    public SlurpHelperPassiveCheckReflectedXSS(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.knownPatterns = Arrays.asList(
                "<script>",
                "</script>",
                "alert(1)",
                "alert(123)",
                "alert(/xss/)",
                "\"><",
                "<script src=",
                "<img src=",
                "javascript:",
                "onmouseover=",
                "onload=",
                "\\\";",
                "onfocus=",
                "<video><source onerror=",
                "<object data="
        );
    }

    private void addPattern(String link) {
        if (this.paramList == null) {
            this.paramList = new ArrayList<String>();
        }
        this.paramList.add(link);
    }

    @Override
    public void doDataCheck(List<String> data) {
        int specialPatternFound = 0;

        for (String param : data) {
            for (String pattern : this.knownPatterns) {
                if (param.toLowerCase().contains(pattern.toLowerCase())) {
                    this.addPattern(pattern);
                    specialPatternFound++;
                }
            }
        }

        if (specialPatternFound > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public void doCheck() {

    }

    @Override
    public List<String> getData() {
        return this.paramList;
    }
}
