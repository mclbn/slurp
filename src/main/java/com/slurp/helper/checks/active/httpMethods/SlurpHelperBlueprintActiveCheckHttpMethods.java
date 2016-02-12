package com.slurp.helper.checks.active.httpMethods;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckHttpMethods extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintActiveCheckHttpMethods instance = new SlurpHelperBlueprintActiveCheckHttpMethods();

    private SlurpHelperBlueprintActiveCheckHttpMethods() {
        this.setTitle("Interesting HTTP method(s)");
        this.setDescription("The web server accepts uncommon HTTP methods.");
        this.setTypeId("ACTIVE_HTTP_METHODS");
        this.setRunOncePerHost();
        this.enable();

    }

    public static SlurpHelperBlueprintActiveCheckHttpMethods getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckHttpMethods newCheck = new SlurpHelperCheckHttpMethods(id, analyzed, parent);

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
