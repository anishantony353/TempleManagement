package com.saarit.temple_management.templemanagement.model.repositories.server_storage;

import com.saarit.temple_management.templemanagement.model.BaseResponse;
import com.saarit.temple_management.templemanagement.model.DTOs.FormType_2_DTO;
import com.saarit.temple_management.templemanagement.model.DTOs.FormType_3a_DTO;
import com.saarit.temple_management.templemanagement.model.DTOs.FormType_3b_1_DTO;
import com.saarit.temple_management.templemanagement.model.DTOs.FormType_3b_2_DTO;
import com.saarit.temple_management.templemanagement.model.DTOs.FormType_4_DTO;
import com.saarit.temple_management.templemanagement.model.DTOs.FormType_5_DTO;
import com.saarit.temple_management.templemanagement.model.DonatedProduct;
import com.saarit.temple_management.templemanagement.model.Festival;
import com.saarit.temple_management.templemanagement.model.FormType_1;
import com.saarit.temple_management.templemanagement.model.DTOs.FormType_1_DTO;
import com.saarit.temple_management.templemanagement.model.DTOs.FormType_1_List_DTO;
import com.saarit.temple_management.templemanagement.model.FormType_2;
import com.saarit.temple_management.templemanagement.model.FormType_3a;
import com.saarit.temple_management.templemanagement.model.FormType_3b_1;
import com.saarit.temple_management.templemanagement.model.FormType_3b_2;
import com.saarit.temple_management.templemanagement.model.FormType_4;
import com.saarit.temple_management.templemanagement.model.FormType_5;
import com.saarit.temple_management.templemanagement.model.LoginFeilds;
import com.saarit.temple_management.templemanagement.model.PoojariWork;
import com.saarit.temple_management.templemanagement.model.RespectedPerson;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;
import com.saarit.temple_management.templemanagement.model.DTOs.Temples_master_DTO;
import com.saarit.temple_management.templemanagement.model.User;
import com.saarit.temple_management.templemanagement.model.WorshipingHouse;
import com.saarit.temple_management.templemanagement.model.WorshipingType;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
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

    /*@GET("temple/getTemples")
    Single<Forms_1_DTO> getAllForm1();*/

    @Multipart
    @POST("user/AddUser")
    Single<BaseResponse> createUser(@Part("user") User user);

    @FormUrlEncoded
    @POST("user/loginuser")
    Single<Temples_master_DTO> getTempleMaster(
            @Field("email") String id,
            @Field("password") String password
    );

    @Multipart
    @POST("temple/addTemple")
    Observable<BaseResponse> addTemple(@Part("temple_data") FormType_1 formType1,
                                       @Part MultipartBody.Part img1,
                                       @Part MultipartBody.Part img2,
                                       @Part MultipartBody.Part img3,
                                       @Part MultipartBody.Part img4);

    @Multipart
    @POST("temple/addForm_2")
    Observable<BaseResponse> addForm2(@Part("form_2_data") FormType_2 formType2,
                                       @Part("form_2_data_festivals[]") List<Festival> festivals,
                                       @Part("form_2_data_poojari_works[]") List<PoojariWork> poojariWorks,
                                       @Part("form_2_data_worshiping_houses[]") List<WorshipingHouse> worshipingHouses,
                                       @Part("form_2_data_respected_persons[]") List<RespectedPerson> respectedPersons,
                                       @Part("form_2_data_worshiping_types[]") List<WorshipingType> worshipingTypes,
                                       @Part MultipartBody.Part img1,
                                       @Part MultipartBody.Part img2);

    @Multipart
    @POST("temple/addForm_3a")
    Observable<BaseResponse> addForm3a(@Part("form_3a_data") FormType_3a formType3a);

    @Multipart
    @POST("temple/addForm_3b1")
    Observable<BaseResponse> addForm3b_1(@Part("form_3b1_data") FormType_3b_1 formType3b_1);

    @Multipart
    @POST("temple/addForm_3b2")
    Observable<BaseResponse> addForm3b_2(@Part("form_3b2_data") FormType_3b_2 formType3b_2);

    @Multipart
    @POST("temple/addForm_4")
    Observable<BaseResponse> addForm4(@Part("form_4_data") FormType_4 formType4,
                                      @Part("form_4_data_products[]") List<DonatedProduct> products
                                      );

    @Multipart
    @POST("temple/addForm_5")
    Observable<BaseResponse> addForm5(@Part("form_5_data") FormType_5 formType5);

/*    @Multipart
    @POST("temple/addForm_3b2")
    Observable<BaseResponse> addForm_Testing(@Body FormType_3b_2 formTypea,
                                     @Body FormType_3b_2 formTypeb);*/

    @POST("temple/getNearbyTemples")
    @FormUrlEncoded
    Single<FormType_1_List_DTO> getNearbyTemples(@Field("northLat")double northLat,
                                                 @Field("southLat")double southLat,
                                                 @Field("westLon")double westLon,
                                                 @Field("eastLon")double eastLon);

    @GET("temple/getForm_1ById")
    Single<FormType_1_DTO> getFormType1byTempleId(@Query("temple_id") int templeId);

    @GET("temple/getForm_2ById")
    Single<FormType_2_DTO> getFormType2byTempleId(@Query("temple_id") int templeId);

    @GET("temple/getForm_3aById")
    Single<FormType_3a_DTO> getFormType3abyTempleId(@Query("temple_id") int templeId);

    @GET("temple/getForm_3b1ById")
    Single<FormType_3b_1_DTO> getFormType3b1byTempleId(@Query("temple_id") int templeId);

    @GET("temple/getForm_3b2ById")
    Single<FormType_3b_2_DTO> getFormType3b2byTempleId(@Query("temple_id") int templeId);

    @GET("temple/getForm_4ById")
    Single<FormType_4_DTO> getFormType4byTempleId(@Query("temple_id") int templeId);

    @GET("temple/getForm_5ById")
    Single<FormType_5_DTO> getFormType5byTempleId(@Query("temple_id") int templeId);

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
