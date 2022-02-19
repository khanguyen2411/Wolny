package com.example.wolny.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wolny.Adapter.JobDetail.BidAdapter;
import com.example.wolny.IMain;
import com.example.wolny.Model.Bid;
import com.example.wolny.Model.Job;
import com.example.wolny.Model.User;
import com.example.wolny.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobDetailActivity extends AppCompatActivity {

    ImageView ivBack, ivFlag, ivMore;
    Button btPlaceBid;
    TextView tvTitle, tvDescription, tvStatus, tvTime, tvBudget, tvSkill, tvTotalBid;
    RecyclerView rvListBids;
    DatabaseReference mDatabase;
    List<Bid> listBid;
    ProgressDialog mProgressDialog;
    Context mContext;
    String username, profileImage, employerID;
    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        mapping();

        mContext = this;
        Job job = (Job) getIntent().getBundleExtra("bundle").getSerializable("job");
        employerID = job.getEmployerID();
        mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        mDatabase.keepSynced(true);

        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        if (!uid.equals(job.getEmployerID())) {
            ivMore.setVisibility(View.GONE);
        }
        String jobId = job.getJobID();

        if (uid.equals(job.getEmployerID())) {
            btPlaceBid.setClickable(false);
            btPlaceBid.setEnabled(false);
            btPlaceBid.setBackgroundColor(Color.parseColor("#444444"));
        }

        tvTitle.setText(job.getTitle());
        tvStatus.setText(job.getStatus());
        String time = job.getTime() + " to complete";
        tvTime.setText(time);
        tvDescription.setText(job.getDescription());
        String budget = job.getBudget() + " " + job.getCurrency();
        tvBudget.setText(budget);
        tvSkill.setText(job.getSkillRequired());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        mReadDataOnce(databaseReference, "Users", uid, new IMain.OnGetDataListener() {
            @Override
            public void onStart() {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(mContext);
                    mProgressDialog.setMessage("Upload bid...");
                    mProgressDialog.setIndeterminate(true);
                }
                mProgressDialog.show();
            }

            @Override
            public void onSuccess(User user) {
                username = Objects.requireNonNull(user.getUsername());
                profileImage = Objects.requireNonNull(user.getProfileImage());

                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        });

        String flagUrl = "https://www.countryflags.io/" + job.getCountry() + "/flat/64.png";
        Glide.with(this)
                .load(flagUrl)
                .into(ivFlag);

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

        initBottomSheet();
        BidAdapter bidAdapter = new BidAdapter(this, job.getCurrency(), job.getType(), uid, bottomSheetDialog, bottomSheetView, databaseReference);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvListBids.getContext(), linearLayoutManager.getOrientation());
        rvListBids.addItemDecoration(dividerItemDecoration);
        rvListBids.setNestedScrollingEnabled(true);
        rvListBids.setLayoutManager(linearLayoutManager);
        rvListBids.setAdapter(bidAdapter);

        mDatabase.child("Bids").child(jobId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int a = (int) snapshot.getChildrenCount();
                String tt = "Total Bids (" + a + ")";
                tvTotalBid.setText(tt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query = mDatabase.child("Bids").child(job.getJobID());

        listBid = new ArrayList<>();

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Bid bid = snapshot.getValue(Bid.class);
                if (!listBid.contains(bid)) {
                    listBid.add(bid);
                }

                bidAdapter.setList(listBid);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                listBid.remove(snapshot.getValue(Bid.class));
                bidAdapter.setList(listBid);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(JobDetailActivity.this, ivMore);

                popupMenu.getMenuInflater()
                        .inflate(R.menu.job_pop_up_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit_job: {
                                Intent intent = new Intent(JobDetailActivity.this, EditJobActivity.class);
                                Bundle editBundle = new Bundle();
                                editBundle.putSerializable("job", job);
                                intent.putExtra("bundle", editBundle);
                                startActivity(intent);
                                finish();
                                break;
                            }

                            case R.id.menu_delete_job: {
                                confirmDeleteJob(databaseReference, jobId);
                                break;
                            }
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    public void confirmDeleteJob(DatabaseReference databaseReference, String jobId) {
        AlertDialog alertDialog = new AlertDialog.Builder(JobDetailActivity.this)
                .setTitle("CONFIRM DELETE JOB")
                .setMessage("Do you want to delete this job?")
                .setPositiveButton("OK", (dialog, which) -> {
                    databaseReference.child("Jobs").child(jobId).removeValue();
                    databaseReference.child("Bids").child(jobId).removeValue();
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .create();

        alertDialog.show();
    }

    void initBottomSheet() {
        bottomSheetView = getLayoutInflater().inflate(R.layout.place_bid_bottom_sheet, null);
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomSheetView);
    }

    private void showBottomSheetDialog(String jobId, String uid) {
        bottomSheetDialog.show();
        Button btCancel = bottomSheetView.findViewById(R.id.btCancel);
        Button btPlaceBid = bottomSheetView.findViewById(R.id.btPlaceBid);
        EditText etBudget = bottomSheetView.findViewById(R.id.etBudget);
        EditText etTime = bottomSheetView.findViewById(R.id.etTime);
        EditText etDescription = bottomSheetView.findViewById(R.id.etDescription);

        btCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        btPlaceBid.setOnClickListener(v -> {
            String budget = etBudget.getText().toString().trim();
            String time = etTime.getText().toString().trim();
            String description = etDescription.getText().toString().trim();

            if (budget.isEmpty() || time.isEmpty() || description.isEmpty()) {
                Toast.makeText(getBaseContext(), "Empty information, check and try again", Toast.LENGTH_SHORT).show();
            } else {

                Bid bid = new Bid(jobId, employerID, uid, username, profileImage, description, time, budget);

                mDatabase.child("Bids").child(jobId).child(uid).setValue(bid).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Done!!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Error!!!!", Toast.LENGTH_SHORT).show();
                        }
                        bottomSheetDialog.dismiss();
                    }
                });
            }

        });

    }

    public void mReadDataOnce(DatabaseReference mDatabase, String child, String uid, final IMain.OnGetDataListener listener) {
        listener.onStart();
        mDatabase.child(child).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


    void mapping() {
        ivMore = findViewById(R.id.ibMore);
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