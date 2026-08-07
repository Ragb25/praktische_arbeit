package model;

/**
 * Model-Klasse: ionenbasierter Antrieb, ein Exemplar von Antrieb.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class IonenbasierterAntrieb extends Antrieb {

    private double xenonKapazitaet;

    /**
     * Legt die Antriebsdaten und die Xenon-Kapazitaet fest.
     *
     * @param seriennummer eindeutige Kennung des Exemplars
     * @param typ Katalogtyp, dem dieses Exemplar zugeordnet ist
     * @param maxSchubkraft maximale Schubkraft dieses Antriebs
     * @param xenonKapazitaet Fassungsvermoegen des Xenon-Tanks
     */
    public IonenbasierterAntrieb(String seriennummer, Komponententyp typ, double maxSchubkraft,
                                  double xenonKapazitaet) {
        super(seriennummer, typ, maxSchubkraft);
        this.xenonKapazitaet = xenonKapazitaet;
    }

    /**
     * Liefert die Xenon-Kapazitaet dieses Antriebs.
     *
     * @return das Fassungsvermoegen des Xenon-Tanks
     */
    public double getXenonKapazitaet() {
        return xenonKapazitaet;
    }

    /**
     * Setzt die Xenon-Kapazitaet, z.B. nach dem Nachfuellen.
     *
     * @param xenonKapazitaet das neue Fassungsvermoegen
     */
    public void setXenonKapazitaet(double xenonKapazitaet) {
        this.xenonKapazitaet = xenonKapazitaet;
    }
}
