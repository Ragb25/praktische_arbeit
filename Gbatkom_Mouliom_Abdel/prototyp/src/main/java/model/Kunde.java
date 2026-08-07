package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Model-Klasse: ein Kunde mit seinen beauftragten Reparaturen und
 * optional einer Bonuskarte.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Kunde {

    private String kundennummer;
    private String name;
    private int bonuspunkteSumme;
    private final List<Reparatur> reparaturen;
    private Bonuskarte bonuskarte;

    /**
     * Legt die Kundenidentitaet an und initialisiert Punktestand und
     * Auftragshistorie mit Startwerten.
     *
     * @param kundennummer eindeutige Kennung des Kunden
     * @param name Name des Kunden
     */
    public Kunde(String kundennummer, String name) {
        this.kundennummer = kundennummer;
        this.name = name;
        this.bonuspunkteSumme = 0;
        this.reparaturen = new ArrayList<>();
        this.bonuskarte = null;
    }

    /**
     * Liefert die eindeutige Kennung dieses Kunden.
     *
     * @return die Kundennummer
     */
    public String getKundennummer() {
        return kundennummer;
    }

    /**
     * Liefert den Namen dieses Kunden.
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen dieses Kunden.
     *
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Liefert den aktuellen Bonuspunktestand dieses Kunden.
     *
     * @return die Bonuspunktesumme
     */
    public int getBonuspunkteSumme() {
        return bonuspunkteSumme;
    }

    /**
     * Setzt den Bonuspunktestand dieses Kunden.
     *
     * @param bonuspunkteSumme die neue Bonuspunktesumme
     */
    public void setBonuspunkteSumme(int bonuspunkteSumme) {
        this.bonuspunkteSumme = bonuspunkteSumme;
    }

    /**
     * Verankert eine Reparatur in der Auftragshistorie dieses Kunden.
     *
     * @param reparatur die zu verankernde Reparatur
     */
    public void addBeauftragteReparatur(Reparatur reparatur) {
        reparaturen.add(reparatur);
    }

    /**
     * Liefert die von diesem Kunden beauftragten Reparaturen.
     *
     * @return eine schreibgeschuetzte Sicht auf die Auftragshistorie
     */
    public List<Reparatur> getReparaturen() {
        return Collections.unmodifiableList(reparaturen);
    }

    /**
     * Liefert die Bonuskarte dieses Kunden.
     *
     * @return die Bonuskarte, oder null wenn der Kunde keine besitzt
     */
    public Bonuskarte getBonuskarte() {
        return bonuskarte;
    }

    /**
     * Setzt oder entzieht die Bonuskarte dieses Kunden.
     *
     * @param bonuskarte die neue Bonuskarte, oder null um sie zu entziehen
     */
    public void setBonuskarte(Bonuskarte bonuskarte) {
        this.bonuskarte = bonuskarte;
    }
}
