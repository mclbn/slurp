package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IBurpExtenderCallbacks;
import burp.IHttpListener;
import burp.IHttpRequestResponse;
import com.slurp.SlurpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SlurpHelper implements IHttpListener {
    private static SlurpHelper instance = new SlurpHelper();
    private SlurpUtils utils = SlurpUtils.getInstance();
    private SlurpAutoHelperOptions options;

    private SlurpHelper() {

    }

    public static SlurpHelper getInstance() {
        return instance;
    }

    public void initHelper() {
        options = SlurpAutoHelperOptions.getInstance();
    }

    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
        if (options.isEnabled()) {

            // checker le tool de provenance
            if (toolFlag == IBurpExtenderCallbacks.TOOL_EXTENDER) {
                return; // let request from Extender pass (avoids infinite loops :-p)
            }
            if (messageIsRequest) { // Handling a request
                // todo
            } else { // Handling a response
                SlurpHelperAnalyzedRequestResponse analyzed = new SlurpHelperAnalyzedRequestResponse(messageInfo);

                doResponsePassiveScan(analyzed);
                doActiveScan(analyzed);
            }
         }
    }

    public void deleteCheckFromResults(AbstractSlurpHelperCheck elem) {
        SlurpHelperCheckFactory.getInstance().removeCheckFromCheckList(elem);
    }

    public void deleteCheckFromResultsAndBlacklist(AbstractSlurpHelperCheck elem) {
        SlurpHelperCheckFactory.getInstance().addToBlackList(elem);
        SlurpHelperCheckFactory.getInstance().removeCheckFromCheckList(elem);
    }

    public void doActiveScan(SlurpHelperAnalyzedRequestResponse analyzed) {
        java.net.URL url = analyzed.getUri();

        if (!(utils.getCallbacks().isInScope(url)) || !options.isEnabled()) {
            return;
        }

        ArrayList<AbstractSlurpHelperCheck> checksToDoList = new ArrayList<AbstractSlurpHelperCheck>();
        SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();
        List<AbstractSlurpCheckBlueprint> inventory = factory.getKnownBluePrints();

        for (AbstractSlurpCheckBlueprint blueprint : inventory) {
            if (blueprint.isEnabled()) {
                AbstractSlurpHelperCheck newCheck = factory.newCheck(blueprint.getTypeId(), analyzed, null, null, null);
                if (newCheck != null && newCheck instanceof AbstractSlurpHelperActiveCheck) {
                    checksToDoList.add(newCheck);
//                    addChecktoAllCheckList(newCheck);
                }
            }
        }

        for (AbstractSlurpHelperCheck check : checksToDoList) {
            /*
            if (options.isEnabled()) {
                check.doCheck();
                check.doSanityCheck();
            }*/
            runCheck(check);
        }
        if (options.autoRefreshEnabled())
            SlurpHelperResultsPane.getInstance().updateDisplayedChecks();
    }

    public void doResponsePassiveScan(SlurpHelperAnalyzedRequestResponse analyzed) {
        java.net.URL url = analyzed.getUri();

        if (!(utils.getCallbacks().isInScope(url)) || !options.isEnabled()) {
            return;
        }

        ArrayList<AbstractSlurpHelperCheck> checksToDoList = new ArrayList<AbstractSlurpHelperCheck>();
        SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();
        List<AbstractSlurpCheckBlueprint> inventory = factory.getKnownBluePrints();

        for (AbstractSlurpCheckBlueprint blueprint : inventory) {
            if (blueprint.isEnabled()) {
                AbstractSlurpHelperCheck newCheck = factory.newCheck(blueprint.getTypeId(), analyzed, null, null, null);
                if (newCheck != null && newCheck instanceof AbstractSlurpHelperPassiveCheck) {
                    checksToDoList.add(newCheck);
//                    addChecktoAllCheckList(newCheck);
                }
            }
        }

        for (AbstractSlurpHelperCheck check : checksToDoList) {
            /*
            if (options.isEnabled()) {
                check.doCheck();
                check.doSanityCheck();
            }*/
            runCheck(check);
        }
        if (options.autoRefreshEnabled())
            SlurpHelperResultsPane.getInstance().updateDisplayedChecks();
    }

    public final CopyOnWriteArrayList<AbstractSlurpHelperCheck> getResults() {
        return SlurpHelperCheckFactory.getInstance().getAllChecks();
    }

    public void addChecktoAllCheckList(AbstractSlurpHelperCheck check) {
        SlurpHelperCheckFactory.getInstance().addCheck(check);
        if (options.autoRefreshEnabled())
            SlurpHelperResultsPane.getInstance().updateProgress();
    }

    public void runCheck(AbstractSlurpHelperCheck check, List<String> data) {
        if (check == null)
            return;
        if (options.isEnabled()) {
            addChecktoAllCheckList(check);
            if (data != null) {
                check.doDataCheck(data);
            } else
                check.doCheck();
            check.doSanityCheck();
        }
    }

    public void runCheck(AbstractSlurpHelperCheck check) {
        runCheck(check, null);
    }
}
