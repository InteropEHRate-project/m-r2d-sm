package eu.interopehrate.mr2dsm.model;

public class Status {
    private String status_code;
    private String status_message;
    private SubStatusCode sub_status_code;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public SubStatusCode getSub_status_code() {
        return sub_status_code;
    }

    public void setSub_status_code(SubStatusCode sub_status_code) {
        this.sub_status_code = sub_status_code;
    }
}
