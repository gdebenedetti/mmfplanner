package no.ntnu.mmfplanner.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.rcarz.jiraclient.greenhopper.Backlog;
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
	private Backlog backlog;
	private String nextId = "A";
	private List<String> mmFsIDs = new ArrayList<String>();

	public TransformJiraDialog(Frame parent, boolean modal, Board board) {
		super(parent, modal);
		this.mainFrame = (MainFrame) parent;
		this.board = board;
		initComponents();
	}

	public TransformJiraDialog(Frame parent, boolean modal, Backlog backlog) {
		super(parent, modal);
		this.mainFrame = (MainFrame) parent;
		this.backlog = backlog;
		initComponents();
	}

	private void initComponents() {
		setTitle("Create new Project and MMFs from JIRA");
		
		final DefaultMutableTreeNode rootBoard = new DefaultMutableTreeNode("Board");
		final DefaultMutableTreeNode rootMMFs = new DefaultMutableTreeNode("MMFs");

		// TODO
		// https://confluence.atlassian.com/jirakb/retrieve-user-stories-under-the-epic-via-rest-call-779158635.html

		final CheckBoxNodeData data = board != null ? new CheckBoxNodeData("Epics (" + board.getEpics().size() + ")",
				true) : backlog != null ? new CheckBoxNodeData("Epics (" + backlog.getEpics().size() + ")", true)
				: null;

		final DefaultMutableTreeNode epics = new DefaultMutableTreeNode(data);

		if (board != null) {
			for (Epic epic : board.getEpics()) {
				//  TODO board.getName()
				final CheckBoxNodeData epicData = new CheckBoxNodeData(epic.getName() + " (" + epic.getKey() + ")",
						false);
				final DefaultMutableTreeNode epicNode = new DefaultMutableTreeNode(epicData);
				epics.add(epicNode);
			}
		} else if (backlog != null) {
			for (net.rcarz.jiraclient.greenhopper.Epic epic : backlog.getEpics()) {
				final CheckBoxNodeData epicData = new CheckBoxNodeData(
						epic.getEpicLabel() + " (" + epic.getKey() + ")", false);
				final DefaultMutableTreeNode epicNode = new DefaultMutableTreeNode(epicData);
				epics.add(epicNode);
			}
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
		
		final JButton transformButton = new JButton("Set as new MMF >>>", UIManager.getIcon("FileView.floppyDriveIcon"));
		final JButton finishButton = new JButton("Finish", UIManager.getIcon("InternalFrame.maximizeIcon"));
		final JButton removeButton = new JButton("Remove <<<");
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
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TreePath[] paths = mmfsTree.getSelectionPaths();
				if (paths != null) {
					for (TreePath path : paths) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
						DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
						if (parent != null) {
							mmfsTreeModel.removeNodeFromParent(node);
							if (parent.getChildCount() < 1 && !parent.isRoot()) {
								// sin hijos
								mmfsTreeModel.removeNodeFromParent(parent);
							}
						}
					}
					mmfsTreeModel.reload();
					expandAllNodes(mmfsTree, 0, mmfsTree.getRowCount());
				}
			}
		});

		finishButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Project project = new Project();

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
		
		transformButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		finishButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		final JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
		middlePanel.add(Box.createRigidArea(new Dimension(100, 50)));
		middlePanel.add(transformButton);
		middlePanel.add(Box.createRigidArea(new Dimension(100, 50)));
		middlePanel.add(removeButton);
		middlePanel.add(Box.createRigidArea(new Dimension(100, 50)));
		middlePanel.add(finishButton);

		final JScrollPane rightScrollPane = new JScrollPane(mmfsTree);
		rightScrollPane.setMinimumSize(new Dimension(400, 800));

		JSplitPane splitPane2 = new JSplitPane();
		splitPane2.setOneTouchExpandable(true);
		splitPane2.setDividerLocation(200);
		splitPane2.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane2.setRightComponent(rightScrollPane);
		splitPane2.setLeftComponent(middlePanel);
		splitPane2.getRightComponent().setMinimumSize(new Dimension(400, 800));

		JSplitPane splitPane1 = new JSplitPane();
		splitPane1.setOneTouchExpandable(true);
		splitPane1.setDividerLocation(400);
		splitPane1.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane1.setRightComponent(splitPane2);
		splitPane1.setLeftComponent(leftScrollPane);
		splitPane1.getLeftComponent().setMinimumSize(new Dimension(400, 800));
		
		getContentPane().add(splitPane1, BorderLayout.CENTER);

		pack();
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
