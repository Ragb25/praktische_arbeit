package model;

/**
 * Model-Klasse: abstrakte Basis fuer alle Forschungsmodule eines Raumschiffs.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public abstract class Forschungsmodul extends Systemkomponente {

    private String forschungsGebiet;

    /**
     * Legt die gemeinsamen Komponentendaten und das Forschungsgebiet fest.
     *
     * @param seriennummer eindeutige Kennung des Exemplars
     * @param typ Katalogtyp, dem dieses Exemplar zugeordnet ist
     * @param forschungsGebiet Fachgebiet, dem dieses Modul dient
     */
    public Forschungsmodul(String seriennummer, Komponententyp typ, String forschungsGebiet) {
        super(seriennummer, typ);
        this.forschungsGebiet = forschungsGebiet;
    }

    /**
     * Liefert das Forschungsgebiet dieses Moduls.
     *
     * @return das Forschungsgebiet
     */
    public String getForschungsGebiet() {
        return forschungsGebiet;
    }

    /**
     * Setzt das Forschungsgebiet, z.B. bei einer Neuausrichtung.
     *
     * @param forschungsGebiet das neue Forschungsgebiet
     */
    public void setForschungsGebiet(String forschungsGebiet) {
        this.forschungsGebiet = forschungsGebiet;
    }
}
