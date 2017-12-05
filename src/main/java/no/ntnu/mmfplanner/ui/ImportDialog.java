package no.ntnu.mmfplanner.ui;

import java.awt.Component;
import java.awt.Image;
import java.net.URL;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import no.ntnu.mmfplanner.ui.action.OpenJiraAction;

public class ImportDialog extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;

	private JButton btnClose;
	private JLabel hpLogoLabel;
	private JLabel versionOneLogoLabel;
	private JButton jiraLogoButton;

	public ImportDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		postInitComponents();
	}

	private void initComponents() {

		btnClose = new JButton("Close");
		hpLogoLabel = new JLabel("HP Agile Manager");
		versionOneLogoLabel = new JLabel("Version One");
		jiraLogoButton = new JButton();

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
														.addComponent(versionOneLogoLabel, Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(hpLogoLabel, Alignment.LEADING,
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
								.addComponent(hpLogoLabel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										groupLayout
												.createSequentialGroup()
												.addComponent(versionOneLogoLabel, GroupLayout.PREFERRED_SIZE, 62,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(jiraLogoButton, GroupLayout.PREFERRED_SIZE, 86,
														GroupLayout.PREFERRED_SIZE)).addGap(29)));
		groupLayout.linkSize(SwingConstants.VERTICAL, new Component[] { hpLogoLabel, versionOneLogoLabel,
				jiraLogoButton });
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] { hpLogoLabel, versionOneLogoLabel,
				jiraLogoButton });
		getContentPane().setLayout(groupLayout);

		pack();
	}

	private void postInitComponents() {
		URL jiraLogo = getClass().getClassLoader().getResource("jira.png");
		if (jiraLogo != null) {
			this.jiraLogoButton.setIcon(new ImageIcon(new ImageIcon(jiraLogo).getImage().getScaledInstance(165, 75,
					Image.SCALE_SMOOTH)));
			//this.jiraLogoButton.setAction(new OpenJiraAction((MainFrame) this.getParent()));
		}

		URL versionOneLogo = getClass().getClassLoader().getResource("version_one.png");
		if (versionOneLogo != null) {
			this.versionOneLogoLabel.setIcon(new ImageIcon(new ImageIcon(versionOneLogo).getImage().getScaledInstance(
					125, 65, Image.SCALE_SMOOTH)));
		}

		URL hpAgileManagerLogo = getClass().getClassLoader().getResource("hp_agile_manager.png");
		if (hpAgileManagerLogo != null) {
			this.hpLogoLabel.setIcon(new ImageIcon(new ImageIcon(hpAgileManagerLogo).getImage().getScaledInstance(80,
					80, Image.SCALE_SMOOTH)));
		}
	}

	private void closeButtonAction(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeButtonAction
		this.dispose();
	}

}
