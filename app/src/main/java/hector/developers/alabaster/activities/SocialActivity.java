package hector.developers.alabaster.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import hector.developers.alabaster.R;

public class SocialActivity extends AppCompatActivity {
    MaterialCardView mFacebookCardId, mInstagramCardId, mWhatsAppCardId;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        mFacebookCardId = findViewById(R.id.facebookCardId);
        mInstagramCardId = findViewById(R.id.instagramCardId);
        mWhatsAppCardId = findViewById(R.id.whatsappCardId);

        mFacebookCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Can be triggered by a view event such as a button press
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                shareIntent.setType("video/*");
                // Launch sharing dialog for image
                shareIntent.setPackage("com.facebook.katana");
                startActivity(Intent.createChooser(shareIntent, "Share Image"));
            }
        });
        mInstagramCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent feedIntent = new Intent(Intent.ACTION_SEND);
                feedIntent.setType("image/*");
//                Intent storiesIntent = new Intent("com.instagram.share.ADD_TO_STORY");
                feedIntent.setType("video/*");
                feedIntent.setPackage("com.instagram.android");
                // Launch sharing dialog for image
                startActivity(Intent.createChooser(feedIntent, "Share to instagram"));
            }
        });

        mWhatsAppCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Not Available at the moment", Toast.LENGTH_LONG).show();
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
                        Intent intent = new Intent(SocialActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

}