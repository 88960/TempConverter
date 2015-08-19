package com.garcia.tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class Temp_Converter extends ActionBarActivity
implements OnEditorActionListener{

    // Define variables for the widgets
    private EditText fahrenheitEditText;
    private TextView conversionTextView;

    // Define the SharedPreference objects
    private SharedPreferences savedValues;

    // Define instance variables that should be saved
    private String fahrenheitEditString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp__converter);

        // Get the references for the widgets
        fahrenheitEditText = (EditText) findViewById(R.id.fahrenheitEditText);
        conversionTextView = (TextView) findViewById(R.id.conversionTextView);

        // Set the listeners
        fahrenheitEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);


    }

    @Override
    protected void onPause() {
        //save the instance variables
        Editor editor = savedValues.edit();
        editor.putString("fahrenheitString",fahrenheitEditString );
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //get the instance variables
        fahrenheitEditString = savedValues.getString("fahrenheitString", "");


        // set the bill amount on its widget
        fahrenheitEditText.setText(fahrenheitEditString);

        //call calculate and display method
        calculateAndDisplay();
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }

    public void calculateAndDisplay() {
        fahrenheitEditString = fahrenheitEditText.getText().toString();
        float fahrenheitTemp, celsiusTemp;
        if(fahrenheitEditString.equals("")){
            fahrenheitTemp = 32;
        }
        else
        {
            fahrenheitTemp = Float.parseFloat(fahrenheitEditString);
        }

        // Calculate the celsius temperature
        celsiusTemp = (fahrenheitTemp - 32) * 5/9;


        //display the other results with formatting
        DecimalFormat decForm = new DecimalFormat("#.#### ");

       /* NumberFormat currency = NumberFormat.getCurrencyInstance();
        TipTextView.setText(currency.format(tipAmount));
        TotalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        PercentTextView.setText(percent.format(tipPercent));*/

        conversionTextView.setText(decForm.format(celsiusTemp)+ (char) 0x00B0+"C");

    }
}
