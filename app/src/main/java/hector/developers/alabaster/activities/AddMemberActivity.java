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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ikhiloyaimokhai.nigeriastatesandlgas.Nigeria;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import hector.developers.alabaster.R;
import hector.developers.alabaster.api.RetrofitClient;
import hector.developers.alabaster.model.Members;
import hector.developers.alabaster.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemberActivity extends AppCompatActivity {
    private static final int SPINNER_HEIGHT = 500;
    private EditText mFirstname, mLastname, mPhone, mEmail, mAddress, mOccupation, mCity;
    Spinner mStateSpinner, mGenderSpinner;
    Button mSave;
    Util util;
    private String mState;
    private List<String> states;
    EditText mDate;
    final Calendar cldr = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        util = new Util();
        mStateSpinner = findViewById(R.id.stateSpinner);
        mGenderSpinner = findViewById(R.id.genderSpinner);
        mFirstname = findViewById(R.id.firstname);
        mLastname = findViewById(R.id.lastname);
        mPhone = findViewById(R.id.phone);
        mEmail = findViewById(R.id.email);
        mAddress = findViewById(R.id.address);
        mCity = findViewById(R.id.city);
        mOccupation = findViewById(R.id.occupation);
        mSave = findViewById(R.id.btnSave);
        mDate = findViewById(R.id.edit_date);

        resizeSpinner(mStateSpinner, SPINNER_HEIGHT);
        states = Nigeria.getStates();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = mFirstname.getText().toString().trim();
                String lastname = mLastname.getText().toString().trim();
                String date = mDate.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                String state = mStateSpinner.getSelectedItem().toString();
                String gender = mGenderSpinner.getSelectedItem().toString();
                String email = mEmail.getText().toString().trim();
                String address = mAddress.getText().toString().trim();
                String city = mCity.getText().toString().trim();
                String occupation = mOccupation.getText().toString().trim();

                //validating fields
                if (util.isNetworkAvailable(getApplicationContext())) {
                    if (TextUtils.isEmpty(firstname)) {
                        mFirstname.setError("Enter Firstname!");
                        mFirstname.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(lastname)) {
                        mLastname.setError("Enter Lastname!");
                        mLastname.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(phone)) {
                        mPhone.setError("Enter Phone!");
                        mPhone.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(email)) {
                        mEmail.setError("Enter Email!");
                        mEmail.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(address)) {
                        mAddress.setError("Enter Address!");
                        mAddress.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(city)) {
                        mCity.setError("Enter City!");
                        mCity.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(occupation)) {
                        mOccupation.setError("Enter Occupation!");
                        mOccupation.requestFocus();
                        return;
                    }
                    addMember(firstname, lastname, date, phone, state, gender, email, address, city, occupation);
                } else {
                    Toast.makeText(AddMemberActivity.this, "Please Check your network connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datePicker = new DatePickerDialog(AddMemberActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                monthOfYear = monthOfYear + 1;
                                mDate.setText(dayOfMonth + "/" + monthOfYear);
                            }
                        }, year, month, day);
                //disable past dates
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                // show date dialog
                datePicker.show();
            }
        });

        //call to method that'll set up state and lga spinner
        setupSpinners();
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderAdapter);

    }

    /**
     * Method to set up the spinners
     */
    public void setupSpinners() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        //populates the quantity spinner ArrayList
        ArrayAdapter<String> statesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);

        // Specify dropdown layout style - simple list view with 1 item per line
        statesAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Apply the adapter to the spinner
        statesAdapter.notifyDataSetChanged();
        mStateSpinner.setAdapter(statesAdapter);

        // Set the integer mSelected to the constant values
        mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mState = (String) parent.getItemAtPosition(position);
                setUpStatesSpinner(position);
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Unknown
            }
        });
    }

    private void setUpStatesSpinner(int position) {
        List<String> list = new ArrayList<>(Nigeria.getLgasByState(states.get(position)));
    }

    private void resizeSpinner(Spinner spinner, int height) {
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            //Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            //set popupWindow height to height
            popupWindow.setHeight(height);

        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private void addMember(String firstname, String lastname, String date, String phone,
                           String state, String gender, String email, String address, String city, String occupation) {

        //making api calls
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Data...");
        progressDialog.show();
        Call<Members> call = RetrofitClient
                .getInstance()
                .getApi()
                .createMember(firstname, lastname, date, phone, state, gender, email, address, city, occupation);
        call.enqueue(new Callback<Members>() {
            @Override
            public void onResponse(Call<Members> call, Response<Members> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Saved Successfully!", Toast.LENGTH_SHORT).show();
                    clearText();

                } else {
                    Toast.makeText(getApplicationContext(), "Saving Failed!", Toast.LENGTH_SHORT).show();
                    System.out.println("Failing+++ :" + response);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Members> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("Error!>>> " + t.getMessage());
            }
        });
    }

    private void clearText() {
        mFirstname.setText("");
        mLastname.setText("");
        mEmail.setText("");
        mOccupation.setText("");
        mCity.setText("");
        mAddress.setText("");
        mPhone.setText("");
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(AddMemberActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}