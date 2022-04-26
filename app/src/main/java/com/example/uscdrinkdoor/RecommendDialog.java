package com.example.uscdrinkdoor;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class RecommendDialog extends DialogFragment {
    private AlertDialog.Builder builder;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Recommended Item:");
            builder.setMessage(R.string.seller_id);


            // Create the AlertDialog object and return it
            return builder.create();
        }

}
