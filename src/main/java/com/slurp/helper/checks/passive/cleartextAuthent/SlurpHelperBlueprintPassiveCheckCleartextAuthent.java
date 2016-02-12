package com.slurp.helper.checks.passive.cleartextAuthent;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckCleartextAuthent extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintPassiveCheckCleartextAuthent instance = new SlurpHelperBlueprintPassiveCheckCleartextAuthent();

    private SlurpHelperBlueprintPassiveCheckCleartextAuthent() {
        this.setTitle("Cleartext authentication");
        this.setDescription("The web server supports cleartext authentication.");
        this.setTypeId("PASSIVE_CLEARAUTHENT");
        this.setRunOncePerUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckCleartextAuthent getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckCleartextAuthent newCheck = new SlurpHelperPassiveCheckCleartextAuthent(id, analyzed, parent);

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
