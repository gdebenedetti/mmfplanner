package no.ntnu.mmfplanner.ui;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ImportJiraDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;

	private JTextField txtUser;
	private JTextField txtPassword;
	private JTextField textURL;

	public ImportJiraDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
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
		JLabel lblUser = new JLabel("User");
		JLabel lblPassword = new JLabel("Password");
		JLabel lblUrl = new JLabel("URL");

		txtUser = new JTextField();
		txtUser.setText("user");
		txtUser.setColumns(10);

		txtPassword = new JTextField();
		txtPassword.setText("password");
		txtPassword.setColumns(10);

		textURL = new JTextField();
		textURL.setColumns(10);

		JLabel lblHttpsjiraspringio = new JLabel("https://jira.spring.io");
		lblHttpsjiraspringio.setEnabled(false);

		JButton btnConnect = new JButton("Connect");
		JButton btnClose = new JButton("Close");

		btnClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				closeButtonAction(evt);
			}
		});

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
																						.addComponent(lblUrl))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								Alignment.TRAILING,
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												textURL,
																												GroupLayout.DEFAULT_SIZE,
																												165,
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
																												230,
																												Short.MAX_VALUE))))
														.addGroup(
																Alignment.TRAILING,
																groupLayout
																		.createSequentialGroup()
																		.addGap(267)
																		.addComponent(btnClose,
																				GroupLayout.DEFAULT_SIZE, 68,
																				Short.MAX_VALUE)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(btnConnect,
																				GroupLayout.DEFAULT_SIZE, 73,
																				Short.MAX_VALUE).addContainerGap()))));
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
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblUrl)
										.addComponent(textURL, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE).addComponent(lblHttpsjiraspringio))
						.addPreferredGap(ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnConnect)
										.addComponent(btnClose)).addContainerGap()));
		getContentPane().setLayout(groupLayout);

		pack();
	}

	private void postInitComponents() {
	}

	private void closeButtonAction(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeButtonAction
		this.dispose();
	}
}
