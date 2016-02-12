package com.slurp.helper.checks.passive.comments.IPinComments;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveIPInComments extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintPassiveIPInComments instance = new SlurpHelperBlueprintPassiveIPInComments();

    private SlurpHelperBlueprintPassiveIPInComments() {
        this.setTitle("IP address(es) in comment(s)");
        this.setDescription("The web page presents IP address(es) in comment(s).");
        this.setTypeId("PASSIVE_COMMENT_IP");
        this.setRunOncePerUrl();
        this.dependsOnCheck(true);
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveIPInComments getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckIPInComments newCheck = new SlurpHelperPassiveCheckIPInComments(id, analyzed, parent);
        newCheck.setInfoSeverity();
        newCheck.setTitle(this.getTitle());
        newCheck.setDescription(this.getDescription());
        newCheck.setTypeId(this.getTypeId());
        newCheck.setBlueprint(instance);
        return newCheck;
    }

    @Override
    public List<JMenuItem> getContextMenu() {
        return null;
    }

    @Override
    public JPanel getOptionsMenu() {
        return null;
    }

    @Override
    public void resetForHost(String host) {

    }
}
