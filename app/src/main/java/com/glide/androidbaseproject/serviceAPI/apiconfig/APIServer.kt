package com.glide.androidbaseproject.serviceAPI.apiconfig

import com.glide.androidbaseproject.appmodel.Movie
import com.glide.androidbaseproject.serviceAPI.model.ResponseModel
import io.reactivex.Observable
import retrofit2.http.POST

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


    @get:POST("{api}/{sub}")
    val movies: Observable<ResponseModel<List<Movie>>>
}
