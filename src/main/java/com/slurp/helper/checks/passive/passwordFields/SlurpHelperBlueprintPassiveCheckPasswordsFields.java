package com.slurp.helper.checks.passive.passwordFields;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckPasswordsFields extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintPassiveCheckPasswordsFields instance = new SlurpHelperBlueprintPassiveCheckPasswordsFields();

    private SlurpHelperBlueprintPassiveCheckPasswordsFields() {
        this.setTitle("Password input field(s)");
        this.setDescription("The web page presents password input field(s).");
        this.setTypeId("PASSIVE_PASSFIELD");
        this.setRunOncePerUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckPasswordsFields getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckPasswordFields newCheck = new SlurpHelperPassiveCheckPasswordFields(id, analyzed, parent);

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
