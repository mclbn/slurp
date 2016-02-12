package com.slurp.helper.checks.passive.hiddenFields;

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

public class SlurpHelperPassiveCheckHiddenFields extends AbstractSlurpHelperPassiveCheck {
    private ArrayList<String> hiddenList = null;

    public SlurpHelperPassiveCheckHiddenFields(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) { // not used ATM
        int fieldsFound = 0;
        Pattern pattern;
        Matcher m;

        pattern = Pattern.compile("(<input.*?type=('|\")*?hidden('|\")*?.*?>)", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE); // ipv4

        for (String comment : data) {
            m = pattern.matcher(comment);
            while (m.find()) {
                this.addField(m.group(1));
                fieldsFound++;
            }
        }

        if (fieldsFound > 0) {
            this.markAsPositive();
        }
    }

    private void addField(String field) {
        if (this.hiddenList == null) {
            this.hiddenList = new ArrayList<String>();
        }
        this.hiddenList.add(field);
    }

    @Override
    public void doCheck() {
        int hiddenFieldsFound = 0;
        String response = this.getAnalyzedReqRes().getResAsString();
        Pattern pattern;
        Matcher m;

        pattern = Pattern.compile("(<input.*?type=('|\")*?hidden('|\")*?.*?>)", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
        m = pattern.matcher(response);

        while (m.find()) {
            this.addField(m.group(1));
            hiddenFieldsFound++;
        }

        if (hiddenFieldsFound > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return this.hiddenList;
    }
}
