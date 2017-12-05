/*
 * Copyright (C) 2017 Guido De Benedetti
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 */

package no.ntnu.mmfplanner.ui;

import java.awt.Component;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import no.ntnu.mmfplanner.ui.action.OpenHPAgileAction;
import no.ntnu.mmfplanner.ui.action.OpenJiraAction;
import no.ntnu.mmfplanner.ui.action.OpenVersionOneAction;

public class ImportDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;

	private JButton btnClose;
	private JButton hpLogoButton;
	private JButton versionOneLogoButton;
	private JButton jiraLogoButton;

	public ImportDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		postInitComponents();
	}

	private void initComponents() {

		btnClose = new JButton("Close");
		hpLogoButton = new JButton(new OpenHPAgileAction((MainFrame) this.getParent()));
		versionOneLogoButton = new JButton(new OpenVersionOneAction((MainFrame) this.getParent()));
		jiraLogoButton = new JButton(new OpenJiraAction((MainFrame) this.getParent()));

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Import");
		setLocationByPlatform(true);
		setName("importDialog"); // NOI18N

		btnClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeButtonAction(evt);
			}
		});

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				groupLayout
						.createSequentialGroup()
						.addGap(29)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(jiraLogoButton, GroupLayout.PREFERRED_SIZE, 264,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.TRAILING)
														.addComponent(versionOneLogoButton, Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(hpLogoButton, Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)))
						.addGap(251).addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						groupLayout.createSequentialGroup().addContainerGap(287, Short.MAX_VALUE)
								.addComponent(btnClose).addContainerGap())
				.addGroup(
						Alignment.LEADING,
						groupLayout
								.createSequentialGroup()
								.addGap(29)
								.addComponent(hpLogoButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										groupLayout
												.createSequentialGroup()
												.addComponent(versionOneLogoButton, GroupLayout.PREFERRED_SIZE, 62,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(jiraLogoButton, GroupLayout.PREFERRED_SIZE, 86,
														GroupLayout.PREFERRED_SIZE)).addGap(29)));
		groupLayout.linkSize(SwingConstants.VERTICAL, new Component[] { hpLogoButton, versionOneLogoButton,
				jiraLogoButton });
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] { hpLogoButton, versionOneLogoButton,
				jiraLogoButton });
		getContentPane().setLayout(groupLayout);

		pack();
	}

	private void postInitComponents() {
	}

	private void closeButtonAction(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeButtonAction
		this.dispose();
	}

}
