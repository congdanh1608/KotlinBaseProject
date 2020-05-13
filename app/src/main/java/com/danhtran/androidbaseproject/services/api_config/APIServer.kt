package com.danhtran.androidbaseproject.services.api_config

import com.danhtran.androidbaseproject.services.model.ResponseModel
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by danhtran on 11/04/2017.
 */

interface APIServer {
    //-----------------Observable<>
    /* @Headers(header)
    @POST("{api}/{sub}")
    Observable<LoginModel> post(@Path("api") String api, @Path("sub") String sub, @Body JsonElement jsonData);

    @Headers(header)
    @GET("{api}")
    Observable<LoginModel> get(@Path("api") String api, @Path("sub") String sub);

    @Headers(header)
    @DELETE("{api}")
    Observable<LoginModel> delete(@Path("api") String api, @Path("sub") String sub);

    @Headers(header)
    @PUT("{api}")
    Observable<LoginModel> put(@Path("api") String api, @Path("sub") String sub, @Body JsonElement jsonData);*/


    @GET("v2/skills")
    fun getSkillTags(): Observable<ResponseModel<String>>
}
