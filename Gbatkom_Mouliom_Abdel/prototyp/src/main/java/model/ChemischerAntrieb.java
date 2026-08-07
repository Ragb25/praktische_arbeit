package model;

/**
 * Model-Klasse: chemisch betriebener Antrieb, ein Exemplar von Antrieb.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class ChemischerAntrieb extends Antrieb {

    private String treibstoffTyp;

    /**
     * Legt die Antriebsdaten und die verwendete Treibstoffart fest.
     *
     * @param seriennummer eindeutige Kennung des Exemplars
     * @param typ Katalogtyp, dem dieses Exemplar zugeordnet ist
     * @param maxSchubkraft maximale Schubkraft dieses Antriebs
     * @param treibstoffTyp Art des verwendeten Treibstoffs
     */
    public ChemischerAntrieb(String seriennummer, Komponententyp typ, double maxSchubkraft, String treibstoffTyp) {
        super(seriennummer, typ, maxSchubkraft);
        this.treibstoffTyp = treibstoffTyp;
    }

    /**
     * Liefert die Art des verwendeten Treibstoffs.
     *
     * @return die Treibstoffart
     */
    public String getTreibstoffTyp() {
        return treibstoffTyp;
    }

    /**
     * Setzt die Treibstoffart, z.B. bei einem Umbau.
     *
     * @param treibstoffTyp die neue Treibstoffart
     */
    public void setTreibstoffTyp(String treibstoffTyp) {
        this.treibstoffTyp = treibstoffTyp;
    }
}
