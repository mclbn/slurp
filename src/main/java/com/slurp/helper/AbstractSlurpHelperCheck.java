package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IHttpRequestResponse;
import burp.IHttpService;
import com.slurp.SlurpUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSlurpHelperCheck {
    private SlurpHelperAnalyzedRequestResponse analyzedReqRes;
    private IHttpRequestResponse originalRequestResponse;
    private ArrayList<IHttpRequestResponse> requestsResponses;
    private int id;
    private String severity = "INFO";
    private String status = "PENDING";
    private String title = "UNDEFINED_TYPE";
    private String description;
    private String typeId;
    private AbstractSlurpCheckBlueprint blueprint;
    private AbstractSlurpHelperCheck parent;
    private boolean markedForDeletion = false;

    public void setId(int id) {
        this.id = id;
    }

    public enum statuses { // not used ATM, just a memo
        PENDING,
        POSITIVE,
        NEGATIVE,
        FALSE_POSITIVE,
        FALSE_NEGATIVE,
        UNCERTAIN;
    }
    public enum severities { // not used ATM, just a memo
        CRITICAL,
        HIGH,
        MEDIUM,
        LOW,
        INFO;
    }

    public AbstractSlurpHelperCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        this.analyzedReqRes = analyzed;
        this.originalRequestResponse = analyzed.getOriginalRequestResponse();
        this.parent = parent;
        this.id = id;
        requestsResponses = new ArrayList<IHttpRequestResponse>();
    }

    // Implements the actual vulnerability check
    public abstract void doCheck();

    // Returns the title of the vulnerability
    public String getTitle() {
        return this.title;
    };

    public void setTitle(String title) {
        this.title = title;
    }

    // Returns a functional description of the vulnerability
    public String getDescription() {
        return this.description;
    };

    public void setDescription(String description) {
        this.description = description;
    }

    // Returns an HttpRequestResponse Arraylist that stores all requests/responses sent during the check execution
    public ArrayList<IHttpRequestResponse> getRequestsAndResponses() {
        return this.requestsResponses;
    };

    // Returns the average severity of the vulnerability
    public final String getSeverity() {
        return severity;
    };

    public void setInfoSeverity() {
        this.severity = "INFO";
    }

    public boolean hasInfoSeverity() {
        if (this.severity.equals("INFO"))
            return true;
        return false;
    }

    public boolean hasLowSeverity() {
        if (this.severity.equals("LOW"))
            return true;
        return false;
    }

    public boolean hasMediumSeverity() {
        if (this.severity.equals("MEDIUM"))
            return true;
        return false;
    }

    public boolean hasHighSeverity() {
        if (this.severity.equals("HIGH"))
            return true;
        return false;
    }

    public boolean hasCriticalSeverity() {
        if (this.severity.equals("CRITICAL"))
            return true;
        return false;
    }

    public void setLowSeverity() {
        this.severity = "LOW";
    }

    public void setMediumSeverity() {
        this.severity = "MEDIUM";
    }

    public void setHighSeverity() {
        this.severity = "HIGH";
    }

    public void setCriticalSeverity() {
        this.severity = "CRITICAL";
    }

    // Returns the status of this check (Pending, Positive, Negative, Marked as False-positive)
    public final String getStatus() {
        return status;
    };

    public void markAsPositive() {
        this.status = "POSITIVE";
    }

    public boolean isPositive() {
        return this.status.equals("POSITIVE");
    }

    public void markAsNegative() {
        this.status = "NEGATIVE";
    }

    public boolean isNegative() {
        return this.status.equals("NEGATIVE");
    }

    public void markAsUncertain() {
        this.status = "UNCERTAIN";
    }

    public boolean isUncertain() {
        return this.status.equals("UNCERTAIN");
    }

    public SlurpHelperAnalyzedRequestResponse getAnalyzedReqRes() {
        return analyzedReqRes;
    }

    // Change the status of an instance of this check to False Positive. Should not be possible on Pending checks!
    public void toggleFalsePositive() {
        if (status.equals("POSITIVE")
                || status.equals("UNCERTAIN"))
            this.status = "FALSE_POSITIVE";
        else if (status.equals("FALSE_POSITIVE"))
            this.status = "POSITIVE";
    }

    public boolean isFalsePositive() {
        return this.status.equals("FALSE_POSITIVE");
    }

    // Change the status of an instance of this check to False Negative. Should not be possible on Pending checks!
    public void toggleFalseNegative() {
        if (status.equals("NEGATIVE"))
            this.status = "FALSE_NEGATIVE";
        else if (status.equals("FALSE_NEGATIVE"))
            this.status = "NEGATIVE";
    }

    public boolean isFalseNegative() {
        return this.status.equals("FALSE_NEGATIVE");
    }

    public abstract void doSanityCheck();

    public IHttpRequestResponse doScanRequest(IHttpService httpService, byte[] request) {
        SlurpUtils utils;

        utils = SlurpUtils.getInstance();
        IHttpRequestResponse res = utils.getCallbacks().makeHttpRequest(httpService, request);
        this.requestsResponses.add(res);
        return res;
    }

    public void addRequestResponse(IHttpRequestResponse msg) {
        this.requestsResponses.add(msg);
    }

    public Object getValueByName(String name){
        name = name.toLowerCase();
        if (name.equals("id")) {
            return this.id;
        } else if (name.equals("host")) {
            return this.getOriginalRequestResponse().getHttpService().getHost();
        } else if (name.equals("title")) {
            return this.title;
        } else if (name.equals("description")) {
            return this.description;
        } else if (name.equals("severity")) {
            return this.severity;
        } else if (name.equals("status")) {
            return this.status;
        } else {
            return "";
        }
    }

    public abstract List<String> getData();

    public abstract void doDataCheck(List<String> data);

    public IHttpRequestResponse getOriginalRequestResponse() {
        return this.originalRequestResponse;
    }

    public final String getTypeId () {
        return this.typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public AbstractSlurpHelperCheck getParent() {
        return this.parent;
    }

    public void markForDeletetion() {
        this.markedForDeletion = true;
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    public AbstractSlurpCheckBlueprint getBlueprint() {
        return blueprint;
    }

    public void setBlueprint(AbstractSlurpCheckBlueprint blueprint) {
        this.blueprint = blueprint;
    }


 }

