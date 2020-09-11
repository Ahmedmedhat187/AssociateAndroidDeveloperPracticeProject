package com.ahmed.androiddeveloperpracticeproject.ui.activities;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.ahmed.androiddeveloperpracticeproject.R;
import com.ahmed.androiddeveloperpracticeproject.api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etName, etLastNAme, etEmail, etProjectLink;
    Button submitProject;
    ImageView btnBack;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading");

        etName = findViewById(R.id.et_name);
        etLastNAme = findViewById(R.id.et_last_name);
        etEmail = findViewById(R.id.et_email);
        etProjectLink = findViewById(R.id.et_project_link);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);

        submitProject = findViewById(R.id.submit_project);
        submitProject.setOnClickListener(this);
    }



    public void createResultAlertDialog(int layout){
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);
        final View customLayout = getLayoutInflater().inflate(layout, null);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void createSubmitAlertDialog(String name, String lastName, String email, String projectLink){
        AlertDialog.Builder builder = new AlertDialog.Builder(SubmitActivity.this);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert_dialog_submit_project, null);
        TextView buttonYes = customLayout.findViewById(R.id.tvYes);
        ImageView buttonClose = customLayout.findViewById(R.id.imageView);
        builder.setView(customLayout);
        AlertDialog dialog = builder.create();
        dialog.show();

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }});

        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                progressDialog.show();
                submitForm(name, lastName, email, projectLink);
            }});
    }


    public void submitForm(String name, String lastName, String email, String projectLink) {
        Call<Void> submit = RetrofitClient
                .getFormInterface()
                .submitProject(name, lastName, email, projectLink);

        submit.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.dismiss();

                if(response.isSuccessful()){
                    createResultAlertDialog(R.layout.custom_alert_dialog_submission_successful);
                    clearFields();
                }
                else{
                    createResultAlertDialog(R.layout.custom_alert_dialog_submission_failed);
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                createResultAlertDialog(R.layout.custom_alert_dialog_submission_failed);
            }
        });
    }


    public void clearFields(){
        etName.setText("");
        etLastNAme.setText("");
        etEmail.setText("");
        etProjectLink.setText("");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_back:
                finish();
                break;

            case R.id.submit_project:
                String name        = etName.getText().toString();
                String lastName    = etLastNAme.getText().toString();
                String email       = etEmail.getText().toString();
                String projectLink = etProjectLink.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(SubmitActivity.this, "Enter valid name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(lastName)){
                    Toast.makeText(SubmitActivity.this, "Enter valid last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(SubmitActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.WEB_URL.matcher(projectLink).matches()){
                    Toast.makeText(SubmitActivity.this, "Enter valid url", Toast.LENGTH_SHORT).show();
                    return;
                }

                createSubmitAlertDialog(name, lastName, email, projectLink);
                break;
        }
    }


}


