package model;

import java.util.Objects;

/**
 * Model-Klasse: abstrakte Basis fuer alle Exemplare einer verbauten
 * Systemkomponente. Traegt nur die Identitaet (Seriennummer) und den
 * Verweis auf ihren Katalogtyp; die technischen Daten liegen in den
 * konkreten Unterklassen.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public abstract class Systemkomponente {

    private final String seriennummer;
    private final Komponententyp typ;

    /**
     * Legt Seriennummer und Katalogtyp unveraenderlich fest.
     *
     * @param seriennummer eindeutige Kennung des Exemplars
     * @param typ Katalogtyp, dem dieses Exemplar zugeordnet ist
     * @throws IllegalArgumentException wenn seriennummer oder typ null ist
     */
    protected Systemkomponente(String seriennummer, Komponententyp typ) {
        if (seriennummer == null) {
            throw new IllegalArgumentException("seriennummer darf nicht null sein");
        }
        if (typ == null) {
            throw new IllegalArgumentException("typ darf nicht null sein");
        }
        this.seriennummer = seriennummer;
        this.typ = typ;
    }

    /**
     * Liefert die eindeutige Seriennummer dieses Exemplars.
     *
     * @return die Seriennummer, nie null
     */
    public String getSeriennummer() {
        return seriennummer;
    }

    /**
     * Liefert den Katalogtyp (Name, Preis, Energiebedarf) dieses Exemplars.
     *
     * @return der Katalogtyp, nie null
     */
    public Komponententyp getTyp() {
        return typ;
    }

    /**
     * Vergleicht zwei Systemkomponenten ueber ihre Seriennummer, da diese
     * die fachliche Identitaet traegt.
     *
     * @param o das zu vergleichende Objekt
     * @return true, wenn o eine Systemkomponente mit gleicher Seriennummer ist
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Systemkomponente)) return false;
        Systemkomponente that = (Systemkomponente) o;
        return Objects.equals(seriennummer, that.seriennummer);
    }

    /**
     * Bildet den Hashcode konsistent zu equals() ueber die Seriennummer.
     *
     * @return der Hashcode dieses Exemplars
     */
    @Override
    public int hashCode() {
        return Objects.hash(seriennummer);
    }

    /**
     * Liefert eine lesbare Kurzbeschreibung fuer Debugging und Logging.
     *
     * @return eine Textdarstellung mit Klassenname, Seriennummer und Typ
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{seriennummer='" + seriennummer + "', typ=" + typ + '}';
    }
}
