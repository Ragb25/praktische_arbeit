package model;

import java.util.Date;

/**
 * Model-Klasse: eine Bonuskarte, die genau einem Kunden gehoert.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Bonuskarte {

    private String kartennummer;
    private Date gueltigBis;
    private final Kunde inhaber;

    /**
     * Verknuepft die Karte unveraenderlich mit ihrem Inhaber, da eine
     * Bonuskarte ohne Kunden bedeutungslos ist.
     *
     * @param kartennummer eindeutige Kennung der Karte
     * @param gueltigBis Datum, bis zu dem die Karte gueltig ist
     * @param inhaber Kunde, dem die Karte gehoert
     */
    public Bonuskarte(String kartennummer, Date gueltigBis, Kunde inhaber) {
        this.kartennummer = kartennummer;
        this.gueltigBis = gueltigBis;
        this.inhaber = inhaber;
    }

    /**
     * Liefert die eindeutige Kennung dieser Karte.
     *
     * @return die Kartennummer
     */
    public String getKartennummer() {
        return kartennummer;
    }

    /**
     * Liefert das Datum, bis zu dem diese Karte gueltig ist.
     *
     * @return das Gueltigkeitsdatum
     */
    public Date getGueltigBis() {
        return gueltigBis;
    }

    /**
     * Setzt das Datum, bis zu dem diese Karte gueltig ist, z.B. bei
     * einer Verlaengerung.
     *
     * @param gueltigBis das neue Gueltigkeitsdatum
     */
    public void setGueltigBis(Date gueltigBis) {
        this.gueltigBis = gueltigBis;
    }

    /**
     * Liefert den Kunden, dem diese Karte gehoert.
     *
     * @return der Inhaber, nie null
     */
    public Kunde getInhaber() {
        return inhaber;
    }
}
