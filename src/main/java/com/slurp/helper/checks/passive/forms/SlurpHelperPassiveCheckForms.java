package com.slurp.helper.checks.passive.forms;

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

public class SlurpHelperPassiveCheckForms extends AbstractSlurpHelperPassiveCheck {
    private ArrayList<String> formList = null;

    public SlurpHelperPassiveCheckForms(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    private void addForm(String form) {
        if (this.formList == null) {
            this.formList = new ArrayList<String>();
        }
        this.formList.add(form);
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    @Override
    public void doCheck() {
        int hiddenFieldsFound = 0;
        String response = this.getAnalyzedReqRes().getResAsString();
        Pattern pattern;
        Matcher m;

        pattern = Pattern.compile("(<form.*?/form>)", Pattern.CASE_INSENSITIVE|Pattern.DOTALL|Pattern.MULTILINE);
        m = pattern.matcher(response);

        while (m.find()) {
            this.addForm(m.group(1));
            hiddenFieldsFound++;
        }

        if (hiddenFieldsFound > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return formList;
    }
}
