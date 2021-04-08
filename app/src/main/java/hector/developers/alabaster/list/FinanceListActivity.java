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
import hector.developers.alabaster.adapter.FinanceAdapter;
import hector.developers.alabaster.adapter.MemberAdapter;
import hector.developers.alabaster.api.RetrofitClient;
import hector.developers.alabaster.model.Finance;
import hector.developers.alabaster.model.Members;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinanceListActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<Finance> financeList;
    private FinanceAdapter adapter;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_list);
        rv = findViewById(R.id.recyclerFinanceView);
        rv.setLayoutManager(new LinearLayoutManager(this));

        setTitle("");
        loadData();
    }

    public void loadData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        Call<List<Finance>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllFinance();

        call.enqueue(new Callback<List<Finance>>() {
            @Override
            public void onResponse(Call<List<Finance>> call, Response<List<Finance>> response) {
                progressDialog.dismiss();
                List<Finance> financeList = response.body();
                adapter = new FinanceAdapter(financeList, FinanceListActivity.this);
                rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Finance>> call, Throwable t) {
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
                        Intent intent = new Intent(FinanceListActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}