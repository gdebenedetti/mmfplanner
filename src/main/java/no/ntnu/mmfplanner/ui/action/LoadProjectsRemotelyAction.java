/*
 * Copyright (C) 2017 Guido De Benedetti
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 */

package no.ntnu.mmfplanner.ui.action;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import no.ntnu.mmfplanner.model.MmfException;
import no.ntnu.mmfplanner.model.Project;
import no.ntnu.mmfplanner.ui.MainFrame;
import no.ntnu.mmfplanner.util.XmlDeserializer;
import nu.xom.ParsingException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.restlet.data.Parameter;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

/**
 * Loads a test project with associated data. This is the project used to calculate the ROI table on page 87
 * in Software by Numbers, and is used when demonstrating the ROI table.
 */
public class LoadProjectsRemotelyAction extends MainAbstractAction {
	private static final long serialVersionUID = 1L;

	private String projects;

	public static final String ACTION_NAME = "Load remotely a Project";

	public static final int ACTION_MNEMONIC = KeyEvent.VK_L;

	public static final String ACTION_ACCELERATOR = "ctrl L";

	public static final String ACTION_DESCRIPTION = "Loads a project remotely";

	public LoadProjectsRemotelyAction(MainFrame mainFrame) {
		super(mainFrame, ACTION_NAME, ACTION_MNEMONIC, ACTION_ACCELERATOR, ACTION_DESCRIPTION);
	}

	public void actionPerformed(ActionEvent e) {
		boolean cancel = mainFrame.queryProjectCloseSave();
		if (cancel) {
			return;
		}

		if (!loadProjectsRemotely()) {
			JOptionPane.showMessageDialog(mainFrame, "Failed to load projects remotely.", "Fail!",
					JOptionPane.WARNING_MESSAGE);
		} else {
			selectProject();
		}
	}

	private boolean loadProjectsRemotely() {
		final StringWriter stringWriter = new StringWriter();

		final JDialog d = new JDialog();
		JPanel p1 = new JPanel(new GridBagLayout());
		p1.add(new JLabel("Please Wait..."), new GridBagConstraints());
		d.getContentPane().add(p1);
		d.setSize(200, 200);
		d.setLocationRelativeTo(mainFrame);
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.setModal(true);

		SwingWorker<?, ?> worker = new SwingWorker<Boolean, Void>() {
			protected Boolean doInBackground() throws InterruptedException {

				ClientResource clientJSON = new ClientResource(
						"https://api.mlab.com/api/1/databases/mmf_planner_db/collections/projectsJSON");

				Parameter fields = new Parameter("f", "{_id\": 1, \"mmfproject.project.name\": 1}");
				Parameter sort = new Parameter("s", "{name\": 1}");
				Parameter apiKey = new Parameter("apiKey", "r5Kh17D7-6KVNy70vxx-aY20h7_2Pb4Q");

				clientJSON.addQueryParameter(fields);
				clientJSON.addQueryParameter(sort);
				clientJSON.addQueryParameter(apiKey);

				try {
					clientJSON.get().write(stringWriter);
				} catch (ResourceException | IOException e) {
					e.printStackTrace();
					return Boolean.FALSE;
				}

				if (!clientJSON.getStatus().isSuccess()) {
					return Boolean.FALSE;
				}

				projects = stringWriter.toString();

				return Boolean.TRUE;
			}

			protected void done() {
				d.dispose();
			}
		};
		worker.execute();
		d.setVisible(true);

		return true;
	}

	private void selectProject() {
		final JSONArray json = new JSONArray(projects);

		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i < json.length(); i++) {
			list.add(json.getJSONObject(i).getJSONObject("mmfproject").getJSONObject("project").getString("name"));
		}

		String s = (String) JOptionPane.showInputDialog(mainFrame, "Select the project to load:\n",
				"Load project remotely", JOptionPane.PLAIN_MESSAGE, null, list.toArray(), null);

		if ((s != null) && (s.length() > 0)) {
			for (int i = 0; i < json.length(); i++) {
				if (s.equals(json.getJSONObject(i).getJSONObject("mmfproject").getJSONObject("project")
						.getString("name"))) {

					final JDialog d = new JDialog();
					JPanel p1 = new JPanel(new GridBagLayout());
					p1.add(new JLabel("Please Wait..."), new GridBagConstraints());
					d.getContentPane().add(p1);
					d.setSize(200, 200);
					d.setLocationRelativeTo(mainFrame);
					d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					d.setModal(true);
					final int j = i;

					SwingWorker<?, ?> worker = new SwingWorker<Void, Void>() {
						protected Void doInBackground() throws InterruptedException {

							String URL = "https://api.mlab.com/api/1/databases/mmf_planner_db/collections/projectsJSON/"
									+ json.getJSONObject(j).getJSONObject("_id").getString("$oid");

							ClientResource clientJSON = new ClientResource(URL);

							Parameter apiKey = new Parameter("apiKey", "r5Kh17D7-6KVNy70vxx-aY20h7_2Pb4Q");

							clientJSON.addQueryParameter(apiKey);

							StringWriter stringWriter = new StringWriter();

							try {
								clientJSON.get().write(stringWriter);
							} catch (ResourceException | IOException e2) {
								// TODO
							}

							// get and remove _id
							String id = stringWriter.toString().substring(stringWriter.toString().indexOf("oid") + 8,
									stringWriter.toString().indexOf("} ,") - 1);
							JSONObject project = new JSONObject("{"
									+ stringWriter.toString().substring(stringWriter.toString().indexOf(',') + 1));
							String xml = XML.toString(project);

							// convert project from JSON to XML/Element
							try {
								InputStream stream = new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8")));
								Project p = XmlDeserializer.readProject(stream);
								p.setId(id);
								mainFrame.setModel(p);
							} catch (MmfException | IOException | ParsingException e1) {
								e1.printStackTrace();
							}

							return null;
						}

						protected void done() {
							d.dispose();
						}
					};
					worker.execute();
					d.setVisible(true);
				}
			}
		}
	}

}
