package com.slurp.helper.checks.active.verbTamper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckVerbTamper extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintActiveCheckVerbTamper instance = new SlurpHelperBlueprintActiveCheckVerbTamper();

    private SlurpHelperBlueprintActiveCheckVerbTamper() {
        this.setTitle("Authentication bypass (verb tampering)");
        this.setDescription("The web server contains a robots.txt file.");
        this.setTypeId("ACTIVE_VERBBYPASS");
        this.setRunOncePerHost();
        this.enable();
    }

    public static SlurpHelperBlueprintActiveCheckVerbTamper getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckVerbTamper newCheck = new SlurpHelperCheckVerbTamper(id, analyzed, parent);

        newCheck.setHighSeverity();
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
