package hector.developers.alabaster.details;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import hector.developers.alabaster.R;
import hector.developers.alabaster.model.Finance;
import hector.developers.alabaster.model.Members;

public class FinanceDetailsActivity extends AppCompatActivity {

    TextView detailDate;
    TextView detailDay;
    TextView detailProgramme;
    TextView detailTithe;
    TextView detailOffering;
    TextView detailFirstFruit;
    TextView detailProjectOrPledgeFund;
    TextView detailOthers;
    TextView detailTotal;
    TextView detailCountingUsher;
    TextView detailReceivedBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_details);
        detailDate = findViewById(R.id.detailDate);
        detailDay = findViewById(R.id.detailsDay);
        detailProgramme = findViewById(R.id.detailProgramme);
        detailTithe = findViewById(R.id.detailsTithe);
        detailOffering = findViewById(R.id.detailOffering);
        detailFirstFruit = findViewById(R.id.detailFirstFruits);
        detailProjectOrPledgeFund = findViewById(R.id.detailProjectOrPledgeFund);
        detailOthers = findViewById(R.id.detailOthers);
        detailTotal = findViewById(R.id.detailGrandTotal);
        detailCountingUsher = findViewById(R.id.detailCountingUsher);
        detailReceivedBy = findViewById(R.id.detailReceivedBy);
        displayDetails();
    }

    private void displayDetails() {
        Finance finance;
        finance = (Finance) getIntent().getSerializableExtra("key");
        assert finance != null;
        detailDate.setText(finance.getDate());
        detailDay.setText(finance.getDay());
        detailProgramme.setText(finance.getProgramme());
        detailTithe.setText("N"+finance.getTithe());
        detailOffering.setText("N"+finance.getOffering());
        detailFirstFruit.setText("N"+finance.getFirstFruit());
        detailProjectOrPledgeFund.setText("N"+finance.getProjectOrPledgeFund());
        detailOthers.setText("N"+finance.getOthers());
        detailTotal.setText("N"+finance.getTotal());
        detailCountingUsher.setText(finance.getCountingUsher());
        detailReceivedBy.setText(finance.getReceivedBy());
    }
}