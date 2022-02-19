package com.example.wolny.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wolny.Model.Job;
import com.example.wolny.Model.User;
import com.example.wolny.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class NewJobActivity extends AppCompatActivity {

    Spinner spProjectType, spCurrency;
    TextView tvPerHour, tvCategory;
    EditText etDescription, etBudget, etSkillRequired, etTitle, etTime;
    String projectType, currency, category;
    Button btPostProject;
    ImageView ivBack;
    DatabaseReference databaseReference;
    String country = "", employerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_job);
        mapping();
        category = getIntent().getExtras().getString("category");
        tvCategory.setText(category);
        setUpSpinner();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Please wait while we post your job");
        progressDialog.setCanceledOnTouchOutside(false);

        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString().replace("-", "");
        String employerID = getIntent().getExtras().getString("uid");
        databaseReference = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        databaseReference.child("Users").child(employerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                assert user != null;
                country = user.getCountry();
                employerName = user.getUsername();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        btPostProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invalidate()) {
                    progressDialog.show();

                    Job job = new Job(uuidAsString, employerID, employerName, "", "",country, category,
                            etTitle.getText().toString(),
                            etDescription.getText().toString(), projectType,
                            etBudget.getText().toString(), etSkillRequired.getText().toString(),
                            "open", currency,
                            etTime.getText().toString());

                    databaseReference.child("Jobs").child(uuidAsString).setValue(job).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                onBackPressed();
                                finish();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getBaseContext(), "Something got error, please wait then try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    boolean invalidate() {
        if (etTitle.getText().toString().length() < 10) {
            etTitle.setError("Title required, must be at least 10 character");
            return false;
        }
        if (etDescription.getText().toString().length() < 30) {
            etDescription.setError("Description required, must be at least 30 character");
            return false;
        }
        if (etBudget.getText().toString().isEmpty()) {
            etBudget.setError("Budget required");
            return false;
        } else {
            if (Integer.parseInt(etBudget.getText().toString()) == 0) {
                etBudget.setError("Budged need to be greater than 0");
                return false;
            }
        }
        if (etTime.getText().toString().isEmpty()) {
            etTime.setError("Time required");
            return false;
        }
        return true;
    }

    void mapping() {
        tvCategory = findViewById(R.id.tvCategory);
        spProjectType = findViewById(R.id.spProjectType);
        spCurrency = findViewById(R.id.spCurrency);
        tvPerHour = findViewById(R.id.tvPerHour);
        etTitle = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        etBudget = findViewById(R.id.etBudget);
        etSkillRequired = findViewById(R.id.etSkillRequired);
        etTime = findViewById(R.id.etTime);
        btPostProject = findViewById(R.id.btPostProject);
        ivBack = findViewById(R.id.ivBack);
    }

    void setUpSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this,
                R.array.project_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spProjectType.setAdapter(adapterType);
        spProjectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    tvPerHour.setVisibility(View.VISIBLE);
                } else {
                    tvPerHour.setVisibility(View.INVISIBLE);
                }
                projectType = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                projectType = "Fixed Price";
            }
        });

        ArrayAdapter<CharSequence> adapterBudget = ArrayAdapter.createFromResource(this,
                R.array.currency_unit, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterBudget.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spCurrency.setAdapter(adapterBudget);
        spCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currency = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currency = "USD";
            }
        });


    }

}