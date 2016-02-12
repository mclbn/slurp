package com.slurp.helper.checks.active.sitemap;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckSitemap extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintActiveCheckSitemap instance = new SlurpHelperBlueprintActiveCheckSitemap();

    private SlurpHelperBlueprintActiveCheckSitemap() {
        this.setTitle("Sitemap.xml file");
        this.setDescription("The web server contains a sitemap.xml file.");
        this.setTypeId("ACTIVE_SITEMAP");
        this.setRunOncePerHost();
        this.enable();
    }

    public static SlurpHelperBlueprintActiveCheckSitemap getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckSitemap newCheck = new SlurpHelperCheckSitemap(id, analyzed, parent);

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
