package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model-Klasse: Rumpf eines Raumschiffs, ein Exemplar von Systemkomponente.
 * Erzeugt Energie und versorgt damit Antriebe und Forschungsmodule.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Rumpf extends Systemkomponente {

    private double energieerzeugung;
    private boolean erfordertSpezialAntrieb;

    private final List<Antrieb> versorgteAntriebe;
    private final List<Forschungsmodul> versorgteForschungsmodule;

    /**
     * Legt die Komponentendaten sowie Energieerzeugung und Spezialantrieb-
     * Anforderung fest und initialisiert die Versorgungslisten leer.
     *
     * @param seriennummer eindeutige Kennung des Exemplars
     * @param typ Katalogtyp, dem dieses Exemplar zugeordnet ist
     * @param energieerzeugung von diesem Rumpf erzeugte Energiemenge
     * @param erfordertSpezialAntrieb ob dieser Rumpf einen Spezialantrieb voraussetzt
     */
    public Rumpf(String seriennummer, Komponententyp typ, double energieerzeugung, boolean erfordertSpezialAntrieb) {
        super(seriennummer, typ);
        this.energieerzeugung = energieerzeugung;
        this.erfordertSpezialAntrieb = erfordertSpezialAntrieb;
        this.versorgteAntriebe = new ArrayList<>();
        this.versorgteForschungsmodule = new ArrayList<>();
    }

    /**
     * Liefert die von diesem Rumpf erzeugte Energiemenge.
     *
     * @return die Energieerzeugung
     */
    public double getEnergieerzeugung() {
        return energieerzeugung;
    }

    /**
     * Setzt die Energieerzeugung, z.B. nach einem Umbau.
     *
     * @param energieerzeugung die neue Energieerzeugung
     */
    public void setEnergieerzeugung(double energieerzeugung) {
        this.energieerzeugung = energieerzeugung;
    }

    /**
     * Prueft, ob dieser Rumpf einen Spezialantrieb voraussetzt.
     *
     * @return true, wenn ein Spezialantrieb erforderlich ist
     */
    public boolean isErfordertSpezialAntrieb() {
        return erfordertSpezialAntrieb;
    }

    /**
     * Setzt, ob dieser Rumpf einen Spezialantrieb voraussetzt.
     *
     * @param erfordertSpezialAntrieb die neue Anforderung
     */
    public void setErfordertSpezialAntrieb(boolean erfordertSpezialAntrieb) {
        this.erfordertSpezialAntrieb = erfordertSpezialAntrieb;
    }

    /**
     * Traegt einen Antrieb in die Liste der von diesem Rumpf versorgten
     * Antriebe ein.
     *
     * @param antrieb der zu versorgende Antrieb
     */
    public void versorgeMitEnergie(Antrieb antrieb) {
        versorgteAntriebe.add(antrieb);
    }

    /**
     * Traegt ein Forschungsmodul in die Liste der von diesem Rumpf
     * versorgten Forschungsmodule ein.
     *
     * @param forschungsmodul das zu versorgende Forschungsmodul
     */
    public void versorgeMitEnergie(Forschungsmodul forschungsmodul) {
        versorgteForschungsmodule.add(forschungsmodul);
    }

    /**
     * Liefert die von diesem Rumpf versorgten Antriebe.
     *
     * @return eine schreibgeschuetzte Sicht auf die versorgten Antriebe
     */
    public List<Antrieb> getVersorgteAntriebe() {
        return Collections.unmodifiableList(versorgteAntriebe);
    }

    /**
     * Liefert die von diesem Rumpf versorgten Forschungsmodule.
     *
     * @return eine schreibgeschuetzte Sicht auf die versorgten Forschungsmodule
     */
    public List<Forschungsmodul> getVersorgteForschungsmodule() {
        return Collections.unmodifiableList(versorgteForschungsmodule);
    }
}
