package com.slurp.helper;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.SlurpUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SlurpAutoHelperOptionsTab extends JPanel {
    private final SlurpUtils utils;
    SlurpAutoHelperOptions options;
    DefaultListModel knownListModel;
    JList knownList;
    DefaultListModel enabledListModel;
    JList enabledList;

    public SlurpAutoHelperOptionsTab() {
        this.utils = SlurpUtils.getInstance();


        JButton pluginOptionButton = new JButton("Check option");
        pluginOptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object [] selected = enabledList.getSelectedValues();
                SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();

                for (AbstractSlurpCheckBlueprint bp : factory.getKnownBluePrints()) {
                    for (Object elem : selected) {
                        if (bp.getTypeId().equals((String) elem)) {
                            JFrame popUp = new JFrame();
                            JPanel content = bp.getOptionsMenu();

                            if (content != null) {
                                popUp.setTitle("Check options");
                                popUp.setLocationRelativeTo(null);
                                popUp.setSize(new Dimension(400, 400));
                                popUp.setContentPane(content);
                                popUp.setVisible(true);
                            }
                        }
                    }
                }
            }
        });

        enabledListModel = new DefaultListModel();
        enabledList = new javax.swing.JList(enabledListModel);
        enabledList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.updateEnabledPanel();

        knownListModel = new DefaultListModel();
        knownList = new javax.swing.JList(knownListModel);
        knownList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.updateKnownPanel();

        final JScrollPane leftPane = new JScrollPane(knownList);
        leftPane.setSize(300, 200);
        leftPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        leftPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        leftPane.setVisible(true);

        final JScrollPane rightPane = new JScrollPane(enabledList);
        rightPane.setSize(300, 200);
        rightPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rightPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        rightPane.setVisible(true);

        final JButton addButton = new JButton("Add ->");
        final JButton removeButton = new JButton("<- Remove");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object [] selected = knownList.getSelectedValues();

                for (Object elem : selected) {
                    addToEnabled((String) elem);
                }
//                updateEnabledPanel();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Object [] selected = enabledList.getSelectedValues();

                for (Object elem : selected) {
                    removeFromEnabled((String) elem);
                }
//                updateEnabledPanel();
            }
        });

        this.add(leftPane);
        this.add(addButton);
        this.add(removeButton);
        this.add(rightPane);
        this.add(pluginOptionButton);
    }

    public void addToEnabled(String typeId) {
        SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();

        for (AbstractSlurpCheckBlueprint blueprint : factory.getKnownBluePrints()) {
            if (blueprint.getTypeId().equals(typeId)) {
                System.out.println("Enabling " + typeId);
                blueprint.enable();
                this.enabledListModel.addElement(typeId);
                this.knownListModel.removeElement(typeId);
            }
        }
    }

    public void removeFromEnabled(String typeId) {
        SlurpHelperCheckFactory factory = SlurpHelperCheckFactory.getInstance();

        for (AbstractSlurpCheckBlueprint blueprint : factory.getKnownBluePrints()) {
            if (blueprint.getTypeId().equals(typeId)) {
                System.out.println("Disabling " + typeId);
                blueprint.disable();
                this.enabledListModel.removeElement(typeId);
                this.knownListModel.addElement(typeId);
            }
        }
    }

    public void updateKnownPanel() {
        List<AbstractSlurpCheckBlueprint> inventory = SlurpAutoHelperOptions.getInstance().getAllBluePrints();
        this.knownListModel.removeAllElements();

        for (AbstractSlurpCheckBlueprint bp : inventory) {
            if (!enabledListModel.contains(bp.getTypeId()))
                this.knownListModel.addElement(bp.getTypeId());
        }
    }

    public void updateEnabledPanel() {
        List<AbstractSlurpCheckBlueprint> inventory = SlurpAutoHelperOptions.getInstance().getEnabledBluePrints();
        this.enabledListModel.removeAllElements();

        for (AbstractSlurpCheckBlueprint bp : inventory) {
            if (bp.isEnabled()) {
                enabledListModel.addElement(bp.getTypeId());
            }
        }
    }
}
