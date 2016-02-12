package com.slurp.helper.checks.passive.reflectedParams;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveReflectedParams extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintPassiveReflectedParams instance = new SlurpHelperBlueprintPassiveReflectedParams();

    private SlurpHelperBlueprintPassiveReflectedParams() {
        this.setTitle("Reflected parameter(s)");
        this.setDescription("The web page presents reflected parameter(s).");
        this.setTypeId("PASSIVE_REFLECTEDPARAMS");
        this.setRunOnAllUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveReflectedParams getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckReflectedParams newCheck = new SlurpHelperPassiveCheckReflectedParams(id, analyzed, parent);

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
