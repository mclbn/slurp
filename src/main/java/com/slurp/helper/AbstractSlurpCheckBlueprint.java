package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import javax.swing.*;
import java.util.List;

public abstract class AbstractSlurpCheckBlueprint {
    private boolean enabled = false;
    private String title = "NO TITLE";
    private String desc = "NO DESCRIPTION";
    private String typeId = "NO_TYPEID";
    private boolean runOnAllUrl = false;
    private boolean runOncePerUrl = true;
    private boolean runOncePerHost = false;
    private boolean dependsOnOtherCheck = false;


    // pour obtenir un nouveau check avec les options actuelles
    public abstract AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent);
    // les éléments à pusher dans le menu contextuel
    public abstract List<JMenuItem> getContextMenu();


    public abstract JPanel getOptionsMenu();

    public boolean isEnabled() {
        return this.enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void setRunOncePerUrl() {
        this.runOncePerUrl = true;
    }

    public void setRunOncePerHost() {
        this.runOncePerHost = true;
    }

    public void setRunOnAllUrl() {
        this.runOnAllUrl = true;
    }

    public boolean isRunOnePerUrl() {
        return runOncePerUrl;
    }

    public boolean isRunOnePerHost() {
        return runOncePerHost;
    }

    public boolean isRunOnAllUrl() {
        return this.runOnAllUrl;
    }

    public void dependsOnCheck(boolean value) {
        this.dependsOnOtherCheck = value;
    }

    public boolean dependsOnCheck() {
        return dependsOnOtherCheck;
    }

    public abstract void resetForHost(String host);

}
