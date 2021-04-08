package hector.developers.alabaster.model;

public class Attendance {
    private String numberOfMales;
    private String numberOfFemales;
    private String numberOfChildren;
    private String date;
    private String total;

    public Attendance() {
    }

    public String getNumberOfMales() {
        return numberOfMales;
    }

    public void setNumberOfMales(String numberOfMales) {
        this.numberOfMales = numberOfMales;
    }

    public String getNumberOfFemales() {
        return numberOfFemales;
    }

    public void setNumberOfFemales(String numberOfFemales) {
        this.numberOfFemales = numberOfFemales;
    }

    public String getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(String numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
