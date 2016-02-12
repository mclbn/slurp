package com.slurp.helper.checks.active.traceAxd;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckTraceAxd extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintActiveCheckTraceAxd instance = new SlurpHelperBlueprintActiveCheckTraceAxd();

    private SlurpHelperBlueprintActiveCheckTraceAxd() {
        this.setTitle("Trace.axd file");
        this.setDescription("The web server contains a Trace.axd file.");
        this.setTypeId("ACTIVE_TRACE_AXD");
        this.setRunOncePerHost();
        this.enable();
    }

    public static SlurpHelperBlueprintActiveCheckTraceAxd getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckTraceAxd newCheck = new SlurpHelperCheckTraceAxd(id, analyzed, parent);

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
