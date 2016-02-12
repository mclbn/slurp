package com.slurp.helper.checks.active.dirFuzz;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SlurpHelperBlueprintActiveCheckDirFuzz extends AbstractSlurpCheckBlueprint {
    private static SlurpHelperBlueprintActiveCheckDirFuzz instance = new SlurpHelperBlueprintActiveCheckDirFuzz();
    private ConcurrentHashMap<String, ArrayList<String>> history = new ConcurrentHashMap<String, ArrayList<String>>();

    private SlurpHelperBlueprintActiveCheckDirFuzz() {
        this.setTitle("Directory listing(s)");
        this.setDescription("The web server exposes directory listiong(s).");
        this.setTypeId("ACTIVE_DIRFUZZ");
        this.setRunOncePerUrl();
        this.enable();
    }

    public static SlurpHelperBlueprintActiveCheckDirFuzz getInstance() {
        return instance;
    }

    public void appendDirToHistory(String host, String urn) {
        if (history.containsKey(host)) {
            ArrayList<String> reqList = history.get(host);

            if (!(reqList.contains(urn)))
                reqList.add(urn);
        } else {
            ArrayList<String> reqList = new ArrayList<String>();

            reqList.add(urn);
            history.put(host, reqList);
        }
    }

    public boolean dirInHistory(String host, String urn) {

        if (history.containsKey(host)) {
            ArrayList<String> list = history.get(host);

            return list.contains(urn);
        }
        return false;
    }

    public void clearHistoryForHost(String host) {
        if (history.containsKey(host)) {
            ArrayList<String> list = history.get(host);
            list.clear();
        }
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperCheckDirFuzz newCheck = new SlurpHelperCheckDirFuzz(id, analyzed, parent);

        newCheck.setLowSeverity();
        newCheck.setTitle(this.getTitle());
        newCheck.setDescription(this.getDescription());
        newCheck.setTypeId(this.getTypeId());
        newCheck.setBlueprint(this);
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
        clearHistoryForHost(host);
    }
}
