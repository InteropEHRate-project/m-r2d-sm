package eu.interopehrate.mr2dsm.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;


public class EidasResponse {
    private String version;
    private UUID id;
    private String inresponse_to;
    private String subject;
    private String name_id_format;
    private String client_ip_address;
    private String created_on;
    private String authentication_context_class;
    private String issuer;
    private Status status;
    private JsonNode attribute_list;
    private String client_Ip_Address;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getInresponse_to() {
        return inresponse_to;
    }

    public void setInresponse_to(String inresponse_to) {
        this.inresponse_to = inresponse_to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName_id_format() {
        return name_id_format;
    }

    public void setName_id_format(String name_id_format) {
        this.name_id_format = name_id_format;
    }

    public String getClient_ip_address() {
        return client_ip_address;
    }

    public void setClient_ip_address(String client_ip_address) {
        this.client_ip_address = client_ip_address;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getAuthentication_context_class() {
        return authentication_context_class;
    }

    public void setAuthentication_context_class(String authentication_context_class) {
        this.authentication_context_class = authentication_context_class;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public JsonNode getAttribute_list() {
        return attribute_list;
    }

    public void setAttribute_list(JsonNode attribute_list) {
        this.attribute_list = attribute_list;
    }

    public String getClient_Ip_Address() {
        return client_Ip_Address;
    }

    public void setClient_Ip_Address(String client_Ip_Address) {
        this.client_Ip_Address = client_Ip_Address;
    }
}
