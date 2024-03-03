import java.util.Scanner; //Import des Scanners
import java.net.UnknownHostException; // Import der Fehlerbibliothek


public class Subnetzrechner2 {

    //Definition der main Klasse
    public static void main(String[] args) throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);

        // Eingabe der IP Adresse über den Nutzer per Scanner
        System.out.print("Bitte IP-Adresse (z.B. 192.168.178.1) eingeben: ");
        String ipAdresse = scanner.nextLine();

        // Eingabe der Subnetzmaske über den Nutzer per Scanner
        System.out.print("Bitte Subnetzmaske (z.B. 255.255.192.0) eingeben: ");
        String subnetzMaske = scanner.nextLine();

        //Erstellen einer Instanz der IP-Klasse
        IP ip = new IP(ipAdresse, subnetzMaske);

        // Netzwerkdetails berechnen
        ip.berechneNetzwerkDetails();

        // Ausgabe der Ergebnisse
        System.out.println("Netz-ID des Netzwerks: " + ip.getNetzAdresse());
        System.out.println("Broadcast-IP des Netzwerks: " + ip.getBroadcastAdresse());
        System.out.println("Anzahl möglicher Geräte im Netzwerk: " + ip.getAnzahlVfHosts());
    }
}