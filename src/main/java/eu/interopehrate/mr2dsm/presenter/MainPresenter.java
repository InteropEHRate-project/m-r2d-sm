package eu.interopehrate.mr2dsm.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import eu.interopehrate.mr2dsm.MainContract;
import eu.interopehrate.mr2dsm.api.AuthenticationKeycloak;
import eu.interopehrate.mr2dsm.model.AccessTokenResponce;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    public AuthenticationKeycloak postsService;
    SharedPreferences pref;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void requestToken(String username, String password) {
        Retrofit retrofitKeycloak = new Retrofit.Builder()
                .baseUrl("http://213.249.46.205:8180")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postsService = retrofitKeycloak.create(AuthenticationKeycloak.class);

        Call<AccessTokenResponce> call = postsService.requestAuthToken("password",username,password,"gateway");

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
        Retrofit retrofitNCP = new Retrofit.Builder()
                .baseUrl("http://192.168.1.50:8084")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postsService = retrofitNCP.create(AuthenticationKeycloak.class);

        String value = "Bearer " + token;

        Call<Object> call = postsService.authenticate(value, "8bbebb8a-02b0-49b3-b780-5159927a9f08");
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.d("MSSG getAccess_token",response.toString());
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("MSSG access_token",t.getMessage());
            }
        });
    }

    public void storeToken(String token) {
        Log.d("MSSG storeToken", token);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences((Context) view);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("access_token", token);
        editor.commit();
        editor.apply();
    }
    
}
