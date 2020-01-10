package com.android.huss.utility;

import android.util.Log;

import com.android.huss.models.Profile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static Profile parseError(final Response<?> response) {
        JSONObject bodyObj = null;
        String status = "";
        int statusCode = 0;
        String message = "";

        try {
            String errorBody = response.errorBody().string();
            Log.e("Errorbody", errorBody);
            if (errorBody != null) {
                bodyObj = new JSONObject(errorBody);
                status = bodyObj.getString("status");
                statusCode = bodyObj.getInt("statusCode");
                message = bodyObj.getString("message");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Unable to parse error";
        }
        Profile profile = new Profile();
        profile.setMessage(message);
        profile.setStatus(status);
        profile.setStatusCode(statusCode);

        return profile;

    }
}