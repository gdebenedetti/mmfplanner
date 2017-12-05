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
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import no.ntnu.mmfplanner.ui.MainFrame;

/**
 * Action for opening a project from file.
 */
public class OpenHPAgileAction extends MainAbstractAction {

	private static final long serialVersionUID = 1L;

	public static final String ACTION_NAME = "Import HP Agile Manager Project...";

	public static final int ACTION_MNEMONIC = KeyEvent.VK_H;

	public static final String ACTION_ACCELERATOR = "ctrl O";

	public static final String ACTION_DESCRIPTION = "Import a project from HP Agile Manager";

	public OpenHPAgileAction(MainFrame mainFrame) {
		super(mainFrame, ACTION_NAME, ACTION_MNEMONIC, ACTION_ACCELERATOR, ACTION_DESCRIPTION);
		URL hpAgileManagerLogo = getClass().getClassLoader().getResource("hp_agile_manager.png");
		if (hpAgileManagerLogo != null) {
			putValue(Action.LARGE_ICON_KEY, new ImageIcon(new ImageIcon(hpAgileManagerLogo).getImage()
					.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
		}
	}

	public void actionPerformed(ActionEvent evt) {
		boolean cancel = mainFrame.queryProjectCloseSave();
		if (cancel) {
			return;
		}

		// show a file chooser
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setMultiSelectionEnabled(false);
		fc.setAcceptAllFileFilterUsed(true);
		FileFilter filter = new JiraFileFilter();
		fc.addChoosableFileFilter(filter);
		if (JFileChooser.APPROVE_OPTION != fc.showOpenDialog(mainFrame)) {
			return;
		}

		try {
			// serialize and write to file
			File file = fc.getSelectedFile();
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			// TODO Read HP Agile Manager File
			// Project project = XmlDeserializer.readProject(mainFrame.getTabPanePanelPlacement(), is);
			// mainFrame.setModel(project);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainFrame, "An error occured while opening HP Agile Manager project:\n" + e,
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
