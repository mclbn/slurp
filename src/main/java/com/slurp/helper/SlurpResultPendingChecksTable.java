package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class SlurpResultPendingChecksTable extends JTable {

    public SlurpResultPendingChecksTable(TableModel tableModel)
    {
        super(tableModel);
    }

    @Override
    public void changeSelection(int row, int col, boolean toggle, boolean extend)
    {
        super.changeSelection(row, col, toggle, extend);
        ArrayList<AbstractSlurpHelperCheck> pendingChecks = SlurpHelperCheckFactory.getInstance().getPendingChecks();

        if (pendingChecks.size() >= row)
        {
            int selectedRow = this.getSelectedRow();
            /*
            maybe do something...
             */
        }
    }
}
