package com.slurp.helper.checks.passive.ajaxForms;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.SlurpUtils;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpHelperPassiveCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SlurpHelperPassiveCheckAjaxForms extends AbstractSlurpHelperPassiveCheck {
    private List<String> knownKeywords;
    private ArrayList<String> keywordList = null;

    public SlurpHelperPassiveCheckAjaxForms(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        this.knownKeywords = Arrays.asList(
                "$.get(",
                "$.Get(",
                "$.post(",
                "$.Post(",
                "$.ajax(",
                "$.Ajax("
        );
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    private void addKeyword(String comment) {
        if (this.keywordList == null) {
            this.keywordList = new ArrayList<String>();
        }
        this.keywordList.add(comment);
    }

    @Override
    public void doCheck() {
        int foundKeywords = 0;
        String resAsString = SlurpUtils.getInstance().getHelpers().bytesToString(getOriginalRequestResponse().getResponse());

        for (String keyword : knownKeywords) {
            if (resAsString.contains(keyword)) {
                foundKeywords++;
                addKeyword(keyword);
            }
        }

        if (foundKeywords > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return keywordList;
    }
}
