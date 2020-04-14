package eu.interopehrate.mr2dsm.api;

/*
 *		Author: UBITECH
 *		Project: InteropEHRate - www.interopehrate.eu
 *
 *	    Description: Authentication library using keycloak
 */

import eu.interopehrate.mr2dsm.model.AccessTokenResponce;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AuthenticationKeycloak {

    /**
     *
     * Responsible for requesting the authentication token from keycloak
     *
     * @param grantType,
     * @param username
     * @param password
     * @param client_id
     *
     */

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/auth/realms/user-authentication/protocol/openid-connect/token")
    Call<AccessTokenResponce> requestAuthToken(@Field("grant_type") String grantType,
                                               @Field("username") String username,
                                               @Field("password") String password,
                                               @Field("client_id") String client_id);


    /**
     *
     * Responsible for requesting the authentication token from keycloak
     *
     * @param token received authentication token
     *
     */

    @POST("/api/v1/bank/account/balance")
    Call<Object> authenticate(@Header("Authorization") String token,
                              @Body String body);
}
