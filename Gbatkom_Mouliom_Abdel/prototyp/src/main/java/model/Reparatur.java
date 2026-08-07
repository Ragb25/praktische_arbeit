package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model-Klasse: ein Reparaturauftrag fuer ein Raumschiff mit den dabei
 * erfassten defekten Komponenten.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Reparatur {

    private String reparaturID;
    private final Raumschiff raumschiff;
    private Reparaturstatus status;
    private final List<Systemkomponente> defekteKomponenten;

    /**
     * Verknuepft die Reparatur unveraenderlich mit ihrem Raumschiff und
     * legt Auftragskennung und Startstatus fest.
     *
     * @param reparaturID eindeutige Kennung des Auftrags
     * @param raumschiff Raumschiff, zu dem dieser Auftrag gehoert
     * @param status Bearbeitungsstand bei Anlage des Auftrags
     * @throws IllegalArgumentException wenn raumschiff null ist
     */
    public Reparatur(String reparaturID, Raumschiff raumschiff, Reparaturstatus status) {
        if (raumschiff == null) {
            throw new IllegalArgumentException("raumschiff darf nicht null sein");
        }
        this.reparaturID = reparaturID;
        this.raumschiff = raumschiff;
        this.status = status;
        this.defekteKomponenten = new ArrayList<>();
    }

    /**
     * Liefert die eindeutige Kennung dieses Auftrags.
     *
     * @return die Auftragskennung
     */
    public String getReparaturID() {
        return reparaturID;
    }

    /**
     * Liefert das Raumschiff, zu dem dieser Auftrag gehoert.
     *
     * @return das Raumschiff, nie null
     */
    public Raumschiff getRaumschiff() {
        return raumschiff;
    }

    /**
     * Liefert den Bearbeitungsstand dieses Auftrags.
     *
     * @return der aktuelle Status
     */
    public Reparaturstatus getStatus() {
        return status;
    }

    /**
     * Setzt den Bearbeitungsstand dieses Auftrags.
     *
     * @param status der neue Status
     */
    public void setStatus(Reparaturstatus status) {
        this.status = status;
    }

    /**
     * Erfasst eine Systemkomponente als defekt im Rahmen dieses Auftrags.
     *
     * @param systemkomponente die defekte Komponente
     */
    public void erfasseDefekt(Systemkomponente systemkomponente) {
        defekteKomponenten.add(systemkomponente);
    }

    /**
     * Liefert die im Rahmen dieses Auftrags erfassten defekten Komponenten.
     *
     * @return eine schreibgeschuetzte Sicht auf die defekten Komponenten
     */
    public List<Systemkomponente> getDefekteKomponenten() {
        return Collections.unmodifiableList(defekteKomponenten);
    }
}
