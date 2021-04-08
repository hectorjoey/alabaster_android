package hector.developers.alabaster.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import hector.developers.alabaster.R;
import hector.developers.alabaster.api.RetrofitClient;
import hector.developers.alabaster.list.BirthListActivity;
import hector.developers.alabaster.model.Attendance;
import hector.developers.alabaster.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAttendanceActivity extends AppCompatActivity {
    EditText mDate;
    private String total;
    private int grandTotal;
    private EditText mNumberOfMen, mNumberOfWomen, mNumberOfChildren;
    Util util;
    Button mSaveAttendance;
    final Calendar cldr = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);
        util = new Util();
        mDate = findViewById(R.id.edit_dateOfAttendance);
        mNumberOfMen = findViewById(R.id.numberOfMen);
        mNumberOfWomen = findViewById(R.id.numberOfWomen);
        mNumberOfChildren = findViewById(R.id.numberOfChildren);
//        mTotal = findViewById(R.id.totalDisplay);

        mSaveAttendance = findViewById(R.id.saveAttendance);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datePicker = new DatePickerDialog(AddAttendanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                mDate.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                            }
                        }, year, month, day);
                //disable future dates
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
                //disable past dates
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                // show date dialog
                datePicker.show();
            }
        });

        mSaveAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberOfMen = mNumberOfMen.getText().toString().trim();
                String numberOfWomen = mNumberOfWomen.getText().toString().trim();
                String numberOfChildren = mNumberOfChildren.getText().toString().trim();
                String date = mDate.getText().toString().trim();

                //validating fields
                if (util.isNetworkAvailable(getApplicationContext())) {
                    if (TextUtils.isEmpty(numberOfMen)) {
                        mNumberOfMen.setError("Field must not be empty!");
                        mNumberOfMen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(numberOfWomen)) {
                        mNumberOfWomen.setError("Field must not be empty!");
                        mNumberOfWomen.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(numberOfChildren)) {
                        mNumberOfChildren.setError("Field must not be empty!");
                        mNumberOfChildren.requestFocus();
                        return;
                    }

                    grandTotal = Integer.parseInt(numberOfMen) + Integer.parseInt(numberOfWomen) + Integer.parseInt(numberOfChildren);
                    total = String.valueOf(grandTotal);
                    addAttendance(numberOfMen, numberOfWomen, numberOfChildren, date, total);
                } else {
                    Toast.makeText(AddAttendanceActivity.this, "Please Check your network connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addAttendance(String numberOfMales, String numberOfFemales, String numberOfChildren,
                               String date, String total) {
        //making api calls
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Data...");
        progressDialog.show();

        Call<Attendance> call = RetrofitClient
                .getInstance()
                .getApi()
                .createAttendance(numberOfMales, numberOfFemales, numberOfChildren, date, total);
        call.enqueue(new Callback<Attendance>() {
            @Override
            public void onResponse(Call<Attendance> call, Response<Attendance> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show();
                    clearText();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Data sending failed!", Toast.LENGTH_SHORT).show();
                    System.out.println("Failing+++ " + response);
                    System.out.println("Responding Fail $$$ " + response.body());
                }
            }

            @Override
            public void onFailure(Call<Attendance> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearText() {
        mNumberOfMen.setText("");
        mNumberOfWomen.setText("");
        mNumberOfChildren.setText("");
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(AddAttendanceActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}