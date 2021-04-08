package hector.developers.alabaster.details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hector.developers.alabaster.R;
import hector.developers.alabaster.model.Members;

public class MembersDetailsActivity extends AppCompatActivity {
    TextView detailMemberFirstname;
    TextView detailMemberLastname;
    TextView detailMemberState;
    TextView detailMemberAddress;
    TextView detailMemberGender;
    TextView detailMemberPhone;
    TextView detailMemberCity;
    TextView detailMemberOccupation;
    TextView detailMemberPosition;
    TextView detailMemberDateOfBirth;
    Button sendMessage, mBtnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_details);

        detailMemberFirstname = findViewById(R.id.detailFirstname);
        detailMemberLastname = findViewById(R.id.detailsLastname);
        detailMemberState = findViewById(R.id.detailsState);
        detailMemberAddress = findViewById(R.id.detailAddress);
        detailMemberPhone = findViewById(R.id.detailPhone);
        detailMemberCity = findViewById(R.id.detailCity);
        detailMemberOccupation = findViewById(R.id.detailOccupation);
        detailMemberPosition = findViewById(R.id.detailPostion);
        detailMemberDateOfBirth = findViewById(R.id.detailDateOfBirth);
        detailMemberGender = findViewById(R.id.detailGender);
        sendMessage = findViewById(R.id.sendText);
        mBtnCall = findViewById(R.id.btnCall);

        displayDetails();

        mBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callMember();
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void callMember() {
        // TODO Auto-generated method stub
        try {
            //  Toast.makeText(mContext, "initiating call to " + phone.getText().toString(), Toast.LENGTH_LONG).show();
            Log.e("Health Details Activity", "Invoking call");
            if (ContextCompat.checkSelfPermission(MembersDetailsActivity.this, "android.permission.CALL_PHONE") == 0) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + detailMemberPhone.getText().toString()));
                startActivity(i);
            } else {
                ActivityCompat.requestPermissions((Activity) MembersDetailsActivity.this, new String[]{"android.permission.CALL_PHONE"}, 0);
            }

        } catch (Exception e) {
            Log.e("Health Details Activity", "Failed to invoke call", e);

        }
    }

    private void sendMessage() {
        Uri uri = Uri.parse("smsto: " + detailMemberPhone.getText().toString());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "We are Just checking on you ");
        startActivity(intent);
    }

    private void displayDetails() {
        Members members;
        members = (Members) getIntent().getSerializableExtra("key");
        assert members != null;
        detailMemberFirstname.setText(members.getFirstname());
        detailMemberLastname.setText(members.getLastname());
        detailMemberState.setText(members.getState());
        detailMemberPhone.setText(members.getPhone());
        detailMemberCity.setText(members.getCity());
        detailMemberAddress.setText(members.getAddress());
        detailMemberDateOfBirth.setText(members.getDateOfBirth());
        detailMemberOccupation.setText(members.getOccupation());
        detailMemberPosition.setText(members.getPosition());
        detailMemberGender.setText(members.getGender());
    }

}