package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model-Klasse: verwaltet den Bestand an Raumschiffen und kennt deren
 * Katalog. Implementiert IRaumschiffVerwaltung.
 * Die ArrayList ist eine Vereinfachung gegenueber dem Datenbankserver
 * aus der Architektur; die Schnittstelle IRaumschiffVerwaltung bleibt
 * davon unberuehrt.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class RaumschiffVerwaltung implements IRaumschiffVerwaltung {

    private final List<Raumschiff> raumschiffe;
    private final Katalog katalog;

    /**
     * Bindet die Verwaltung an ihren Katalog, ohne den Komponenten
     * nicht bepreist werden koennten.
     *
     * @param katalog Preis- und Typkatalog dieser Verwaltung
     * @throws IllegalArgumentException wenn katalog null ist
     */
    public RaumschiffVerwaltung(Katalog katalog) {
        if (katalog == null) {
            throw new IllegalArgumentException("katalog darf nicht null sein");
        }
        this.katalog = katalog;
        this.raumschiffe = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Raumschiff raumschiffLaden(String registrierungsnummer) {
        if (registrierungsnummer == null) {
            return null;
        }
        for (Raumschiff raumschiff : raumschiffe) {
            if (raumschiff.getRaumschiffID().equals(registrierungsnummer)) {
                return raumschiff;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void raumschiffSpeichern(Raumschiff raumschiff) {
        if (raumschiff == null) {
            throw new IllegalArgumentException("raumschiff darf nicht null sein");
        }
        if (!raumschiffe.contains(raumschiff)) {
            raumschiffe.add(raumschiff);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Systemkomponente komponenteLaden(String raumschiffID, String seriennummer) {
        Raumschiff raumschiff = raumschiffLaden(raumschiffID);
        if (raumschiff == null || seriennummer == null) {
            return null;
        }
        for (Systemkomponente komponente : raumschiff.getSystemkomponenten()) {
            if (komponente.getSeriennummer().equals(seriennummer)) {
                return komponente;
            }
        }
        return null;
    }

    /**
     * Liefert den Katalog dieser Verwaltung. Nicht Teil des Interface,
     * nur fuer die Einstiegsklasse gedacht.
     *
     * @return der Katalog, nie null
     */
    public Katalog getKatalog() {
        return katalog;
    }

    /**
     * Liefert den gesamten Raumschiffbestand. Nicht Teil des Interface,
     * nur fuer die Einstiegsklasse gedacht.
     *
     * @return eine schreibgeschuetzte Sicht auf den Raumschiffbestand
     */
    public List<Raumschiff> getRaumschiffe() {
        return Collections.unmodifiableList(raumschiffe);
    }
}
