package hector.developers.alabaster.details;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import hector.developers.alabaster.R;
import hector.developers.alabaster.api.RetrofitClient;
import hector.developers.alabaster.list.RequestListActivity;
import hector.developers.alabaster.model.Members;
import hector.developers.alabaster.model.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDetailActivity extends AppCompatActivity {
    Spinner mApprovalSpinner;
    TextView detailReqDate, detailReqName, detailReqDep, detailReqAmount, detailReqReason, detailReqStatus;
    Button mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        detailReqDate = findViewById(R.id.detailReqDate);
        detailReqName = findViewById(R.id.detailReqName);
        detailReqDep = findViewById(R.id.detailReqDep);
        detailReqAmount = findViewById(R.id.detailReqAmount);
        detailReqReason = findViewById(R.id.detailReqReason);
        detailReqStatus = findViewById(R.id.detailReqStatus);
        mUpdate = findViewById(R.id.updateButton);
        mApprovalSpinner = findViewById(R.id.approvalSpinner);

        displayDetails();

        ArrayAdapter<CharSequence> approvalAdapter = ArrayAdapter.createFromResource(this,
                R.array.approval_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        approvalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mApprovalSpinner.setAdapter(approvalAdapter);

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String approvalStatus = mApprovalSpinner.getSelectedItem().toString();
                updateRequest(approvalStatus);
            }
        });

    }

    private void updateRequest(String approvalStatus) {

//        Request request = new Request();
        //making api calls
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Request...");
        progressDialog.show();

        Call<Request> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateRequest( approvalStatus);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_LONG).show();
                    Intent updateIntent = new Intent(getApplicationContext(), RequestListActivity.class);
                    startActivity(updateIntent);

                    System.out.println("Updating >>>" + response);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update failed!", Toast.LENGTH_LONG).show();
                    System.out.println("Not Updating >>>" + response);
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Update Error!", Toast.LENGTH_LONG).show();
                System.out.println("Not Updating >>>" + t);
                System.out.println("Not Updating >>>" + t.getMessage());
            }
        });
    }

    private void displayDetails() {
        Request request;
        request = (Request) getIntent().getSerializableExtra("key");
        assert request != null;

        detailReqDate.setText(request.getDate());
        detailReqName.setText(request.getNameOfRequester());
        detailReqDep.setText(request.getDepartment());
        detailReqAmount.setText(String.format("N%s", request.getAmount() + ".00"));
        detailReqReason.setText(request.getReason());
        detailReqStatus.setText(request.getApprovalStatus());
    }
}