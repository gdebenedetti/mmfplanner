/*
 * Copyright (C) 2007 Snorre Gylterud, Stein Magnus Jodal, Johannes Knutsen,
 * Erik Bagge Ottesen, Ralf Bjarne Taraldset, and Iterate AS
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 */

package no.ntnu.mmfplanner.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import no.ntnu.mmfplanner.ui.MainFrame;
import no.ntnu.mmfplanner.util.XmlSerializer;
import nu.xom.Document;
import nu.xom.Element;

import org.json.JSONObject;
import org.json.XML;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

/**
 * Serializes the current project to XML and saves it to disk.
 */
public class SaveProjectRemotellyAction extends MainAbstractAction {

	private static final long serialVersionUID = 1L;

	public static final String ACTION_NAME = "Save Project remotelly...";

	public static final int ACTION_MNEMONIC = KeyEvent.VK_S;

	public static final String ACTION_ACCELERATOR = "ctrl S";

	public static final String ACTION_DESCRIPTION = "Save the current project";

	public SaveProjectRemotellyAction(MainFrame mainFrame) {
		super(mainFrame, ACTION_NAME, ACTION_MNEMONIC, ACTION_ACCELERATOR, ACTION_DESCRIPTION);
	}

	public void actionPerformed(ActionEvent evt) {
		if (save()) {
			JOptionPane.showMessageDialog(mainFrame, "Project remotely saved successfully.");
		} else {
			JOptionPane.showMessageDialog(mainFrame, "Failed to save the project remotely.", "Fail!",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public boolean save() {

		ClientResource clientJSON = new ClientResource(
				"https://api.mlab.com/api/1/databases/mmf_planner_db/collections/projectsJSON?apiKey=r5Kh17D7-6KVNy70vxx-aY20h7_2Pb4Q");

		// ClientResource clientXML = new ClientResource(
		// "https://api.mlab.com/api/1/databases/mmf_planner_db/collections/projectsXML?apiKey=r5Kh17D7-6KVNy70vxx-aY20h7_2Pb4Q");

		Document document = XmlSerializer.workspaceToDocument(mainFrame.getTabPanePanelPlacement(),
				mainFrame.getProject());

		Element root = document.getRootElement();

		JSONObject json = XML.toJSONObject(root.toXML());

		StringRepresentation stringRep = new StringRepresentation(json.toString());
		stringRep.setMediaType(MediaType.APPLICATION_JSON);

		clientJSON.setMethod(Method.POST);
		clientJSON.getReference().addQueryParameter("format", "json");
		clientJSON.post(stringRep, MediaType.APPLICATION_JSON);

		return clientJSON.getStatus().isSuccess();
	}
}
