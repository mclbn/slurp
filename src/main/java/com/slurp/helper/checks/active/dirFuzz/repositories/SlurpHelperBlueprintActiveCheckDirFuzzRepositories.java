package com.slurp.helper.checks.active.dirFuzz.repositories;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckDirFuzzRepositories extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintActiveCheckDirFuzzRepositories instance = new SlurpHelperBlueprintActiveCheckDirFuzzRepositories();

    private SlurpHelperBlueprintActiveCheckDirFuzzRepositories() {
        this.setTitle("Repository file(s)");
        this.setDescription("The web server exposes repository file(s).");
        this.setTypeId("ACTIVE_DIRFUZZREPO");
        this.setRunOncePerUrl();
        this.dependsOnCheck(true);
        this.enable();
    }

    public static SlurpHelperBlueprintActiveCheckDirFuzzRepositories getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckDirFuzzRepositories newCheck = new SlurpHelperCheckDirFuzzRepositories(id, analyzed, parent);

        newCheck.setMediumSeverity();
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
