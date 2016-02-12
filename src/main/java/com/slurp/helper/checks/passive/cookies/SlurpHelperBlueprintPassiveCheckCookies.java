package com.slurp.helper.checks.passive.cookies;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckCookies extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintPassiveCheckCookies instance = new SlurpHelperBlueprintPassiveCheckCookies();


    private SlurpHelperBlueprintPassiveCheckCookies() {
        this.setTitle("Missing cookie attribute(s)");
        this.setDescription("Cookies are missing security attributes.");
        this.setTypeId("PASSIVE_COOKIES");
        this.setRunOnAllUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintPassiveCheckCookies getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckCookies newCheck = new SlurpHelperPassiveCheckCookies(id, analyzed, parent);

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
