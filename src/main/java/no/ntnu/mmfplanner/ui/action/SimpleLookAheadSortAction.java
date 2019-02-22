/*
 * Copyright (C) 2007 Snorre Gylterud, Stein Magnus Jodal, Johannes Knutsen,
 * Erik Bagge Ottesen, Ralf Bjarne Taraldset, and Iterate AS
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 */

package no.ntnu.mmfplanner.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import no.ntnu.mmfplanner.model.SimpleLookAheadProjectSorter;
import no.ntnu.mmfplanner.ui.MainFrame;
import no.ntnu.mmfplanner.ui.SortDialog;

/**
 * Starts a {@link SimpleLookAheadProjectSorter} instance as a new thread, and opens the {@link SortDialog}
 */
public class SimpleLookAheadSortAction extends MainAbstractAction {
    private static final long serialVersionUID = 1L;

    public static final String ACTION_NAME = "Simple Look-Ahead Sort";

    public static final int ACTION_KEY = KeyEvent.VK_H;

    public static final String ACTION_DESCRIPTION = "Use Simple Look-Ahead algorithm to get the optimal NPV";

    public SimpleLookAheadSortAction(MainFrame mainFrame) {
        super(mainFrame, ACTION_NAME, ACTION_KEY, null, ACTION_DESCRIPTION);

    }

    /**
     * Start the IFM Heuristic sorter
     */
    public void actionPerformed(ActionEvent e) {
    	SimpleLookAheadProjectSorter simpleLookAheadProjectSorter = new SimpleLookAheadProjectSorter(mainFrame.getProject());
        SortDialog sortDialog = new SortDialog(mainFrame, enabled, simpleLookAheadProjectSorter);
        simpleLookAheadProjectSorter.start(true);
        sortDialog.setVisible(true);
    }

}
