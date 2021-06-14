package eu.interopehrate.mr2dsm.view;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;

import eu.interopehrate.mr2dsm.model.AuthRequest;

public interface EidasMixin {

    default String generateRequestUrl(String url, AuthRequest auth){
        String request = "";
        try {
            String json = new ObjectMapper().writeValueAsString(auth);
            request = URLEncoder.encode(json, "UTF-8");
        } catch (Exception e) {
            Log.e("generateRequestUrl", "Failed to generate request URL: " + e.getMessage());
        }

        String requestUrl = url + "?attr=" + request;
        return requestUrl;
    }

}
