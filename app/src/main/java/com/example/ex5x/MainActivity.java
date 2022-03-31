package com.example.ex5x;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements FragA.FragAListener, FragB.FragBListener{

	private FragA fragA;
	private FragB fragB;
	String keepEd1,keepEd2;
	private float sum = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragA = (FragA) getSupportFragmentManager().findFragmentByTag("FRAGA");
		fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");

//		FragB fragB;
//		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//		{
//			getSupportFragmentManager().beginTransaction()
//					.setReorderingAllowed(true)
//					.add(R.id.fragContainer, FragB.class, null,"FRAGB")
//					.addToBackStack("BBB")
//					.commit();
//			getSupportFragmentManager().executePendingTransactions();
//		}
//		fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");
	}


	//This command belongs to the Frag A
	public void operationButtonClicked(View view, String ed1, String ed2) {

		int btId, num1, num2;
		keepEd1 = ed1;
		keepEd2 = ed2;
		String tempString;
		if (!(view instanceof Button)) {
			Log.e("Error:", "view is not an instance of Button");
			return;
		}

		btId = ((Button)view).getId();
		num1 = Integer.valueOf(ed1);
		num2 = Integer.valueOf(ed2);

		switch (btId){
			case R.id.btAdd:
				Log.i("Oparation","Add");
				sum = num1 + num2;
				break;
			case R.id.btSub:
				Log.i("Oparation","Subtraction");
				sum = num1 - num2;
				break;
			case R.id.btMul:
				Log.i("Oparation","Multiplication");
				sum = num1 * num2;
				break;
			case R.id.btDiv:
				Log.i("Oparation","Division");
				if(num2 == 0){
					Log.e("Error oparation","Attempt divid by zero");
					showToast(getString(R.string.ERROR_DIVIDE_BY_ZERO));
					return;
				}
				if(num1 % num2 == 0){
					sum = num1 / num2;
				}else{
					sum = num1 / (float) num2;
				}
				break;
		}
		//setResultTextView(sum);
		FragB fragB;
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.add(R.id.fragContainer, FragB.class, null,"FRAGB")
					.addToBackStack("BBB")
					.commit();
			getSupportFragmentManager().executePendingTransactions();
		}
		fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");
		fragB.onNewClick(sum);
	}


	/**This function get text and show Toast message*/
	public void showToast(String text){
		Toast toast = Toast.makeText(this,text,Toast.LENGTH_SHORT);
		toast.show();
	}


	@Override
	public void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putFloat("res", sum);
		outState.putString("ed1", keepEd1);
		outState.putString("ed2", keepEd2);
	}

	@Override
	public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		sum = savedInstanceState.getFloat("res");
		keepEd1 = savedInstanceState.getString("ed1");
		keepEd2 = savedInstanceState.getString("ed2");

		FragB fragB;
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.add(R.id.fragContainer, FragB.class, null,"FRAGB")
					.addToBackStack("BBB")
					.commit();
			getSupportFragmentManager().executePendingTransactions();
		}
		fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");
		fragB.onNewClick(sum);

		fragA.restoreEditTexts(keepEd1 ,keepEd2);


	}
}
