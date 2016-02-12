package com.slurp.helper.checks.passive.comments.linksInComments;

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

public class SlurpHelperPassiveCheckLinksInComments extends AbstractSlurpHelperPassiveCheck {
    private ArrayList<String> linkList = null;

    SlurpHelperPassiveCheckLinksInComments(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    private void addLink(String link) {
        if (this.linkList == null) {
            this.linkList = new ArrayList<String>();
        }
        this.linkList.add(link);
    }

    @Override
    public void doCheck() {

    }

    public void doDataCheck(List<String> data) {
        int linksFound = 0;
        Pattern pattern;
        Matcher m;

        pattern = Pattern.compile("(([a-z]+)://(-\\.)?([^\\s/?\\.#-]+\\.?)+(/[^\\s]*))", Pattern.DOTALL | Pattern.MULTILINE);

        for (String comment : data) {
            m = pattern.matcher(comment);
            while (m.find()) {
                this.addLink(m.group(1));
//            System.out.println(m.group());
                linksFound++;
            }
        }

        if (linksFound > 0) {
            this.markAsPositive();
        }
    }

    @Override
    public List<String> getData() {
        return linkList;
    }
}

