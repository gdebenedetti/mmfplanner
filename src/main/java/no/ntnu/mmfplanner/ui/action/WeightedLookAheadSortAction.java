/*
 * Copyright (C) 2018 gdebenedetti
 */

package no.ntnu.mmfplanner.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import no.ntnu.mmfplanner.model.WeightedLookAheadProjectSorter;
import no.ntnu.mmfplanner.ui.MainFrame;
import no.ntnu.mmfplanner.ui.SortDialog;

/**
 * Starts a {@link WeightedLookAheadProjectSorter} instance as a new thread, and opens the {@link SortDialog}
 */
public class WeightedLookAheadSortAction extends MainAbstractAction {
	private static final long serialVersionUID = 1L;

	public static final String ACTION_NAME = "Weighted Look-Ahead Sort";

	public static final int ACTION_KEY = KeyEvent.VK_H;

	public static final String ACTION_DESCRIPTION = "Use Weighted Look-Ahead algorithm to get the optimal NPV";

	public WeightedLookAheadSortAction(MainFrame mainFrame) {
		super(mainFrame, ACTION_NAME, ACTION_KEY, null, ACTION_DESCRIPTION);

	}

	/**
	 * Start the IFM Heuristic sorter
	 */
	public void actionPerformed(ActionEvent e) {
		WeightedLookAheadProjectSorter weightedLookAheadProjectSorter = new WeightedLookAheadProjectSorter(
				mainFrame.getProject());
		SortDialog sortDialog = new SortDialog(mainFrame, enabled, weightedLookAheadProjectSorter);
		weightedLookAheadProjectSorter.start(true);
		sortDialog.setVisible(true);
	}

}
