public class Aktie {

    private String wkn;
    private String name;
    private double aktuellerKurs;

    public Aktie(String wkn, String name, double aktuellerKurs) {
        this.wkn = wkn;
        this.name = name;
        this.aktuellerKurs = aktuellerKurs;
    }

    public String getWkn() {
        return wkn;
    }

    public String getName() {
        return name;
    }

    public double getAktuellerKurs() {
        return aktuellerKurs;
    }

    public void setAktuellerKurs(double neuerKurs) {
        this.aktuellerKurs = neuerKurs;
    }

}
