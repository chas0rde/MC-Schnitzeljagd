package de.hsb.kss.mc_schnitzeljagd.location;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Define a DialogFragment that displays the error dialog.
 */
public class ErrorDialogFragment extends DialogFragment {
    // Global field to contain the error dialog
    private Dialog dialog;
    // Default constructor. Sets the dialog field to null
    public ErrorDialogFragment() {
        super();
        dialog = null;
    }
    // Set the dialog to display
    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
    // Return a Dialog to the DialogFragment.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }
}