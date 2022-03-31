package com.example.ex5x;

//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class FragB extends Fragment implements SeekBar.OnSeekBarChangeListener{

	FragBListener listener;
	private float lastResult;
	private SeekBar seekBar;
	private TextView  txtResult, txtEcample;
	private String resultForamt = "2";

	@Override
	public void onAttach(@NonNull Context context) {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_b, container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		txtResult = view.findViewById(R.id.txtRes);
		txtEcample = view.findViewById(R.id.txtEcample);
		seekBar = view.findViewById(R.id.seekBar);
		seekBar.setOnSeekBarChangeListener(this);
		seekBar.setProgress(Integer.parseInt(resultForamt));
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

	/*This function update the result textview*/
	private void updateResult() {
		float num = 123;
		txtEcample.setText(String.format(Locale.getDefault(), "%." + resultForamt + "f", num));

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



	public interface FragBListener{
		//put here methods you want to utilize to communicate with the hosting activity
	}

}
