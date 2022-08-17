package eu.interopehrate.mr2dsm.model;

import android.util.Base64;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

public class SmsspTokenRequest {
    private List<Attribute> attribute_list;
    private String citizen_country;
    private String created_on;
    private Boolean force_authentication;
    private UUID id;
    private NameIDPolicy nameIDPolicy;
    private String provider_name;
    private String serviceUrl;
    private SPType SPType;
    private String version;
    private RequestedAuthenticationContext requestedAuthenticationContext;

    public SmsspTokenRequest(List<Attribute> attribute_list, String citizen_country, String created_on, Boolean force_authentication, UUID id, NameIDPolicy nameIDPolicy, String provider_name, String serviceUrl, SPType SPType, String version, RequestedAuthenticationContext requestedAuthenticationContext) {
        this.attribute_list = attribute_list;
        this.citizen_country = citizen_country;
        this.created_on = created_on;
        this.force_authentication = force_authentication;
        this.id = id;
        this.nameIDPolicy = nameIDPolicy;
        this.provider_name = provider_name;
        this.serviceUrl = serviceUrl;
        this.SPType = SPType;
        this.version = version;
        this.requestedAuthenticationContext = requestedAuthenticationContext;
    }

    public SmsspTokenRequest(String created_on, UUID id, String serviceUrl, String version) {
        this.created_on = created_on;
        this.id = id;
        this.serviceUrl = serviceUrl;
        this.version = version;
    }

    public String convertToJSON(){
        ObjectMapper obj = new ObjectMapper();
        try {
            //Wrap it as an authentication_request
            return "{ \"authentication_request\" : " + obj.writeValueAsString(this) + " }";
        }
        catch (JsonProcessingException e){
            Log.e("SmsspTokenRequest","Error converting to JSON");
            return "";
        }
    }

    public String encodeToken(){
        return Base64.encodeToString(this.convertToJSON().getBytes(), Base64.DEFAULT);
    }

    public List<Attribute> getAttribute_list() {
        return attribute_list;
    }

    public void setAttribute_list(List<Attribute> attribute_list) {
        this.attribute_list = attribute_list;
    }

    public String getCitizen_country() {
        return citizen_country;
    }

    public void setCitizen_country(String citizen_country) {
        this.citizen_country = citizen_country;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public Boolean getForce_authentication() {
        return force_authentication;
    }

    public void setForce_authentication(Boolean force_authentication) {
        this.force_authentication = force_authentication;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public NameIDPolicy getNameIDPolicy() {
        return nameIDPolicy;
    }

    public void setNameIDPolicy(NameIDPolicy nameIDPolicy) {
        this.nameIDPolicy = nameIDPolicy;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public SPType getSPType() {
        return SPType;
    }

    public void setSPType(SPType SPType) {
        this.SPType = SPType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public RequestedAuthenticationContext getRequestedAuthenticationContext() {
        return requestedAuthenticationContext;
    }

    public void setRequestedAuthenticationContext(RequestedAuthenticationContext requestedAuthenticationContext) {
        this.requestedAuthenticationContext = requestedAuthenticationContext;
    }
}
