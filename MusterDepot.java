import java.time.LocalDate;

public class MusterDepot {

    private Aktie[] aktien;
    private Transaktion[] transaktionen;

    private int anzahlRegistrierteAktien;
    private int anzahlTransaktionen;

    public MusterDepot() {
        this.aktien = new Aktie[20];
        this.transaktionen = new Transaktion[100];

        this.anzahlRegistrierteAktien = 0;
        this.anzahlTransaktionen = 0;

    }

    public int getAktien() {
        return anzahlRegistrierteAktien;
    }

    public void registriereAktie(String wkn, String name, double aktuellerKurs) {
        if (anzahlRegistrierteAktien < 20) {
            Aktie aktie1 = new Aktie(wkn, name, aktuellerKurs);

            aktien[anzahlRegistrierteAktien] = aktie1;

            anzahlRegistrierteAktien++;
            System.out.println("Die Aktie " + aktie1.getName() + " wurde erfolgreich registriert!");

        } else
            System.out.println(
                    "Das Depot ist voll! Es können keine weiteren Aktien registriert werden (max. 20 Aktien).");
    }

    public Aktie aktieSuchen(String wkn) {
        for (int i = 0; i < anzahlRegistrierteAktien; i++) {
            if (aktien[i].getWkn().equals(wkn)) {
                return aktien[i];
            }
        }
        return null;
    }

    public void initializeData() {
        registriereAktie("100", "Apple", 150.0);
        registriereAktie("200", "Microsoft", 280.0);
        registriereAktie("300", "SAP", 120.0);
        registriereAktie("400", "Tesla", 200.0);
        registriereAktie("500", "Mercedes", 360.0);
        registriereAktie("600", "Volkswagen", 400.0);
        registriereAktie("700", "Google", 330.0);
        registriereAktie("800", "Amazon", 240.0);

        // Das sind random Käufe und Verkäufe mit random werten, damit bei start vom
        // Programm unter menüpunkt 5 direkt schon Transaktionen vorhanden sind.
        Aktie apple = aktieSuchen("100");
        aktienKaufBuchen(apple, 100.0, 10, "KAUF", LocalDate.now());

        Aktie ms = aktieSuchen("200");
        aktienKaufBuchen(ms, 250.0, 5, "KAUF", LocalDate.now());

        Aktie sap = aktieSuchen("300");
        aktienKaufBuchen(sap, 100.0, 20, "KAUF", LocalDate.now());

        Aktie tesla = aktieSuchen("400");
        aktienKaufBuchen(tesla, 150.0, 2, "KAUF", LocalDate.now());

        aktienVerkaufBuchen(sap, 130.0, 5, "VERKAUF", LocalDate.now());

        aktienKaufBuchen(apple, 110.0, 5, "KAUF", LocalDate.now());
    }

    public void aktienKaufBuchen(Aktie gehandelteAktie, double aktienPreis, int anzahlAktien, String art,
            LocalDate transaktionsDatum) {
        if (anzahlTransaktionen < 100) {
            Transaktion transaktion1 = new Transaktion(aktienPreis, anzahlAktien, LocalDate.now(), "KAUF",
                    gehandelteAktie);

            transaktionen[anzahlTransaktionen] = transaktion1;

            anzahlTransaktionen++;
            System.out.println("Der Kauf von Aktie " + gehandelteAktie.getName() + " wurde gebucht.");
        } else {
            System.out
                    .println("Fehler: Es können keine weiteren Transaktionen geführt werden (max. 100 Transaktionen).");
        }
    }

    public void alleAktienAnzeigen() {
        System.out.println("******** Alle vorhandenen Aktien ********");
        System.out.println();
        System.out.println("Name (WKN): aktueller Kurs");
        System.out.println();
        for (int i = 0; i < anzahlRegistrierteAktien; i++) {
            System.out.println(i + 1 + ". " + aktien[i].getName() + "(" + aktien[i].getWkn() + "): "
                    + aktien[i].getAktuellerKurs() + " EUR");
        }
    }

    // Berechnet nur den Bestand
    public int berechneBestand(Aktie a) {
        int bestand = 0;
        for (int i = 0; i < anzahlTransaktionen; i++) {
            if (transaktionen[i].getGehandelteAktie().equals(a)) {
                if (transaktionen[i].getArt().equals("KAUF")) {
                    bestand = bestand + transaktionen[i].getAnzahlAktien();

                } else if (transaktionen[i].getArt().equals("VERKAUF")) {
                    bestand = bestand - transaktionen[i].getAnzahlAktien();
                }

            }
        }
        return bestand;

    }

    // Zeigt dem Benutzer die verkaufbaren Aktien mit Hilfe von berechneBestand. für
    // übersichtAnzeigen, da hier bestand ist
    public void zeigeVerkaufbareAktien() {
        System.out.println("******** Deine verkaufbaren Aktien ********");
        System.out.println();
        boolean aktienGefunden = false;
        double gesamtWert = 0;
        int anzeigeNummer = 1;
        for (int i = 0; i < anzahlRegistrierteAktien; i++) {
            int anzahlVerkaufbareAktien = berechneBestand(aktien[i]);

            if (anzahlVerkaufbareAktien > 0) {
                aktienGefunden = true;

                double marktwert = anzahlVerkaufbareAktien * aktien[i].getAktuellerKurs();
                gesamtWert = gesamtWert + marktwert;

                double kaufKurs = berechneDurchschnittsPreis(aktien[i]);
                double gewinn = marktwert - (anzahlVerkaufbareAktien * kaufKurs);

                System.out.println(
                        anzeigeNummer + ". " + "Aktie: " + aktien[i].getName() + " (" + aktien[i].getWkn() + ")");
                System.out.println("   Bestand: " + anzahlVerkaufbareAktien + " Stück");
                System.out.println("   Aktueller Kurs: " + aktien[i].getAktuellerKurs() + " EUR");
                System.out.println("   Kaufkurs: " + kaufKurs + " EUR");
                System.out.println("   Marktwert: " + marktwert);
                System.out.println("   Gewinn/Verlust: " + gewinn + " EUR");
                System.out.println();

                anzeigeNummer++;

            }

        }

        // Hinweis soll außerhalb for schleife sein
        if (aktienGefunden == false) {
            System.out.println("Hinweis: Es sind keine Aktien vorhanden zum Verkauf.");
            System.out.println();
        }

        // Summe ausgeben für übersicht anzeigen
        System.out.println("Der Gesamtwert Ihres Depots: " + gesamtWert + " EUR.");
        System.out.println();

    }

    // speichert Buchungen beim Verkauf
    public void aktienVerkaufBuchen(Aktie gehandelteAktie, double aktienPreis, int anzahlAktien, String art,
            LocalDate transaktionsDatum) {
        if (anzahlTransaktionen < 100) {
            Transaktion transaktion1 = new Transaktion(aktienPreis, anzahlAktien, LocalDate.now(), "VERKAUF",
                    gehandelteAktie);

            transaktionen[anzahlTransaktionen] = transaktion1;

            anzahlTransaktionen++;
            System.out.println("Der Verkauf von Aktie " + gehandelteAktie.getName() + " wurde gebucht.");
        } else {
            System.out
                    .println("Fehler: Es können keine weiteren Transaktionen geführt werden (max. 100 Transaktionen).");
        }

    }

    // Kurs Aktualisieren (4d)
    public boolean kursAktualisieren(String wkn, double neuerKurs) {
        Aktie gesuchteAktie = aktieSuchen(wkn);
        if (gesuchteAktie == null) {
            System.out.println("Fehler: Diese Aktie existiert nicht!");
            return false;

        } else {
            gesuchteAktie.setAktuellerKurs(neuerKurs);
            return true;
        }
    }

    public double berechneDurchschnittsPreis(Aktie a) {
        double gesamtKosten = 0;
        int gesamtGekaufteAnzahl = 0;

        for (int i = 0; i < anzahlTransaktionen; i++) {
            if (transaktionen[i].getGehandelteAktie().equals(a)) {
                if (transaktionen[i].getArt().equals("KAUF")) {
                    gesamtKosten = gesamtKosten
                            + (transaktionen[i].getAnzahlAktien() * transaktionen[i].getAktienPreis());
                    gesamtGekaufteAnzahl = gesamtGekaufteAnzahl + transaktionen[i].getAnzahlAktien();

                }

            }
        }
        if (gesamtGekaufteAnzahl > 0) {
            return gesamtKosten / gesamtGekaufteAnzahl;
        } else {
            return 0;
        }

    }

}
