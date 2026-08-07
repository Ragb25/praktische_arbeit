package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model-Klasse: ein Mitarbeiter mit den von ihm durchgefuehrten
 * Reparaturen und verbuchten Bonuskarten.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Mitarbeiter {

    private String name;
    private double umsatz;
    private final List<Reparatur> durchgefuehrteReparaturen;
    private final List<Bonuskarte> verbuchteBonuskarten;

    /**
     * Legt die Mitarbeiteridentitaet an und initialisiert die
     * Historienlisten mit Startwerten.
     *
     * @param name Name des Mitarbeiters
     * @param umsatz bisher erzielter Umsatz
     */
    public Mitarbeiter(String name, double umsatz) {
        this.name = name;
        this.umsatz = umsatz;
        this.durchgefuehrteReparaturen = new ArrayList<>();
        this.verbuchteBonuskarten = new ArrayList<>();
    }

    /**
     * Liefert den Namen dieses Mitarbeiters.
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen dieses Mitarbeiters.
     *
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Liefert den bisher erzielten Umsatz dieses Mitarbeiters.
     *
     * @return der Umsatz
     */
    public double getUmsatz() {
        return umsatz;
    }

    /**
     * Setzt den erzielten Umsatz dieses Mitarbeiters.
     *
     * @param umsatz der neue Umsatz
     */
    public void setUmsatz(double umsatz) {
        this.umsatz = umsatz;
    }

    /**
     * Haelt fest, dass dieser Mitarbeiter eine Reparatur durchgefuehrt hat.
     *
     * @param reparatur die durchgefuehrte Reparatur
     */
    public void fuehreReparaturAus(Reparatur reparatur) {
        durchgefuehrteReparaturen.add(reparatur);
    }

    /**
     * Liefert die von diesem Mitarbeiter durchgefuehrten Reparaturen.
     *
     * @return eine schreibgeschuetzte Sicht auf die Reparaturhistorie
     */
    public List<Reparatur> getDurchgefuehrteReparaturen() {
        return Collections.unmodifiableList(durchgefuehrteReparaturen);
    }

    /**
     * Schreibt dem Karteninhaber Bonuspunkte gut und dokumentiert die
     * Verbuchung bei diesem Mitarbeiter.
     *
     * @param bonuskarte die Karte, auf die Punkte verbucht werden
     * @param punkte Anzahl der gutzuschreibenden Punkte
     */
    public void verbuchePunkte(Bonuskarte bonuskarte, int punkte) {
        Kunde inhaber = bonuskarte.getInhaber();
        inhaber.setBonuspunkteSumme(inhaber.getBonuspunkteSumme() + punkte);
        verbuchteBonuskarten.add(bonuskarte);
    }

    /**
     * Liefert die von diesem Mitarbeiter verbuchten Bonuskarten.
     *
     * @return eine schreibgeschuetzte Sicht auf die verbuchten Bonuskarten
     */
    public List<Bonuskarte> getVerbuchteBonuskarten() {
        return Collections.unmodifiableList(verbuchteBonuskarten);
    }
}
