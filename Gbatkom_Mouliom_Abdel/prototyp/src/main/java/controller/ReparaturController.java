package controller;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import model.IPersonenVerwaltung;
import model.IRaumschiffVerwaltung;
import model.IReparaturVerwaltung;
import model.Kunde;
import model.Raumschiff;
import model.Reparatur;
import model.Reparaturstatus;
import model.Systemkomponente;
import view.IAuftragsBeobachter;

/**
 * Controller-Klasse: steuert den Anwendungsfall "Reparatur beauftragen".
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class ReparaturController implements IReparaturSteuerung {

    private final IRaumschiffVerwaltung raumschiffVerwaltung;
    private final IReparaturVerwaltung reparaturVerwaltung;
    private final IPersonenVerwaltung personenVerwaltung;
    private final List<IAuftragsBeobachter> beobachter;
    private int letzteNummer = 0;

    /**
     * Injiziert die drei Fachverwaltungen über ihre Interfaces; ohne
     * sie kann der Anwendungsfall nicht ablaufen.
     *
     * @param raumschiffVerwaltung
     * @param reparaturVerwaltung
     * @param personenVerwaltung
     * @throws IllegalArgumentException
     */
    public ReparaturController(IRaumschiffVerwaltung raumschiffVerwaltung, IReparaturVerwaltung reparaturVerwaltung,
                                IPersonenVerwaltung personenVerwaltung) {
        if (raumschiffVerwaltung == null) {
            throw new IllegalArgumentException("raumschiffVerwaltung darf nicht null sein");
        }
        if (reparaturVerwaltung == null) {
            throw new IllegalArgumentException("reparaturVerwaltung darf nicht null sein");
        }
        if (personenVerwaltung == null) {
            throw new IllegalArgumentException("personenVerwaltung darf nicht null sein");
        }
        this.raumschiffVerwaltung = raumschiffVerwaltung;
        this.reparaturVerwaltung = reparaturVerwaltung;
        this.personenVerwaltung = personenVerwaltung;
        this.beobachter = new ArrayList<>();
    }

    /**
     * Lädt Kunde und Raumschiff, erzeugt die Reparatur mit Status
     * ANGELEGT, verankert sie beim Kunden, speichert sie und
     * benachrichtigt danach die angemeldeten Beobachter.
     */
    @Override
    public String auftragAnlegen(String kundennummer, String raumschiffId) {
        Kunde kunde = personenVerwaltung.kundeLaden(kundennummer);
        if (kunde == null) {
            throw new IllegalArgumentException("Kunde nicht gefunden: " + kundennummer);
        }
        Raumschiff raumschiff = raumschiffVerwaltung.raumschiffLaden(raumschiffId);
        if (raumschiff == null) {
            throw new IllegalArgumentException("Raumschiff nicht gefunden: " + raumschiffId);
        }
        String auftragsnummer = nummerErzeugen();
        Reparatur reparatur = new Reparatur(auftragsnummer, raumschiff, Reparaturstatus.ANGELEGT);
        kunde.addBeauftragteReparatur(reparatur);
        reparaturVerwaltung.reparaturSpeichern(reparatur);
        personenVerwaltung.kundeSpeichern(kunde);
        for (IAuftragsBeobachter einBeobachter : beobachter) {
            einBeobachter.neuerAuftrag(auftragsnummer);
        }
        return auftragsnummer;
    }

    /**
     *
     */
    @Override
    public void beobachterAnmelden(IAuftragsBeobachter beobachter) {
        if (beobachter == null) {
            throw new IllegalArgumentException("beobachter darf nicht null sein");
        }
        if (!this.beobachter.contains(beobachter)) {
            this.beobachter.add(beobachter);
        }
    }

    /**
     * Nimmt die Kenntnummer von defekte Komponente und nimmt diese entgegen, falls vohandend,
     * sonst werft sich eine Exeption
     */
    @Override
    public void defekteKomponenteMelden(String auftragsnummer, String seriennummer) {
        if (auftragsnummer == null || auftragsnummer.isEmpty()) {
            throw new IllegalArgumentException("auftragsnummer darf nicht leer sein");
        }
        if (seriennummer == null || seriennummer.isEmpty()) {
            throw new IllegalArgumentException("seriennummer darf nicht leer sein");
        }
        Reparatur reparatur = reparaturVerwaltung.reparaturLaden(auftragsnummer);
        if (reparatur == null) {
            throw new IllegalArgumentException("Auftrag nicht gefunden: " + auftragsnummer);
        }
        String raumschiffId = reparatur.getRaumschiff().getRaumschiffID();
        Systemkomponente komponente = raumschiffVerwaltung.komponenteLaden(raumschiffId, seriennummer);
        if (komponente == null) {
            throw new IllegalArgumentException("Komponente nicht gefunden: " + seriennummer);
        }
        if (reparatur.getDefekteKomponenten().contains(komponente)) {
            return;
        }
        reparatur.erfasseDefekt(komponente);
        reparaturVerwaltung.reparaturSpeichern(reparatur);
        for (IAuftragsBeobachter einBeobachter : beobachter) {
            einBeobachter.auftragGeaendert(auftragsnummer);
        }
    }

    /**
     * Erzeugt eine fortlaufende Auftragsnummer im Format "R-Jahr-0001".
     * Bewusste Vereinfachung gegenueber der Architektur: ein einfacher
     * Zaehler statt einer Nummernvergabe durch die Datenbank.
     *
     * @return die neu erzeugte Auftragsnummer
     */
    private String nummerErzeugen() {
        letzteNummer++;
        return String.format("R-%d-%04d", Year.now().getValue(), letzteNummer);
    }
}
