package eu.interopehrate.mr2dsm.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AuthRequest implements Serializable {
    private String username;
    private String password;
    private ContextClass eidasloa;
    private String checkBoxIpAddress;
    private String smsspToken;
    private String callback;
    private String jSonRequestDecoded;
    private String doNotmodifyTheResponse;

    public AuthRequest() {
        initialize();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContextClass getEidasloa() {
        return eidasloa;
    }

    public void setEidasloa(ContextClass eidasloa) {
        this.eidasloa = eidasloa;
    }

    public String getCheckBoxIpAddress() {
        return checkBoxIpAddress;
    }

    public void setCheckBoxIpAddress(String checkBoxIpAddress) {
        this.checkBoxIpAddress = checkBoxIpAddress;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getjSonRequestDecoded() {
        return jSonRequestDecoded;
    }

    public void setjSonRequestDecoded(String jSonRequestDecoded) {
        this.jSonRequestDecoded = jSonRequestDecoded;
    }

    public String getDoNotmodifyTheResponse() {
        return doNotmodifyTheResponse;
    }

    public void setDoNotmodifyTheResponse(String doNotmodifyTheResponse) {
        this.doNotmodifyTheResponse = doNotmodifyTheResponse;
    }

    public String getSmsspToken() {
        return smsspToken;
    }

    public void setSmsspToken(String smsspToken) {
        this.smsspToken = smsspToken;
    }

    private void initialize() {
        List<Attribute> attribute_list = new ArrayList<Attribute>();
        attribute_list.add(new Attribute("requested_attribute", Attribute.CURRENT_ADDRESS, true,null));
        attribute_list.add(new Attribute("requested_attribute", Attribute.FAMILY_NAME, true,null));
        attribute_list.add(new Attribute("requested_attribute", Attribute.FIRST_NAME, true,null));
        attribute_list.add(new Attribute("requested_attribute", Attribute.DATE_OF_BIRTH, true,null));
        attribute_list.add(new Attribute("requested_attribute", Attribute.GENDER, false,null));
        attribute_list.add(new Attribute("requested_attribute", Attribute.PERSON_IDENTIFIER, true,null));
        attribute_list.add(new Attribute("requested_attribute", Attribute.PLACE_OF_BIRTH, false,null));
        attribute_list.add(new Attribute("requested_attribute", Attribute.BIRTH_NAME, false,null));

        SmsspTokenRequest smsspToken = new SmsspTokenRequest(Instant.now().toString(), UUID.randomUUID(),"http://localhost:8080/SP","1");
        smsspToken.setAttribute_list(attribute_list);
        smsspToken.setCitizen_country(null);
        smsspToken.setProvider_name("DEMO-SP-CA");
        smsspToken.setForce_authentication(true);
        smsspToken.setServiceUrl("http://localhost:8080/SpecificProxyService/IdpResponse");
        smsspToken.setNameIDPolicy(NameIDPolicy.PERSISTENT);
        smsspToken.setSPType(SPType.PUBLIC);
        smsspToken.setRequestedAuthenticationContext(new RequestedAuthenticationContext("minimum", ContextClass.C));
        this.setUsername(null);
        this.setPassword(null);
        this.setSmsspToken(smsspToken.encodeToken());
        this.setEidasloa(ContextClass.A);
        this.setCallback("http://localhost:8080/SpecificProxyService/IdpResponse");
        this.setCheckBoxIpAddress("on");
        this.setDoNotmodifyTheResponse("on");
        this.setjSonRequestDecoded(smsspToken.convertToJSON());
    }



}
