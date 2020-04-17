package eu.interopehrate.mr2dsm;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.interopehrate.mr2dsm.rest.AuthenticationKeycloak;
import eu.interopehrate.mr2dsm.api.MR2DSM;
import eu.interopehrate.mr2dsm.model.AccessTokenResponce;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainMR2DSM implements MR2DSM {
    private View view;
    public AuthenticationKeycloak postsService;
    SharedPreferences pref;

    public MainMR2DSM(View view) {
        this.view = view;
    }

    @Override
    public void requestToken(String username, String password) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://192.168.1.51:8180");
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofitKeycloak = builder
                .build();
        postsService = retrofitKeycloak.create(AuthenticationKeycloak.class);

        Call<AccessTokenResponce> call = postsService.requestAuthToken("password",username,password,"caller");

        call.enqueue(new Callback<AccessTokenResponce>() {
            @Override
            public void onResponse(Call<AccessTokenResponce> call, Response<AccessTokenResponce> response) {
                String accessToken = response.body().getAccess_token().toString();
                Log.d("access_token",accessToken);
                storeToken(accessToken);
            }

            @Override
            public void onFailure(Call<AccessTokenResponce> call, Throwable t) {
                Log.e("access_token",t.getMessage());
            }
        });
    }

    @Override
    public void authenticate(String token) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofitNCP = new Retrofit.Builder()
                .baseUrl("http://192.168.1.51:8085")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        postsService = retrofitNCP.create(AuthenticationKeycloak.class);

        String value = "Bearer " + token;
        Log.d("MSSG value",value);

        Call<String> call = postsService.authenticate(value/*, "8bbebb8a-02b0-49b3-b780-5159927a9f08"*/);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("MSSG getAccess_token",response.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("MSSG access_token",t.getMessage());
            }
        });
    }

    public void storeToken(String token) {
        Log.d("MSSG storeToken", token);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("access_token", token);
        editor.commit();
        editor.apply();
    }
    
}
