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
import com.example.wolny.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditJobActivity extends AppCompatActivity {

    Spinner spProjectType, spCurrency;
    TextView tvPerHour, tvCategory;
    EditText etDescription, etBudget, etSkillRequired, etTitle, etTime;
    String projectType, currency, category;
    Button btUpdateProject;
    ImageView ivBack;
    DatabaseReference databaseReference;
    String country = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_job);
        mapping();
        setUpSpinner();
        databaseReference = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating");
        progressDialog.setCanceledOnTouchOutside(false);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        Job job = (Job) bundle.getSerializable("job");
        setupView(job);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btUpdateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invalidate()){
                    progressDialog.show();

                    Job job1 = new Job(job.getJobID(), job.getEmployerID(), job.getEmployerName(), "","", country, category,
                            etTitle.getText().toString(),
                            etDescription.getText().toString(), projectType,
                            etBudget.getText().toString(), etSkillRequired.getText().toString(),
                            "open", currency,
                            etTime.getText().toString());
                    databaseReference.child("Jobs").child(job.getJobID()).setValue(job1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
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

    void setupView(Job job) {
        category = job.getCategory();
        tvCategory.setText(category);

        etDescription.setText(job.getDescription());

        etBudget.setText(job.getBudget());

        etSkillRequired.setText(job.getSkillRequired());

        etTitle.setText(job.getTitle());

        etTime.setText(job.getTime());

        country = job.getCountry();

        if (job.getType().equals("Fixed Price")) {
            spProjectType.setSelection(0);
        } else {
            spProjectType.setSelection(1);
        }
        if (job.getCurrency().equals("$ USD")) {
            spCurrency.setSelection(0);
        } else {
            spCurrency.setSelection(1);
        }
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
        btUpdateProject = findViewById(R.id.btUpdateProject);
        ivBack = findViewById(R.id.ivBack);
    }
}