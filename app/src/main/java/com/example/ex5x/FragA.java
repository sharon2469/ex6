package com.example.ex5x;

//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class FragA extends Fragment implements OnClickListener{
	FragAListener listener;
	private Button btAdd, btSub, btMul, btDiv, btClear;
	private EditText edNum1, edNum2;

	@Override
	public void onAttach(@NonNull Context context) {
		try{
			this.listener = (FragAListener)context;
		}catch(ClassCastException e){
			throw new ClassCastException("the class " +
					context.getClass().getName() +
					" must implements the interface 'FragAListener'");
		}
		super.onAttach(context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_a, container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		ValidOperandTextWatcher validOperand = new ValidOperandTextWatcher();
		this.btAdd = view.findViewById(R.id.btAdd);
		this.btSub = view.findViewById(R.id.btSub);
		this.btMul = view.findViewById(R.id.btMul);
		this.btDiv = view.findViewById(R.id.btDiv);
		this.edNum1 = view.findViewById(R.id.edOperand1);
		this.edNum2 = view.findViewById(R.id.edOperand2);

		btAdd.setOnClickListener(this);
		btSub.setOnClickListener(this);
		btMul.setOnClickListener(this);
		btDiv.setOnClickListener(this);
		edNum1.addTextChangedListener(validOperand);
		edNum2.addTextChangedListener(validOperand);

		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		listener.operationButtonClicked(v,edNum1.getText().toString(),edNum2.getText().toString());
	}


	private void setAllButtonsEnabled(boolean enabled) {
		btAdd.setEnabled(enabled);
		btSub.setEnabled(enabled);
		btMul.setEnabled(enabled);
		btDiv.setEnabled(enabled);
	}

	public void restoreEditTexts(String keepEd1, String keepEd2) {
		edNum1.setText(keepEd1);
		edNum2.setText(keepEd2);
	}

	/*This is Member Class method -> implements TextWatcher*/
	/**This private class implements TextWatcher handle the actions when there is change in the inputs*/
	private class ValidOperandTextWatcher implements TextWatcher
	{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// no action
		}

		/**This function handle the action when the text change in the editText (we do it with the listener)*/
		@Override
		public void afterTextChanged(Editable s) {
			//check if the second editText is zero
			try {
				int operand1 = Integer.parseInt(edNum1.getText().toString());
				int operand2 = Integer.parseInt(edNum2.getText().toString());
				setAllButtonsEnabled(true);

				//int operand2 = Integer.parseInt(s.toString());
				btDiv.setEnabled(operand2 != 0); //check if the second editText is zero
			} catch (NumberFormatException e) {
				setAllButtonsEnabled(false);
			}
		}
	}



	public interface FragAListener{
		public void operationButtonClicked(View view, String ed1, String ed2);
	}
}
