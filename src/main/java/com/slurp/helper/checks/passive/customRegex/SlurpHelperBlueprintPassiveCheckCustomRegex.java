package com.slurp.helper.checks.passive.customRegex;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.helper.AbstractSlurpHelperCheck;
import com.slurp.helper.AbstractSlurpCheckBlueprint;
import com.slurp.helper.SlurpHelperAnalyzedRequestResponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SlurpHelperBlueprintPassiveCheckCustomRegex extends AbstractSlurpCheckBlueprint {
    static private SlurpHelperBlueprintPassiveCheckCustomRegex instance = new SlurpHelperBlueprintPassiveCheckCustomRegex();
    private String regex = "http://.*/*";
    private boolean matchRequest = false;

    private SlurpHelperBlueprintPassiveCheckCustomRegex() {
        this.setTitle("Custom regex.");
        this.setDescription("You custom regex matched.");
        this.setTypeId("PASSIVE_REGEX");
        this.setRunOncePerUrl();
        this.disable();
    }

    public static SlurpHelperBlueprintPassiveCheckCustomRegex getInstance() {
        return instance;
    }

    @Override
    public AbstractSlurpHelperCheck getNewCheck(int id, SlurpHelperAnalyzedRequestResponse analyzed, AbstractSlurpHelperCheck parent) {
        SlurpHelperPassiveCheckCustomRegex newCheck = new SlurpHelperPassiveCheckCustomRegex(id, analyzed, parent);

        newCheck.setInfoSeverity();
        newCheck.setTitle(this.getTitle());
        newCheck.setDescription(this.getDescription());
        newCheck.setTypeId(this.getTypeId());
        newCheck.setRegex(this.regex);
        newCheck.setBlueprint(instance);

        if (matchRequest) // I should implement a way to set thisto true :-)
            newCheck.matchRequest();

        return newCheck;
    }

    @Override
    public List<JMenuItem> getContextMenu() {
        return null;
    }

    @Override
    public JPanel getOptionsMenu() {
        JPanel panel = new JPanel();
        final JTextField regexTextField = new JTextField(20);
        final JButton applyButton = new JButton("Apply");

        regexTextField.setText(this.regex);
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                regex = regexTextField.getText();
            }
        });
        regexTextField.setEditable(true);
        panel.add(regexTextField);
        panel.add(applyButton);

        return panel;
    }

    @Override
    public void resetForHost(String host) {

    }
}
