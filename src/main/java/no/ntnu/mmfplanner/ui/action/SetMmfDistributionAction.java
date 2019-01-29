package no.ntnu.mmfplanner.ui.action;

import java.awt.event.ActionEvent;

import no.ntnu.mmfplanner.ui.DistributionDialog;
import no.ntnu.mmfplanner.ui.MainFrame;

public class SetMmfDistributionAction extends MainAbstractAction {

	private static final long serialVersionUID = 1L;

	public static final String ACTION_NAME = "Select Mmf Distribution...";
	public static final String ACTION_DESCRIPTION = "Set Mmf Distribution";

	public SetMmfDistributionAction(MainFrame mainFrame) {
		super(mainFrame, ACTION_NAME, -1, null, ACTION_DESCRIPTION);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DistributionDialog distributionDialog = new DistributionDialog(mainFrame, true);
		distributionDialog.setVisible(true);
	}

}
