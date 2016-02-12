package com.slurp.helper.checks.active.robots;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.List;

public class SlurpHelperBlueprintActiveCheckRobots extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintActiveCheckRobots instance = new SlurpHelperBlueprintActiveCheckRobots();

    private SlurpHelperBlueprintActiveCheckRobots() {
        this.setTitle("Robots.txt file");
        this.setDescription("The web server contains a robots.txt file.");
        this.setTypeId("ACTIVE_ROBOTS");
        this.setRunOncePerHost();
        this.enable();
    }

    public static SlurpHelperBlueprintActiveCheckRobots getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckRobots newCheck = new SlurpHelperCheckRobots(id, analyzed, parent);

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
