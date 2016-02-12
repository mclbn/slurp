package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.*;
import com.slurp.SlurpUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SlurpHelperResultsPane extends JSplitPane implements IMessageEditorController {
    private static SlurpHelperResultsPane instance = new SlurpHelperResultsPane();
    SlurpResultTableModel resultTableModel;
    JSplitPane lowerPanel;
    SlurpResultTable resultTable;
    JScrollPane resultPane, dataScrollPane;
    SlurpHelperResultsFilter filterPanel;
    IMessageEditor requestViewer;
    IMessageEditor responseViewer;
    private IHttpRequestResponse currentlyDisplayedRequest;
    JSplitPane requestResponseSplitPanel;
    JSplitPane urlDataSplitPanel;
    JSplitPane resultSplitPanel;
    JScrollPane urlPanel;
    private ArrayList<AbstractSlurpHelperCheck> displayedChecks;
    private ArrayList<SlurpHelperResultUrl> displayedUrls;
//    private ArrayList<IHttpRequestResponse> displayedUrls;
    SlurpResultUrlTableModel urlTableModel;
    SlurpResultUrlTable urlTable;
    SlurpUtils utils;
    SlurpHelperFilterOptions filterOptions;
    JTextArea dataPane;
    JPopupMenu tablePopupMenu;
    JMenuItem markFalsePositiveMenuItem;
    JMenuItem markFalseNegativeMenuItem;
    JMenuItem showDataMenuItem;
    JMenuItem permanentlyDeleteMenuItem;
    JMenuItem permanentlyResetMenuItem;
    JMenuItem addToScopeMenuItem;
    JMenuItem excludeFromScopeMenuItem;
    JMenuItem sendToRepeaterMenuItem;
    JMenuItem sendToIntruderMenuItem;
    JMenuItem sendToSpiderMenuItem;

    private SlurpHelperResultsPane() {

    }

    public static SlurpHelperResultsPane getInstance() {
        return instance;
    }

    public void initHelperResultsPane() {
        this.utils = SlurpUtils.getInstance();
        this.filterOptions = SlurpHelperFilterOptions.getInstance();

        this.displayedChecks = new ArrayList<AbstractSlurpHelperCheck>();

        this.setOrientation(JSplitPane.VERTICAL_SPLIT);

        filterPanel = new SlurpHelperResultsFilter();

        requestViewer = utils.getCallbacks().createMessageEditor(SlurpHelperResultsPane.this, false);
        responseViewer = utils.getCallbacks().createMessageEditor(SlurpHelperResultsPane.this, false);

        resultTableModel = new SlurpResultTableModel();
        resultTable = new SlurpResultTable(resultTableModel);
        resultTable.setAutoCreateRowSorter(true);
        resultPane = new JScrollPane(resultTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        displayedUrls = new ArrayList<SlurpHelperResultUrl>();
        urlTableModel = new SlurpResultUrlTableModel();
        urlTable = new SlurpResultUrlTable(urlTableModel);
        urlPanel = new JScrollPane(urlTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        urlTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        urlTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        urlTable.getColumnModel().getColumn(2).setPreferredWidth(30);
        urlTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        urlTable.setAutoCreateRowSorter(true);

        markFalsePositiveMenuItem = new JMenuItem("Toggle false positive");
        markFalsePositiveMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<AbstractSlurpHelperCheck> selectedChecks = getSelectedChecks();

                for (AbstractSlurpHelperCheck check : selectedChecks) {
                    // todo
                }


                final CopyOnWriteArrayList<AbstractSlurpHelperCheck> results = SlurpHelper.getInstance().getResults();
                int [] selectedRowIndexes = resultTable.getSelectedRows();

                for (int selectedRowIndex : selectedRowIndexes) {
                    AbstractSlurpHelperCheck selectedCheck = displayedChecks.get(resultTable.convertRowIndexToModel(selectedRowIndex));
                    String typeId = selectedCheck.getTypeId();

                    for (AbstractSlurpHelperCheck entry : results) {
                        boolean showStatus = false;
                        boolean showSeverity = false;

                        if (!entry.getTypeId().equals(typeId))
                            continue;

                        if (filterOptions.showingPositive() && entry.isPositive()) {
                            showStatus = true;
                        } else if (filterOptions.showingNegative() && entry.isNegative()) {
                            showStatus = true;
                        } else if (filterOptions.showingUncertain() && entry.isUncertain()) {
                            showStatus = true;
                        } else if (filterOptions.showingFalseNegative() && entry.isFalseNegative()) {
                            showStatus = true;
                        } else if (filterOptions.showingFalsePositive() && entry.isFalsePositive()) {
                            showStatus = true;
                        }

                        if (filterOptions.showingInfoSeverity() && entry.hasInfoSeverity()) {
                            showSeverity = true;
                        } else if (filterOptions.showingLowSeverity() && entry.hasLowSeverity()) {
                            showSeverity = true;
                        } else if (filterOptions.showingMediumSeverity() && entry.hasMediumSeverity()) {
                            showSeverity = true;
                        } else if (filterOptions.showingHighSeverity() && entry.hasHighSeverity()) {
                            showSeverity = true;
                        } else if (filterOptions.showingCriticalSeverity() && entry.hasCriticalSeverity()) {
                            showSeverity = true;
                        }

                        if (showStatus && showSeverity) {
                            entry.toggleFalsePositive();
                        }
                    }
                }
                updateDisplayedChecks();
                updateChecks();
            }
        });

        markFalseNegativeMenuItem = new JMenuItem("Toggle false negative");
        markFalseNegativeMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final CopyOnWriteArrayList<AbstractSlurpHelperCheck> results = SlurpHelper.getInstance().getResults();
                int [] selectedRowIndexes = resultTable.getSelectedRows();

                for (int selectedRowIndex : selectedRowIndexes) {
                    AbstractSlurpHelperCheck selectedCheck = displayedChecks.get(resultTable.convertRowIndexToModel(selectedRowIndex));
                    String typeId = selectedCheck.getTypeId();

                    for (AbstractSlurpHelperCheck entry : results) {
                        boolean showStatus = false;
                        boolean showSeverity = false;

                        if (!entry.getTypeId().equals(typeId))
                            continue;

                        if (filterOptions.showingPositive() && entry.isPositive()) {
                            showStatus = true;
                        } else if (filterOptions.showingNegative() && entry.isNegative()) {
                            showStatus = true;
                        } else if (filterOptions.showingUncertain() && entry.isUncertain()) {
                            showStatus = true;
                        } else if (filterOptions.showingFalseNegative() && entry.isFalseNegative()) {
                            showStatus = true;
                        } else if (filterOptions.showingFalsePositive() && entry.isFalsePositive()) {
                            showStatus = true;
                        }

                        if (filterOptions.showingInfoSeverity() && entry.hasInfoSeverity()) {
                            showSeverity = true;
                        } else if (filterOptions.showingLowSeverity() && entry.hasLowSeverity()) {
                            showSeverity = true;
                        } else if (filterOptions.showingMediumSeverity() && entry.hasMediumSeverity()) {
                            showSeverity = true;
                        } else if (filterOptions.showingHighSeverity() && entry.hasHighSeverity()) {
                            showSeverity = true;
                        } else if (filterOptions.showingCriticalSeverity() && entry.hasCriticalSeverity()) {
                            showSeverity = true;
                        }

                        if (showStatus && showSeverity) {
                            entry.toggleFalseNegative();
                        }
                    }
                }
                updateDisplayedChecks();
                updateChecks();
            }
        });

        showDataMenuItem = new JMenuItem("Copy all data to clipboard");
        showDataMenuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<AbstractSlurpHelperCheck> selectedChecks = getSelectedChecks();
                    String dataString = "";

                    for (AbstractSlurpHelperCheck check : selectedChecks) {
                        final List<String> data = check.getData();

                        if (data != null) {
                            for (String line : data) {
                                dataString += line;
                                dataString += "\n";
                            }
                        }
                        dataString += "\n";
                    }
                    StringSelection selection = new StringSelection(dataString);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, selection);
                }
         });

        permanentlyDeleteMenuItem = new JMenuItem("Delete (check will not run again)");
        permanentlyDeleteMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<AbstractSlurpHelperCheck> selectedChecks = getSelectedChecks();

                for (AbstractSlurpHelperCheck check : selectedChecks) {
                    SlurpHelper.getInstance().deleteCheckFromResultsAndBlacklist(check);
                    displayedChecks.remove(check);
                }
                updateChecks();
            }
        });

        permanentlyResetMenuItem = new JMenuItem("Reset (delete, check will run again if appropriate)");
        permanentlyResetMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<AbstractSlurpHelperCheck> selectedChecks = getSelectedChecks();

                for (AbstractSlurpHelperCheck check : selectedChecks) {
                    SlurpHelper.getInstance().deleteCheckFromResults(check);
                    displayedChecks.remove(check);
                }
                updateChecks();
            }
        });

        addToScopeMenuItem = new JMenuItem("Add to scope");
        addToScopeMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                IHttpRequestResponse selectedReqRes = displayedUrls.get(urlTable.convertRowIndexToModel(urlTable.getSelectedRow())).getHttpRequestResponse();
                IRequestInfo reqInfo = SlurpUtils.getInstance().getHelpers().analyzeRequest(selectedReqRes.getHttpService(), selectedReqRes.getRequest());
                SlurpUtils.getInstance().getCallbacks().includeInScope(reqInfo.getUrl());
            }
        });

        excludeFromScopeMenuItem = new JMenuItem("Exclude from scope");
        excludeFromScopeMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                IHttpRequestResponse selectedReqRes = displayedUrls.get(urlTable.convertRowIndexToModel(urlTable.getSelectedRow())).getHttpRequestResponse();
                IRequestInfo reqInfo = SlurpUtils.getInstance().getHelpers().analyzeRequest(selectedReqRes.getHttpService(), selectedReqRes.getRequest());
                SlurpUtils.getInstance().getCallbacks().excludeFromScope(reqInfo.getUrl());
            }
        });

        sendToRepeaterMenuItem = new JMenuItem("Send to Repeater");
        sendToRepeaterMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                IHttpRequestResponse selectedReqRes = displayedUrls.get(urlTable.convertRowIndexToModel(urlTable.getSelectedRow())).getHttpRequestResponse();
                boolean useHttps = false;
                if (selectedReqRes.getHttpService().getProtocol().toLowerCase().equals("https"))
                    useHttps = true;

                SlurpUtils.getInstance().getCallbacks().sendToRepeater(selectedReqRes.getHttpService().getHost(),
                        selectedReqRes.getHttpService().getPort(), useHttps, selectedReqRes.getRequest(), null);
            }
        });

        sendToIntruderMenuItem = new JMenuItem("Send to Intruder");
        sendToIntruderMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                IHttpRequestResponse selectedReqRes = displayedUrls.get(urlTable.convertRowIndexToModel(urlTable.getSelectedRow())).getHttpRequestResponse();
                boolean useHttps = false;
                if (selectedReqRes.getHttpService().getProtocol().toLowerCase().equals("https"))
                    useHttps = true;

                SlurpUtils.getInstance().getCallbacks().sendToIntruder(selectedReqRes.getHttpService().getHost(),
                        selectedReqRes.getHttpService().getPort(), useHttps, selectedReqRes.getRequest(), null);
            }
        });

        sendToSpiderMenuItem = new JMenuItem("Spider from here");
        sendToSpiderMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                IHttpRequestResponse selectedReqRes = displayedUrls.get(urlTable.convertRowIndexToModel(urlTable.getSelectedRow())).getHttpRequestResponse();
                IRequestInfo reqInfo = SlurpUtils.getInstance().getHelpers().analyzeRequest(selectedReqRes.getHttpService(), selectedReqRes.getRequest());
                SlurpUtils.getInstance().getCallbacks().sendToSpider(reqInfo.getUrl());
            }
        });

        tablePopupMenu = new JPopupMenu();
        tablePopupMenu.add(showDataMenuItem);
        tablePopupMenu.add(markFalsePositiveMenuItem);
        tablePopupMenu.add(markFalseNegativeMenuItem);
        tablePopupMenu.add(permanentlyDeleteMenuItem);

        final JPopupMenu urlPopupMenu = new JPopupMenu();
        urlPopupMenu.add(addToScopeMenuItem);
        urlPopupMenu.add(excludeFromScopeMenuItem);
        urlPopupMenu.add(new JSeparator());
        urlPopupMenu.add(sendToRepeaterMenuItem);
        urlPopupMenu.add(sendToIntruderMenuItem);
        urlPopupMenu.add(sendToSpiderMenuItem);


        resultTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    updateContextMenu();
                    tablePopupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        urlTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    // get the coordinates of the mouse click
                    Point p = e.getPoint();

                    // get the row index that contains that coordinate
                    int rowNumber = urlTable.rowAtPoint(p);
                    int colNumber = urlTable.columnAtPoint(p);

                    // Get the ListSelectionModel of the JTable
                    ListSelectionModel model = urlTable.getSelectionModel();

                    // set the selected interval of rows. Using the "rowNumber"
                    // variable for the beginning and end selects only that one row.
                    model.setSelectionInterval(rowNumber, rowNumber);
                    urlPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    urlTable.changeSelection(rowNumber, colNumber, false, false);
                }
            }
        });

        dataPane = new JTextArea("");
        dataPane.setEditable(false);
        dataPane.setCaretPosition(0);
        dataScrollPane = new JScrollPane(dataPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        urlDataSplitPanel = new JSplitPane();
        urlDataSplitPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        urlDataSplitPanel.setTopComponent(urlPanel);
        urlDataSplitPanel.setBottomComponent(dataScrollPane);
        urlDataSplitPanel.setResizeWeight(0.5d);

        resultSplitPanel = new JSplitPane();
        resultSplitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        resultSplitPanel.setLeftComponent(resultPane);
        resultSplitPanel.setRightComponent(urlDataSplitPanel);
        resultSplitPanel.setResizeWeight(.8d);

        requestResponseSplitPanel = new JSplitPane();
        requestResponseSplitPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        requestResponseSplitPanel.setLeftComponent(requestViewer.getComponent());
        requestResponseSplitPanel.setRightComponent(responseViewer.getComponent());
        requestResponseSplitPanel.setResizeWeight(.5d);

        lowerPanel = new JSplitPane();
        lowerPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
        lowerPanel.setTopComponent(resultSplitPanel);
        lowerPanel.setBottomComponent(requestResponseSplitPanel);
        lowerPanel.setResizeWeight(.5d);

        this.setTopComponent(filterPanel);
        this.setBottomComponent(lowerPanel);
    }

    public void updateChecks() {
        this.resultTableModel.fireTableDataChanged();
    }

    public void updateUrls() {
        this.urlTableModel.fireTableDataChanged();
    }

    public void updateDisplayedChecks() {
        final SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();
        final CopyOnWriteArrayList<AbstractSlurpHelperCheck> allChecks = factory.getAllChecks();
        ConcurrentHashMap<String, ArrayList<String>> checkDisplayed = new ConcurrentHashMap<String, ArrayList<String>>();
        this.displayedChecks = new ArrayList<AbstractSlurpHelperCheck>();

        for (AbstractSlurpHelperCheck entry : allChecks) {
            boolean showStatus = false;
            boolean showSeverity = false;
            String hostname = (String) entry.getValueByName("Host");
            String typeId = entry.getTypeId();

            if (checkDisplayed.containsKey(hostname)) {
                if (checkDisplayed.get(hostname).contains(typeId))
                    continue;
            }

            if (filterOptions.showingPositive() && entry.isPositive()) {
                showStatus = true;
            } else if (filterOptions.showingNegative() && entry.isNegative()) {
                showStatus = true;
            } else if (filterOptions.showingUncertain() && entry.isUncertain()) {
                showStatus = true;
            } else if (filterOptions.showingFalseNegative() && entry.isFalseNegative()) {
                showStatus = true;
            } else if (filterOptions.showingFalsePositive() && entry.isFalsePositive()) {
                showStatus = true;
            }

            if (filterOptions.showingInfoSeverity() && entry.hasInfoSeverity()) {
                showSeverity = true;
            } else if (filterOptions.showingLowSeverity() && entry.hasLowSeverity()) {
                showSeverity = true;
            } else if (filterOptions.showingMediumSeverity() && entry.hasMediumSeverity()) {
                showSeverity = true;
            } else if (filterOptions.showingHighSeverity() && entry.hasHighSeverity()) {
                showSeverity = true;
            } else if (filterOptions.showingCriticalSeverity() && entry.hasCriticalSeverity()) {
                showSeverity = true;
            }

            if (showStatus && showSeverity) {
                this.displayedChecks.add(entry);

                if (checkDisplayed.containsKey(hostname)) {
                    List<String> listKey = checkDisplayed.get(hostname);

                    if (listKey.contains(typeId)) {
                        return;
                    } else {
                        listKey.add(typeId);
                    }
                } else {
                    ArrayList<String> typeIdList = new ArrayList<String>();
                    typeIdList.add(typeId);
                    checkDisplayed.put(hostname, typeIdList);
                }
            }
        }
        this.updateChecks();
        this.updateUrls();
        this.updateProgress();
    }

    public void updateRequestResponsePanel(IHttpRequestResponse messageInfo) {
        requestViewer.setMessage(messageInfo.getRequest(), true);
        responseViewer.setMessage(messageInfo.getResponse(), false);
        currentlyDisplayedRequest = messageInfo;
    }

    public void updateRequests(String host, String typeId) {
        SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();
        final CopyOnWriteArrayList<AbstractSlurpHelperCheck> allChecks = factory.getAllChecks();
        this.displayedUrls = new ArrayList<SlurpHelperResultUrl>();

        for (AbstractSlurpHelperCheck entry : allChecks) {
            boolean showStatus = false;
            boolean showSeverity = false;
            String hostname = (String) entry.getValueByName("Host");

            if (!entry.getTypeId().equals(typeId)
                    || !hostname.equals(host))
                continue;

            if (filterOptions.showingPositive() && entry.isPositive()) {
                showStatus = true;
            } else if (filterOptions.showingNegative() && entry.isNegative()) {
                showStatus = true;
            } else if (filterOptions.showingUncertain() && entry.isUncertain()) {
                showStatus = true;
            } else if (filterOptions.showingFalseNegative() && entry.isFalseNegative()) {
                showStatus = true;
            } else if (filterOptions.showingFalsePositive() && entry.isFalsePositive()) {
                showStatus = true;
            }

            if (filterOptions.showingInfoSeverity() && entry.hasInfoSeverity()) {
                showSeverity = true;
            } else if (filterOptions.showingLowSeverity() && entry.hasLowSeverity()) {
                showSeverity = true;
            } else if (filterOptions.showingMediumSeverity() && entry.hasMediumSeverity()) {
                showSeverity = true;
            } else if (filterOptions.showingHighSeverity() && entry.hasHighSeverity()) {
                showSeverity = true;
            } else if (filterOptions.showingCriticalSeverity() && entry.hasCriticalSeverity()) {
                showSeverity = true;
            }

            if (showStatus && showSeverity) {
                final ArrayList<IHttpRequestResponse> urlList = entry.getRequestsAndResponses();
                for (IHttpRequestResponse url : urlList) {
                    SlurpHelperResultUrl URI = new SlurpHelperResultUrl(entry, url);

                    this.displayedUrls.add(URI);
                }
            }
        }
        this.updateUrls();
        if (urlTable.getRowCount() > 0) {
            urlTable.setRowSelectionInterval(0, 0);
            updateRequestResponsePanel(this.displayedUrls.get(0).getHttpRequestResponse());
        }
    }

    public void updateContextMenu() {
        AbstractSlurpHelperCheck selectedCheck;

        try {
            selectedCheck = displayedChecks.get(resultTable.convertRowIndexToModel(resultTable.getSelectedRow()));
        } catch (IndexOutOfBoundsException e) {
            return;
        }

        SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();

        tablePopupMenu = new JPopupMenu();
        tablePopupMenu.add(showDataMenuItem);

        if (resultTable.getSelectedRowCount() == 1) {

            if (selectedCheck.getStatus().equals("POSITIVE")
                    || selectedCheck.getStatus().equals("FALSE_POSITIVE")
                    || selectedCheck.getStatus().equals("UNCERTAIN"))
                tablePopupMenu.add(markFalsePositiveMenuItem);
            else if (selectedCheck.getStatus().equals("NEGATIVE")
                    || selectedCheck.getStatus().equals("FALSE_NEGATIVE"))
                tablePopupMenu.add(markFalseNegativeMenuItem);

            tablePopupMenu.add(permanentlyDeleteMenuItem);
            tablePopupMenu.add(permanentlyResetMenuItem);

            tablePopupMenu.add(new JSeparator());

            String selectedType = selectedCheck.getTypeId();
            List<JMenuItem> menuItems = factory.getContextMenu(selectedType);

            if (menuItems != null) {
                for (JMenuItem item : menuItems)
                    tablePopupMenu.add(item);
            }
        } else {
            tablePopupMenu.add(permanentlyResetMenuItem);
            tablePopupMenu.add(permanentlyDeleteMenuItem);
        }
    }

    public void updateData() {
        ArrayList<AbstractSlurpHelperCheck> selectedChecks = getSelectedChecks();
        this.dataPane.setText("");

        for (AbstractSlurpHelperCheck check : selectedChecks) {
            final List<String> data = check.getData();

            if (data != null) {
                for (String line : data) {
                    this.dataPane.append(line);
                    this.dataPane.append("\n");
                }
            }
            this.dataPane.append("\n");
        }
    }

    public ArrayList<SlurpHelperResultUrl> getDisplayedUrls() {
        return displayedUrls;
    }

    public ArrayList<AbstractSlurpHelperCheck> getDisplayedChecks() {
//        this.updateDisplayedChecks();
        return displayedChecks;
    }

    @Override
    public IHttpService getHttpService() {
        return currentlyDisplayedRequest.getHttpService();
    }

    @Override
    public byte[] getRequest() {
        return currentlyDisplayedRequest.getRequest();
    }

    @Override
    public byte[] getResponse() {
        return currentlyDisplayedRequest.getResponse();
    }

    public void updateProgress() {
        filterPanel.updateProgress();
    }

    private ArrayList<AbstractSlurpHelperCheck> getSelectedChecks() {
        ArrayList<AbstractSlurpHelperCheck> selectedChecks = new ArrayList<AbstractSlurpHelperCheck>();
        final CopyOnWriteArrayList<AbstractSlurpHelperCheck> results = SlurpHelper.getInstance().getResults();
        int [] selectedRowIndexes = resultTable.getSelectedRows();

        for (int selectedRowIndex : selectedRowIndexes) {
            AbstractSlurpHelperCheck selectedCheck = displayedChecks.get(resultTable.convertRowIndexToModel(selectedRowIndex));
            String typeId = selectedCheck.getTypeId();
            String selectedHostname = (String) selectedCheck.getValueByName("Host");

            for (AbstractSlurpHelperCheck entry : results) {
                boolean showStatus = false;
                boolean showSeverity = false;

                if (!entry.getTypeId().equals(typeId)
                        || !entry.getValueByName("Host").equals(selectedHostname))
                    continue;

                if (filterOptions.showingPositive() && entry.isPositive()) {
                    showStatus = true;
                } else if (filterOptions.showingNegative() && entry.isNegative()) {
                    showStatus = true;
                } else if (filterOptions.showingUncertain() && entry.isUncertain()) {
                    showStatus = true;
                } else if (filterOptions.showingFalseNegative() && entry.isFalseNegative()) {
                    showStatus = true;
                } else if (filterOptions.showingFalsePositive() && entry.isFalsePositive()) {
                    showStatus = true;
                }

                if (filterOptions.showingInfoSeverity() && entry.hasInfoSeverity()) {
                    showSeverity = true;
                } else if (filterOptions.showingLowSeverity() && entry.hasLowSeverity()) {
                    showSeverity = true;
                } else if (filterOptions.showingMediumSeverity() && entry.hasMediumSeverity()) {
                    showSeverity = true;
                } else if (filterOptions.showingHighSeverity() && entry.hasHighSeverity()) {
                    showSeverity = true;
                } else if (filterOptions.showingCriticalSeverity() && entry.hasCriticalSeverity()) {
                    showSeverity = true;
                }

                if (showStatus && showSeverity) {
                    selectedChecks.add(entry);
                }
            }
        }

        return selectedChecks;
    }

}
