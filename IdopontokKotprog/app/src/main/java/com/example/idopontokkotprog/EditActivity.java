package com.example.idopontokkotprog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {
    private boolean isNew;
    private String selectedAppointmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        isNew = getIntent().getBooleanExtra("isNew", true);

        initDeleteButton();
        initStatusSpinner();
        initStartDatePicker();
        initEndDatePicker();

        if(!isNew){
            selectedAppointmentId = getIntent().getStringExtra("identifier");
            String status = getIntent().getStringExtra("status");
            String appointmentType = getIntent().getStringExtra("appointmentType");
            String description = getIntent().getStringExtra("description");
            String start = getIntent().getStringExtra("start");
            String end = getIntent().getStringExtra("end");
            String participant = getIntent().getStringExtra("actor");

            int statusSelectedIndex = 0;
            switch (status){
                case "Függőben" : statusSelectedIndex = 0;
                    break;
                case "Felvéve" : statusSelectedIndex = 1;
                    break;
                case "Megérkezett" : statusSelectedIndex = 2;
                    break;
                case "Teljesítve" : statusSelectedIndex = 3;
                    break;
                case "Visszavonva" : statusSelectedIndex = 4;
                    break;
            }

            ((Spinner)findViewById(R.id.spinnerStatus)).setSelection(statusSelectedIndex);
            ((EditText)findViewById(R.id.editAppointmentType)).setText(appointmentType);
            ((EditText)findViewById(R.id.editDescription)).setText(description);
            ((EditText)findViewById(R.id.editStart)).setText(start);
            ((EditText)findViewById(R.id.editEnd)).setText(end);
            ((EditText)findViewById(R.id.editParticipant)).setText(participant);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();

                    int mYear = bundle.getInt("year");
                    int mMonth = bundle.getInt("month");
                    int mDay = bundle.getInt("day");


                    EditText editStart = findViewById(R.id.editStart);

                    editStart.setText(mYear + ". " + mMonth + ". " + mDay);


                } else if (resultCode == Activity.RESULT_CANCELED) {
                }
                break;
        }
    }

    private void initDeleteButton(){
        Button buttonDelete = (Button)findViewById(R.id.buttonDelete);
        if (!isNew) buttonDelete.setVisibility(View.VISIBLE);
    }

    private void initStatusSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initStartDatePicker(){
        final Calendar myCalendar = Calendar.getInstance();

        EditText editText= (EditText) findViewById(R.id.editStart);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText, myCalendar);
            }

        };

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void initEndDatePicker(){
        final Calendar myCalendar = Calendar.getInstance();

        EditText editText= (EditText) findViewById(R.id.editEnd);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText, myCalendar);
            }

        };

        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(EditText editText, Calendar myCalendar) {
        String myFormat = "yyyy.MM.dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        editText.setText(sdf.format(myCalendar.getTime()));
    }

    public void saveOnClick(View v){
        Intent intent = new Intent();
        intent.putExtra("status", ((Spinner)findViewById(R.id.spinnerStatus)).getSelectedItem().toString());
        intent.putExtra("appointmentType", ((EditText)findViewById(R.id.editAppointmentType)).getText().toString());
        intent.putExtra("description", ((EditText)findViewById(R.id.editDescription)).getText().toString());
        intent.putExtra("start", ((EditText)findViewById(R.id.editStart)).getText().toString());
        intent.putExtra("end", ((EditText)findViewById(R.id.editEnd)).getText().toString());
        intent.putExtra("participant", ((EditText)findViewById(R.id.editParticipant)).getText().toString());

        if(!isNew){
            intent.putExtra("identifier", selectedAppointmentId);
        }

        setResult(1, intent);

        finish();
    }

    public void cancelOnClick(View v){
        Intent intent = new Intent();
        setResult(0, intent);
        finish();
    }

    public void deleteOnClick(View v){
        Intent intent = new Intent();
        intent.putExtra("identifier", selectedAppointmentId);
        setResult(2, intent);
        finish();
    }
}
