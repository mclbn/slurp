package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import javax.swing.*;
import javax.swing.table.TableModel;

public class SlurpResultTable extends JTable {

    public SlurpResultTable(TableModel tableModel)
    {
        super(tableModel);
    }

    @Override
    public void changeSelection(int row, int col, boolean toggle, boolean extend)
    {
        super.changeSelection(row, col, toggle, extend);
        SlurpHelperResultsPane helperPaneInstance = SlurpHelperResultsPane.getInstance();

        if (helperPaneInstance.getDisplayedChecks().size() >= row) {

            int selectedRow = this.getSelectedRow();

            AbstractSlurpHelperCheck entry = helperPaneInstance.getDisplayedChecks().get(this.convertRowIndexToModel(selectedRow));

            helperPaneInstance.updateRequests((String)entry.getValueByName("Host"), entry.getTypeId());
            helperPaneInstance.updateData();
        }
    }
}

