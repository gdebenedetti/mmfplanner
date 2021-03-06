package no.ntnu.mmfplanner.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.text.NumberFormatter;

import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.TokenCredentials;
import net.rcarz.jiraclient.agile.AgileClient;
import net.rcarz.jiraclient.agile.Epic;
import net.rcarz.jiraclient.greenhopper.Backlog;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import net.rcarz.jiraclient.greenhopper.RapidView;
import no.ntnu.mmfplanner.jira.model.Board;

public class ImportJiraDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JTextField textURL;
	private JFormattedTextField txtProjectid;
	private JRadioButton rdbtnAgileClient;
	private JRadioButton rdbtnGreehopperClient;

	private MainFrame mainFrame;
	private Board board;
	private Backlog backlog;

	public ImportJiraDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		this.mainFrame = (MainFrame) parent;
		initComponents();
		postInitComponents();
	}

	private void initComponents() {
		setTitle("New project from JIRA");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setName("importJiraDialog"); // NOI18N
		setResizable(false);

		JLabel lblCredentials = new JLabel("JIRA Credentials");
		lblCredentials.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel lblUser = new JLabel("User*");
		JLabel lblPassword = new JLabel("Password*");
		JLabel lblUrl = new JLabel("URL*");
		JLabel lblProjectId = new JLabel("Project ID");

		txtUser = new JTextField();
		txtUser.setText("gdebenedetti");
		//txtUser.setText("agustin.dib@joinmosaic.com");
		txtUser.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setText("Master44!!");
		//txtPassword.setText("ApDpXlMosAI@2018");
		txtPassword.setColumns(10);

		textURL = new JTextField();
		textURL.setText("http://jirahn.tigo.com.hn");
		// https://joinmosaic.atlassian.net
		// https://jira.spring.io
		// https://issues.apache.org/jira/
		// https://tracker.fdvs.com.ar
		// http://jira.atlassian.com
		// RapidStart.jspa
		// http://jirahn.tigo.com.hn
		// https://jira.bikalabs.com
		textURL.setColumns(10);

		JLabel lblHttpsjiraspringio = new JLabel("https://jira.spring.io");
		lblHttpsjiraspringio.setEnabled(false);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);

		txtProjectid = new JFormattedTextField(formatter);
		// txtProjectid.setValue(43);
		txtProjectid.setValue(87);
		txtProjectid.setColumns(10);

		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				// check textfields no empty
				if ("".equals(txtUser.getText()) || "".equals(textURL.getText())) {
					return;
				}

				char[] password = txtPassword.getPassword();
				if (password == null || password.length == 0) {
					return;
				}

				if ("".equals(txtProjectid.getValue())) {
					return;
				}

				// check if is Agile or Greenhopper Client
				if (!(rdbtnAgileClient.isSelected() || rdbtnGreehopperClient.isSelected())) {
					return;
				}

				loadJira();

				if (board != null) {
					// load ok del backlog en Jira
					TransformJiraDialog transformJiraDialog = new TransformJiraDialog(mainFrame, true, board);
					transformJiraDialog.setVisible(true);
				} else if (backlog != null) {
					TransformJiraDialog transformJiraDialog = new TransformJiraDialog(mainFrame, true, backlog);
					transformJiraDialog.setVisible(true);
				}

				closeButtonAction(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		rdbtnAgileClient = new JRadioButton("Agile Client");
		rdbtnAgileClient.setSelected(true);
		rdbtnGreehopperClient = new JRadioButton("Greehopper Client");
		group.add(rdbtnAgileClient);
		group.add(rdbtnGreehopperClient);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblCredentials, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(565))
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.TRAILING, false)
														.addComponent(lblPassword, Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(lblUser, Alignment.LEADING,
																GroupLayout.PREFERRED_SIZE, 104,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.LEADING)
														.addComponent(txtUser, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(475, Short.MAX_VALUE))
						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.TRAILING)
														.addGroup(
																Alignment.LEADING,
																groupLayout
																		.createSequentialGroup()
																		.addComponent(rdbtnAgileClient)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(rdbtnGreehopperClient)
																		.addPreferredGap(ComponentPlacement.RELATED,
																				348, GroupLayout.PREFERRED_SIZE))
														.addGroup(
																Alignment.LEADING,
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(lblUrl)
																						.addComponent(lblProjectId))
																		.addGap(60)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												textURL,
																												GroupLayout.DEFAULT_SIZE,
																												333,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												lblHttpsjiraspringio))
																						.addComponent(
																								txtProjectid,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)))
														.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 184,
																GroupLayout.PREFERRED_SIZE)).addGap(127)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addComponent(lblCredentials, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblUser)
										.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblPassword)
										.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(11)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblUrl)
										.addComponent(textURL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE).addComponent(lblHttpsjiraspringio))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblProjectId)
										.addComponent(txtProjectid, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(rdbtnAgileClient)
										.addComponent(rdbtnGreehopperClient)).addGap(18).addComponent(btnConnect)
						.addGap(35)));
		getContentPane().setLayout(groupLayout);

		pack();
	}

	private void postInitComponents() {
	}

	private void closeButtonAction(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeButtonAction
		this.dispose();
	}

	private void loadJira() {

		final JDialog d = new JDialog();
		JPanel p1 = new JPanel(new GridBagLayout());
		p1.add(new JLabel("Loading Jira data. Please Wait..."), new GridBagConstraints());
		d.getContentPane().add(p1);
		d.setSize(300, 300);
		d.setLocationRelativeTo(mainFrame);
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.setModal(true);

		SwingWorker<?, ?> workerAgile = new SwingWorker<Board, Void>() {
			protected Board doInBackground() throws InterruptedException, JiraException {

//				 BasicCredentials credentials = new BasicCredentials(txtUser.getText(),
//				 String.valueOf(txtPassword
//				 .getPassword()));
				TokenCredentials credentials = new TokenCredentials(txtUser.getText(), String.valueOf(txtPassword
						.getPassword()));
				JiraClient jira = new JiraClient(textURL.getText(), credentials);

				AgileClient ac = new AgileClient(jira);

				// Configuration configuration = Configuration.get(ac.getRestclient(),
				// ((Number) txtProjectid.getValue()).intValue());

				net.rcarz.jiraclient.agile.Board agileBoard = ac
						.getBoard(((Number) txtProjectid.getValue()).intValue());

				Board board = new Board(agileBoard.getId(), agileBoard.getName(), agileBoard.getSelfURL(),
						agileBoard.getType());

				List<Epic> epics = agileBoard.getEpics();
				for (Epic epic : epics) {
					no.ntnu.mmfplanner.jira.model.Epic e = new no.ntnu.mmfplanner.jira.model.Epic(epic.getId(),
							epic.getName(), epic.getKey(), epic.getSummary(), epic.isDone());

					// TODO check if user has permision to view epic's issues
//					List<Issue> issues = epic.getIssues();
//					for (Issue issue : issues) {
//						no.ntnu.mmfplanner.jira.model.Issue i = new no.ntnu.mmfplanner.jira.model.Issue(issue.getId(),
//								issue.getName(), issue.getKey(), issue.getTimeTracking().getOriginalEstimate(), e);
//						e.getIssues().add(i);
//					}

					board.getEpics().add(e);
				}

				return board;
			}

			@Override
			public void done() {
				d.dispose();
				try {
					board = get();
				} catch (Exception e) {
					String why = null;
					Throwable cause = e.getCause();
					if (cause != null) {
						why = cause.getMessage();
					} else {
						why = e.getMessage();
					}
					System.err.println("Error retrieving file: " + why);
					JOptionPane.showMessageDialog(mainFrame, "Failed to load JIRA data.", "Fail!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		};

		SwingWorker<?, ?> workerGreehopper = new SwingWorker<Backlog, Void>() {
			protected Backlog doInBackground() throws InterruptedException, JiraException {

//				BasicCredentials credentials = new BasicCredentials(txtUser.getText(), String.valueOf(txtPassword
//						.getPassword()));
				TokenCredentials credentials = new TokenCredentials(txtUser.getText(), String.valueOf(txtPassword
						.getPassword()));
				JiraClient jira = new JiraClient(textURL.getText(), credentials);

				GreenHopperClient gh = new GreenHopperClient(jira);
				// ID del proyecto Spring XD
				RapidView rapidView = gh.getRapidView(((Number) txtProjectid.getValue()).intValue());

				/* Get backlog data */
				Backlog innerBacklog = rapidView.getBacklogData();

				return innerBacklog;
			}

			@Override
			public void done() {
				d.dispose();
				try {
					backlog = get();
				} catch (Exception e) {
					String why = null;
					Throwable cause = e.getCause();
					if (cause != null) {
						why = cause.getMessage();
					} else {
						why = e.getMessage();
					}
					System.err.println("Error retrieving file: " + why);
					JOptionPane.showMessageDialog(mainFrame, "Failed to load JIRA data.", "Fail!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		};

		if (rdbtnAgileClient.isSelected()) {
			workerAgile.execute();
		} else if (rdbtnGreehopperClient.isSelected()) {
			workerGreehopper.execute();
		}

		d.setVisible(true);
	}
}
