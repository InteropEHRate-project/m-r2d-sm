package eu.interopehrate.mr2dsm.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class UserDetails {
    private HomeAddress currentAddress;
    private String familyName;
    private String firstName;
    private String dateOfBirth;
    private String gender;
    private String personIdentifier;
    private String birthName;
    private String placeOfBirth;

    public HomeAddress getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(HomeAddress currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonIdentifier() {
        return personIdentifier;
    }

    public void setPersonIdentifier(String personIdentifier) {
        this.personIdentifier = personIdentifier;
    }

    public String getBirthName() {
        return birthName;
    }

    public void setBirthName(String birthName) {
        this.birthName = birthName;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public static UserDetails create(List<ResponseAttibute> attributes) {

        UserDetails user = new UserDetails();

        for(ResponseAttibute attr : attributes) {
            switch (attr.getName()) {
                case Attribute.BIRTH_NAME: {
                    String value = attr.getValues().get(0).getValue();
                    user.setBirthName(value);
                    break;
                }
                case Attribute.PLACE_OF_BIRTH: {
                    String value = attr.getValues().get(0).getValue();
                    user.setPlaceOfBirth(value);
                    break;
                }
                case Attribute.CURRENT_ADDRESS: {
                    HomeAddress value = new ObjectMapper().convertValue(attr.getValue(), HomeAddress.class);
                    user.setCurrentAddress(value);
                    break;
                }
                case Attribute.FAMILY_NAME: {
                    String value = attr.getValues().get(0).getValue();
                    user.setFamilyName(value);
                    break;
                }
                case Attribute.FIRST_NAME: {
                    String value = attr.getValues().get(0).getValue();
                    user.setFirstName(value);
                    break;
                }
                case Attribute.DATE_OF_BIRTH: {
                    String value = (String) attr.getValue();
                    user.setDateOfBirth(value);
                    break;
                }
                case Attribute.GENDER: {
                    String value = attr.getValues().get(0).getValue();
                    user.setGender(value);
                    break;
                }
                case Attribute.PERSON_IDENTIFIER: {
                    String value = attr.getValues().get(0).getValue();
                    user.setPersonIdentifier(value);
                    break;
                }
            }
        }
        return user;
    }


}
