package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import java.util.ArrayList;
import java.util.List;

public class SlurpAutoHelperOptions {
    private static SlurpAutoHelperOptions instance = new SlurpAutoHelperOptions();
    private ArrayList<AbstractSlurpCheckBlueprint> enabledBluePrints = null;
    private boolean enabled = true;
    private boolean autoRefresh = true;
    private boolean robotsCheck = true;
    private boolean crossdomainCheck = true;
    private boolean headerCheck = true;
    private boolean wsdlCheck = true;
    private boolean cookiesCheck = true;
    private boolean traceAxdCheck = true;
    private boolean httpMethodsCheck = true;
    private boolean httpCommentsCheck = true;
    private boolean localIPCheck = true;
    private boolean clearTextAuthentCheck = true;
    private boolean uploadCheck = true;
    private boolean verbBypassCheck = true;
    private boolean regexCheck = true;
    private boolean regexRequestCheck = true;

    private SlurpAutoHelperOptions() {
        this.updateEnabledBluePrints();
    }

    public static SlurpAutoHelperOptions getInstance() {
        return instance;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean autoRefreshEnabled() {
        return this.autoRefresh;
    }

    public void enableAutoRefresh() {
        this.autoRefresh = true;
    }

    public void disableAutoRefresh() {
        this.autoRefresh = false;
    }

    public void updateEnabledBluePrints() {
        List<AbstractSlurpCheckBlueprint> inventory;

        if (this.enabledBluePrints == null) {
            this.enabledBluePrints = new ArrayList<AbstractSlurpCheckBlueprint>();
        }

        inventory = SlurpHelperCheckFactory.getInstance().getKnownBluePrints();

        for (AbstractSlurpCheckBlueprint blueprint : inventory) {
            if (blueprint.isEnabled()) {
                this.enabledBluePrints.add(blueprint);
            }
        }
    }

    public List<AbstractSlurpCheckBlueprint> getEnabledBluePrints() {
        return this.enabledBluePrints;
    }

    public List<AbstractSlurpCheckBlueprint> getAllBluePrints() {
        return SlurpHelperCheckFactory.getInstance().getKnownBluePrints();
    }

    public void enableRobots() {
        System.out.println("Enabling robots.txt");
        this.robotsCheck = true;
    }

    public void disableRobots() {
        System.out.println("Disabling robots.txt");
        this.robotsCheck = false;
    }

    public boolean robotsEnabled() {
        return this.robotsCheck;
    }

    public void enableCrossdomain() {
        System.out.println("Enabling crossdomain");
        this.crossdomainCheck = true;
    }

    public void disableCrossdomain() {
        System.out.println("Disabling crossdomain");
        this.crossdomainCheck = false;
    }

    public boolean crossdomainEnabled() {
        return this.crossdomainCheck;
    }


    public void enableHeaders() {
        System.out.println("Enabling headers");
        this.headerCheck = true;
    }

    public void disableHeaders() {
        System.out.println("Disabling headers");
        this.headerCheck = false;
    }

    public boolean headersEnabled() {
        return this.headerCheck;
    }

    public void enableWsdl() {
        System.out.println("Enabling wsdl");
        this.wsdlCheck = true;
    }

    public void disableWsdl() {
        System.out.println("Disabling wsdl");
        this.wsdlCheck = false;
    }

    public boolean wsdlEnabled() {
        return this.wsdlCheck;
    }

    public void enableCookies() {
        System.out.println("Enabling cookies");
        this.cookiesCheck = true;
    }

    public void disableCookies() {
        System.out.println("Disabling cookies");
        this.cookiesCheck = false;
    }

    public boolean traceAxdEnabled() {
        return this.traceAxdCheck;
    }

    public void enableTraceAxd() {
        System.out.println("Enabling trace.axd");
        this.traceAxdCheck = true;
    }

    public void disableTraceAxd() {
        System.out.println("Disabling trace.axd");
        this.traceAxdCheck = false;
    }

    public boolean cookiesEnabled() {
        return this.cookiesCheck;
    }

    public void enableHttpMethods() {
        System.out.println("Enabling HTTP methods");
        this.httpMethodsCheck = true;
    }

    public void disableHttpMethods() {
        System.out.println("Disabling HTTP methods");
        this.httpMethodsCheck = false;
    }

    public boolean httpMethodsEnabled() {
        return this.httpMethodsCheck;
    }

    public void enableHttpComments() {
        System.out.println("Enabling HTTP comments");
        this.httpCommentsCheck = true;
    }

    public void disableHttpComments() {
        System.out.println("Disabling HTTP comments");
        this.httpCommentsCheck = false;
    }

    public boolean httpCommentsEnabled() {
        return this.httpCommentsCheck;
    }

    public void enableLocalIp() {
        System.out.println("Enabling local IP");
        this.localIPCheck = true;
    }

    public void disableLocalIp() {
        System.out.println("Disabling local IP");
        this.localIPCheck = false;
    }

    public boolean localIpEnabled() {
        return this.localIPCheck;
    }

    public void enableCleartextAuthent() {
        System.out.println("Enabling cleartext authent detection");
        this.clearTextAuthentCheck = true;
    }

    public void disableCleartextAuthent() {
        System.out.println("Disabling cleartext authent detection");
        this.clearTextAuthentCheck = false;
    }

    public boolean cleartextAuthentEnabled() {
        return this.clearTextAuthentCheck;
    }

    public void enableUpload() {
        System.out.println("Enabling upload detection");
        this.uploadCheck = true;
    }

    public void disableUpload() {
        System.out.println("Disabling upload detection");
        this.uploadCheck = false;
    }

    public boolean uploadEnabled() {
        return this.uploadCheck;
    }

    public void enableVerbBypass() {
        System.out.println("Enabling HTTP verb authentication bypass");
        this.verbBypassCheck = true;
    }

    public void disableVerbBypass() {
        System.out.println("Disabling HTTP verb authentication bypass");
        this.verbBypassCheck = false;
    }

    public boolean verbBypassEnabled() {
        return this.verbBypassCheck;
    }

    public void enableRegex() {
        System.out.println("Enabling custom regex");
        this.regexCheck = true;
    }

    public void disableRegex() {
        System.out.println("Disabling custom regex");
        this.regexCheck = false;
    }

    public boolean regexEnabled() {
        return this.regexCheck;
    }


    public void enableRequestRegex() {
        System.out.println("Enabling custom regex");
        this.regexRequestCheck = true;
    }

    public void disableRequestRegex() {
        System.out.println("Disabling custom regex");
        this.regexRequestCheck = false;
    }

    public boolean regexRequestEnabled() {
        return this.regexRequestCheck;
    }
}

