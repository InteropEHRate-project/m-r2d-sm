package eu.interopehrate.mr2dsm;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.interopehrate.mr2dsm.model.EidasResponse;
import eu.interopehrate.mr2dsm.model.ResponseDetails;
import eu.interopehrate.mr2dsm.model.ResponseAttibute;
import eu.interopehrate.mr2dsm.model.SubStatusCode;
import eu.interopehrate.mr2dsm.model.UserDetails;
import eu.interopehrate.mr2dsm.util.FileUtil;
import eu.interopehrate.mr2dsm.util.SecurityUtil;
import io.jsonwebtoken.Claims;

public class MR2DSM {

    private static final String LOG_TAG = "MR2DSM";

    public static ResponseDetails decode(final Context context, final String jwt) throws InvalidKeySpecException {

        String key = "";
        try {
            key = FileUtil.LoadData(context, "private.pub");
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to load private.pub :" + e.getMessage());
        }
        Log.d("key", key);
        Claims jwtClaims = SecurityUtil.decode(jwt, key);

        ResponseDetails res = new ResponseDetails();

        res.setAssertion(jwtClaims.get("assertion").toString());
        String encryptedData = jwtClaims.get("attributes").toString();
        String data = SecurityUtil.decode(encryptedData, key).get("data3").toString();
        data = data.replaceAll("\n","");
        data = data.replaceAll("\r","");

        //Extract only the response part with REGEX
        Pattern pattern = Pattern.compile("(?<=\"response\" : )(.*)(?=\\}</textarea)");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) data = matcher.group(1);

        //Map JSON to JAVA object
        EidasResponse response = null;
        UserDetails user = null;
        try {
            response = new ObjectMapper().readValue(data, EidasResponse.class);
            List<ResponseAttibute> attributes = new ObjectMapper()
                    .readValue(response.getAttribute_list().toString(),
                            new TypeReference<List<ResponseAttibute>>(){});

            user = UserDetails.create(attributes);
        } catch (JsonProcessingException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        if (response.getStatus().getSub_status_code() != null) {
            Log.d(LOG_TAG, "Successful Response with id: " + response.getId() +
                    "\n and status " + response.getStatus().getSub_status_code() +
                    "\n and issuer:" + response.getIssuer());
        }
        else {
            Log.d(LOG_TAG,"Successful Response with id " + response.getId() +
                    " \n with status " + response.getStatus().getStatus_code() +
                    " \n and with attributes " + response.getAttribute_list());
        }
        res.setEidasResponse(response);
        res.setUserDetails(user);

        //Check if the user was authenticated
        boolean authenticated = false;
        if (response.getStatus().getSub_status_code() != null &&
                response.getStatus().getSub_status_code().equals(SubStatusCode.AuthnSuccess)) authenticated = true;
        if (response.getStatus().getSub_status_code() == null &&
                response.getStatus().getStatus_code().equals("success")) authenticated = true;

        res.setAuthenticated(authenticated);

        //Only display the extraJwt if the Authentication process was successful
        if (authenticated) {
            Log.d(LOG_TAG, "The assertion is: "+ res.getAssertion());
        }

        return res;
    }

}
