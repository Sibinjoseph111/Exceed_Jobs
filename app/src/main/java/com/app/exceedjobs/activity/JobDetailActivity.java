package com.app.exceedjobs.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.exceedjobs.R;
import com.app.exceedjobs.model.JobModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.card.MaterialCardView;

public class JobDetailActivity extends AppCompatActivity {

    //const

    //var
    private JobModel job;
    private Boolean paymentStatus = false;

    //widget
    private MaterialToolbar toolbar;
    private TextView title_TV, location_TV, salary_TV, mode_TV, date_TV, experience_TV, description_TV, company_TV, category_TV, qualification_TV, ageLimit_TV, workDays_TV,email_TV, contact_TV, workTime_TV, link_TV;
    private LinearLayout company_LL, category_LL, jobProfile_LL, salary_LL, experience_LL, qualification_LL, age_LL, days_LL, email_LL, time_LL, link_LL;
    private CustomTabsIntent customTabsIntent;
    private MaterialCardView contact_CV;

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
//        profile_TV = findViewById(R.id.jobDetailProfile_TV);
        qualification_TV = findViewById(R.id.jobDetailQualification_TV);
        ageLimit_TV = findViewById(R.id.jobDetailAge_TV);
        workDays_TV = findViewById(R.id.jobDetailDays_TV);
        contact_TV = findViewById(R.id.contact_TV);
        workTime_TV = findViewById(R.id.jobDetailTime_TV);
        link_TV = findViewById(R.id.jobDetailLink_TV);
        contact_CV = findViewById(R.id.contact_CV);

        company_LL = findViewById(R.id.company_LL);
        category_LL = findViewById(R.id.category_LL);
//        jobProfile_LL = findViewById(R.id.jobProfile_LL);
        salary_LL = findViewById(R.id.salary_LL);
        experience_LL = findViewById(R.id.experience_LL);
        qualification_LL = findViewById(R.id.qualification_LL);
        age_LL = findViewById(R.id.age_LL);
        days_LL = findViewById(R.id.days_LL);
        time_LL = findViewById(R.id.time_LL);
        email_LL = findViewById(R.id.email_LL);
        link_LL = findViewById(R.id.link_LL);

        setContents();

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        customTabsIntent = builder.build();

    }

    private void getPaymentStatus() {
        String status = getSharedPreferences(getString(R.string.sharedpreferences_ID),MODE_PRIVATE).getString("paymentStatus","0");
        if (status.equals("1")) paymentStatus = true;
    }

    private void setContents() {

        if (job.getCompany().trim().length() == 0) company_LL.setVisibility(View.GONE);
        if (job.getCategory().trim().length() == 0) category_LL.setVisibility(View.GONE);
        if (job.getSalary().trim().length() == 0 || job.getSalary().equals("0")) salary_LL.setVisibility(View.GONE);
        if (job.getExperience().trim().length() == 0) experience_LL.setVisibility(View.GONE);
        if (job.getQualification().trim().length() == 0) qualification_LL.setVisibility(View.GONE);
        if (job.getAge_limit().trim().length() == 0) age_LL.setVisibility(View.GONE);
        if (job.getWork_days().trim().length() == 0) days_LL.setVisibility(View.GONE);
        if (job.getWorkingTime().trim().length() == 0) time_LL.setVisibility(View.GONE);
        if (job.getEmail().trim().length() == 0) email_LL.setVisibility(View.GONE);
        if (job.getLink().trim().length() == 0) link_LL.setVisibility(View.GONE);

        title_TV.setText(job.getTitle());
        location_TV.setText(" "+job.getLocation());
        salary_TV.setText("₹ "+job.getSalary());
        date_TV.setText(job.getAdded_date());
        experience_TV.setText(job.getExperience()+ " years");
        description_TV.setText(job.getDescription());
        company_TV.setText(job.getCompany());
        category_TV.setText(job.getCategory());
//        profile_TV.setText(job.getJob_type());
        qualification_TV.setText(job.getQualification());
        ageLimit_TV.setText(job.getAge_limit());
        workDays_TV.setText(job.getWork_days());
        mode_TV.setText(job.getJob_type());
        email_TV.setText(job.getEmail());
        workTime_TV.setText(job.getWorkingTime());
        link_TV.setText(job.getLink());

        experience_TV.setSelected(true);
        description_TV.setSelected(true);
        company_TV.setSelected(true);
        category_TV.setSelected(true);
//        profile_TV.setSelected(true);
        qualification_TV.setSelected(true);
        email_TV.setSelected(true);


        if (!paymentStatus){
            company_TV.setText(R.string.become_paid);
            company_TV.setTextColor(getResources().getColor(R.color.colorPrimary));
            email_TV.setText(R.string.become_paid);
            email_TV.setTextColor(getResources().getColor(R.color.colorPrimary));

            contact_TV.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            contact_TV.setText(R.string.payment_reminder);
        }else {
            if (job.getContact().trim().length() == 0) contact_CV.setVisibility(View.GONE);
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

    public void openLink(View view) {
        String url = job.getLink();
        try {
            customTabsIntent.launchUrl(this, Uri.parse(url));
        }catch (Exception e){
            Log.e("Link Error",e.getMessage()+" ");
            Toast.makeText(this,"Error opening link. Copy the link and try in a browser.",Toast.LENGTH_LONG).show();
        }
    }
}