package hector.developers.alabaster.model;

import java.io.Serializable;

public class Finance implements Serializable {
    private String date;
    private String day;
    private String programme;
    private String tithe;
    private String offering;
    private String firstFruit;
    private String projectOrPledgeFund;
    private String others;
    private String countingUsher;
    private String receivedBy;
    private String total;

    public Finance() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getTithe() {
        return tithe;
    }

    public void setTithe(String tithe) {
        this.tithe = tithe;
    }

    public String getOffering() {
        return offering;
    }

    public void setOffering(String offering) {
        this.offering = offering;
    }

    public String getFirstFruit() {
        return firstFruit;
    }

    public void setFirstFruit(String firstFruit) {
        this.firstFruit = firstFruit;
    }

    public String getProjectOrPledgeFund() {
        return projectOrPledgeFund;
    }

    public void setProjectOrPledgeFund(String projectOrPledgeFund) {
        this.projectOrPledgeFund = projectOrPledgeFund;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getCountingUsher() {
        return countingUsher;
    }

    public void setCountingUsher(String countingUsher) {
        this.countingUsher = countingUsher;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
