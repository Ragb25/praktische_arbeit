package model;

/**
 * Model-Klasse: abstrakte Basis fuer alle Antriebsarten eines Raumschiffs.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public abstract class Antrieb extends Systemkomponente {

    private double maxSchubkraft;

    /**
     * Legt die gemeinsamen Komponentendaten und die maximale Schubkraft fest.
     *
     * @param seriennummer eindeutige Kennung des Exemplars
     * @param typ Katalogtyp, dem dieses Exemplar zugeordnet ist
     * @param maxSchubkraft maximale Schubkraft dieses Antriebs
     */
    public Antrieb(String seriennummer, Komponententyp typ, double maxSchubkraft) {
        super(seriennummer, typ);
        this.maxSchubkraft = maxSchubkraft;
    }

    /**
     * Liefert die maximale Schubkraft dieses Antriebs.
     *
     * @return die maximale Schubkraft
     */
    public double getMaxSchubkraft() {
        return maxSchubkraft;
    }

    /**
     * Setzt die maximale Schubkraft, z.B. nach einer Reparatur oder einem Upgrade.
     *
     * @param maxSchubkraft die neue maximale Schubkraft
     */
    public void setMaxSchubkraft(double maxSchubkraft) {
        this.maxSchubkraft = maxSchubkraft;
    }
}
