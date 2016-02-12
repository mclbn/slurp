package com.slurp.helper.checks.passive.errors;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckErrors extends AbstractSlurpCheckBlueprint {
    public static SlurpHelperBlueprintPassiveCheckErrors instance = new SlurpHelperBlueprintPassiveCheckErrors();

    private SlurpHelperBlueprintPassiveCheckErrors() {
        this.setTitle("Interesting error(s)");
        this.setDescription("The web server presents interesting error message(s).");
        this.setTypeId("PASSIVE_ERRORS");
        this.setRunOnAllUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckErrors getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckErrors newCheck = new SlurpHelperPassiveCheckErrors(id, analyzed, parent);

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
