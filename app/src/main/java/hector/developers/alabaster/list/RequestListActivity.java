package hector.developers.alabaster.list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import hector.developers.alabaster.R;
import hector.developers.alabaster.activities.MainActivity;
import hector.developers.alabaster.adapter.AttendanceAdapter;
import hector.developers.alabaster.adapter.RequestAdapter;
import hector.developers.alabaster.api.RetrofitClient;
import hector.developers.alabaster.model.Attendance;
import hector.developers.alabaster.model.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestListActivity extends AppCompatActivity {

    ProgressDialog loadingBar;
    private RecyclerView rv;
    private List<Request> requestList;
    private RequestAdapter adapter;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        rv = findViewById(R.id.recyclerViewRequest);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        mToolbar = findViewById(R.id.main_page_toolbar);
//        setSupportActionBar(mToolbar);
        setTitle("");
//        loadingBar = new ProgressDialog(this);
        loadData();
    }

    public void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        Call<List<Request>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllRequest();

        call.enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                progressDialog.dismiss();
                List<Request> requestList = response.body();
                adapter = new RequestAdapter(requestList, RequestListActivity.this);
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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
                        Intent intent = new Intent(RequestListActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}