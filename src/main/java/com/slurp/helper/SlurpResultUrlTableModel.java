package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.SlurpUtils;

import javax.swing.table.AbstractTableModel;

public class SlurpResultUrlTableModel extends AbstractTableModel {
    SlurpHelperResultsPane helperPane;
    private String[] columnNames = {"Check id", "URI", "Size", "Code"};
    private SlurpUtils utils = SlurpUtils.getInstance();

    public SlurpResultUrlTableModel() {
        helperPane = SlurpHelperResultsPane.getInstance();
    }

    @Override
    public int getRowCount() {
        return helperPane.getDisplayedUrls().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (helperPane.getDisplayedUrls().size() -1 < row) {
            return "";
        } else {
            SlurpHelperResultUrl res = helperPane.getDisplayedUrls().get(row);
            if (col == 0) { // id is check id
                return res.getRelatedCheck().getValueByName("Id");
            } else if (col == 1) {
                SlurpUtils utils = SlurpUtils.getInstance();

                return utils.getUriFromRequest(res.getHttpRequestResponse());
            } else if (col == 2) {
                return res.getHttpRequestResponse().getResponse().length;
            } else if (col == 3) {
                return Integer.toString(utils.getCodeFromResponse(res.getHttpRequestResponse().getResponse()));
            } else
                return "";
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return this.columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0)
            return Integer.class;
        else
            return super.getColumnClass(columnIndex);
    }
}
