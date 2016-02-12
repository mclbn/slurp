package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import javax.swing.table.AbstractTableModel;

public class SlurpResultPendingChecksTableModel extends AbstractTableModel {
    SlurpHelperCheckFactory factory;
    private String[] columnNames = {"Id", "Host", "Title", "URI"};

    public SlurpResultPendingChecksTableModel() {
        factory = SlurpHelperCheckFactory.getInstance();
    }

    @Override
    public int getRowCount() {
        return factory.getPendingChecks().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (factory.getPendingChecks().size() -1 < row) {
            return "";
        } else if (columnNames[col].equals("URI")) {
            AbstractSlurpHelperCheck check = factory.getPendingChecks().get(row);
            return check.getAnalyzedReqRes().getUri().toString();
        } else {
                AbstractSlurpHelperCheck check = factory.getPendingChecks().get(row);
                return check.getValueByName(columnNames[col]);
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return this.columnNames[columnIndex];
    }


    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnNames[columnIndex].equals("Id"))
            return Integer.class;
        else
            return super.getColumnClass(columnIndex);
    }
}
