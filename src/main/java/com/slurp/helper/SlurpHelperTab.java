package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.ITab;
import com.slurp.SlurpUtils;

import java.awt.Component;
import javax.swing.JTabbedPane;

public class SlurpHelperTab implements ITab {
    SlurpUtils utils = SlurpUtils.getInstance();
    JTabbedPane tabs;
    SlurpManualScanner manualScanner;
    SlurpAutoHelperOptionsTab options;
    SlurpAutoHelperOptionsTab optionsTab;
    private SlurpHelperResultsPane resultTab;
    private SlurpHelper autoHelper;
    private SlurpChecklistTab checklistTab;

    public SlurpHelperTab() {

        tabs = new JTabbedPane();

        utils.getCallbacks().customizeUiComponent(tabs);
        utils.getCallbacks().addSuiteTab(SlurpHelperTab.this);

        manualScanner = new SlurpManualScanner();
        optionsTab = new SlurpAutoHelperOptionsTab();
        resultTab = SlurpHelperResultsPane.getInstance();
        resultTab.initHelperResultsPane();
        checklistTab = new SlurpChecklistTab();

        tabs.addTab("Results", null, resultTab, null);
//        tabs.addTab("Manual Scanner", null, manualScanner, null);
        tabs.addTab("Options", null, optionsTab, null);
        tabs.addTab("Notes", null, checklistTab, null);

        autoHelper = SlurpHelper.getInstance();
        autoHelper.initHelper();
        utils.getCallbacks().registerHttpListener(autoHelper);
    }

    @Override
    public String getTabCaption() {
        return "Slurp";
    }

    @Override
    public Component getUiComponent() {
        return tabs;
    }

}
