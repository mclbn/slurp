package com.slurp.helper.checks.active.dirFuzz.backups;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckDirFuzzBackups extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintActiveCheckDirFuzzBackups instance = new SlurpHelperBlueprintActiveCheckDirFuzzBackups();

    private SlurpHelperBlueprintActiveCheckDirFuzzBackups() {
        this.setTitle("Backup file(s)");
        this.setDescription("The web server exposes backup file(s).");
        this.setTypeId("ACTIVE_DIRFUZZBACKUP");
        this.setRunOncePerUrl();
        this.dependsOnCheck(true);
        this.disable();
    }

    public static SlurpHelperBlueprintActiveCheckDirFuzzBackups getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckDirFuzzBackups newCheck = new SlurpHelperCheckDirFuzzBackups(id, analyzed, parent);

        newCheck.setMediumSeverity();
        newCheck.setTitle(this.getTitle());
        newCheck.setDescription(this.getDescription());
        newCheck.setTypeId(this.getTypeId());
        newCheck.lightMode();
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
