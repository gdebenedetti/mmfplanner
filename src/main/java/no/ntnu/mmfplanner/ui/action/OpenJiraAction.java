/*
 * Copyright (C) 2017 Guido De Benedetti
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 */

package no.ntnu.mmfplanner.ui.action;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.Action;
import javax.swing.ImageIcon;

import no.ntnu.mmfplanner.ui.ImportJiraDialog;
import no.ntnu.mmfplanner.ui.MainFrame;

/**
 * Action for opening a project from file.
 */
public class OpenJiraAction extends MainAbstractAction {

	private static final long serialVersionUID = 1L;

	public static final String ACTION_NAME = "Create New Project from JIRA";

	public static final int ACTION_MNEMONIC = KeyEvent.VK_J;

	public static final String ACTION_ACCELERATOR = "ctrl J";

	public static final String ACTION_DESCRIPTION = "Import data project from Jira";

	public OpenJiraAction(MainFrame mainFrame) {
		super(mainFrame, ACTION_NAME, ACTION_MNEMONIC, ACTION_ACCELERATOR, ACTION_DESCRIPTION);
		URL jiraLogo = getClass().getClassLoader().getResource("jira.png");
		if (jiraLogo != null) {
			putValue(Action.LARGE_ICON_KEY,
					new ImageIcon(new ImageIcon(jiraLogo).getImage().getScaledInstance(165, 75, Image.SCALE_SMOOTH)));
		}
	}

	public void actionPerformed(ActionEvent evt) {
		boolean cancel = mainFrame.queryProjectCloseSave();
		if (cancel) {
			return;
		}

		ImportJiraDialog importJiraDialog = new ImportJiraDialog(mainFrame, true);
		importJiraDialog.setVisible(true);
	}
}
