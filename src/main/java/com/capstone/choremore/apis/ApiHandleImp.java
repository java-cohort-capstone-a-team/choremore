package com.capstone.choremore.apis;

import com.capstone.choremore.config.Config;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Service
public class ApiHandleImp {

    @AllArgsConstructor
    @Setter
    @Getter
    private static class ImageResponse {

        Boolean okhttp3;
        Boolean b64_encoded_aligned;
        Integer num_faces;
        String b64_encoded_output;

    }

    public byte[] apiCall (MultipartFile image, String toon) throws Exception {

        String filePath = System.getProperty("java.io.tmpdir") + File.separator + image.getOriginalFilename();
        File file = new File(filePath);
        image.transferTo(file);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", "IMG_8020.png", RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder()
                .url(toon)
                .method("POST", body)
                .addHeader("X-RapidAPI-Key", Config.toonApiKey)
                .addHeader("X-RapidAPI-Host", Config.toonApiHost)
                .build();
        try (Response response = client.newCall(request).execute()) {

            assert response.body() != null;

            GsonBuilder builder = new GsonBuilder();
            ImageResponse newResponse = builder.create().fromJson(response.body().string(), ImageResponse.class);
            byte[] imageByte = Base64.getDecoder().decode(newResponse.b64_encoded_output.getBytes());

            return imageByte;

        }

    }

    public static void main(String[] args) throws Exception {

    }

}
