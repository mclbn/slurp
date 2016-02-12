package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

public class SlurpHelperFilterOptions {
    private static SlurpHelperFilterOptions instance = new SlurpHelperFilterOptions();
    private boolean showScopeOnly = true;
    private boolean showPositive = true;
    private boolean showUncertain = true;
    private boolean showNegative = false;
    private boolean showFalsePositive = false;
    private boolean showFalseNegative = false;
    private boolean showInfo = true;
    private boolean showLow = true;
    private boolean showMedium = true;
    private boolean showHigh = true;
    private boolean showCritical = true;

    private SlurpHelperFilterOptions() {
    }

    public static SlurpHelperFilterOptions getInstance() {
        return instance;
    }

    public boolean showingScopeOnly() {
        return showScopeOnly;
    }

    public boolean showingPositive() {
        return showPositive;
    }

    public boolean showingUncertain() {
        return showUncertain;
    }

    public boolean showingNegative() {
        return showNegative;
    }

    public boolean showingFalsePositive() {
        return showFalsePositive;
    }

    public boolean showingFalseNegative() {
        return showFalseNegative;
    }

    public boolean showingInfoSeverity() {
        return showInfo;
    }

    public boolean showingLowSeverity() {
        return showLow;
    }

    public boolean showingMediumSeverity() {
        return showMedium;
    }

    public boolean showingHighSeverity() {
        return showHigh;
    }

    public boolean showingCriticalSeverity() {
        return showCritical;
    }

    public void showScopeOnly() {
        this.showScopeOnly = true;
    }

    public void showPositive() {
        this.showPositive = true;
    }

    public void showUncertain() {
        this.showUncertain = true;
    }

    public void showNegative() {
        this.showNegative = true;
    }

    public void showFalsePositive() {
        this.showFalsePositive = true;
    }

    public void showFalseNegative() {
        this.showFalseNegative = true;
    }

    public void showOutOfScope() {
        this.showScopeOnly = false;
    }

    public void showInfoSeverity() {
        this.showInfo = true;
    }

    public void showLowSeverity() {
        this.showLow = true;
    }

    public void showMediumSeverity() {
        this.showMedium = true;
    }

    public void showHighSeverity() {
        this.showHigh = true;
    }

    public void showCriticalSeverity() {
        this.showCritical = true;
    }

    public void hidePositive() {
        this.showPositive = false;
    }

    public void hideUncertain() {
        this.showUncertain = false;
    }

    public void hideNegative() {
        this.showNegative = false;
    }

    public void hideFalsePositive() {
        this.showFalsePositive = false;
    }

    public void hideFalseNegative() {
        this.showFalseNegative = false;
    }

    public void hideInfoSeverity() {
        this.showInfo = false;
    }

    public void hideLowSeverity() {
        this.showLow = false;
    }

    public void hideMediumSeverity() {
        this.showMedium = false;
    }

    public void hideHighSeverity() {
        this.showHigh = false;
    }

    public void hideCriticalSeverity() {
        this.showCritical = false;
    }
}
