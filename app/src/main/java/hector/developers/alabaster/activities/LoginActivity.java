package hector.developers.alabaster.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import hector.developers.alabaster.api.RetrofitClient;
import hector.developers.alabaster.R;
import hector.developers.alabaster.model.Users;
import hector.developers.alabaster.utils.SessionManagement;
import hector.developers.alabaster.utils.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText mEmail, mPassword;
    Button mLogin;
    ProgressDialog progressDialog;
    private ImageView cloud1, star;
    Animation animCloud, animStar;
    Util util;
    private SessionManagement sessionManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        util = new Util();
        mEmail = findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPassword);
        mLogin = findViewById(R.id.btLogin);
        cloud1 = findViewById(R.id.cloud1);
        star = findViewById(R.id.star);
        animCloud = AnimationUtils.loadAnimation(this, R.anim.animcloud);
        animStar = AnimationUtils.loadAnimation(this, R.anim.animstar);
        cloud1.startAnimation(animCloud);
        star.startAnimation(animStar);

        sessionManagement = new SessionManagement(this);

        sessionManagement.getLoginEmail();
        sessionManagement.getLoginPassword();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //validating fields
                if (util.isNetworkAvailable(getApplicationContext())) {
                    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                        mEmail.setError("Enter Email!");
                        mEmail.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        mPassword.setError("Enter Password!");
                        mPassword.requestFocus();
                        return;
                    }
                    loginUser(email, password);

                } else {
                    Toast.makeText(LoginActivity.this, "Please Check your network connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String email, String password) {
        Call<Users> call;
        call = RetrofitClient
                .getInstance()
                .getApi()
                .login(email, password);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Authenticating Please Wait...");
        progressDialog.show();

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    Users users = response.body();
                    System.out.println("users XXXXXX " + response);
                    assert response.body() != null;
//                    userID = response.body().getId();
                    assert users != null;
                    if (users.getUserType().equalsIgnoreCase("Others")) {
                        Intent infoIntent = new Intent(getApplicationContext(), CreateRequestActivity.class);
                        startActivity(infoIntent);
                        finish();

                    } else {
                        Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                        sessionManagement.setLoginEmail(email);
                        sessionManagement.setLoginPassword(password);
                        assert response.body() != null;

                        Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        System.out.println("Responding:::" + response);
                        mainIntent.putExtra("userType", response.body().getUserType());
                        System.out.println("User Type ???" + response.body().getUserType());
                        mainIntent.putExtra("userType", users.getUserType());
                        System.out.println("User Type XXXXx" + response.body().getUserType());
                        saveUserType(users.getUserType());
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_SHORT).show();
                    System.out.println("Failed!>>" + response);
                    System.out.println("Failed!>>" + response.body());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void saveUserType(String userType) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("userTypes", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.clear();
        edit.putString("userTypes", userType + "");
        edit.apply();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}