package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model-Klasse: verwaltet den Bestand an Reparaturauftraegen eines
 * Ladens. Implementiert IReparaturVerwaltung.
 * Die ArrayList ist eine Vereinfachung gegenueber dem Datenbankserver
 * aus der Architektur; die Schnittstelle IReparaturVerwaltung bleibt
 * davon unberuehrt.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class ReparaturVerwaltung implements model.IReparaturVerwaltung {

    private final List<model.Reparatur> reparaturen;
    private final model.Laden laden;

    /**
     * Bindet die Verwaltung an ihren Laden, ohne den kein
     * Reparaturauftrag zugeordnet werden kann.
     *
     * @param laden Filiale, deren Auftraege diese Verwaltung fuehrt
     * @throws IllegalArgumentException wenn laden null ist
     */
    public ReparaturVerwaltung(model.Laden laden) {
        if (laden == null) {
            throw new IllegalArgumentException("laden darf nicht null sein");
        }
        this.laden = laden;
        this.reparaturen = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public model.Reparatur reparaturLaden(String auftragsnummer) {
        if (auftragsnummer == null) {
            return null;
        }
        for (model.Reparatur reparatur : reparaturen) {
            if (reparatur.getReparaturID().equals(auftragsnummer)) {
                return reparatur;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * Nimmt die Reparatur zusaetzlich beim zugeordneten Laden auf.
     */
    @Override
    public void reparaturSpeichern(model.Reparatur reparatur) {
        if (reparatur == null) {
            throw new IllegalArgumentException("reparatur darf nicht null sein");
        }
        if (!reparaturen.contains(reparatur)) {
            reparaturen.add(reparatur);
            laden.addReparatur(reparatur);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<model.Reparatur> offeneReparaturen() {
        List<model.Reparatur> offene = new ArrayList<>();
        for (model.Reparatur reparatur : reparaturen) {
            if (reparatur.getStatus() != model.Reparaturstatus.ABGESCHLOSSEN) {
                offene.add(reparatur);
            }
        }
        return offene;
    }

    /**
     * Liefert den zugeordneten Laden. Nicht Teil des Interface, nur
     * fuer die Einstiegsklasse gedacht.
     *
     * @return der Laden, nie null
     */
    public model.Laden getLaden() {
        return laden;
    }

    /**
     * Liefert den gesamten Reparaturbestand. Nicht Teil des Interface,
     * nur fuer die Einstiegsklasse gedacht.
     *
     * @return eine schreibgeschuetzte Sicht auf den Reparaturbestand
     */
    public List<model.Reparatur> getReparaturen() {
        return Collections.unmodifiableList(reparaturen);
    }
}
