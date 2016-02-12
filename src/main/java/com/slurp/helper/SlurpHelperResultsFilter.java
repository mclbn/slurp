package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.SlurpUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlurpHelperResultsFilter extends JPanel {
    JButton filterButton;
    SlurpHelperResultsPane resultPanel;
    SlurpHelperFilterOptions filterOptions;
    JCheckBox scopeOnlyCheckBox;
    JSeparator separatorOne, separatorTwo, separatorThree, separatorFour;
    JCheckBox positiveCheckbox;
    JCheckBox falseNegativeCheckbox;
    JCheckBox uncertainCheckbox;
    JCheckBox falsePositiveCheckbox;
    JCheckBox negativeCheckbox;
    JCheckBox lowCheckBox, infoCheckBox, mediumCheckBox, highCheckBox, criticalCheckBox;
    JButton checksProgress;
    SlurpResultPendingChecksTableModel pendingTableModel;
    SlurpResultPendingChecksTable pendingTable;
    JScrollPane pendingScrollPane;

    public SlurpHelperResultsFilter() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        resultPanel = SlurpHelperResultsPane.getInstance();
        filterOptions = SlurpHelperFilterOptions.getInstance();

        scopeOnlyCheckBox = new JCheckBox("In scope only");
        scopeOnlyCheckBox.setSelected(true);

        separatorOne = new JSeparator(SwingConstants.VERTICAL);
        separatorOne.setPreferredSize(new Dimension(5, 1));

        positiveCheckbox = new JCheckBox("Positive");
        positiveCheckbox.setSelected(true);
        falseNegativeCheckbox = new JCheckBox("False negative");
        falseNegativeCheckbox.setSelected(true);
        uncertainCheckbox = new JCheckBox("Uncertain");
        uncertainCheckbox.setSelected(true);
        falsePositiveCheckbox = new JCheckBox("False Positive");
        falsePositiveCheckbox.setSelected(false);
        negativeCheckbox = new JCheckBox("Negative");
        negativeCheckbox.setSelected(false);

        separatorTwo = new JSeparator(SwingConstants.VERTICAL);
        separatorTwo.setPreferredSize(new Dimension(5, 1));

        infoCheckBox = new JCheckBox("Info");
        infoCheckBox.setSelected(true);
        lowCheckBox = new JCheckBox("Low");
        lowCheckBox.setSelected(true);
        mediumCheckBox = new JCheckBox("Medium");
        mediumCheckBox.setSelected(true);
        highCheckBox = new JCheckBox("High");
        highCheckBox.setSelected(true);
        criticalCheckBox = new JCheckBox("Critical");
        criticalCheckBox.setSelected(true);

        separatorThree = new JSeparator(SwingConstants.VERTICAL);
        separatorThree.setPreferredSize(new Dimension(5, 1));

        //        searchTextField = new JTextField("Not implemented yet :-)", 30);
        filterButton = new JButton("Filter / Refresh");
        filterButton.setBounds(50, 60, 80, 30);

        separatorFour = new JSeparator(SwingConstants.VERTICAL);
        separatorFour.setPreferredSize(new Dimension(5, 1));

        pendingTableModel = new SlurpResultPendingChecksTableModel();


        checksProgress = new JButton("Pending checks: 0");
        checksProgress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SlurpResultPendingCheckFrame popUp = SlurpResultPendingCheckFrame.getInstance();
                pendingTable = new SlurpResultPendingChecksTable(pendingTableModel);
                pendingTable.getColumnModel().getColumn(0).setPreferredWidth(20);
                pendingTable.getColumnModel().getColumn(1).setPreferredWidth(100);
                pendingTable.getColumnModel().getColumn(2).setPreferredWidth(300);
                pendingTable.getColumnModel().getColumn(3).setPreferredWidth(300);
                pendingTable.setAutoCreateRowSorter(true);
                pendingScrollPane = new JScrollPane(pendingTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

                popUp.setTitle("Pending Checks");
                popUp.setLocationRelativeTo(null);
                popUp.setSize(new Dimension(750, 350));
                popUp.add(pendingScrollPane);
                popUp.setVisible(true);
            }
        });

        final JToggleButton enableButton;
        final SlurpAutoHelperOptions options = SlurpAutoHelperOptions.getInstance();
        final SlurpUtils utils = SlurpUtils.getInstance();

        enableButton = new JToggleButton("Disable");
        enableButton.setBounds(50, 60, 80, 30);
        enableButton.setSelected(true);
        options.enable();

        enableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (options.isEnabled()) {
                    options.disable();
                    enableButton.setText("Start");
                    utils.getCallbacks().issueAlert("Slurp Auto Scanner disabled.");
                    enableButton.setSelected(false);
                } else {
                    options.enable();
                    enableButton.setText("Stop");
                    utils.getCallbacks().issueAlert("Slurp Auto Scanner enabled.");
                    enableButton.setSelected(true);
                }

            }
        });

        final JToggleButton autoRefreshButton;

        autoRefreshButton = new JToggleButton("Auto Refresh");
        autoRefreshButton.setBounds(50, 60, 80, 30);
        autoRefreshButton.setSelected(true);
        options.enableAutoRefresh();

        autoRefreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (options.autoRefreshEnabled()) {
                    options.disableAutoRefresh();
                    utils.getCallbacks().issueAlert("Auto Refresh disabled.");
                    autoRefreshButton.setSelected(false);
                } else {
                    options.enableAutoRefresh();
                    utils.getCallbacks().issueAlert("Auto Refresh enabled.");
                    autoRefreshButton.setSelected(true);
                    SlurpHelperResultsPane.getInstance().updateDisplayedChecks();
                }

            }
        });



        JButton optionsButton = new JButton("Options");
        optionsButton.setBounds(50, 60, 80, 30);

        optionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // gerre les options
            }
        });

        layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addComponent(enableButton)
                        .addComponent(separatorOne, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(positiveCheckbox)
                                                .addComponent(falseNegativeCheckbox)
                                                .addComponent(uncertainCheckbox))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(falsePositiveCheckbox)
                                                .addComponent(negativeCheckbox))))
                        .addComponent(separatorTwo, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(criticalCheckBox)
                                                .addComponent(highCheckBox)
                                                .addComponent(mediumCheckBox))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(lowCheckBox)
                                                .addComponent(infoCheckBox))))
                        .addComponent(separatorThree, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(filterButton)
                                .addComponent(autoRefreshButton))
                        .addComponent(separatorFour, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checksProgress)
        );


        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(enableButton)
                        .addComponent(separatorOne)
                        .addComponent(positiveCheckbox)
                        .addComponent(falsePositiveCheckbox)
                        .addComponent(separatorTwo)
                        .addComponent(criticalCheckBox)
                        .addComponent(lowCheckBox)
                        .addComponent(separatorThree)
                        .addComponent(filterButton)
                        .addComponent(separatorFour)
                        .addComponent(checksProgress))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(falseNegativeCheckbox)
                                        .addComponent(negativeCheckbox)
                                        .addComponent(highCheckBox)
                                        .addComponent(infoCheckBox)
                                        .addComponent(autoRefreshButton))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(uncertainCheckbox)
                                        .addComponent(mediumCheckBox))))

        );


        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (scopeOnlyCheckBox.isSelected())
                    filterOptions.showScopeOnly();
                else
                    filterOptions.showOutOfScope();

                if (positiveCheckbox.isSelected())
                    filterOptions.showPositive();
                else
                    filterOptions.hidePositive();

                if (uncertainCheckbox.isSelected()) {
                    filterOptions.showUncertain();
                } else {
                    filterOptions.hideUncertain();
                }

                if (falseNegativeCheckbox.isSelected()) {
                    filterOptions.showFalseNegative();
                } else {
                    filterOptions.hideFalseNegative();
                }

                if (falsePositiveCheckbox.isSelected()) {
                    filterOptions.showFalsePositive();
                } else {
                    filterOptions.hideFalsePositive();
                }

                if (negativeCheckbox.isSelected()) {
                    filterOptions.showNegative();
                } else {
                    filterOptions.hideNegative();
                }

                if (infoCheckBox.isSelected()) {
                    filterOptions.showInfoSeverity();
                } else {
                    filterOptions.hideInfoSeverity();
                }

                if (lowCheckBox.isSelected()) {
                    filterOptions.showLowSeverity();
                } else {
                    filterOptions.hideLowSeverity();
                }

                if (mediumCheckBox.isSelected()) {
                    filterOptions.showMediumSeverity();
                } else {
                    filterOptions.hideMediumSeverity();
                }

                if (highCheckBox.isSelected()) {
                    filterOptions.showHighSeverity();
                } else {
                    filterOptions.hideHighSeverity();
                }

                if (criticalCheckBox.isSelected()) {
                    filterOptions.showCriticalSeverity();
                } else {
                    filterOptions.hideCriticalSeverity();
                }

                resultPanel.updateDisplayedChecks();
            }
        });
    }

    public void updateProgress() {
        SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();
        checksProgress.setText("Pending checks: " + factory.getPendingChecks().size());
        pendingTableModel.fireTableDataChanged();
    }
}
