package no.ntnu.mmfplanner.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import no.ntnu.mmfplanner.ui.DistributionDialog;
import no.ntnu.mmfplanner.ui.MainFrame;

public class SetMmfDistributionAction extends MainAbstractAction {

	private static final long serialVersionUID = 1L;

	public static final String ACTION_NAME = "Select Mmf Distribution...";
	public static final int ACTION_MNEMONIC = KeyEvent.VK_M;
	public static final String ACTION_ACCELERATOR = "ctrl M";
	public static final String ACTION_DESCRIPTION = "Set Mmf Distribution";

	public SetMmfDistributionAction(MainFrame mainFrame) {
		super(mainFrame, ACTION_NAME, ACTION_MNEMONIC, ACTION_ACCELERATOR, ACTION_DESCRIPTION);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		boolean cancel = mainFrame.queryProjectCloseSave();
		if (cancel) {
			return;
		}

		DistributionDialog distributionDialog = new DistributionDialog(mainFrame, true);
		distributionDialog.setVisible(true);
	}

}
