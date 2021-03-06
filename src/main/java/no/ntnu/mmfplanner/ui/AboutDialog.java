/*
 * Copyright (C) 2007 Snorre Gylterud, Stein Magnus Jodal, Johannes Knutsen,
 * Erik Bagge Ottesen, Ralf Bjarne Taraldset, and Iterate AS
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 */

package no.ntnu.mmfplanner.ui;

import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle;

/**
 * Creates an About dialog for the program.
 */
public class AboutDialog extends javax.swing.JDialog {
    private static final long serialVersionUID = 1L;

    /** Creates new form AboutDialog */
    public AboutDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        postInitComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        descLabel = new javax.swing.JLabel();
        iterateLogoLabel = new javax.swing.JLabel();
        ntnuLogoLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About");
        setLocationByPlatform(true);
        setName("aboutDialog"); // NOI18N

        titleLabel
                .setText("<html>\n<p>MMF Planner started as an idea at Iterate AS.\nIt was given as an assignment to the five students Snorre Gylterud, Stein Magnus Jodal, Johannes Knutsen, Erik Bagge Ottesen, and Ralf Bjarne Taraldset as a part of the Customer Driven Project at the Norwegian University of Science and Technology in the autumn of 2007.</p>\n<br>\n<p>The project is open source and available at <a href=\"https://github.com/jodal/mmfplanner\">https://github.com/jodal/mmfplanner</a></p>");
        titleLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        descLabel.setFont(new java.awt.Font("DejaVu Sans", 0, 24));
        descLabel.setText("MMF Planner");

        iterateLogoLabel.setFocusable(false);
        iterateLogoLabel.setMaximumSize(new java.awt.Dimension(100, 33));
        iterateLogoLabel.setMinimumSize(new java.awt.Dimension(100, 33));
        iterateLogoLabel.setPreferredSize(new java.awt.Dimension(100, 33));

        ntnuLogoLabel.setFocusable(false);
        ntnuLogoLabel.setMaximumSize(new java.awt.Dimension(100, 19));
        ntnuLogoLabel.setMinimumSize(new java.awt.Dimension(100, 19));
        ntnuLogoLabel.setPreferredSize(new java.awt.Dimension(100, 19));

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonAction(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout
                .setHorizontalGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addComponent(descLabel)
                                                        .addGroup(
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                titleLabel,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                323,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addGroup(
                                                                                layout
                                                                                        .createParallelGroup(
                                                                                                GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(
                                                                                                ntnuLogoLabel,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                100,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(
                                                                                                iterateLogoLabel,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                100,
                                                                                                GroupLayout.PREFERRED_SIZE))))
                                        .addContainerGap()).addGroup(
                                GroupLayout.Alignment.TRAILING,
                                layout.createSequentialGroup().addContainerGap(
                                        380, Short.MAX_VALUE).addComponent(
                                        closeButton,
                                        GroupLayout.PREFERRED_SIZE, 80,
                                        GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap()));
        layout
                .setVerticalGroup(layout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(descLabel)
                                        .addPreferredGap(
                                                LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addGroup(
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                iterateLogoLabel,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                33,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(
                                                                                ntnuLogoLabel,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                19,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED,
                                                                                92,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(
                                                                                closeButton,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                25,
                                                                                GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(
                                                                titleLabel,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                183,
                                                                Short.MAX_VALUE))
                                        .addContainerGap()));

        pack();
    }

    private void closeButtonAction(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeButtonAction
        this.dispose();
    }

    private javax.swing.JButton closeButton;
    private javax.swing.JLabel descLabel;
    private javax.swing.JLabel iterateLogoLabel;
    private javax.swing.JLabel ntnuLogoLabel;
    private javax.swing.JLabel titleLabel;

    private void postInitComponents() {
        URL ntnuLogo = getClass().getClassLoader().getResource("ntnu.png");
        if (ntnuLogo != null) {
            this.ntnuLogoLabel.setIcon(new ImageIcon(ntnuLogo));
        }

        URL iterateLogo = getClass().getClassLoader()
                .getResource("iterate.png");
        if (iterateLogo != null) {
            this.iterateLogoLabel.setIcon(new ImageIcon(iterateLogo));
        }
    }

}
