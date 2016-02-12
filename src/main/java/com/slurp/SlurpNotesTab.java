package com.slurp;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SlurpNotesTab extends JSplitPane {
    JButton notesOpenButton;
    JButton notesSaveButton;
    JButton notesClearButton;
    JPanel notesPanel;
    JTextArea notesArea;
    JScrollPane notesScrollArea;
    private Action openAction;
    private Action saveAction;
    private Action exitAction;

    public SlurpNotesTab() {

        this.setOrientation(JSplitPane.VERTICAL_SPLIT);

        notesPanel = new JPanel();
        notesOpenButton = new JButton("Load");
        notesSaveButton = new JButton("Save");
        notesClearButton = new JButton("Clear");

        notesOpenButton.setBounds(50, 60, 80, 30);
        notesSaveButton.setBounds(50, 60, 80, 30);
        notesClearButton.setBounds(50, 60, 80, 30);

        notesPanel.add(notesOpenButton);
        notesPanel.add(notesSaveButton);
        notesPanel.add(notesClearButton);

        notesOpenButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                loadFromFile();
            }});

        notesSaveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                saveToFile();
            }});

        notesClearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                notesArea.setText(null);
            }
        });

        notesArea=new JTextArea();
        notesScrollArea=new JScrollPane(notesArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        this.setTopComponent(notesPanel);
        this.setBottomComponent(notesScrollArea);
    }

    public void loadFromFile() {
        JOptionPane.showMessageDialog(null, "Not implemented yet!", "Not implemented", JOptionPane.ERROR_MESSAGE);
    }

    public void saveToFile() {
        JOptionPane.showMessageDialog(null, "Not implemented yet!", "Not implemented", JOptionPane.ERROR_MESSAGE);
    }

    public void clearNotes() {
        this.notesArea.setText(null);
    }
}
