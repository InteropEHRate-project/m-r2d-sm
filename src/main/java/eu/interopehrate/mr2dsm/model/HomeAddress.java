package eu.interopehrate.mr2dsm.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeAddress {
    private String cv_address_area;
    private String locator_designator;
    private String locator_name;
    private String po_box;
    private String post_code;

    public String getCv_address_area() {
        return cv_address_area;
    }

    public void setCv_address_area(String cv_address_area) {
        this.cv_address_area = cv_address_area;
    }

    public String getLocator_designator() {
        return locator_designator;
    }

    public void setLocator_designator(String locator_designator) {
        this.locator_designator = locator_designator;
    }

    public String getLocator_name() {
        return locator_name;
    }

    public void setLocator_name(String locator_name) {
        this.locator_name = locator_name;
    }

    public String getPo_box() {
        return po_box;
    }

    public void setPo_box(String po_box) {
        this.po_box = po_box;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }
}
