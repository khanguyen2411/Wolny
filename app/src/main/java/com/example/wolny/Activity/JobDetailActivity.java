package com.example.wolny.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.Model.Bid;
import com.example.wolny.Model.Job;
import com.example.wolny.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobDetailActivity extends AppCompatActivity {

    ImageView ivBack, ivFlag;
    Button btPlaceBid;
    TextView tvTitle, tvDescription, tvStatus, tvTime, tvBudget, tvSkill, tvTotalBid;
    RecyclerView rvListBids;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        mapping();

        Job job = (Job)getIntent().getBundleExtra("bundle").getSerializable("job");
        mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String jobId = job.getJobID();
        if(uid.equals(job.getEmployerID())){
            btPlaceBid.setClickable(false);
            btPlaceBid.setBackgroundColor(Color.parseColor("#f6f6f6"));
        }

        tvTitle.setText(job.getTitle());
        tvStatus.setText(job.getStatus());
        String time = job.getTime() + " to complete";
        tvTime.setText(time);
        tvDescription.setText(job.getDescription());
        String budget = job.getBudget() + " " +job.getCurrency();
        tvBudget.setText(budget);
        tvSkill.setText(job.getSkillRequired());

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        btPlaceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog(jobId, uid);
            }
        });
    }

    private void showBottomSheetDialog(String jobId, String uid) {
        View mView = getLayoutInflater().inflate(R.layout.place_bid_bottom_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(mView);
        bottomSheetDialog.show();

        Button btCancel = mView.findViewById(R.id.btCancel);
        Button btPlaceBid = mView.findViewById(R.id.btPlaceBid);
        EditText etBudget = mView.findViewById(R.id.etBudget);
        EditText etTime = mView.findViewById(R.id.etTime);
        EditText etDescription = mView.findViewById(R.id.etDescription);


        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        btPlaceBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budget = etBudget.getText().toString().trim();
                String time = etTime.getText().toString().trim();
                String description = etDescription.getText().toString().trim();

                if(budget.isEmpty() || time.isEmpty() || description.isEmpty()){
                    Toast.makeText(getBaseContext(), "Empty information, check and try again", Toast.LENGTH_SHORT).show();
                } else {
                    Bid bid = new Bid(jobId, uid, description, time, budget);
                    mDatabase.child("Bids").child(jobId).setValue(bid).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getBaseContext(), "Done!!!!", Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                            } else {
                                Toast.makeText(getBaseContext(), "Error!!!!", Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                            }
                        }
                    });
                }

            }
        });
    }

    void mapping() {
        ivBack = findViewById(R.id.ivBack);
        ivFlag = findViewById(R.id.ivFlag);
        btPlaceBid = findViewById(R.id.btPlaceBid);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvStatus = findViewById(R.id.tvStatus);
        tvTime = findViewById(R.id.tvTime);
        tvBudget = findViewById(R.id.tvBudget);
        tvSkill = findViewById(R.id.tvSkill);
        tvTotalBid = findViewById(R.id.tvTotalBid);
        rvListBids = findViewById(R.id.rvListBids);
    }
}