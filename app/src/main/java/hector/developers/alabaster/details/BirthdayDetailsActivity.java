package hector.developers.alabaster.details;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hector.developers.alabaster.R;
import hector.developers.alabaster.model.Members;

public class BirthdayDetailsActivity extends AppCompatActivity {
TextView bdFirstname, bdGender, bdPhone, bdDob;
Button mBtnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_details);
        bdFirstname = findViewById(R.id.detFirstname);
        bdGender = findViewById(R.id.detGender);
        bdPhone = findViewById(R.id.detPhone);
        bdDob = findViewById(R.id.detDob);
        mBtnText = findViewById(R.id.sendBirthdayText);

        displayTodayBirthday();
        mBtnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
    }

    private void sendSms() {
        Uri uri = Uri.parse("smsto: " + bdPhone.getText().toString());
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", "Today is a wonderful day worth celebrating, because today God Almighty gifted you with life. May you share the joy of salvation with everyone around you. Happy birthday!. From Alabaster Assembly  ");
        startActivity(intent);
    }

    private void displayTodayBirthday() {
        Members members;
        members = (Members) getIntent().getSerializableExtra("key");
        assert members != null;
        bdFirstname.setText(members.getFirstname());
        bdPhone.setText(members.getPhone());
        bdDob.setText(members.getDateOfBirth());
        bdGender.setText(members.getGender());
    }
}