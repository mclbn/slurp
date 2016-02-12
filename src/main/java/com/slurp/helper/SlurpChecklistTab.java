package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.SlurpNotesTab;

import javax.swing.*;

public class SlurpChecklistTab extends JTabbedPane {

    public SlurpChecklistTab() {
        JPanel checklistPanel = new JPanel();
        SlurpNotesTab notesEditor = new SlurpNotesTab();

//        tabs.addTab("Checklist", null, checklistPanel, null);
        this.addTab("Notes", null, notesEditor, null);
    }
}
