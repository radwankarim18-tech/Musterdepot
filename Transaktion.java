import java.time.LocalDate;

public class Transaktion {

    private double aktienPreis;
    private int anzahlAktien;
    private LocalDate transaktionsDatum;
    private String art; // Kauf oder Verkauf
    private Aktie gehandelteAktie;

    public Transaktion(double aktienPreis, int anzahlAktien, LocalDate transaktionsDatum, String art,
            Aktie gehandelteAktie) {
        this.aktienPreis = aktienPreis;
        this.anzahlAktien = anzahlAktien;
        this.transaktionsDatum = transaktionsDatum;
        this.art = art;
        this.gehandelteAktie = gehandelteAktie;
    }

    public double getAktienPreis() {
        return aktienPreis;
    }

    public int getAnzahlAktien() {
        return anzahlAktien;
    }

    public LocalDate getTransaktionsDatum() {
        return transaktionsDatum;
    }

    public String getArt() {
        return art;
    }

    public Aktie getGehandelteAktie() {
        return gehandelteAktie;
    }
}
