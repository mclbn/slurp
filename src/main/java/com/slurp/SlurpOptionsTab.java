package com.slurp;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IBurpExtenderCallbacks;
import burp.ITab;

import java.awt.Component;
import javax.swing.JTabbedPane;

public class SlurpOptionsTab implements ITab {
    JTabbedPane tabs;
    private final IBurpExtenderCallbacks callbacks;

    public SlurpOptionsTab(final IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;

        tabs = new JTabbedPane();

        callbacks.customizeUiComponent(tabs);
        callbacks.addSuiteTab(SlurpOptionsTab.this);
    }

    @Override
    public String getTabCaption() {
        return "XMBurp Options";
    }

    @Override
    public Component getUiComponent() {
        return tabs;
    }

}
