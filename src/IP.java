import java.net.InetAddress;
import java.net.UnknownHostException;

public class IP {
    // Definition der Variablen
    byte[] ipAdresseTeileInByte;
    byte[] subnetzMaskeTeileInByte;
    byte[] netzAdresseInByte;
    byte[] broadcastAdresseInByte;
    InetAddress netzAdresse;
    InetAddress broadcastAdresse;

    public IP(String ipAdresse, String subnetzMaske) throws UnknownHostException {
        // Erstellen der Standard Konstruktoren
        ipAdresseTeileInByte = ipAdresseZerschmettern(ipAdresse);
        subnetzMaskeTeileInByte = subnetzMaskeZerschmettern(subnetzMaske);
        // Definition des Arrays für die Berechnung der Netz-ID
        netzAdresseInByte = new byte[4];
        broadcastAdresseInByte = new byte[4];
    }

    // Umwandlung des Strings ipAdresse über die Bibliothek inetAddress in ein byte Format
    private byte[] ipAdresseZerschmettern(String ipAdresse) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(ipAdresse);
        return inetAddress.getAddress();
    }

    // Umwandlung des Strings subnetzMaske über die Bibliothek inetAddress in ein byte Format
    private byte[] subnetzMaskeZerschmettern(String subnetzMaske) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(subnetzMaske);
        return inetAddress.getAddress();
    }

    // Berechnung der Netz-ID über eine Schleife indem die IP-Adresse und Subnetzmaske in byte binär verknüpft werden
    public InetAddress getNetzAdresse() throws UnknownHostException {
        for (int i = 0; i < ipAdresseTeileInByte.length; i++) {
            netzAdresseInByte[i] = (byte) (ipAdresseTeileInByte[i] & subnetzMaskeTeileInByte[i]);
        }
        return InetAddress.getByAddress(netzAdresseInByte);
    }

    // Berechnung der Broadcast-Adresse über eine Schleife die die Netzadresse und die invertierte Subnetzmaske
    // über bitweises oder verknüpft.
    public InetAddress getBroadcastAdresse() throws UnknownHostException {
        for (int i = 0; i < ipAdresseTeileInByte.length; i++) {
            broadcastAdresseInByte[i] = (byte) (netzAdresseInByte[i] | ~subnetzMaskeTeileInByte[i]);
        }
        return InetAddress.getByAddress(broadcastAdresseInByte);
    }

    // Aufruf der Methoden zu Berechnung der Netzadresse und Broadcastadresse, und anschließenden Speicherung
    // der Werte in den Variablen netzAdresse und broadcastAdresse
    public void berechneNetzwerkDetails() throws UnknownHostException {
        netzAdresse = getNetzAdresse();
        broadcastAdresse = getBroadcastAdresse();
    }

    // Berechnung der Anzahl der verfügbaren Hosts bei Nutzung der math.pow Funktion. Abzug des Hostanteils (in Bit)
    // und Abzug der Netzadresse und Broadcastadresse
    public int getAnzahlVfHosts() {
        return (int) (Math.pow(2, 32 - getAnzahlBitsInSubnetzmaske()) - 2);
    }

    // Bitweise Prüfung der Subnetzmaske auf 0 und 1
    private int getAnzahlBitsInSubnetzmaske() {
        // Initialisierung der Variable für die spätere Nutzung als Anzahl der Bits für den Hostanteil
        int count = 0;

        // Iteriere über jedes Byte in der Subnetzmaske
        for (byte b : subnetzMaskeTeileInByte) {
            for (int i = 0; i < 8; i++) {
                // Überprüfe, ob das Bit gesetzt ist (1)
                if ((b & (1 << i)) != 0) {
                    count++;
                }
            }
        }
        return count;
    }
}