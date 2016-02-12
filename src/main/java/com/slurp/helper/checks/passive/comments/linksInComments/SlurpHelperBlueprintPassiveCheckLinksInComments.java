package com.slurp.helper.checks.passive.comments.linksInComments;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckLinksInComments extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintPassiveCheckLinksInComments instance = new SlurpHelperBlueprintPassiveCheckLinksInComments();

    private SlurpHelperBlueprintPassiveCheckLinksInComments() {
        this.setTitle("Link(s) in comment(s)");
        this.setDescription("The web page presents link(s) in comment(s).");
        this.setTypeId("PASSIVE_COMMENT_LINKS");
        this.setRunOncePerUrl();
        this.dependsOnCheck(true);
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckLinksInComments getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckLinksInComments newCHeck = new SlurpHelperPassiveCheckLinksInComments(id, analyzed, parent);
        newCHeck.setInfoSeverity();
        newCHeck.setTitle(this.getTitle());
        newCHeck.setDescription(this.getDescription());
        newCHeck.setTypeId(this.getTypeId());
        newCHeck.setBlueprint(instance);
        return newCHeck;
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
