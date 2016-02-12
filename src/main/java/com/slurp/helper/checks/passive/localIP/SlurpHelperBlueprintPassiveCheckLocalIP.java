package com.slurp.helper.checks.passive.localIP;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckLocalIP extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintPassiveCheckLocalIP instance = new SlurpHelperBlueprintPassiveCheckLocalIP();

    private SlurpHelperBlueprintPassiveCheckLocalIP() {
        this.setTitle("Local IP address(es) in page(s)");
        this.setDescription("The web page presents local IP address(es).");
        this.setTypeId("PASSIVE_LOCALIP");
        this.setRunOncePerUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckLocalIP getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckLocalIP newCheck = new SlurpHelperPassiveCheckLocalIP(id, analyzed, parent);

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
