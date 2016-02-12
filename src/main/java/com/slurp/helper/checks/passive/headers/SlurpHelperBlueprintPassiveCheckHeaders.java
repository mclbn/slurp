package com.slurp.helper.checks.passive.headers;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckHeaders extends AbstractSlurpCheckBlueprint {
    public static SlurpHelperBlueprintPassiveCheckHeaders instance = new SlurpHelperBlueprintPassiveCheckHeaders();

    private SlurpHelperBlueprintPassiveCheckHeaders() {
        this.setTitle("Interesting header(s)");
        this.setDescription("The web server presents interesting header(s).");
        this.setTypeId("PASSIVE_HEADERS");
        this.setRunOncePerUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckHeaders getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckHeaders newCheck = new SlurpHelperPassiveCheckHeaders(id, analyzed, parent);

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
