package no.ntnu.mmfplanner.ui;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.rcarz.jiraclient.greenhopper.Backlog;
import net.rcarz.jiraclient.greenhopper.Epic;
import net.rcarz.jiraclient.greenhopper.SprintIssue;
import no.ntnu.mmfplanner.ui.model.CheckBoxNodeData;
import no.ntnu.mmfplanner.ui.renderer.CheckBoxNodeRenderer;

public class TransformJiraDialog extends JDialog {

	private static final long serialVersionUID = 868016866061428678L;

	private MainFrame mainFrame;
	private Backlog backlog;

	public TransformJiraDialog(Frame parent, boolean modal, Backlog backlog) {
		super(parent, modal);
		this.mainFrame = (MainFrame) parent;
		this.backlog = backlog;
		initComponents();
		postInitComponents();
	}

	private void initComponents() {
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Board");

		final DefaultMutableTreeNode epics = add(root, "Epics (" + backlog.getEpics().size() + ")", true);
		for (Epic epic : backlog.getEpics()) {
			add(epics, epic.getEpicLabel() + " (" + epic.getKey() + " / " + epic.getEstimateStatistic().getFieldValue()
					+ ")", false);
		}
		root.add(epics);

		final DefaultMutableTreeNode issues = add(root, "Issues (" + backlog.getIssues().size() + ")", true);
		for (SprintIssue issue : backlog.getIssues()) {
			add(issues, issue.getSummary() + " (" + issue.getEpic() + ")", false);
		}
		root.add(issues);

		final DefaultTreeModel treeModel = new DefaultTreeModel(root);
		final JTree tree = new JTree(treeModel);

		final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		tree.setCellRenderer(renderer);

		final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(tree);
		tree.setCellEditor(editor);
		tree.setEditable(true);
		tree.setVisibleRowCount(root.getLeafCount());
		tree.expandPath(new TreePath(epics.getPath()));

		// listen for changes in the selection
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(final TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				CheckBoxNodeData data = (CheckBoxNodeData) selectedNode.getUserObject();
				System.out.println(data.getText() + ": selected / Checked: " + data.isChecked());
			}
		});

		final JScrollPane scrollPane = new JScrollPane(tree);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		pack();
	}

	private void postInitComponents() {
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

}
