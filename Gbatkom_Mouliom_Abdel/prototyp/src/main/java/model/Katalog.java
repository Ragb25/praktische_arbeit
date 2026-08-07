package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model-Klasse: Katalog aus mindestens einem Komponententyp
 * (Komposition, 1..*).
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Katalog {

    private String bezeichnung;
    private final List<Komponententyp> typen;

    /**
     * Legt die Bezeichnung fest und erzwingt beim Anlegen die
     * Multiplizitaet 1..*, indem der erste Typ zwingend uebergeben wird.
     *
     * @param bezeichnung Name des Katalogs
     * @param ersterTyp erster Komponententyp des Katalogs
     * @throws IllegalArgumentException wenn ersterTyp null ist
     */
    public Katalog(String bezeichnung, Komponententyp ersterTyp) {
        if (ersterTyp == null) {
            throw new IllegalArgumentException("ersterTyp darf nicht null sein");
        }
        this.bezeichnung = bezeichnung;
        this.typen = new ArrayList<>();
        this.typen.add(ersterTyp);
    }

    /**
     * Liefert die Bezeichnung dieses Katalogs.
     *
     * @return die Bezeichnung
     */
    public String getBezeichnung() {
        return bezeichnung;
    }

    /**
     * Setzt die Bezeichnung dieses Katalogs.
     *
     * @param bezeichnung die neue Bezeichnung
     */
    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    /**
     * Liefert alle Komponententypen dieses Katalogs.
     *
     * @return eine schreibgeschuetzte Sicht auf die Komponententypen
     */
    public List<Komponententyp> getTypen() {
        return Collections.unmodifiableList(typen);
    }

    /**
     * Nimmt einen weiteren Komponententyp in den Katalog auf.
     *
     * @param typ der aufzunehmende Komponententyp
     * @throws IllegalArgumentException wenn typ null ist
     */
    public void addTyp(Komponententyp typ) {
        if (typ == null) {
            throw new IllegalArgumentException("typ darf nicht null sein");
        }
        typen.add(typ);
    }

    /**
     * Entfernt einen Komponententyp aus dem Katalog. Verhindert, dass der
     * Katalog dadurch leer wuerde (Multiplizitaet 1..*).
     *
     * @param typ der zu entfernende Komponententyp
     * @throws IllegalStateException wenn dies der letzte Typ im Katalog ist
     */
    public void removeTyp(Komponententyp typ) {
        if (typen.size() <= 1) {
            throw new IllegalStateException("Katalog braucht mindestens einen Komponententyp");
        }
        typen.remove(typ);
    }

    /**
     * Sucht einen Komponententyp anhand seiner Katalognummer.
     *
     * @param katalognummer Kennung des gesuchten Typs
     * @return der gefundene Komponententyp, oder null wenn keiner mit
     *         dieser Kennung existiert
     */
    public Komponententyp typSuchen(String katalognummer) {
        for (Komponententyp typ : typen) {
            if (typ.getKatalognummer().equals(katalognummer)) {
                return typ;
            }
        }
        return null;
    }
}
