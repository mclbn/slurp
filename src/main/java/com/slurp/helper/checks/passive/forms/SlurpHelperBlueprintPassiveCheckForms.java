package com.slurp.helper.checks.passive.forms;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckForms extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintPassiveCheckForms instance = new SlurpHelperBlueprintPassiveCheckForms();

    private SlurpHelperBlueprintPassiveCheckForms() {
        this.setTitle("HTML Form(s)");
        this.setDescription("The web page presents HTML form(s).");
        this.setTypeId("PASSIVE_FORMS");
        this.setRunOncePerUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckForms getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckForms newCheck = new SlurpHelperPassiveCheckForms(id, analyzed, parent);

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
