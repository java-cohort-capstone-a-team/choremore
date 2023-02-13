package com.capstone.choremore.apis;

import com.capstone.choremore.config.Config;
import com.capstone.choremore.models.Avatar;
import okhttp3.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class ApiHandleImp {

    // Undead API call to turn a picture into a zombie >>>>>>>>>>
    public String Undead() throws Exception {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image","IMG_8020.png",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("/Users/alvinmckenzie/Desktop/IMG_8020.png")))
                .build();
        Request request = new Request.Builder()
                .url("https://toonify.p.rapidapi.com/v0/zombify?proceed_without_face=false&return_aligned=false")
                .method("POST", body)
                .addHeader("X-RapidAPI-Key", Config.toonApiKey)
                .addHeader("X-RapidAPI-Host", Config.toonApiHost)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }
    // <<<<<<<<<< Undead API call to turn a picture into a zombie

    // Mage API call to turn a picture into a mage >>>>>>>>>>

    public String Mage() throws Exception {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image","IMG_8020.png",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("/Users/alvinmckenzie/Desktop/IMG_8020.png")))
                .build();
        Request request = new Request.Builder()
                .url("https://toonify.p.rapidapi.com/v0/comic?proceed_without_face=false&return_aligned=false")
                .method("POST", body)
                .addHeader("X-RapidAPI-Key", Config.toonApiKey)
                .addHeader("X-RapidAPI-Host", Config.toonApiHost)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }

    //  <<<<<<<<<< Mage API call to turn a picture into a mage

    // Warrior API call to turn a picture into a warrior >>>>>>>>>>

    public String Warrior() throws Exception {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image","IMG_8020.png",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("/Users/alvinmckenzie/Desktop/IMG_8020.png")))
                .build();
        Request request = new Request.Builder()
                .url("https://toonify.p.rapidapi.com/v0/toonifyplus")
                .method("POST", body)
                .addHeader("X-RapidAPI-Key", Config.toonApiKey)
                .addHeader("X-RapidAPI-Host", Config.toonApiHost)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }

    // <<<<<<<<<< Warrior API call to turn a picture into a warrior

    //  Fairy API call to turn a picture into a fairy >>>>>>>>>>

    public String Fairy() throws Exception {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image","IMG_8020.png",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("/Users/alvinmckenzie/Desktop/IMG_8020.png")))
                .build();
        Request request = new Request.Builder()
                .url("https://toonify.p.rapidapi.com/v0/emojify")
                .method("POST", body)
                .addHeader("X-RapidAPI-Key", Config.toonApiKey)
                .addHeader("X-RapidAPI-Host", Config.toonApiHost)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }

    // <<<<<<<<<< Fairy API call to turn a picture into a fairy

//    this is the code I am using to test the api >>>>>>>>>

    public String Dwarf() throws Exception {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image","IMG_8020.png",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("/Users/alvinmckenzie/Desktop/IMG_8020.png")))
                .build();
        Request request = new Request.Builder()
                .url("https://toonify.p.rapidapi.com/v0/emojify")
                .method("POST", body)
                .addHeader("X-RapidAPI-Key", "90f9e0e25fmshb4177c4a73b724ap1bba37jsn41cbc5bb5ce9")
                .addHeader("X-RapidAPI-Host", "toonify.p.rapidapi.com")
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }

    }

    //   <<<<<<<<<< this is the code I am using to test the api

    public static void main(String[] args) throws Exception {
        ApiHandleImp apiHandleImp = new ApiHandleImp();
        apiHandleImp.Undead();
        apiHandleImp.Mage();
        apiHandleImp.Warrior();

        String t = apiHandleImp.Dwarf();
        System.out.println(t);
        apiHandleImp.Fairy();
    }

//    public String getAvatarImg(Avatar myAvatar) {
//        String avatarImg = "";
//        switch (myAvatar.getAvatarType()) {
//            case "Mage":
//                try {
//                    avatarImg = Mage();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case "Warrior":
//                try {
//                    avatarImg = Warrior();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case "Fairy":
//                try {
//                    avatarImg = Fairy();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case "Dwarf":
//                try {
//                    avatarImg = Dwarf();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case "Undead":
//                try {
//                    avatarImg = Undead();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//        return avatarImg;
//    }
}
