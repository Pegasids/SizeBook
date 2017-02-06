package com.example.sizebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is a view class of the project.
 * It shows the record detail of a person.
 */
public class RecordDetailActivity extends AppCompatActivity {

    TextView NameTextView, DateTextView, NeckTextView, BustTextView, ChestTextView;
    TextView WaistTextView, HipTextView, InseamTextView, CommentTextView;
    Records selectedRecord;
    int selectedPosition;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selectedRecord = (Records) getIntent().getSerializableExtra("selectedRecord");
        selectedPosition = getIntent().getIntExtra("selectedPosition", -1);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(selectedRecord.getName() + "");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Called after method onCreate().
     * This method get all record details and displays details in TextViews.
     */
    @Override
    protected void onStart() {
        super.onStart();

        NameTextView = (TextView) findViewById(R.id.name);
        DateTextView = (TextView) findViewById(R.id.date);
        NeckTextView = (TextView) findViewById(R.id.neck);
        BustTextView = (TextView) findViewById(R.id.bust);
        ChestTextView = (TextView) findViewById(R.id.chest);
        WaistTextView = (TextView) findViewById(R.id.waist);
        HipTextView = (TextView) findViewById(R.id.hip);
        InseamTextView = (TextView) findViewById(R.id.inseam);
        CommentTextView = (TextView) findViewById(R.id.comment);

        NameTextView.setText(selectedRecord.getName());
        if (selectedRecord.getNeck() != -1.0f) {
            String temp = String.valueOf(selectedRecord.getNeck()) + " inch";
            NeckTextView.setText(temp);
        }
        if (selectedRecord.getBust() != -1.0f) {
            String temp = String.valueOf(selectedRecord.getBust()) + " inch";
            BustTextView.setText(temp);
        }
        if (selectedRecord.getChest() != -1.0f) {
            String temp = String.valueOf(selectedRecord.getChest()) + " inch";
            ChestTextView.setText(temp);
        }
        if (selectedRecord.getWaist() != -1.0f) {
            String temp = String.valueOf(selectedRecord.getWaist()) + " inch";
            WaistTextView.setText(temp);
        }
        if (selectedRecord.getHip() != -1.0f) {
            String temp = String.valueOf(selectedRecord.getHip()) + " inch";
            HipTextView.setText(temp);
        }
        if (selectedRecord.getInseam() != -1.0f) {
            String temp = String.valueOf(selectedRecord.getInseam()) + " inch";
            InseamTextView.setText(temp);
        }
        CommentTextView.setText(selectedRecord.getComment());

        if (!selectedRecord.getDate().isEmpty()) {
            DateTextView.setText(selectedRecord.getDate());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recorddetail, menu);
        return true;
    }

    /**
     * Contain a switch. If R.id.editbutton is click go to EditRecordActivity.
     * If R.id.deletebutton is clicked call deleteOption().show().
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.editbutton:
                Intent intent = new Intent(this, EditRecordActivity.class);
                intent.putExtra("selectedEditRecord", selectedRecord);
                intent.putExtra("editSelectedPosition", selectedPosition);
                startActivity(intent);
                return true;

            case R.id.deletebutton:
                deleteOption().show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Display a AlertDialog for delete confirmation.
     * If Delete is clicked, go and sent the record position to MainActivity.
     * @return
     */
    private AlertDialog deleteOption() {
        return new AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_warning_black_24dp)
            .setTitle("Delete Record")
            .setMessage("Are you sure you want to permanently delete this record?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // do something
                    Intent intent = new Intent(RecordDetailActivity.this, MainActivity.class);
                    intent.putExtra("delPosition", selectedPosition);
                    startActivity(intent);
                    Toast.makeText(RecordDetailActivity.this, "Record deleted", Toast.LENGTH_SHORT).show();

                }
            })
            .create();
    }

}
