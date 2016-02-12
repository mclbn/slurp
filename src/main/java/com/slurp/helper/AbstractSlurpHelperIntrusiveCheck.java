package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import java.util.ArrayList;

public abstract class AbstractSlurpHelperIntrusiveCheck extends AbstractSlurpHelperCheck {
    private ArrayList<SlurpHelperInjectionPoint> injectPoints;

    public AbstractSlurpHelperIntrusiveCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, ArrayList<SlurpHelperInjectionPoint> offsets, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
        if (offsets != null)
            this.injectPoints = new ArrayList<SlurpHelperInjectionPoint>(offsets);
        else
            this.injectPoints = new ArrayList<SlurpHelperInjectionPoint>();
    }

    public void doSanityCheck() {

    }
}
