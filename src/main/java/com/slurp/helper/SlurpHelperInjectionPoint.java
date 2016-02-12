package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

public class SlurpHelperInjectionPoint {
    int start;
    int stop;

    SlurpHelperInjectionPoint(int startOffset, int stopOffset) {
        this.start = startOffset;
        this.stop = stopOffset;
    }

    final int getStart() {
        return this.start;
    }

    final int getStop() {
        return this.stop;
    }
}
