package com.saarit.temple_management.templemanagement.model.repositories;

import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.FormType_1_DTO;
import com.saarit.temple_management.templemanagement.model.FormType_1_List_DTO;
import com.saarit.temple_management.templemanagement.model.Forms_1_DTO;
import com.saarit.temple_management.templemanagement.model.LoginFeilds;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.model.Temples_master_DTO;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    // Register new user
    @FormUrlEncoded
    @POST("notes/user/register")
    Single<SuccessOrFailure> isLoginSuccess(LoginFeilds loginFeilds);

    @GET("temple/getTemples")
    Single<Forms_1_DTO> getAllForm1();

    @GET("temple/getTemples")
    Single<Temples_master_DTO> getTempleMaster();

    @Multipart
    @POST("temple/addTemple")
    Observable<BaseResponse> addTemple(@Part("temple_data") FormType_1 formType1,
                                       @Part MultipartBody.Part img1,
                                       @Part MultipartBody.Part img2,
                                       @Part MultipartBody.Part img3,
                                       @Part MultipartBody.Part img4);


    @POST("temple/getNearbyTemples")
    @FormUrlEncoded
    Single<FormType_1_List_DTO> getNearbyTemples(@Field("northLat")double northLat,
                                                 @Field("southLat")double southLat,
                                                 @Field("westLon")double westLon,
                                                 @Field("eastLon")double eastLon);

    @GET("temple/getForm_1ById")
    Single<FormType_1_DTO> getFormType1byTempleId(@Query("temple_id") int templeId);

//    // Create note
//    @FormUrlEncoded
//    @POST("notes/new")
//    Single<Note> createNote(@Field("note") String note);
//
//    // Fetch all notes
//    @GET("notes/all")
//    Single<List<Note>> fetchAllNotes();
//
//    // Update single note
//    @FormUrlEncoded
//    @PUT("notes/{id}")
//    Completable updateNote(@Path("id") int noteId, @Field("note") String note);
//
//    // Delete note
//    @DELETE("notes/{id}")
//    Completable deleteNote(@Path("id") int noteId);
}
