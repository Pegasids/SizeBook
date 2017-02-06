package com.example.sizebook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a view class of the project; it's for
 * adding new Record-objects. New Record-objects will be sent to
 * MainActivity using intent.putExtra.
 * In this class, EditText are displayed for user's input.
 * It also includes input filter for correct input.
 */
public class AddNewRecordActivity extends AppCompatActivity {

    private EditText NameEditText, NeckEditText, BustEditText, ChestEditText;
    private EditText WaistEditText, HipEditText, InseamEditText, CommentEditText;
    private TextView DateTextView;
    private Button DateBtn;
    private int year_x, month_x, day_x;
    private static final int DIALOG_ID = 0;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DateTextView = (TextView) findViewById(R.id.AddDateTextView);
        NameEditText = (EditText) findViewById(R.id.name);
        CommentEditText = (EditText) findViewById(R.id.comment);
        NeckEditText = (EditText) findViewById(R.id.neck);
        NeckEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3,1)});
        BustEditText = (EditText) findViewById(R.id.bust);
        BustEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,1)});
        ChestEditText = (EditText) findViewById(R.id.chest);
        ChestEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,1)});
        WaistEditText = (EditText) findViewById(R.id.waist);
        WaistEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,1)});
        HipEditText = (EditText) findViewById(R.id.hip);
        HipEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,1)});
        InseamEditText = (EditText) findViewById(R.id.inseam);
        InseamEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3,1)});

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        showDialogOnButtonClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_addnewrecord, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.cancelbutton:
                cancelOnClick();
                break;

            case R.id.addbutton:
                SaveButtonOnClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Credit- Pinhassi
     * url: http://stackoverflow.com/questions/5357455/limit-decimal-places-in-android-edittext
     *
     * This class limit the number of digits in EditText fields.
     */
    private class DecimalDigitsInputFilter implements InputFilter {
        Pattern mPattern;

        private DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
            mPattern= Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher=mPattern.matcher(dest);
            if(!matcher.matches())
                return "";
            return null;
        }
    }

    /**
     * Called when android:id="@+id/addbutton" is clicked.
     * This class creates a new Record-object and use intent.putExtra to send it to MainActivity.
     * It also ensures that NameEditText is not empty.
     */
    private void SaveButtonOnClick() {
        String name = NameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            NameEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(NameEditText, InputMethodManager.SHOW_IMPLICIT);
            Toast.makeText(AddNewRecordActivity.this, "Name required", Toast.LENGTH_SHORT).show();
        } else {
            Records newRecord = new Records(name, DateTextView.getText().toString().trim(), CommentEditText.getText().toString().trim(),
                    EditTextToFloat(NeckEditText), EditTextToFloat(BustEditText), EditTextToFloat(ChestEditText),
                    EditTextToFloat(WaistEditText), EditTextToFloat(HipEditText), EditTextToFloat(InseamEditText));

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("newRecord",newRecord);
            Toast.makeText(AddNewRecordActivity.this, "New record saved", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    /**
     * Return and convert EditText's content to Float type.
     * Return -1.0f if EditText is empty.
     * @param EditText
     * @return
     */
    private Float EditTextToFloat (EditText EditText) {
        String temp = EditText.getText().toString().trim();
        if (temp.isEmpty()) {
            return -1.0f;
        }
        return Float.valueOf(temp);
    }

    /**
     * Called when android:id="@+id/cancelbutton" is clicked.
     * And go back to MainActivity.
     */
    private void cancelOnClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Called when android:id="@+id/clear_field_button" is click
     * @param view
     * This method clears the DateTextView.
     */
    public void clearFieldOnClick(View view) {
        DateTextView.setText("");
        Toast.makeText(AddNewRecordActivity.this, "Date cleared", Toast.LENGTH_SHORT).show();
    }

    /**
     * Credit- ProgrammingKnowledge
     * url: https://www.youtube.com/watch?v=czKLAx750N0
     *
     * Displays a DatePicker Dialog.
     */
    private void showDialogOnButtonClick() {
        DateBtn = (Button) findViewById(R.id.date_dialog_button);
        DateBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }
    /**
     * Credit- ProgrammingKnowledge
     * url: https://www.youtube.com/watch?v=czKLAx750N0
     *
     * Displays a DatePicker Dialog.
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, datePickerListener, year_x, month_x, day_x);
        return null;
    }
    /**
     * Credit- ProgrammingKnowledge
     * url: https://www.youtube.com/watch?v=czKLAx750N0
     *
     * Displays a DatePicker Dialog and set DateTextView to the selected date.
     */
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int datOfMonth){
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = datOfMonth;
            DateTextView.setTextColor(Color.BLACK);
            DateTextView.setText(year_x + "-" + String.format("%02d",month_x) + "-" + String.format("%02d",day_x));

            Toast.makeText(AddNewRecordActivity.this, "Added a date", Toast.LENGTH_SHORT).show();
        }
    };

}
