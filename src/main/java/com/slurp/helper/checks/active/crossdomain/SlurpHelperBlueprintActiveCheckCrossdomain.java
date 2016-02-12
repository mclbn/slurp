package com.slurp.helper.checks.active.crossdomain;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckCrossdomain extends AbstractSlurpCheckBlueprint {
    public static SlurpHelperBlueprintActiveCheckCrossdomain instance = new SlurpHelperBlueprintActiveCheckCrossdomain();

    private SlurpHelperBlueprintActiveCheckCrossdomain() {
        this.setTitle("Crossdomain settings file");
        this.setDescription("The web server contains a crossdomain settings file.");
        this.setTypeId("ACTIVE_CROSSDOMAIN");
        this.setRunOncePerHost();
        this.enable();
    }

    public static SlurpHelperBlueprintActiveCheckCrossdomain getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckCrossdomain newCheck = new SlurpHelperCheckCrossdomain(id, analyzed, parent);

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
