package com.slurp.helper.checks.passive.comments;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IResponseInfo;
import com.slurp.helper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SlurpHelperPassiveCheckComments extends AbstractSlurpHelperPassiveCheck {
    private ArrayList<String> commentList = null;

    SlurpHelperPassiveCheckComments(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    @Override
    public void doDataCheck(List<String> data) {

    }

    private void addComment(String comment) {
        if (this.commentList == null) {
            this.commentList = new ArrayList<String>();
        }
        this.commentList.add(comment);
    }

    private int doCheckForHTML() {
        String response = this.getAnalyzedReqRes().getResAsString();
        Pattern pattern;
        Matcher m;
        int commentsFound = 0;

        pattern = Pattern.compile("(<!--.*?-->)", Pattern.DOTALL|Pattern.MULTILINE);
        m = pattern.matcher(response);

        while (m.find()) {
            this.addComment(m.group(1));
            commentsFound++;
        }
        return commentsFound;
    }

    private int doCheckForJS() {
        String response = this.getAnalyzedReqRes().getResAsString();
        Pattern pattern;
        Matcher m;
        int commentsFound = 0;

        pattern = Pattern.compile("(/\\*.*?\\*/|^//.*)$", Pattern.DOTALL|Pattern.MULTILINE);
        m = pattern.matcher(response);

        while (m.find()) {
            this.addComment(m.group(1));
            commentsFound++;
        }
        return commentsFound;
    }

    private int doCheckForCSS() {
        String response = this.getAnalyzedReqRes().getResAsString();
        Pattern pattern;
        Matcher m;
        int commentsFound = 0;

        pattern = Pattern.compile("(/\\*.*?\\*/)", Pattern.DOTALL|Pattern.MULTILINE);
        m = pattern.matcher(response);

        while (m.find()) {
            this.addComment(m.group(1));
            commentsFound++;
        }
        return commentsFound;
    }

    @Override
    public void doCheck() {
        int commentsFound = 0;
        IResponseInfo resInfo = this.getAnalyzedReqRes().getResInfo();
        String MIMEType = resInfo.getInferredMimeType();

        if (MIMEType.equals("HTML") || MIMEType.equals("XML")) {
            commentsFound += doCheckForHTML();
        } else if (MIMEType.equals("CSS")) {
            commentsFound += doCheckForCSS();
        } else if (MIMEType.equals("script")) {
            commentsFound += doCheckForJS();
        }

        if (commentsFound > 0) {
            ArrayList<String> data = new ArrayList<String>();
            for (String entry : this.commentList) {
                data.add(entry);
            }
            this.markAsPositive();

            //doing subchecks here
            SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();
            SlurpHelper helper = SlurpHelper.getInstance();
            AbstractSlurpHelperPassiveCheck checkLinks = (AbstractSlurpHelperPassiveCheck) factory.newCheck("PASSIVE_COMMENT_LINKS", this.getAnalyzedReqRes(), this, null, this);
            AbstractSlurpHelperPassiveCheck checkIp = (AbstractSlurpHelperPassiveCheck) factory.newCheck("PASSIVE_COMMENT_IP", this.getAnalyzedReqRes(), this, null, this);

            helper.runCheck(checkLinks, data);
            helper.runCheck(checkIp, data);
        }
    }

    @Override
    public List<String> getData() {
        return commentList;
    }
}
