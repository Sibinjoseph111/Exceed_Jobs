package com.app.exceedjobs.api;

import com.app.exceedjobs.model.JobModel;
import com.app.exceedjobs.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JobApi {

    @GET("listjob")
    Call<List<JobModel>> getJobs();

}
