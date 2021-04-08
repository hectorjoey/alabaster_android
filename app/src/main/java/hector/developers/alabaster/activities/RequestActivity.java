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
import android.widget.Toast;

import java.util.Calendar;

import hector.developers.alabaster.R;
import hector.developers.alabaster.api.RetrofitClient;
import hector.developers.alabaster.model.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class RequestActivity extends AppCompatActivity {

    Button mSubmit;
    EditText mDate, mName, mDepartment, mAmount, mReason;
    final Calendar cldr = Calendar.getInstance();
    String approvalStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        mDate = findViewById(R.id.dateOfRequest);
        mName = findViewById(R.id.nameOfRequester);
        mDepartment = findViewById(R.id.department);
        mAmount = findViewById(R.id.amountRequested);
        mReason = findViewById(R.id.reason);
        mSubmit = findViewById(R.id.requestSubmit);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datePicker = new DatePickerDialog(RequestActivity.this,
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

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = mDate.getText().toString();
                String name = mName.getText().toString().trim();
                String department = mDepartment.getText().toString().trim();
                String amount = mAmount.getText().toString().trim();
                String reason = mReason.getText().toString().trim();

                //validating fields
                if (TextUtils.isEmpty(name)) {
                    mName.setError("Please Enter your name!");
                    mName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(department)) {
                    mDepartment.setError("Please Enter your department!");
                    mDepartment.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(amount)) {
                    mAmount.setError("Please Enter amount!");
                    mAmount.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(reason)) {
                    mReason.setError("Please Enter amount!");
                    mReason.requestFocus();
                    return;
                }
                createRequest(date, name, department, amount, reason, approvalStatus);
            }
        });
    }

    private void createRequest(String date, String nameOfRequester, String department, String amount, String reason, String approvalStatus) {
        //making api calls
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Data...");
        progressDialog.show();

        Call<Request> call = RetrofitClient
                .getInstance()
                .getApi()
                .createRequest(date, nameOfRequester, department, amount, reason, approvalStatus);

        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Request Sent!", Toast.LENGTH_LONG).show();
                    System.out.println("Sending Request " + response);
                    clearText();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Request sending failed!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearText() {
        mDate.setText("");
        mName.setText("");
        mDepartment.setText("");
        mAmount.setText("");
        mReason.setText("");
    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(RequestActivity.this, CreateRequestActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

}