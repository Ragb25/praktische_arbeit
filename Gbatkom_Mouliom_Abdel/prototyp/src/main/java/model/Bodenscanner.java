package model;

/**
 * Model-Klasse: Bodenscanner, ein Exemplar von Forschungsmodul.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Bodenscanner extends model.Forschungsmodul {

    private double erfassungsRadius;

    /**
     * Legt die Moduldaten und den Erfassungsradius fest.
     *
     * @param seriennummer eindeutige Kennung des Exemplars
     * @param typ Katalogtyp, dem dieses Exemplar zugeordnet ist
     * @param forschungsGebiet Fachgebiet, dem dieses Modul dient
     * @param erfassungsRadius Reichweite des Scanners
     */
    public Bodenscanner(String seriennummer, model.Komponententyp typ, String forschungsGebiet, double erfassungsRadius) {
        super(seriennummer, typ, forschungsGebiet);
        this.erfassungsRadius = erfassungsRadius;
    }

    /**
     * Liefert den Erfassungsradius dieses Scanners.
     *
     * @return die Scanreichweite
     */
    public double getErfassungsRadius() {
        return erfassungsRadius;
    }

    /**
     * Setzt den Erfassungsradius, z.B. nach einem Upgrade.
     *
     * @param erfassungsRadius die neue Scanreichweite
     */
    public void setErfassungsRadius(double erfassungsRadius) {
        this.erfassungsRadius = erfassungsRadius;
    }
}
