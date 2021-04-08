package hector.developers.alabaster.list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hector.developers.alabaster.R;
import hector.developers.alabaster.activities.MainActivity;
import hector.developers.alabaster.adapter.AttendanceAdapter;
import hector.developers.alabaster.model.Attendance;

public class ViewTodayAttendanceActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<Attendance> attendanceList;
    private AttendanceAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_today_attendance);
        rv = findViewById(R.id.recyclerViewTodayAttendance);
        rv.setLayoutManager(new LinearLayoutManager(this));

        loadData();
    }

    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.get("https://alabaster-assembly-service.herokuapp.com/api/v1/attendance")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                            String currentDate = sdf.format(new Date());
                            attendanceList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    Attendance model = new Attendance();
                                    JSONObject obj = response.getJSONObject(i);
                                    String numberOfMales = obj.getString("numberOfMales");
                                    String numberOfFemales = obj.getString("numberOfFemales");
                                    String numberOfChildren = obj.getString("numberOfChildren");
                                    String date = obj.getString("date");
                                    String total = obj.getString("total");

                                    model.setNumberOfMales(numberOfMales);
                                    model.setNumberOfFemales(numberOfFemales);
                                    model.setNumberOfChildren(numberOfChildren);
                                    model.setDate(date);
                                    model.setTotal(total);

                                    if (date.equals(currentDate)) {
                                        attendanceList.add(model);
                                        progressDialog.dismiss();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                adapter = new AttendanceAdapter(attendanceList, ViewTodayAttendanceActivity.this);
                                rv.setAdapter(adapter);
                            }
                            progressDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Birthday!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        System.out.println(anError.getMessage());
                        Log.d("Error::", anError.getMessage());
                    }
                });
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Want to go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(ViewTodayAttendanceActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}