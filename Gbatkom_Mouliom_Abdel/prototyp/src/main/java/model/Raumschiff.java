package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model-Klasse: ein Raumschiff mit seinen verbauten Systemkomponenten.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Raumschiff {

    private String raumschiffID;
    private String modellName;

    /**
     * Komposition: eine Systemkomponente gehoert genau einem Raumschiff
     * und stirbt mit ihm (Multiplizitaet 1..*).
     */
    private final List<Systemkomponente> systemkomponenten;

    /**
     * Legt Kennung und Modellname fest und erzwingt beim Anlegen die
     * Multiplizitaet 1..*, indem die erste Komponente zwingend uebergeben
     * wird.
     *
     * @param raumschiffID eindeutige Kennung des Raumschiffs
     * @param modellName Modellbezeichnung des Raumschiffs
     * @param ersteSystemkomponente erste verbaute Systemkomponente
     * @throws IllegalArgumentException wenn raumschiffID oder ersteSystemkomponente null ist
     */
    public Raumschiff(String raumschiffID, String modellName, Systemkomponente ersteSystemkomponente) {
        if (raumschiffID == null) {
            throw new IllegalArgumentException("raumschiffID darf nicht null sein");
        }
        if (ersteSystemkomponente == null) {
            throw new IllegalArgumentException("ersteSystemkomponente darf nicht null sein");
        }
        this.raumschiffID = raumschiffID;
        this.modellName = modellName;
        this.systemkomponenten = new ArrayList<>();
        this.systemkomponenten.add(ersteSystemkomponente);
    }

    /**
     * Liefert die eindeutige Kennung dieses Raumschiffs.
     *
     * @return die Raumschiff-ID
     */
    public String getRaumschiffID() {
        return raumschiffID;
    }

    /**
     * Liefert den Modellnamen dieses Raumschiffs.
     *
     * @return der Modellname
     */
    public String getModellName() {
        return modellName;
    }

    /**
     * Setzt den Modellnamen, z.B. nach einem Refit.
     *
     * @param modellName der neue Modellname
     */
    public void setModellName(String modellName) {
        this.modellName = modellName;
    }

    /**
     * Baut eine weitere Systemkomponente in dieses Raumschiff ein.
     *
     * @param systemkomponente die einzubauende Komponente
     * @throws IllegalArgumentException wenn systemkomponente null ist
     */
    public void addSystemkomponente(Systemkomponente systemkomponente) {
        if (systemkomponente == null) {
            throw new IllegalArgumentException("systemkomponente darf nicht null sein");
        }
        systemkomponenten.add(systemkomponente);
    }

    /**
     * Entfernt eine Systemkomponente aus diesem Raumschiff. Verhindert,
     * dass das Raumschiff dadurch komponentenlos wuerde (Multiplizitaet 1..*).
     *
     * @param systemkomponente die zu entfernende Komponente
     * @throws IllegalStateException wenn dies die letzte Komponente ist
     */
    public void removeSystemkomponente(Systemkomponente systemkomponente) {
        if (systemkomponenten.size() <= 1) {
            throw new IllegalStateException("Raumschiff braucht mindestens eine Systemkomponente");
        }
        systemkomponenten.remove(systemkomponente);
    }

    /**
     * Liefert alle in diesem Raumschiff verbauten Systemkomponenten.
     *
     * @return eine schreibgeschuetzte Sicht auf die Systemkomponenten
     */
    public List<Systemkomponente> getSystemkomponenten() {
        return Collections.unmodifiableList(systemkomponenten);
    }
}
