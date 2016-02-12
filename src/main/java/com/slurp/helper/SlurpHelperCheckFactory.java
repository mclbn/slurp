package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.checks.active.crossdomain.SlurpHelperBlueprintActiveCheckCrossdomain;
import com.slurp.helper.checks.active.dirFuzz.SlurpHelperBlueprintActiveCheckDirFuzz;
import com.slurp.helper.checks.active.dirFuzz.backups.SlurpHelperBlueprintActiveCheckDirFuzzBackups;
import com.slurp.helper.checks.active.dirFuzz.commonFiles.SlurpHelperBlueprintActiveCheckDirFuzzCommonFiles;
import com.slurp.helper.checks.active.dirFuzz.repositories.SlurpHelperBlueprintActiveCheckDirFuzzRepositories;
import com.slurp.helper.checks.active.dirFuzz.webServices.SlurpHelperBlueprintActiveCheckDirFuzzWebServices;
import com.slurp.helper.checks.active.httpMethods.SlurpHelperBlueprintActiveCheckHttpMethods;
import com.slurp.helper.checks.active.robots.SlurpHelperBlueprintActiveCheckRobots;
import com.slurp.helper.checks.active.sitemap.SlurpHelperBlueprintActiveCheckSitemap;
import com.slurp.helper.checks.active.traceAxd.SlurpHelperBlueprintActiveCheckTraceAxd;
import com.slurp.helper.checks.active.verbTamper.SlurpHelperBlueprintActiveCheckVerbTamper;
import com.slurp.helper.checks.passive.ajaxForms.SlurpHelperBlueprintPassiveCheckAjaxForms;
import com.slurp.helper.checks.passive.comments.IPinComments.SlurpHelperBlueprintPassiveIPInComments;
import com.slurp.helper.checks.passive.cleartextAuthent.SlurpHelperBlueprintPassiveCheckCleartextAuthent;
import com.slurp.helper.checks.passive.comments.SlurpHelperBlueprintPassiveCheckComments;
import com.slurp.helper.checks.passive.cookies.SlurpHelperBlueprintPassiveCheckCookies;
import com.slurp.helper.checks.passive.customRegex.SlurpHelperBlueprintPassiveCheckCustomRegex;
import com.slurp.helper.checks.passive.errors.SlurpHelperBlueprintPassiveCheckErrors;
import com.slurp.helper.checks.passive.forms.SlurpHelperBlueprintPassiveCheckForms;
import com.slurp.helper.checks.passive.headers.SlurpHelperBlueprintPassiveCheckHeaders;
import com.slurp.helper.checks.passive.comments.linksInComments.SlurpHelperBlueprintPassiveCheckLinksInComments;
import com.slurp.helper.checks.passive.hiddenFields.SlurpHelperBlueprintPassiveCheckHiddenFields;
import com.slurp.helper.checks.passive.localIP.SlurpHelperBlueprintPassiveCheckLocalIP;
import com.slurp.helper.checks.passive.passwordFields.SlurpHelperBlueprintPassiveCheckPasswordsFields;
import com.slurp.helper.checks.passive.reflectedParams.SlurpHelperBlueprintPassiveReflectedParams;
import com.slurp.helper.checks.passive.reflectedParams.specialChars.SlurpHelperBlueprintPassiveReflectedXSS;
import com.slurp.helper.checks.passive.upload.SlurpHelperBlueprintPassiveCheckUpload;
import com.slurp.helper.checks.passive.wsdl.SlurpHelperBlueprintPassiveCheckWsdl;

import javax.swing.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SlurpHelperCheckFactory {
    static private SlurpHelperCheckFactory instance = new SlurpHelperCheckFactory();
    static ArrayList<AbstractSlurpCheckBlueprint> inventory;
    private static int idCount = 0;
    private ConcurrentHashMap<String, ArrayList<String>> blackList = new ConcurrentHashMap<String, ArrayList<String>>();
    private CopyOnWriteArrayList<AbstractSlurpHelperCheck> allChecks = new CopyOnWriteArrayList<AbstractSlurpHelperCheck>();

    private SlurpHelperCheckFactory() {
        inventory = new ArrayList<AbstractSlurpCheckBlueprint>();

        // passive checks
        inventory.add(SlurpHelperBlueprintPassiveCheckCustomRegex.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckCookies.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckCleartextAuthent.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckHeaders.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckErrors.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckComments.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveIPInComments.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckLinksInComments.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckLocalIP.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckUpload.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckWsdl.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveReflectedParams.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveReflectedXSS.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckForms.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckHiddenFields.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckPasswordsFields.getInstance());
        inventory.add(SlurpHelperBlueprintPassiveCheckAjaxForms.getInstance());

        // active checks
        inventory.add(SlurpHelperBlueprintActiveCheckRobots.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckSitemap.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckCrossdomain.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckTraceAxd.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckHttpMethods.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckVerbTamper.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckDirFuzz.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckDirFuzzRepositories.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckDirFuzzCommonFiles.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckDirFuzzBackups.getInstance());
        inventory.add(SlurpHelperBlueprintActiveCheckDirFuzzWebServices.getInstance());

        System.out.println("Check factory initialized, knowing the following checks:");
        for (AbstractSlurpCheckBlueprint bp : inventory) {
            System.out.println(bp.getTypeId());
        }
    }

    public boolean checkShouldRun(AbstractSlurpCheckBlueprint blueprint, SlurpHelperAnalyzedRequestResponse originatingAnalyzed) {
        if (isInBlackList(originatingAnalyzed, blueprint.getTypeId())) {
            return false;
        }

        if (blueprint.isRunOnAllUrl()) {
            return true;
        }

        if (blueprint.isRunOnePerHost()) {
            String host = originatingAnalyzed.getOriginalRequestResponse().getHttpService().getHost();

            for (AbstractSlurpHelperCheck check : allChecks) {
                SlurpHelperAnalyzedRequestResponse analyzed = check.getAnalyzedReqRes();
                String storedHost = analyzed.getOriginalRequestResponse().getHttpService().getHost();

                if (storedHost.equals(host)
                        && check.getTypeId().equals(blueprint.getTypeId())) {
                    return false;
                }
            }
            return true;
        } else { // once per URI is default behaviour
            URL uri = originatingAnalyzed.getUri();

            for (AbstractSlurpHelperCheck check : allChecks) {
                SlurpHelperAnalyzedRequestResponse analyzed = check.getAnalyzedReqRes();
                URL storedUrl = analyzed.getUri();

                if (storedUrl.equals(uri)
                        && (check.getTypeId().equals(blueprint.getTypeId()))) {
                    return false;
                }
            }
            return true;
        }
    }

    public void removeCheckFromCheckList(AbstractSlurpHelperCheck check) {
        if (this.allChecks.contains(check)) {
            AbstractSlurpCheckBlueprint bp = check.getBlueprint();
            if (bp != null) // in case of lazy or bad module developer
                bp.resetForHost(check.getOriginalRequestResponse().getHttpService().getHost());
            this.allChecks.remove(check);
        }
    }

    public static SlurpHelperCheckFactory getInstance() {
        return instance;
    }

    public List<AbstractSlurpCheckBlueprint> getKnownBluePrints() {
        return inventory;
    }

    public JPanel getOptionsMenu(String type) {
        for (AbstractSlurpCheckBlueprint elem : inventory) {
            if (elem.getTypeId().equals(type)) {
                return elem.getOptionsMenu();
            }
        }
        return null;
    }

    public List<JMenuItem> getContextMenu(String type) {
        for (AbstractSlurpCheckBlueprint elem : inventory) {
            if (elem.getTypeId().equals(type)) {
                return elem.getContextMenu();
            }
        }
        return null;
    }

    // offsets for intrusive checks are just ignored ATM
    public AbstractSlurpHelperCheck newCheck(String type, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent,
                                             ArrayList<SlurpHelperInjectionPoint> offsets, AbstractSlurpHelperCheck originatingCheck) {
        AbstractSlurpHelperCheck newCheck = null;
        int id = idCount;

        for (AbstractSlurpCheckBlueprint elem : inventory) {

            if (elem.getTypeId().equals(type) && elem.isEnabled()) {

                if (elem.dependsOnCheck() && (originatingCheck == null)) {
                    break;
                }

                if (checkShouldRun(elem, analyzed)) {
                    newCheck = elem.getNewCheck(id, analyzed, parent);
                }
            }
        }

        if (newCheck != null) {
            idCount++;
        }
        return newCheck;
    }

    public CopyOnWriteArrayList<AbstractSlurpHelperCheck> getAllChecks() {
        return allChecks;
    }

    public ArrayList<AbstractSlurpHelperCheck> getPendingChecks() {
        ArrayList<AbstractSlurpHelperCheck> pendingChecks = new ArrayList<AbstractSlurpHelperCheck>();
        for (AbstractSlurpHelperCheck check : allChecks) {
            if (check.getStatus().equals("PENDING")) {
                pendingChecks.add(check);
            }
        }
        return pendingChecks;
    }

    public void addCheck(AbstractSlurpHelperCheck check) {
        allChecks.add(check);
    }

    public boolean isInBlackList(SlurpHelperAnalyzedRequestResponse analyzed, String typeId) {
        String URL = analyzed.getUri().toString();

        if (blackList.containsKey(URL)) {
            ArrayList<String> typeList = blackList.get(URL);

            if (typeList.contains(typeId))
                return true;
        }
        return false;
    }

    public void addToBlackList(AbstractSlurpHelperCheck check) {
        String URL = check.getAnalyzedReqRes().getUri().toString();
        String typeId = check.getTypeId();

        if (blackList.containsKey(URL)) {
            ArrayList<String> typeList = blackList.get(URL);

            if (!(typeList.contains(typeId)))
                typeList.add(typeId);

        } else {
            ArrayList<String> typeList = new ArrayList<String>();
            typeList.add(typeId);
            blackList.put(URL, typeList);
        }

    }
}
