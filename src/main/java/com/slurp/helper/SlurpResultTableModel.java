package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import javax.swing.table.AbstractTableModel;

public class SlurpResultTableModel extends AbstractTableModel {
    SlurpHelperResultsPane resultPane;
    private String[] columnNames = {"Host", "Title", "Severity", "Status"};

    public SlurpResultTableModel() {
        resultPane = SlurpHelperResultsPane.getInstance();
    }

    @Override
    public int getRowCount() {
        return resultPane.getDisplayedChecks().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (resultPane.getDisplayedChecks().size() -1 < row) {
            return "";
        } else {
            return (resultPane.getDisplayedChecks().get(row).getValueByName(columnNames[col]));
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return this.columnNames[columnIndex];
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }
}
