import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DepotApp {

    private static Scanner sc = new Scanner(System.in);

    private static MusterDepot depot = new MusterDepot();

    public static void main(String[] args) {

        depot.initializeData();
        
        System.out.println("**********************************************************************");
        System.out.println("                     WILLKOMMEN BEI DER DEPOT-APP                     ");
        System.out.println("**********************************************************************");
        System.out.println();

        do {
            menueAnzeigen();
            try {
                int menueEingabe = sc.nextInt();

                if (menueEingabe == 1) {
                    registriereAktie();
                } else if (menueEingabe == 2) {
                    aktienKaufBuchen();
                } else if (menueEingabe == 3) {
                    aktienVerkaufBuchen();
                } else if (menueEingabe == 4) {
                    kursAktualisieren();
                } else if (menueEingabe == 5) {
                    uebersichtAnzeigen();
                } else if (menueEingabe == 6) {
                    programmBeenden();
                    break;
                } else {
                    System.out.print("Bitte Zahl von 1 bis 6 eingeben.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Bitte eine Zahl eingeben!");
                sc.next();
            }
        } while (true);
    }

    public static void menueAnzeigen() {
        System.out.println("******** Menü ********");
        System.out.println("Bitte geben Sie die jeweilige Zahl ein, um die gewünschte Aktion zu starten");
        System.out.println();
        System.out.println("1. Aktie registrieren");
        System.out.println("2. Aktienkauf buchen");
        System.out.println("3. Aktienverkauf buchen");
        System.out.println("4. Aktienkurs aktualisieren");
        System.out.println("5. Depotübersicht anzeigen");
        System.out.println("6. Programm beenden");
        System.err.print("Aktion: ");
        System.out.println();
    }

    public static void registriereAktie() {

        System.out.print("WKN eingeben: ");
        String wkn = sc.next();

        System.out.print("Name der Aktie eingeben: ");
        String name = sc.next();

        double aktuellerKurs;
        while (true) {
            System.out.print("Aktuellen Kurs eingeben: " + "Hinweis!: Nutzen Sie ein , und kein .");
            try {
                aktuellerKurs = sc.nextDouble();
                if (aktuellerKurs > 0) {
                    break;
                }
                System.out.println("Der Kurs muss größer als 0 sein.");
            } catch (InputMismatchException e) {
                System.out.println("Bitte eine Zahl eingeben.");
                sc.next();
            }
        }

        depot.registriereAktie(wkn, name, aktuellerKurs);
    }

    public static void aktienKaufBuchen() {
        // Eingabe WKN
        Aktie gefundeneAktie = null;
        do {
            depot.alleAktienAnzeigen();
            System.out.print("Bitte geben Sie die WKN der gewünschten Aktie ein: ");
            String eingabeWkn = sc.next();
            gefundeneAktie = depot.aktieSuchen(eingabeWkn);
            if (gefundeneAktie == null) {
                System.out.println("Die Aktie mit der WKN " + eingabeWkn + " wurde nicht gefunden.");
                continue;
            } else {
                System.out.println("Sie haben die Aktie " + gefundeneAktie.getName() + " gewählt.");
                break;
            }
        } while (true);

        // Eingabe Anzahl
        int eingabeAnzahl;
        do {
            try {
                System.out.print("Bitte geben Sie die gewünschte Anzahl an Aktien ein: ");
                eingabeAnzahl = sc.nextInt();
                if (eingabeAnzahl <= 0) {
                    System.out.println("Bitte geben Sie eine Zahl, die größer als 0 ist.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Bitte eine Zahl eingeben.");
                sc.next();

            }
        } while (true);

        // Eingabe Kaufkurs
        double eingabeKurs;
        do {
            try {
                System.out.print("Bitte geben Sie den gewünschten Kaufkurs ein.");
                eingabeKurs = sc.nextDouble();
                if (eingabeKurs <= 0) {
                    System.out.println("Bitte geben Sie eine Zahl, die größer als 0 ist.");
                    continue;
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Bitte eine Zahl eingeben.");
                sc.next();
            }
        } while (true);

        // Kaufdatum speichern
        LocalDate getKaufdatum = LocalDate.now();

        // Alle Infos über Kauf speichern und an Musterdepot schicken
        depot.aktienKaufBuchen(gefundeneAktie, eingabeKurs, eingabeAnzahl, "KAUF", getKaufdatum);

    }

    public static void aktienVerkaufBuchen() {

        // Eingabe WKN
        Aktie gefundeneAktieVerkauf = null;
        do {
            depot.alleAktienAnzeigen();
            System.out.print("Bitte geben Sie die WKN der gewünschten Aktie ein: ");
            String eingabeWknVerkauf = sc.next();
            gefundeneAktieVerkauf = depot.aktieSuchen(eingabeWknVerkauf);
            if (gefundeneAktieVerkauf == null) {
                System.out.println("Die Aktie mit der WKN " + eingabeWknVerkauf + " wurde nicht gefunden.");
                continue;
            } else {
                depot.berechneBestand(gefundeneAktieVerkauf);
                int aktuellerBestand = depot.berechneBestand(gefundeneAktieVerkauf);

                if (aktuellerBestand == 0) {
                    System.out.println(
                            "Diese Aktie mit der WKN " + eingabeWknVerkauf + " besitzen Sie nicht (Bestand = 0).");
                    continue;

                } else {
                    System.out.println("Sie besitzen " + aktuellerBestand + " Stück von dieser Aktie.");
                }
                System.out.println("Sie haben die Aktie " + gefundeneAktieVerkauf.getName() + " gewählt.");
                break;
            }
        } while (true);

        // Eingabe Anzahl an gewünschten Aktien
        int eingabeAnzahlVerkauf;
        do {
            System.out.print("Bitte geben Sie die gewünschte Anzahl an Aktien an: ");
            try {
                eingabeAnzahlVerkauf = sc.nextInt();
                if (eingabeAnzahlVerkauf <= 0) {
                    System.out.println("Bitte geben Sie eine Zahl größer als 0.");
                    continue;
                } else { // richtig eingegeben. jetzt prüfen ob eingabe > bestand ist

                    if (eingabeAnzahlVerkauf > depot.berechneBestand(gefundeneAktieVerkauf)) {
                        System.out.println("Sie besitzen nicht so viele Aktien. Aktienbestand: "
                                + depot.berechneBestand(gefundeneAktieVerkauf));
                        continue;

                    } else { // Alles richtig (eingabe und menge stimmen mit bestand ein)
                        break;
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Bitte eine Zahl eingeben.");
                sc.next();
            }
        } while (true);

        // Eingabe Preis
        double eingabeKursVerkauf;
        do {
            System.out.print("Bitte geben Sie den gewünschten Kurs (Preis) an: ");
            try {
                eingabeKursVerkauf = sc.nextDouble();
                if (eingabeKursVerkauf <= 0) {
                    System.out.println("Bitte geben Sie einen Preis größer als 0.");
                    continue;
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Bitte eine Zahl eingeben.");
                sc.next();
            }
        } while (true);

        depot.aktienVerkaufBuchen(gefundeneAktieVerkauf, eingabeKursVerkauf, eingabeAnzahlVerkauf, "VERKAUF",
                LocalDate.now());

    }

    public static void kursAktualisieren() {
        Aktie gefundeneAktieKurs = null;
        do {
            depot.alleAktienAnzeigen();
            System.out.print("Bitte geben Sie die WKN der gewünschten Aktie ein: ");
            String eingabeWknKursAktualisieren = sc.next();
            gefundeneAktieKurs = depot.aktieSuchen(eingabeWknKursAktualisieren);
            if (gefundeneAktieKurs == null) {
                System.out.println("Die Aktie mit der WKN " + eingabeWknKursAktualisieren + " wurde nicht gefunden.");
                continue;
            } else {
                double neuerKurs;
                try {
                    System.out.print("Bitte geben Sie den gewünschten Kurs ein: ");
                    neuerKurs = sc.nextDouble();
                    if (neuerKurs <= 0) {
                        System.out.println("Bitte geben Sie eine Zahl größer als 0 ein.");
                        continue;

                    } else {
                        boolean gesuchteAktie = depot.kursAktualisieren(eingabeWknKursAktualisieren, neuerKurs);
                        if (gesuchteAktie) {
                            System.out.println("Der Kurs von dieser Aktie wurde erfolgreich aktualisiert.");
                        }
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Bitte eine Zahl eingeben.");
                    sc.next();
                }
            }

        } while (true);

    }

    public static void uebersichtAnzeigen() {

        // in Klasse Musterdepot
        depot.zeigeVerkaufbareAktien();

    }

    public static void programmBeenden() {

    }

}
