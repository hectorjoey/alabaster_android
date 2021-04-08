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
import hector.developers.alabaster.adapter.FinanceAdapter;
import hector.developers.alabaster.model.Finance;

public class ViewTodayRevenueActivity extends AppCompatActivity {

    ProgressDialog loadingBar;
    private RecyclerView rv;
    private List<Finance> financeList;
    private FinanceAdapter adapter;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_today_revenue);

        rv = findViewById(R.id.recyclerViewTodayRevenue);
        rv.setLayoutManager(new LinearLayoutManager(this));
        loadingBar = new ProgressDialog(this);
        loadData();
    }

    private void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.get("https://alabaster-assembly-service.herokuapp.com/api/v1/finance")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
                            String currentDate = sdf.format(new Date());
                            financeList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    Finance model = new Finance();
                                    JSONObject obj = response.getJSONObject(i);

                                    String day = obj.getString("day");
                                    String programme = obj.getString("programme");
                                    String tithe = obj.getString("tithe");
                                    String offering = obj.getString("offering");
                                    String firstFruit = obj.getString("firstFruit");
                                    String projectOrPledgeFund = obj.getString("projectOrPledgeFund");
                                    String others = obj.getString("others");
                                    String countingUsher = obj.getString("countingUsher");
                                    String receivedBy = obj.getString("receivedBy");
                                    String date = obj.getString("date");
                                    String total = obj.getString("total");

                                    model.setDay(day);
                                    model.setProgramme(programme);
                                    model.setTithe(tithe);
                                    model.setOffering(offering);
                                    model.setFirstFruit(firstFruit);
                                    model.setProjectOrPledgeFund(projectOrPledgeFund);
                                    model.setOthers(others);
                                    model.setCountingUsher(countingUsher);
                                    model.setReceivedBy(receivedBy);
                                    model.setDate(date);
                                    model.setTotal(total);

                                    if (date.equals(currentDate)) {
                                        financeList.add(model);
                                        progressDialog.dismiss();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                adapter = new FinanceAdapter(financeList, ViewTodayRevenueActivity.this);
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
                        Intent intent = new Intent(ViewTodayRevenueActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();

    }
}