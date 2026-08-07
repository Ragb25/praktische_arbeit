package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model-Klasse: verwaltet den Bestand an Kunden und Mitarbeitern.
 * Implementiert IPersonenVerwaltung.
 * Die ArrayList ist eine Vereinfachung gegenueber dem Datenbankserver
 * aus der Architektur; die Schnittstelle IPersonenVerwaltung bleibt
 * davon unberuehrt.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class PersonenVerwaltung implements IPersonenVerwaltung {

    private final List<Kunde> kunden;
    private final List<Mitarbeiter> mitarbeiter;

    /**
     * Initialisiert leere Bestandslisten, da die Verwaltung ohne
     * Vorgaben von aussen startet.
     */
    public PersonenVerwaltung() {
        this.kunden = new ArrayList<>();
        this.mitarbeiter = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Kunde kundeLaden(String kundennummer) {
        if (kundennummer == null) {
            return null;
        }
        for (Kunde kunde : kunden) {
            if (kunde.getKundennummer().equals(kundennummer)) {
                return kunde;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void kundeSpeichern(Kunde kunde) {
        if (kunde == null) {
            throw new IllegalArgumentException("kunde darf nicht null sein");
        }
        if (!kunden.contains(kunde)) {
            kunden.add(kunde);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Mitarbeiter mitarbeiterLaden(String name) {
        if (name == null) {
            return null;
        }
        for (Mitarbeiter einMitarbeiter : mitarbeiter) {
            if (einMitarbeiter.getName().equals(name)) {
                return einMitarbeiter;
            }
        }
        return null;
    }

    /**
     * Nimmt einen Mitarbeiter in den Bestand auf. Nicht Teil des
     * Interface, nur fuer die Einstiegsklasse gedacht.
     *
     * @param mitarbeiter der aufzunehmende Mitarbeiter
     * @throws IllegalArgumentException wenn mitarbeiter null ist
     */
    public void mitarbeiterSpeichern(Mitarbeiter mitarbeiter) {
        if (mitarbeiter == null) {
            throw new IllegalArgumentException("mitarbeiter darf nicht null sein");
        }
        if (!this.mitarbeiter.contains(mitarbeiter)) {
            this.mitarbeiter.add(mitarbeiter);
        }
    }

    /**
     * Liefert den gesamten Kundenbestand. Nicht Teil des Interface,
     * nur fuer die Einstiegsklasse gedacht.
     *
     * @return eine schreibgeschuetzte Sicht auf den Kundenbestand
     */
    public List<Kunde> getKunden() {
        return Collections.unmodifiableList(kunden);
    }

    /**
     * Liefert den gesamten Mitarbeiterbestand. Nicht Teil des
     * Interface, nur fuer die Einstiegsklasse gedacht.
     *
     * @return eine schreibgeschuetzte Sicht auf den Mitarbeiterbestand
     */
    public List<Mitarbeiter> getMitarbeiter() {
        return Collections.unmodifiableList(mitarbeiter);
    }
}
