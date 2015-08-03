package com.physphil.android.unitconverterultimate.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.physphil.android.unitconverterultimate.R;
import com.physphil.android.unitconverterultimate.util.Constants;
import com.physphil.android.unitconverterultimate.util.Conversions;
import com.physphil.android.unitconverterultimate.util.Convert;



public class SetDecimalSeparatorDialogFragment extends DialogFragment{
	int currentDecimalSeparator = 0;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity activity = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(R.string.dialogSetDecimalSeparator);
		
		//Read from preferences file to determine current separator setting
		SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
		currentDecimalSeparator = preferences.getInt(Constants.SETTINGS_CURRENT_DECIMAL_SEPARATOR, 0);
		
		builder.setSingleChoiceItems(R.array.decimal_separators, currentDecimalSeparator, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				currentDecimalSeparator = which;
			}
		});
		
		//Set the action buttons
		builder.setPositiveButton(R.string.buttonOK, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// Store decimal separator in preferences file
				SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt(Constants.SETTINGS_CURRENT_DECIMAL_SEPARATOR, currentDecimalSeparator);
				editor.commit();
			}
		});
		
		builder.setNegativeButton(R.string.buttonCancel, new DialogInterface.OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				//Exit back to main program
				dialog.cancel();
			}
		});
		
		return builder.create();
	}
	
	@Override
	public void onStop(){
		super.onStop();
		
		//Read selected conversion info to call Convert
		int selectedConversion = getArguments().getInt(Constants.SETTINGS_CONVERSION, -1);
		int fromGroupId = getArguments().getInt(Constants.FROM_GROUP_ID, 0);
		int toGroupId = getArguments().getInt(Constants.TO_GROUP_ID, 0);
		
		//Call convert with new decimal separator
		if(fromGroupId != 0 && toGroupId != 0 && selectedConversion != -1){
			
			if(selectedConversion == Conversions.TEMPERATURE){
				Convert.convertTempValue(getActivity());
			}
			else{
				Convert.convertValue(getActivity(), fromGroupId, toGroupId);
			}
		}
	}
	
}
