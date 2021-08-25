package com.example.wolny.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wolny.Adapter.Profile.SkillAdapter;
import com.example.wolny.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivBack, ivProfile, ivEditSummary, ivAddSkill;
    TextView tvUsername, tvSmallUsername, tvContentAbout;
    RecyclerView rcvSkill;
    String current_user_id, summary, skills;
    List<String> listSkills = new ArrayList<>();
    StorageReference mRef;
    DatabaseReference mDatabase;
    ProgressDialog mDialog;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mapping();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        mRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        String username = getIntent().getExtras().getString("username");

        current_user_id = getIntent().getExtras().getString("uid");

        tvUsername.setText(username);

        tvSmallUsername.setText("@" + username);

        SkillAdapter skillAdapter = new SkillAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvSkill.setLayoutManager(gridLayoutManager);
        rcvSkill.setNestedScrollingEnabled(true);
        rcvSkill.setAdapter(skillAdapter);

        mDatabase.child("Users").child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imageUrl = Objects.requireNonNull(snapshot.child("profileImage").getValue()).toString();
                summary = Objects.requireNonNull(snapshot.child("summary").getValue()).toString();
                skills = Objects.requireNonNull(snapshot.child("skills").getValue()).toString();

                convertToList();
                skillAdapter.setList(listSkills);
                tvContentAbout.setText(summary);

                if (!imageUrl.equals("default")){
                    Glide.with(getBaseContext()).load(imageUrl).into(ivProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askForPermission();
            }
        });

        ivEditSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(summary);
            }
        });

        ivAddSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SkillActivity.class);
                intent.putExtra("uid", current_user_id);
                intent.putExtra("skills", skills);
                startActivity(intent);
            }
        });
    }

    public void convertToList(){
        listSkills = new ArrayList<>(Arrays.asList(skills.split(", ")));
    }
    private void openDialog(String currentSummary) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_change_summary);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);
        dialog.setCancelable(false);

        EditText etSummary = dialog.findViewById(R.id.etSummary);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnSave = dialog.findViewById(R.id.btnSave);

        dialog.show();

        etSummary.setText(summary);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                hideKeyboard(v);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newSummary = etSummary.getText().toString();
                mDatabase.child("Users").child(current_user_id).child("summary").setValue(newSummary);
                dialog.dismiss();
            }
        });


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void askForPermission() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                selectProfileImage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(ProfileActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .check();
    }

    private void selectProfileImage() {
        ImagePicker.Companion.with(this)
                .crop(1, 1)	    			//Crop image(Optional), Check Customization for more option
                .compress(512)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(300, 300)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(data != null){
                mDialog = new ProgressDialog(ProfileActivity.this);
                mDialog.setTitle("Uploading image...");
                mDialog.setMessage("Please wait while we upload and process the image");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();

                Uri uri = data.getData();

                StorageReference filePath = mRef.child("/imageProfile").child(current_user_id + ".jpg");

                filePath.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mDatabase.child("Users").child(current_user_id).child("profileImage").setValue(uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                mDialog.dismiss();
                                                Toast.makeText(ProfileActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        } else {
                            mDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Error in uploading", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    void mapping() {
        ivBack = findViewById(R.id.ivBack);
        ivProfile = findViewById(R.id.ivProfile);
        ivEditSummary = findViewById(R.id.ivEditSummary);
        tvUsername = findViewById(R.id.tvUsername);
        tvSmallUsername = findViewById(R.id.tvSmallUsername);
        tvContentAbout = findViewById(R.id.tvContentAbout);
        rcvSkill = findViewById(R.id.rcvSkill);
        ivAddSkill = findViewById(R.id.ivAddSkill);
    }

}