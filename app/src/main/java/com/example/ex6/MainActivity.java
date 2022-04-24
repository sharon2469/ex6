package com.example.ex6;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements FragA.FragAListener, FragB.FragBListener, SeekBarActivity.ISeekBarActivity {
	public static String PROG = "progress";
	public int resultPrecision = 2;

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

		if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)){
			if (fragB != null) {
				getSupportFragmentManager().beginTransaction()
						.show(fragB)
						.addToBackStack(null)
						.commit();
			}
			else {
				getSupportFragmentManager().beginTransaction()
						.add(R.id.fragContainer, FragB.class,null, "FRAGB")
						.commit();
			}
			getSupportFragmentManager().executePendingTransactions();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.exit, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
			case R.id.menuExit:
				MyExit newFragment = MyExit.newInstance();
				newFragment.show(getSupportFragmentManager(), "exitDialog");
				break;
			case R.id.menuSetting:
				SeekBarActivity settingsDialog = SeekBarActivity.newInstance();
				Bundle bundle = new Bundle();
				bundle.putInt(PROG, resultPrecision);
				settingsDialog.setArguments(bundle);
				settingsDialog.show(getSupportFragmentManager(), "settingsDialog");
				break;
		}


		return  true;
	}

	//This command belongs to the Frag A
	public void operationButtonClicked(int operation, String ed1, String ed2) {

		int num1, num2;
		keepEd1 = ed1;
		keepEd2 = ed2;

		num1 = Integer.valueOf(ed1);
		num2 = Integer.valueOf(ed2);

		switch (operation){
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
		fragB.setResultForamt(String.valueOf((resultPrecision)));
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
		fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.show(fragB)
					.addToBackStack("BBB")
					.commit();
			getSupportFragmentManager().executePendingTransactions();
		}
		fragB.onNewClick(sum);

		fragA.restoreEditTexts(keepEd1 ,keepEd2);


	}

	@Override
	public void onSeekBarChanged(int progress) {
		FragB fragB;
		fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
		{
			getSupportFragmentManager().beginTransaction()
					.setReorderingAllowed(true)
					.show(fragB)
					.addToBackStack("BBB")
					.commit();
			getSupportFragmentManager().executePendingTransactions();
		}

		//set the new format and the sum
		resultPrecision = progress;
		fragB.setResultForamt(String.valueOf(progress));
		fragB.onNewClick(sum);

	}
}
