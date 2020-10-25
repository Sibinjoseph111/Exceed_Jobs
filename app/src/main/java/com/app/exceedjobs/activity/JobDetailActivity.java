package com.app.exceedjobs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.exceedjobs.R;
import com.app.exceedjobs.model.JobModel;
import com.google.android.material.appbar.MaterialToolbar;

public class JobDetailActivity extends AppCompatActivity {

    //const

    //var
    private JobModel job;
    private Boolean paymentStatus = false;

    //widget
    private MaterialToolbar toolbar;
    private TextView title_TV, location_TV, salary_TV, mode_TV, date_TV, experience_TV, description_TV, company_TV, category_TV, profile_TV, qualification_TV, ageLimit_TV, workDays_TV,email_TV, contact_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getPaymentStatus();

        job = getIntent().getParcelableExtra("job");

        title_TV = findViewById(R.id.jobDetailTitle_TV);
        email_TV = findViewById(R.id.jobDetailEmail_TV);
        location_TV = findViewById(R.id.jobDetailLocation_TV);
        salary_TV = findViewById(R.id.jobDetailSalary_TV);
        mode_TV = findViewById(R.id.jobDetailMode_TV);
        date_TV = findViewById(R.id.jobDetailDate_TV);
        experience_TV = findViewById(R.id.jobDetailExperience_TV);
        description_TV = findViewById(R.id.jobDetailDescription_TV);
        company_TV = findViewById(R.id.jobDetailCompany_TV);
        category_TV = findViewById(R.id.jobDetailCategory_TV);
        profile_TV = findViewById(R.id.jobDetailProfile_TV);
        qualification_TV = findViewById(R.id.jobDetailQualification_TV);
        ageLimit_TV = findViewById(R.id.jobDetailAge_TV);
        workDays_TV = findViewById(R.id.jobDetailDays_TV);
        contact_TV = findViewById(R.id.contact_TV);

        setContents();

    }

    private void getPaymentStatus() {
        String status = getSharedPreferences(getString(R.string.sharedpreferences_ID),MODE_PRIVATE).getString("paymentStatus","0");
        if (status.equals("1")) paymentStatus = true;
    }

    private void setContents() {
        title_TV.setText(job.getTitle());
        location_TV.setText(" "+job.getLocation());
        salary_TV.setText("₹ "+job.getSalary());
        date_TV.setText(job.getAdded_date());
        experience_TV.setText(job.getExperience()+ " years");
        description_TV.setText(job.getDescription());
        company_TV.setText(job.getCompany());
        category_TV.setText(job.getCategory());
        profile_TV.setText(job.getJob_type());
        qualification_TV.setText(job.getQualification());
        ageLimit_TV.setText(job.getAge_limit());
        workDays_TV.setText(job.getWork_days());
        mode_TV.setText(job.getMode());
        email_TV.setText(job.getEmail());

        if (!paymentStatus){
            company_TV.setText(R.string.become_paid);
            company_TV.setTextColor(getResources().getColor(R.color.colorPrimary));
            email_TV.setText(R.string.become_paid);
            email_TV.setTextColor(getResources().getColor(R.color.colorPrimary));

            contact_TV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            contact_TV.setText(R.string.payment_reminder);

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void contact(View view) {
        if (!paymentStatus){
            startActivity(new Intent(this,PaymentActivity.class));
        }else {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+job.getContact()));
            startActivity(intent);
        }
    }
}