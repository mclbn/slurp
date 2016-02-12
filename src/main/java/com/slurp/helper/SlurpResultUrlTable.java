package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import javax.swing.*;
import javax.swing.table.TableModel;

public class SlurpResultUrlTable extends JTable {

    public SlurpResultUrlTable(TableModel tableModel)
    {
        super(tableModel);
    }

    @Override
    public void changeSelection(int row, int col, boolean toggle, boolean extend)
    {
        super.changeSelection(row, col, toggle, extend);
        SlurpHelperResultsPane helperPaneInstance = SlurpHelperResultsPane.getInstance();

        if (helperPaneInstance.getDisplayedUrls().size() >= row)
        {
            int selectedRow = this.getSelectedRow();

            SlurpHelperResultUrl entry = helperPaneInstance.getDisplayedUrls().get(this.convertRowIndexToModel(selectedRow));

            helperPaneInstance.updateRequestResponsePanel(entry.getHttpRequestResponse());

        }
    }
}
