package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

public abstract class AbstractSlurpHelperPassiveCheck extends AbstractSlurpHelperCheck {
    public AbstractSlurpHelperPassiveCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        super(id, analyzed, parent);
    }

    public final void doSanityCheck() {
        if (!this.isMarkedForDeletion()) {
            if (this.getStatus().equals("PENDING"))
                this.markAsNegative();
        }
        addRequestResponse(this.getAnalyzedReqRes().getOriginalRequestResponse());
    }
}
