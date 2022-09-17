package in.icomputercoding.covid_19trackerapp;
public class Model {
    private  final String districtName, confirmed, deceased, recovered, active, confirmeddelta, deceaseddelta, recovereddelta;


    public Model(String name, String confirmed, String deceased, String recovered, String active, String confirmeddelta, String deceaseddelta, String recovereddelta) {
        this.districtName = name;
        this.confirmed = confirmed;
        this.active = active;
        this.deceased = deceased;
        this.recovered = recovered;
        this.confirmeddelta = confirmeddelta;
        this.deceaseddelta = deceaseddelta;
        this.recovereddelta = recovereddelta;


    }

    public String getDistrictName() {
        return districtName;
    }


    public String getConfirmed() {
        return confirmed;
    }

    public String getActive() {
        return active;
    }

    public String getDeceased() {
        return deceased;

    }


    public String getConfirmeddelta() {
        return confirmeddelta;
    }

    public String getDeceaseddelta() {
        return deceaseddelta;
    }

    public String getRecovereddelta() {
        return recovereddelta;
    }

    public String getRecovered() {
        return recovered;
    }

}


