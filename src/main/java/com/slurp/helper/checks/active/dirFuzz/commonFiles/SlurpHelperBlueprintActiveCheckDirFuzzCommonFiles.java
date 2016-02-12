package com.slurp.helper.checks.active.dirFuzz.commonFiles;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckDirFuzzCommonFiles extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintActiveCheckDirFuzzCommonFiles instance = new SlurpHelperBlueprintActiveCheckDirFuzzCommonFiles();

    private SlurpHelperBlueprintActiveCheckDirFuzzCommonFiles() {
        this.setTitle("Common file(s)");
        this.setDescription("The web server exposes interesting file(s).");
        this.setTypeId("ACTIVE_DIRFUZZFILES");
        this.setRunOncePerUrl();
        this.dependsOnCheck(true);
        this.disable();
    }

    public static SlurpHelperBlueprintActiveCheckDirFuzzCommonFiles getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckDirFuzzCommonFiles newCheck = new SlurpHelperCheckDirFuzzCommonFiles(id, analyzed, parent);

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
