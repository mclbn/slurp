package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

public abstract class AbstractSlurpHelperActiveCheck extends AbstractSlurpHelperCheck {
    public AbstractSlurpHelperActiveCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    public final void doSanityCheck() {
        if (getStatus().equals("PENDING"))
            this.markAsNegative();
    }
}
