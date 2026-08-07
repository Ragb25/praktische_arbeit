package model;

/**
 * Model-Klasse: Teleskop, ein Exemplar von Forschungsmodul.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Teleskop extends Forschungsmodul {

    private double brennweite;

    /**
     * Legt die Moduldaten und die Brennweite fest.
     *
     * @param seriennummer eindeutige Kennung des Exemplars
     * @param typ Katalogtyp, dem dieses Exemplar zugeordnet ist
     * @param forschungsGebiet Fachgebiet, dem dieses Modul dient
     * @param brennweite Brennweite der Optik
     */
    public Teleskop(String seriennummer, Komponententyp typ, String forschungsGebiet, double brennweite) {
        super(seriennummer, typ, forschungsGebiet);
        this.brennweite = brennweite;
    }

    /**
     * Liefert die Brennweite dieses Teleskops.
     *
     * @return die Brennweite
     */
    public double getBrennweite() {
        return brennweite;
    }

    /**
     * Setzt die Brennweite, z.B. nach einem Objektivwechsel.
     *
     * @param brennweite die neue Brennweite
     */
    public void setBrennweite(double brennweite) {
        this.brennweite = brennweite;
    }
}
