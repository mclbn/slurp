package com.slurp.helper.checks.passive.wsdl;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckWsdl extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintPassiveCheckWsdl instance = new SlurpHelperBlueprintPassiveCheckWsdl();

    private SlurpHelperBlueprintPassiveCheckWsdl() {
        this.setTitle("Wsdl file");
        this.setDescription("The web server presents a Wsdl file.");
        this.setTypeId("PASSIVE_WSDL_DETECT");
        this.setRunOncePerUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckWsdl getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckWsdl newCheck = new SlurpHelperPassiveCheckWsdl(id, analyzed, parent);

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
