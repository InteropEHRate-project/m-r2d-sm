package eu.interopehrate.mr2dsm.api;

/*
 *		Author: UBITECH
 *		Project: InteropEHRate - www.interopehrate.eu
 *
 *	    Description: Authentication library using keycloak
 */

public interface MR2DSM {

    /**
     *
     * Responsible for requesting the authentication token from keycloak
     *
     * @param username
     * @param password
     *
     */

    void requestToken(String username, String password);
    void authenticate(String token); //TODO to remove
}
