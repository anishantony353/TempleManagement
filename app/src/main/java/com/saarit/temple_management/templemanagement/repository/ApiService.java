package com.saarit.temple_management.templemanagement.repository;

import com.saarit.temple_management.templemanagement.model.LoginFeilds;
import com.saarit.temple_management.templemanagement.model.SuccessOrFailure;

import io.reactivex.Single;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    // Register new user
    @FormUrlEncoded
    @POST("notes/user/register")
    Single<SuccessOrFailure> isLoginSuccess(LoginFeilds loginFeilds);

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
