package com.slurp.helper.checks.active.dirFuzz.webServices;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckDirFuzzWebServices extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintActiveCheckDirFuzzWebServices instance = new SlurpHelperBlueprintActiveCheckDirFuzzWebServices();

    private SlurpHelperBlueprintActiveCheckDirFuzzWebServices() {
        this.setTitle("Web services file(s)");
        this.setDescription("The web server exposes Web Services description file(s).");
        this.setTypeId("ACTIVE_DIRFUZZWS");
        this.setRunOncePerUrl();
        this.dependsOnCheck(true);
        this.enable();
    }

    public static SlurpHelperBlueprintActiveCheckDirFuzzWebServices getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckDirFuzzWebServices newCheck = new SlurpHelperCheckDirFuzzWebServices(id, analyzed, parent);

        newCheck.setLowSeverity();
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
