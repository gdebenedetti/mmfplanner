package no.ntnu.mmfplanner.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import no.ntnu.mmfplanner.ui.ImportDialog;
import no.ntnu.mmfplanner.ui.MainFrame;

public class ImportAction extends MainAbstractAction {

	private static final long serialVersionUID = 1L;

	public static final String ACTION_NAME = "Import Project from...";

	public static final int ACTION_MNEMONIC = KeyEvent.VK_I;

	public static final String ACTION_ACCELERATOR = "ctrl I";

	public static final String ACTION_DESCRIPTION = "Import a project";

	public ImportAction(MainFrame mainFrame) {
		super(mainFrame, ACTION_NAME, ACTION_MNEMONIC, ACTION_ACCELERATOR, ACTION_DESCRIPTION);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		boolean cancel = mainFrame.queryProjectCloseSave();
		if (cancel) {
			return;
		}

		ImportDialog importDialog = new ImportDialog(mainFrame, true);
		importDialog.setVisible(true);
	}

}
