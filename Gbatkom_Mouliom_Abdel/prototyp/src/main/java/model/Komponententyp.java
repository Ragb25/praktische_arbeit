package model;

/**
 * Model-Klasse: beschreibt einen Bautyp einer Systemkomponente
 * (Katalogeintrag mit Name, Preis und Energiebedarf).
 * Kennt die konkreten Exemplare (Systemkomponente) nicht.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Komponententyp {

    private final String katalognummer;
    private String name;
    private double preis;
    private double energiebedarf;

    /**
     * Legt die unveraenderliche Katalognummer und die Typdaten fest.
     *
     * @param katalognummer eindeutige Kennung des Typs im Katalog
     * @param name Bezeichnung des Typs
     * @param preis Verkaufspreis des Typs
     * @param energiebedarf Energiebedarf des Typs
     */
    public Komponententyp(String katalognummer, String name, double preis, double energiebedarf) {
        this.katalognummer = katalognummer;
        this.name = name;
        this.preis = preis;
        this.energiebedarf = energiebedarf;
    }

    /**
     * Liefert die unveraenderliche Identitaet des Typs im Katalog.
     *
     * @return die Katalognummer
     */
    public String getKatalognummer() {
        return katalognummer;
    }

    /**
     * Liefert die Bezeichnung dieses Typs.
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt die Bezeichnung dieses Typs.
     *
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Liefert den Verkaufspreis dieses Typs.
     *
     * @return der Preis
     */
    public double getPreis() {
        return preis;
    }

    /**
     * Setzt den Verkaufspreis dieses Typs.
     *
     * @param preis der neue Preis
     */
    public void setPreis(double preis) {
        this.preis = preis;
    }

    /**
     * Liefert den Energiebedarf dieses Typs.
     *
     * @return der Energiebedarf
     */
    public double getEnergiebedarf() {
        return energiebedarf;
    }

    /**
     * Setzt den Energiebedarf dieses Typs.
     *
     * @param energiebedarf der neue Energiebedarf
     */
    public void setEnergiebedarf(double energiebedarf) {
        this.energiebedarf = energiebedarf;
    }
}
