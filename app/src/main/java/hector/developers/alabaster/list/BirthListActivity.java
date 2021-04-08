package hector.developers.alabaster.list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hector.developers.alabaster.R;
import hector.developers.alabaster.activities.MainActivity;
import hector.developers.alabaster.adapter.BirthdayAdapter;
import hector.developers.alabaster.model.Members;

public class BirthListActivity extends AppCompatActivity {
    //    String TAG = "BirthList ACTIVITY";
    private Toolbar mToolbar;
    ProgressDialog loadingBar;
    private RecyclerView rv;
    private List<Members> membersList;
    private BirthdayAdapter adapter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birth_list);

        rv = findViewById(R.id.recyclerViews);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        mToolbar = findViewById(R.id.main_page_toolbar);
//        setSupportActionBar(mToolbar);
//        setTitle("");
        loadingBar = new ProgressDialog(this);
        date = String.valueOf(android.text.format.DateFormat.format("d/M", new Date()));
        loadData("https://alabaster-assembly-service.herokuapp.com/api/v1/members");

    }

    public void loadData(String url) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.get(url)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
//                            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
//                            String currentDate = sdf.format(new Date());
                            membersList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    Members model = new Members();
                                    JSONObject obj = response.getJSONObject(i);
                                    String id = String.valueOf(obj.getLong("id"));
                                    String firstname = obj.getString("firstname");
                                    String lastname = obj.getString("lastname");
                                    String email = obj.getString("email");
                                    String dateOfBirth = obj.getString("dateOfBirth");
                                    String gender = obj.getString("gender");
                                    String phone = obj.getString("phone");

                                    model.setId(Long.valueOf(id));
                                    model.setFirstname(firstname);
                                    model.setLastname(lastname);
                                    model.setEmail(email);
                                    model.setGender(gender);
                                    model.setDateOfBirth(dateOfBirth);
                                    model.setPhone(phone);

                                    if (date.equals(dateOfBirth)) {
                                        membersList.add(model);
                                        progressDialog.dismiss();
                                        notifyAdmin();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                adapter = new BirthdayAdapter(membersList, BirthListActivity.this);
                                rv.setAdapter(adapter);
                                notifyAdmin();
                            }
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Birthday!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError.getMessage());
                        Log.d("Error::", anError.getMessage());
                    }
                });
    }

    private void notifyAdmin() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
//                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Notification")
                .setContentText("Login to take actions!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(BirthListActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}