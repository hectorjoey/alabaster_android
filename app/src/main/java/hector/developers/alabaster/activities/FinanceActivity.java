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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import hector.developers.alabaster.R;
import hector.developers.alabaster.api.RetrofitClient;
import hector.developers.alabaster.model.Finance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinanceActivity extends AppCompatActivity {

    EditText mDateOfTheWeek, mProgramme, mTithe, mOffering, mFirstFruit,
            mProjectOrPledgeFund, mOthers, mCountingUsher, mReceivedBy;
    final Calendar cldr = Calendar.getInstance();
    Spinner mDayOfTheWeekSpinner;
    Button mSaveFundButton;
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        mDayOfTheWeekSpinner = findViewById(R.id.daysOfTheWeekSpinner);
        mProgramme = findViewById(R.id.edit_programme);
        mTithe = findViewById(R.id.edit_tithe);
        mOffering = findViewById(R.id.edit_offering);
        mFirstFruit = findViewById(R.id.edit_firstFruits);
        mProjectOrPledgeFund = findViewById(R.id.edit_projectOrPledge);
        mOthers = findViewById(R.id.edit_others);
        mCountingUsher = findViewById(R.id.edit_countingUsher);
        mReceivedBy = findViewById(R.id.edit_receivedBy);
        mSaveFundButton = findViewById(R.id.saveFund);

        mDateOfTheWeek = findViewById(R.id.edit_dateOfEntry);

        mDateOfTheWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datePicker = new DatePickerDialog(FinanceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                mDateOfTheWeek.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
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
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> dayOfWeekAdapter = ArrayAdapter.createFromResource(this,
                R.array.daysOfTheWeek_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        dayOfWeekAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mDayOfTheWeekSpinner.setAdapter(dayOfWeekAdapter);
        mSaveFundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = mDateOfTheWeek.getText().toString().trim();
                String day = mDayOfTheWeekSpinner.getSelectedItem().toString();
                String programme = mProgramme.getText().toString().trim();
                String tithe = mTithe.getText().toString().trim();
                String offering = mOffering.getText().toString().trim();
                String firstFruit = mFirstFruit.getText().toString().trim();
                String projectOrPledgeFund = mProjectOrPledgeFund.getText().toString().trim();
                String others = mOthers.getText().toString().trim();
                String countingUsher = mCountingUsher.getText().toString().trim();
                String receivedBy = mReceivedBy.getText().toString().trim();

                //validating fields
                if (TextUtils.isEmpty(programme)) {
                    mProgramme.setError("Enter Programme!");
                    mProgramme.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(tithe)) {
                    mTithe.setError("Enter value for Tithe!");
                    mTithe.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(offering)) {
                    mOffering.setError("Enter value for Offering!");
                    mOffering.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(firstFruit)) {
                    mFirstFruit.setError("Enter value for First Fruit!");
                    mFirstFruit.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(projectOrPledgeFund)) {
                    mProjectOrPledgeFund.setError("Enter value for Project Or Pledge Fund!");
                    mProjectOrPledgeFund.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(others)) {
                    mOthers.setError("Enter value for Others!");
                    mOthers.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(countingUsher)) {
                    mCountingUsher.setError("Enter name of Counting Usher!");
                    mCountingUsher.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(receivedBy)) {
                    mReceivedBy.setError("Enter name of Receiver!");
                    mReceivedBy.requestFocus();
                    return;
                }
                int grandTotal = Integer.parseInt(mTithe.getText().toString()) + Integer.parseInt(mFirstFruit.getText().toString())
                        + Integer.parseInt(mOffering.getText().toString()) + Integer.parseInt(mProjectOrPledgeFund.getText().toString())
                        + Integer.parseInt(mOthers.getText().toString());

                String total = String.valueOf(grandTotal);
                saveFund(date, day, programme, tithe, offering, firstFruit, projectOrPledgeFund, others, countingUsher, receivedBy, total);
            }
        });
    }

    private void saveFund(String date, String day, String programme, String tithe, String offering, String firstFruit,
                          String projectOrPledgeFund, String others, String countingUsher, String receivedBy, String total) {
        Call<Finance> call = RetrofitClient
                .getInstance()
                .getApi()
                .addFinance(date, day, programme, tithe, offering, firstFruit, projectOrPledgeFund, others, countingUsher, receivedBy, total);

        //making api calls
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Data...");
        progressDialog.show();
        call.enqueue(new Callback<Finance>() {
            @Override
            public void onResponse(Call<Finance> call, Response<Finance> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Data Saved Successfully!", Toast.LENGTH_LONG).show();
                    clearText();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Data Saving Failed!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Finance> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void clearText() {
        mProgramme.setText("");
        mTithe.setText("");
        mOffering.setText("");
        mFirstFruit.setText("");
        mProjectOrPledgeFund.setText("");
        mOthers.setText("");
        mCountingUsher.setText("");
        mReceivedBy.setText("");
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(FinanceActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}