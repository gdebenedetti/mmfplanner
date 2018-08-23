package no.ntnu.mmfplanner.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.NumberFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.text.NumberFormatter;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.greenhopper.Backlog;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import net.rcarz.jiraclient.greenhopper.RapidView;

public class ImportJiraDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JTextField textURL;
	private JFormattedTextField txtProjectid;

	private MainFrame mainFrame;
	private Backlog backlog;
	private JTextField textField;

	public ImportJiraDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		this.mainFrame = (MainFrame) parent;
		initComponents();
		postInitComponents();
	}

	private void initComponents() {
		setTitle("Jira Credentials");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setName("importJiraDialog"); // NOI18N
		setResizable(false);

		JLabel lblCredentials = new JLabel("Credentials");
		lblCredentials.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JLabel lblUser = new JLabel("User*");
		JLabel lblPassword = new JLabel("Password*");
		JLabel lblUrl = new JLabel("URL*");
		JLabel lblProjectId = new JLabel("Project ID");

		txtUser = new JTextField();
		txtUser.setText("gdebenedetti");
		txtUser.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setText("Master44!!");
		txtPassword.setColumns(10);

		textURL = new JTextField();
		textURL.setText("https://jira.spring.io");
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
		txtProjectid.setValue(43);
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

				loadJira();

				if (backlog != null) {
					// load ok del backlog en Jira
					// TODO a procesar la transformacion Backlog2IFM
				}

				closeButtonAction(evt);
			}
		});

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeButtonAction(evt);
			}
		});

		JSeparator separator = new JSeparator();

		JLabel lblTransformationOptions = new JLabel("Transformation options");

		JCheckBox chckbxEpic = new JCheckBox("1 Epic \u2261 1 MMF");
		chckbxEpic.setHorizontalAlignment(SwingConstants.LEFT);

		JSlider slider = new JSlider();
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);

		JLabel lblNewLabel = new JLabel("1 MMF \u2261 1 US");

		JLabel lblNewLabel_1 = new JLabel("1 MMF \u2261 N US");

		JLabel lblStoryPointsAverage = new JLabel("Story points average");

		textField = new JTextField();
		textField.setColumns(10);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.TRAILING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(separator,
																				GroupLayout.DEFAULT_SIZE, 487,
																				Short.MAX_VALUE).addContainerGap())
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(lblStoryPointsAverage)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(221)
																										.addComponent(
																												btnClose,
																												GroupLayout.DEFAULT_SIZE,
																												105,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												btnConnect,
																												GroupLayout.DEFAULT_SIZE,
																												109,
																												Short.MAX_VALUE))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addPreferredGap(
																												ComponentPlacement.UNRELATED)
																										.addComponent(
																												textField,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)))
																		.addContainerGap())
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createParallelGroup(
																												Alignment.TRAILING,
																												false)
																										.addComponent(
																												lblCredentials,
																												Alignment.LEADING,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												lblPassword,
																												Alignment.LEADING,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												lblUser,
																												Alignment.LEADING,
																												GroupLayout.DEFAULT_SIZE,
																												104,
																												Short.MAX_VALUE))
																						.addComponent(lblUrl)
																						.addComponent(lblProjectId))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												txtProjectid,
																												GroupLayout.PREFERRED_SIZE,
																												GroupLayout.DEFAULT_SIZE,
																												GroupLayout.PREFERRED_SIZE)
																										.addContainerGap(
																												303,
																												Short.MAX_VALUE))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												textURL,
																												GroupLayout.DEFAULT_SIZE,
																												238,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												lblHttpsjiraspringio)
																										.addGap(50))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																txtUser,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																txtPassword,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE))
																										.addContainerGap(
																												303,
																												Short.MAX_VALUE))))
														.addGroup(
																groupLayout.createSequentialGroup()
																		.addComponent(lblTransformationOptions)
																		.addContainerGap(386, Short.MAX_VALUE))))
						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addGap(10)
										.addComponent(chckbxEpic)
										.addPreferredGap(ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
										.addComponent(lblNewLabel)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblNewLabel_1).addGap(35)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addComponent(lblCredentials, GroupLayout.PREFERRED_SIZE, 34,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblUser)
														.addComponent(txtUser, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblPassword)
														.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblUrl)
														.addComponent(textURL, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(lblHttpsjiraspringio))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblProjectId)
														.addComponent(txtProjectid, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(
												groupLayout
														.createParallelGroup(Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addPreferredGap(
																												ComponentPlacement.UNRELATED)
																										.addComponent(
																												lblTransformationOptions)
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.UNRELATED)
																																		.addComponent(
																																				slider,
																																				GroupLayout.PREFERRED_SIZE,
																																				GroupLayout.DEFAULT_SIZE,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addGap(15)
																																		.addComponent(
																																				chckbxEpic))))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(44)
																										.addComponent(
																												lblNewLabel)))
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								textField,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								lblStoryPointsAverage)))
														.addGroup(
																groupLayout.createSequentialGroup().addGap(44)
																		.addComponent(lblNewLabel_1)))
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(btnConnect).addComponent(btnClose))
										.addContainerGap()));
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
		p1.add(new JLabel("Please Wait..."), new GridBagConstraints());
		d.getContentPane().add(p1);
		d.setSize(100, 100);
		d.setLocationRelativeTo(mainFrame);
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.setModal(true);

		SwingWorker<?, ?> worker = new SwingWorker<Backlog, Void>() {
			protected Backlog doInBackground() throws InterruptedException, JiraException {

				BasicCredentials creds = new BasicCredentials(txtUser.getText(), String.valueOf(txtPassword
						.getPassword()));
				JiraClient jira = new JiraClient(textURL.getText(), creds);
				GreenHopperClient gh = new GreenHopperClient(jira);

				// ID del proyecto Spring XD
				RapidView board = gh.getRapidView(((Number) txtProjectid.getValue()).intValue());

				/* Get backlog data */
				Backlog innerBacklog = board.getBacklogData();
				// board.getId();
				// board.getName();
				// board.getSprints();

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
					JOptionPane.showMessageDialog(mainFrame, "Failed to load Jira projects.", "Fail!",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		};
		worker.execute();
		d.setVisible(true);
	}
}
