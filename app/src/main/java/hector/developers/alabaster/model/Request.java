package hector.developers.alabaster.model;

import java.io.Serializable;

public class Request implements Serializable {

    private String date;
    private String nameOfRequester;
    private String department;
    private String amount;
    private String reason;
    private String approvalStatus;

    public Request() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameOfRequester() {
        return nameOfRequester;
    }

    public void setNameOfRequester(String nameOfRequester) {
        this.nameOfRequester = nameOfRequester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
