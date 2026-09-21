/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klijent;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

/**
 *
 * @author Milica Bjelovuk
 */

public interface RESTinterfejs {
    
    //zah1
    @POST("resources/mesto")
    Call<ResponseBody> createMesto(@Body RequestBody body);

    //zah2
    @POST("resources/korisnik")
    Call<ResponseBody> createKorisnik(@Body RequestBody body);

    //zah3
    @PUT("resources/korisnik/{id}/email")
    Call<ResponseBody> changeKorisnikEmail(@Path("id") int id, @Body RequestBody body);

    //zah4
    @PUT("resources/korisnik/{id}/mesto")
    Call<ResponseBody> changeKorisnikMesto(@Path("id") int id, @Body RequestBody body);

    //zah5
    @POST("resources/kategorija")
    Call<ResponseBody> createKategorija(@Body RequestBody body);

    //zah6
    @POST("resources/audiosnimak")
    Call<ResponseBody> createAudiosnimak(@Body RequestBody body);

    //zah7
    @PUT("resources/audiosnimak/{id}/naziv")
    Call<ResponseBody> changeNazivAudiosnimak(@Path("id") int id, @Body RequestBody body);

    //zah8
    @POST("resources/audiosnimak/{id}/kategorija")
    Call<ResponseBody> addKategorija(@Path("id") int id, @Body RequestBody body);

    //zah9
    @POST("resources/paket")
    Call<ResponseBody> createPaket(@Body RequestBody body);

    //zah10
    @PUT("resources/paket/{id}/cena")
    Call<ResponseBody> changeCenaPaketa(@Path("id") int id, @Body RequestBody body);

    //zah11
    @POST("resources/pretplata")
    Call<ResponseBody> createPretplata(@Body RequestBody body);

    //zah12
    @POST("resources/slusanje")
    Call<ResponseBody> createSlusanje(@Body RequestBody body);

    //zah13
    @POST("resources/omiljeni")
    Call<ResponseBody> addOmiljeni(@Body RequestBody body);

    //zah14
    @POST("resources/ocena")
    Call<ResponseBody> createOcena(@Body RequestBody body);

    //zah15
    @PUT("resources/ocena/{id}")
    Call<ResponseBody> changeOcena(@Path("id") int id, @Body RequestBody body);

    //zah16
    @DELETE("resources/ocena/{id}")
    Call<ResponseBody> deleteOcena(@Path("id") int id);

    //zah17
    @DELETE("resources/audiosnimak/{id}")
    Call<ResponseBody> deleteAudiosnimak(@Path("id") int id);

    //zah18
    @GET("resources/mesto")
    Call<ResponseBody> getAllMesta();

    //zah19
    @GET("resources/korisnik")
    Call<ResponseBody> getAllKorisnici();

    //zah20
    @GET("resources/kategorija")
    Call<ResponseBody> getAllKategorije();

    //zah21
    @GET("resources/audiosnimak")
    Call<ResponseBody> getAllAudiosnimci();

    //zah22
    @GET("resources/audiosnimak/{id}/kategorije")
    Call<ResponseBody> getKategorijeZaAudiosnimak(@Path("id") int id);

    //zah23
    @GET("resources/paket")
    Call<ResponseBody> getAllPaketi();

    //zah24
    @GET("resources/pretplata/korisnik/{id}")
    Call<ResponseBody> getPretplateZaKorisnika(@Path("id") int korisnikId);

    //zah25
    @GET("resources/slusanje/audiosnimak/{id}")
    Call<ResponseBody> getSlusanjaZaSnimak(@Path("id") int snimakId);

    //zah26
    @GET("resources/ocena/audiosnimak/{id}")
    Call<ResponseBody> getOceneZaSnimak(@Path("id") int snimakId);

    //zah27
    @GET("resources/omiljeni/korisnik/{id}")
    Call<ResponseBody> getOmiljeni(@Path("id") int korisnikId);
    
}
