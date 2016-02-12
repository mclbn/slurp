package com.slurp;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import burp.IBurpExtenderCallbacks;
import burp.IContextMenuFactory;
import burp.IContextMenuInvocation;
import burp.IExtensionHelpers;
import com.slurp.helper.SlurpHelperTab;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class SlurpMenu implements IContextMenuFactory {
    private ArrayList<JMenuItem> menuList;
    private final IBurpExtenderCallbacks callbacks;
    private final IExtensionHelpers helpers;
    private SlurpHelperTab scannerTab;

    public SlurpMenu(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        helpers = callbacks.getHelpers();
    }

    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        menuList = new ArrayList<JMenuItem>();
        JMenuItem helperItem = new JMenuItem("Send to Slurp");

        helperItem.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Not implemented yet");
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        menuList.add(helperItem);

        return menuList;
    }

}
