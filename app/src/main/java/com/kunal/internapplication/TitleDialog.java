package com.kunal.internapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class TitleDialog extends DialogFragment {

    private EditText title;
    private TitleDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.title_dialog, null);

        builder.setView(view)
                .setPositiveButton("OK", (dialog, id) -> {
                    String title_txt = title.getText().toString().trim();
                    listener.applyTexts(title_txt);
                })
                .setNegativeButton("Cancel", (dialog, id) -> TitleDialog.this.getDialog().cancel());
        title = view.findViewById(R.id.title);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (TitleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "Must Implement TitleDialogListener");
        }

    }

    public interface TitleDialogListener{
        void applyTexts(String title);
    }

}
