package com.example.ex6;

//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class FragB extends Fragment implements SeekBar.OnSeekBarChangeListener {

	FragBListener listener; // hold the mainactivity referance
	private float lastResult;
	private TextView  txtResult, txtEcample;
	private String resultForamt = "2";

	@Override
	public void onAttach(@NonNull Context context) {
		//this connect our mainactivity with the B fragment when the context var is the mainactivity
		try{
			this.listener = (FragBListener)context;
		}catch(ClassCastException e){
			throw new ClassCastException("the class " +
					getActivity().getClass().getName() +
					" must implements the interface 'FragBListener'");
		}
		super.onAttach(context);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.settings, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		setHasOptionsMenu(true);//TURN ON THE SHOW ACTION BAR MENU

		return inflater.inflate(R.layout.frag_b, container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		txtResult = view.findViewById(R.id.txtRes);
		updateResult();
		super.onViewCreated(view, savedInstanceState);
	}

	//the activity informs fragB about new click in fragA
	public void onNewClick(float sum){
		setResultTextView(sum);

	}

	private void setResultTextView(float result) {
		lastResult = result;
		updateResult();
	}

	public void setResultForamt(String newFormat) {
		resultForamt = newFormat;
	}

	private void updateResult() {
		try {
			txtResult.setText(String.format(Locale.getDefault(), "%." + resultForamt + "f", lastResult));
		} catch (NumberFormatException e) {
			txtResult.setText("");
		}
	}


	@Override
	public void onProgressChanged(SeekBar seekBar, int value, boolean b) {
		resultForamt = String.valueOf(value);
		updateResult();
	}


	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}


	//the interface of this fragment that include the methods
	public interface FragBListener{
		//put here methods you want to utilize to communicate with the hosting activity
	}

}
