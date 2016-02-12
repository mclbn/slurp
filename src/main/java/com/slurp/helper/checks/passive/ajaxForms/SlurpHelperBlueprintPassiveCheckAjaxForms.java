package com.slurp.helper.checks.passive.ajaxForms;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckAjaxForms extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintPassiveCheckAjaxForms instance = new SlurpHelperBlueprintPassiveCheckAjaxForms();

    private SlurpHelperBlueprintPassiveCheckAjaxForms() {
        this.setTitle("AJAX Form(s)");
        this.setDescription("The web page presents AJAX form(s).");
        this.setTypeId("PASSIVE_AJAX_FORMS");
        this.setRunOncePerUrl();
        this.enable();
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckAjaxForms newCheck = new SlurpHelperPassiveCheckAjaxForms(id, analyzed, parent);

        newCheck.setInfoSeverity();
        newCheck.setTitle(this.getTitle());
        newCheck.setDescription(this.getDescription());
        newCheck.setTypeId(this.getTypeId());
        newCheck.setBlueprint(instance);
        return newCheck;
    }

    public static SlurpHelperBlueprintPassiveCheckAjaxForms getInstance() {
        return instance;
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
