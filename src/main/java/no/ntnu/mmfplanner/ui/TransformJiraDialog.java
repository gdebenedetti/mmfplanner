package no.ntnu.mmfplanner.ui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import no.ntnu.mmfplanner.jira.model.Board;
import no.ntnu.mmfplanner.jira.model.Epic;
import no.ntnu.mmfplanner.model.Mmf;
import no.ntnu.mmfplanner.model.Project;
import no.ntnu.mmfplanner.ui.model.CheckBoxNodeData;
import no.ntnu.mmfplanner.ui.renderer.CheckBoxNodeRenderer;

public class TransformJiraDialog extends JDialog {

	private static final long serialVersionUID = 868016866061428678L;

	private MainFrame mainFrame;
	private Board board;
	private String nextId;
	private List<String> mmFsIDs;

	public TransformJiraDialog(Frame parent, boolean modal, Board board) {
		super(parent, modal);
		this.mainFrame = (MainFrame) parent;
		this.board = board;
		this.nextId = "A";
		this.mmFsIDs = new ArrayList<String>();
		initComponents();
	}

	private void initComponents() {
		final DefaultMutableTreeNode rootBoard = new DefaultMutableTreeNode("Board");
		final DefaultMutableTreeNode rootMMFs = new DefaultMutableTreeNode("MMFs");

		// TODO
		// https://confluence.atlassian.com/jirakb/retrieve-user-stories-under-the-epic-via-rest-call-779158635.html

		final CheckBoxNodeData data = new CheckBoxNodeData("Epics (" + board.getEpics().size() + ")", true);
		final DefaultMutableTreeNode epics = new DefaultMutableTreeNode(data);

		for (Epic epic : board.getEpics()) {
			final CheckBoxNodeData epicData = new CheckBoxNodeData(epic.getName() + " (" + epic.getKey() + ")", false);
			final DefaultMutableTreeNode epicNode = new DefaultMutableTreeNode(epicData);
			epics.add(epicNode);

			// for (Issue issue : epic.getIssues()) {
			// final CheckBoxNodeData issueData = new CheckBoxNodeData(issue.getName() + " (" + issue.getKey()
			// + ")"
			// + " (" + issue.getOriginalEstimate() + ")", false);
			// final DefaultMutableTreeNode issueNode = new DefaultMutableTreeNode(issueData);
			// epicNode.add(issueNode);
			// }
		}
		rootBoard.add(epics);

		final DefaultTreeModel boardTreeModel = new DefaultTreeModel(rootBoard);
		final JTree epicsTree = new JTree(boardTreeModel);
		final DefaultTreeModel mmfsTreeModel = new DefaultTreeModel(rootMMFs);
		final JTree mmfsTree = new JTree(mmfsTreeModel);

		final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		epicsTree.setCellRenderer(renderer);

		final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(epicsTree);
		epicsTree.setCellEditor(editor);
		epicsTree.setEditable(true);
		epicsTree.setVisibleRowCount(rootBoard.getLeafCount());
		epicsTree.expandPath(new TreePath(epics.getPath()));

		final JScrollPane leftScrollPane = new JScrollPane(epicsTree);
		final JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		final JButton transformButton = new JButton("Transform >>>");
		final JButton finishButton = new JButton("Finish");
		transformButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode mmfNode = null;
				boolean newMMF = true;
				int childCount = epics.getChildCount();
				for (int i = 0; i < childCount; i++) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) epics.getChildAt(i);
					if (isChecked(node)) {
						if (newMMF) {
							// nuevo MMF? Si: creo MMF padre
							String mmfID = getNextId();
							mmFsIDs.add(mmfID);
							mmfNode = new DefaultMutableTreeNode("MMF " + mmfID);
							mmfNode.add(new DefaultMutableTreeNode(((CheckBoxNodeData) getData(node)).getText()));
							rootMMFs.add(mmfNode);
							newMMF = false;
						} else {
							// sino agrego al MMF ya creado
							mmfNode.add(new DefaultMutableTreeNode(((CheckBoxNodeData) getData(node)).getText()));
						}
					}
				}
				mmfsTreeModel.reload();
				uncheckAllNodes(rootBoard);
				boardTreeModel.reload();
				expandAllNodes(epicsTree, 0, epicsTree.getRowCount());
				expandAllNodes(mmfsTree, 0, mmfsTree.getRowCount());
			}
		});
		finishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO crear proyecto y pasarlo al mainframe
				Project project = new Project("JIRA_2_IFM", 6, 0.1, 1);

				// Creo las MMFs
				int childCount = rootMMFs.getChildCount();
				for (int i = 0; i < childCount; i++) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) rootMMFs.getChildAt(i);

					// itero las épicas
					StringBuilder mmfName = new StringBuilder();
					for (int j = 0; j < node.getChildCount(); j++) {
						DefaultMutableTreeNode epic = (DefaultMutableTreeNode) node.getChildAt(j);
						mmfName.append(epic.toString());
						if (j < node.getChildCount() - 1)
							mmfName.append(" | ");
					}

					Mmf mmf = new Mmf(null, mmfName.toString());
					project.add(mmf);
				}

				mainFrame.setModel(project);
				closeButtonAction(null);
			}
		});

		middlePanel.add(transformButton);
		middlePanel.add(finishButton);
		final JScrollPane rightScrollPane = new JScrollPane(mmfsTree);

		JSplitPane splitPane1 = new JSplitPane();
		splitPane1.setOneTouchExpandable(true);
		splitPane1.setDividerLocation(250);
		JSplitPane splitPane2 = new JSplitPane();
		splitPane2.setOneTouchExpandable(true);
		splitPane2.setDividerLocation(150);

		splitPane1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane1.setRightComponent(splitPane2);
		splitPane1.setLeftComponent(leftScrollPane);
		splitPane2.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane2.setRightComponent(rightScrollPane);
		splitPane2.setLeftComponent(middlePanel);

		getContentPane().add(splitPane1, BorderLayout.CENTER);

		pack();
	}

	// backlog.getEpics();
	// backlog.getEpics().get(0).getEpicLabel(); // Spring-Cloud-Task
	// backlog.getEpics().get(0).getEpicStats();
	// backlog.getEpics().get(0).getSummary(); // Update batch modules to be short lived
	// backlog.getEpics().get(0).getTypeName(); // Epic
	// backlog.getEpics().get(0).getPriorityName(); // Major
	// backlog.getEpics().get(0).getStatusName(); // To Do / Done
	// backlog.getIssues();
	// backlog.getIssues().get(0).getAssigneeName();
	// backlog.getIssues().get(0).getAssigneeName();
	// backlog.getIssues().get(0).getSummary();
	// backlog.getIssues().get(0).getTypeName();
	// backlog.getMarkers();
	// backlog.getProjects();
	// backlog.getRankCustomFieldId();
	// backlog.getSprints();
	// backlog.getVersionsPerProject();
	// backlog.getProjects();

	private static DefaultMutableTreeNode add(final DefaultMutableTreeNode parent, final String text,
			final boolean checked) {
		final CheckBoxNodeData data = new CheckBoxNodeData(text, checked);
		final DefaultMutableTreeNode node = new DefaultMutableTreeNode(data);
		parent.add(node);
		return node;
	}

	private CheckBoxNodeData getData(final DefaultMutableTreeNode node) {
		final Object userObject = node.getUserObject();
		if (!(userObject instanceof CheckBoxNodeData))
			return null;
		return (CheckBoxNodeData) userObject;
	}

	/** Extracts the check box state of a given tree node. */
	private boolean isChecked(final DefaultMutableTreeNode node) {
		final CheckBoxNodeData data = getData(node);
		if (data == null)
			return false;
		return data.isChecked();
	}

	private void unCheck(final DefaultMutableTreeNode node) {
		final CheckBoxNodeData data = getData(node);
		if (data == null)
			return;
		data.setChecked(false);
	}

	private boolean isValidId(String id) {
		return (null != id) && id.matches("Z*[A-Y]") && (!mmFsIDs.contains(id));
	}

	private String getNextId() {
		// check if next id is correct.
		while (!isValidId(nextId)) {
			// find next id value
			char nextChar = (char) (1 + nextId.charAt(nextId.length() - 1));
			String pre = nextId.substring(0, nextId.length() - 1);
			nextId = pre + nextChar;
			if (nextChar == 'Z') {
				// We're at the last usable character in this set. We retry all
				// previous characters
				// in an attempt to avoid multiple characters, otherwise we add
				// another 'A' character
				for (int i = 0; i < 25 * 10 + 1; i++) {
					nextId = "ZZZZZZZZZZ".substring(0, i / 25) + (char) ('A' + i % 25);
					if (isValidId(nextId)) {
						return nextId;
					}
				}
			}
		}
		return nextId;
	}

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	private void uncheckAllNodes(DefaultMutableTreeNode root) {
		Enumeration e = root.preorderEnumeration();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
			if (isChecked(node)) {
				unCheck(node);
			}
		}
	}

	private void closeButtonAction(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_closeButtonAction
		this.dispose();
	}
}
