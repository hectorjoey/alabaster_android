package hector.developers.alabaster.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;

import hector.developers.alabaster.R;
import hector.developers.alabaster.list.AttendanceListActivity;
import hector.developers.alabaster.list.BirthListActivity;
import hector.developers.alabaster.list.FinanceListActivity;
import hector.developers.alabaster.list.MemberListActivity;
import hector.developers.alabaster.list.RequestListActivity;
import hector.developers.alabaster.list.ViewTodayAttendanceActivity;
import hector.developers.alabaster.list.ViewTodayRevenueActivity;

public class MainActivity extends AppCompatActivity {
    MaterialCardView mViewMemberCardId, mAddMemberCardId, mAddAttendanceCardId,
            mViewAttendanceCardId, mViewBirthdayCardId, mAddRevenueCardId,
            mViewRevenueCardId, mViewTodayAttendanceCardId, mViewTodayRevenueCardId;

    androidx.appcompat.widget.Toolbar mToolbar;
    String userTypes;
    TextView mUserT;

    String account = "Welcome Account";
    String user = "Welcome Usher";
    String admin = "Welcome Admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewMemberCardId = findViewById(R.id.viewMemberCardId);
        mAddMemberCardId = findViewById(R.id.addMemberCardId);
        mAddAttendanceCardId = findViewById(R.id.addAttendanceCardId);
        mViewAttendanceCardId = findViewById(R.id.viewAttendanceCardId);
        mViewBirthdayCardId = findViewById(R.id.viewBirthdayCardId);
        mAddRevenueCardId = findViewById(R.id.addRevenueCardId);
        mViewRevenueCardId = findViewById(R.id.viewRevenueCardId);
        mViewTodayAttendanceCardId = findViewById(R.id.viewTodayAttendanceCardId);
        mViewTodayRevenueCardId = findViewById(R.id.viewTodayRevenueCardId);
        mUserT = findViewById(R.id.userT);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("");

        HashMap<String, String> userType = getUserTypes();
        userTypes = userType.get("userTypes");
        System.out.println("Userss><><" + userTypes);

        if (userTypes != null && (userTypes.equals("USHER"))) {
            mToolbar.setVisibility(View.GONE);
            mAddAttendanceCardId.setVisibility(View.VISIBLE);
            mViewAttendanceCardId.setVisibility(View.GONE);
            mViewMemberCardId.setVisibility(View.GONE);
            mAddMemberCardId.setVisibility(View.GONE);
            mViewBirthdayCardId.setVisibility(View.GONE);
            mAddRevenueCardId.setVisibility(View.VISIBLE);
            mViewRevenueCardId.setVisibility(View.GONE);
            mViewTodayAttendanceCardId.setVisibility(View.VISIBLE);
            mViewTodayRevenueCardId.setVisibility(View.VISIBLE);
            mUserT.setText(user);
        } else if (userTypes != null && (userTypes.equals("ACCOUNT"))) {
            mToolbar.setVisibility(View.GONE);
            mAddAttendanceCardId.setVisibility(View.GONE);
            mViewMemberCardId.setVisibility(View.GONE);
            mAddMemberCardId.setVisibility(View.GONE);
            mViewAttendanceCardId.setVisibility(View.GONE);
            mViewTodayAttendanceCardId.setVisibility(View.GONE);
            mViewBirthdayCardId.setVisibility(View.GONE);
            mAddRevenueCardId.setVisibility(View.GONE);
            mViewRevenueCardId.setVisibility(View.VISIBLE);
            mUserT.setText(account);

        } else {
            mUserT.setText(admin);
        }

        mViewTodayRevenueCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewTodayRevenueIntent = new Intent(getApplicationContext(), ViewTodayRevenueActivity.class);
                startActivity(viewTodayRevenueIntent);
                finish();
            }
        });
        mViewTodayAttendanceCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent todayAttendanceIntent = new Intent(getApplicationContext(), ViewTodayAttendanceActivity.class);
                startActivity(todayAttendanceIntent);
                finish();
            }
        });

        mViewMemberCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewMembersIntent = new Intent(MainActivity.this, MemberListActivity.class);
                startActivity(viewMembersIntent);
                finish();
            }
        });

        mAddMemberCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewMembersIntent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(viewMembersIntent);
                finish();
            }
        });

        mAddAttendanceCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAttendanceIntent = new Intent(MainActivity.this, AddAttendanceActivity.class);
                startActivity(addAttendanceIntent);
                finish();
            }
        });

        mViewAttendanceCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewAttendanceIntent = new Intent(getApplicationContext(), AttendanceListActivity.class);
                startActivity(viewAttendanceIntent);
                finish();
            }
        });
        mViewBirthdayCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewBirthdayIntent = new Intent(getApplicationContext(), BirthListActivity.class);
                startActivity(viewBirthdayIntent);
                finish();
            }
        });

        mAddRevenueCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRevenueIntent = new Intent(getApplicationContext(), FinanceActivity.class);
                startActivity(addRevenueIntent);
                finish();
            }
        });

        mViewRevenueCardId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRevenueIntent = new Intent(getApplicationContext(), FinanceListActivity.class);
                startActivity(addRevenueIntent);
                finish();
            }
        });

    }

    private HashMap<String, String> getUserTypes() {
        HashMap<String, String> userType = new HashMap<>();
        SharedPreferences sharedPreferences = this.getSharedPreferences("userTypes", Context.MODE_PRIVATE);
        userType.put("userTypes", sharedPreferences.getString("userTypes", null));
        return userType;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.share:
                share();
                return true;
            case R.id.requests:
                viewRequests();
                return true;
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void viewRequests() {
        Intent requestIntent = new Intent(getApplicationContext(), RequestListActivity.class);
        startActivity(requestIntent);
        finish();
    }

    private void logout() {
        Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(logoutIntent);
        finish();
    }

    private void share() {
        Intent shareIntent = new Intent(MainActivity.this, SocialActivity.class);
        startActivity(shareIntent);
        finish();
    }


    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Go back?")
                .setMessage("Are you sure you want to go back?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }
}