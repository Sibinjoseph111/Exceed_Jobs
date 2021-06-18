package com.app.exceedjobs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class JobModel implements Parcelable {

    @SerializedName("id")
    String id;

    @SerializedName("jobtitle")
    String title;

    @SerializedName("jobdescription")
    String description;

    @SerializedName("location")
    String location;

    @SerializedName("salary")
    String salary;

    @SerializedName("company_name")
    String company;

    @SerializedName("category")
    String category;

    @SerializedName("mode")
    String mode;

    @SerializedName("post_date")
    String added_date;

    @SerializedName("jobtype")
    String job_type;

    @SerializedName("experience")
    String experience;

    @SerializedName("qualification")
    String qualification;

    @SerializedName("age")
    String age_limit;

    @SerializedName("workdays")
    String work_days;

    @SerializedName("number")
    String contact;

    @SerializedName("email")
    String email;

    @SerializedName("workingtime")
    String workingTime;

    @SerializedName("joblink")
    String link;

    @SerializedName("otherbenefit")
    String otherBenifits;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getSalary() {
        return salary;
    }

    public String getCompany() {
        return company;
    }

    public String getCategory() {
        return category;
    }

    public String getMode() {
        return mode;
    }

    public String getAdded_date() {
        return added_date;
    }

    public String getJob_type() {
        return job_type;
    }

    public String getExperience() {
        return experience;
    }

    public String getQualification() {
        return qualification;
    }

    public String getAge_limit() {
        return age_limit;
    }

    public String getWork_days() {
        return work_days;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public String getLink() {
        return link;
    }

    public String getOtherBenifits() {
        return otherBenifits;
    }

    protected JobModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        location = in.readString();
        salary = in.readString();
        company = in.readString();
        category = in.readString();
        mode = in.readString();
        added_date = in.readString();
        job_type = in.readString();
        experience = in.readString();
        qualification = in.readString();
        age_limit = in.readString();
        work_days = in.readString();
        contact = in.readString();
        email = in.readString();
        workingTime = in.readString();
        link = in.readString();
        otherBenifits = in.readString();
    }

    public static final Creator<JobModel> CREATOR = new Creator<JobModel>() {
        @Override
        public JobModel createFromParcel(Parcel in) {
            return new JobModel(in);
        }

        @Override
        public JobModel[] newArray(int size) {
            return new JobModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(location);
        parcel.writeString(salary);
        parcel.writeString(company);
        parcel.writeString(category);
        parcel.writeString(mode);
        parcel.writeString(added_date);
        parcel.writeString(job_type);
        parcel.writeString(experience);
        parcel.writeString(qualification);
        parcel.writeString(age_limit);
        parcel.writeString(work_days);
        parcel.writeString(contact);
        parcel.writeString(email);
        parcel.writeString(workingTime);
        parcel.writeString(link);
        parcel.writeString(otherBenifits);
    }
}
