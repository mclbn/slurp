package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import javax.swing.*;

public class SlurpResultPendingCheckFrame extends JFrame {
    static private SlurpResultPendingCheckFrame instance = new SlurpResultPendingCheckFrame();

    private SlurpResultPendingCheckFrame() {

    }

    public static SlurpResultPendingCheckFrame getInstance() {
        return instance;
    }
}
