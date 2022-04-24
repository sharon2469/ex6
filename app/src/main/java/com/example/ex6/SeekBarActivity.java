package com.example.ex6;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class SeekBarActivity extends DialogFragment implements SeekBar.OnSeekBarChangeListener {
    public static String PROG = "progress";
    private ISeekBarActivity mListener;
    private SeekBar mSeekBar;
    private TextView editTextExample;

    public static SeekBarActivity newInstance() {
        SeekBarActivity frag = new SeekBarActivity();
        return frag;
    }

    public interface ISeekBarActivity {
        public void onSeekBarChanged(int progress);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.SettingsDialog);
        View seekBarView = getActivity().getLayoutInflater().inflate(R.layout.seek_bar, null);

        mSeekBar = (SeekBar) seekBarView.findViewById(R.id.seekBar);
        editTextExample = (TextView) seekBarView.findViewById(R.id.txtEcample);
        mSeekBar.setOnSeekBarChangeListener(this);


        /* Get precision and update seekBar */
        Bundle args = getArguments();
        if (args != null) {
            int prog = args.getInt(PROG);
            if (0 <= prog && prog <= 5)
                mSeekBar.setProgress(prog);
        }

        // Inflate and set the layout for the dialog

        builder.setTitle(R.string.settings_precision)
                .setView(seekBarView)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mListener.onSeekBarChanged(mSeekBar.getProgress());
                                dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                );
        return builder.create();
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(progress == 0){
            editTextExample.setText("example: 123");
        }else if(progress == 1){
            editTextExample.setText("example: 123.0");
        }else if(progress == 2){
            editTextExample.setText("example: 123.00");
        }else if(progress == 3){
            editTextExample.setText("example: 123.000");
        }else if(progress == 4){
            editTextExample.setText("example: 123.0000");
        }else if(progress == 5){
            editTextExample.setText("example: 123.00000");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        //this connect our mainactivity with the B fragment when the context var is the mainactivity
        try{
            this.mListener = (ISeekBarActivity)context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    getActivity().getClass().getName() +
                    " must implements the interface 'FragBListener'");
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}




