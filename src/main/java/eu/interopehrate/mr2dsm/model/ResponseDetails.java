package eu.interopehrate.mr2dsm.model;

public class ResponseDetails {
    private EidasResponse eidasResponse;
    private String assertion;
    private boolean authenticated;
    private UserDetails userDetails;

    public ResponseDetails() {
    }

    public EidasResponse getEidasResponse() {
        return eidasResponse;
    }

    public String getAssertion() {
        return assertion;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setEidasResponse(EidasResponse eidasResponse) {
        this.eidasResponse = eidasResponse;
    }

    public void setAssertion(String assertion) {
        this.assertion = assertion;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
