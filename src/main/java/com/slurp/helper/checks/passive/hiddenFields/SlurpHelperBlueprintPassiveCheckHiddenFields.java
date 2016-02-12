package com.slurp.helper.checks.passive.hiddenFields;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckHiddenFields extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintPassiveCheckHiddenFields instance = new SlurpHelperBlueprintPassiveCheckHiddenFields();

    private SlurpHelperBlueprintPassiveCheckHiddenFields() {
        this.setTitle("Hidden field(s)");
        this.setDescription("The web page presents hidden field(s).");
        this.setTypeId("PASSIVE_HIDDENFIELD");
        this.setRunOncePerUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckHiddenFields getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckHiddenFields newCheck = new SlurpHelperPassiveCheckHiddenFields(id, analyzed, parent);

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
