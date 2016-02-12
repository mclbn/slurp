package com.slurp.helper.checks.passive.reflectedParams.specialChars;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveReflectedXSS extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintPassiveReflectedXSS instance = new SlurpHelperBlueprintPassiveReflectedXSS();

    private SlurpHelperBlueprintPassiveReflectedXSS() {
        this.setTitle("Possible XSS");
        this.setDescription("The web page presents reflected character(s).");
        this.setTypeId("PASSIVE_REFLECTEDXSS");
        this.setRunOnAllUrl();
        this.dependsOnCheck(true);
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveReflectedXSS getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckReflectedXSS newCheck = new SlurpHelperPassiveCheckReflectedXSS(id, analyzed, parent);

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
