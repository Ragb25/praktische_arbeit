package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model-Klasse: eine Filiale mit ihrem Personal, ihrem Katalog und den
 * dort erfassten Reparaturauftraegen.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Laden {

    private String filialID;
    private String name;
    private String adresse;

    /**
     * Aggregation: ein Mitarbeiter gehoert dem Laden, existiert aber
     * auch unabhaengig von ihm weiter (Multiplizitaet 1..*).
     */
    private final List<Mitarbeiter> mitarbeiter;
    private final List<Reparatur> reparaturen;
    private final Katalog katalog;

    /**
     * Legt die Filialdaten fest und erzwingt beim Anlegen mindestens
     * einen Mitarbeiter und einen Katalog, da ein Laden ohne beides
     * nicht arbeitsfaehig ist.
     *
     * @param filialID eindeutige Kennung der Filiale
     * @param name Name der Filiale
     * @param adresse Standort der Filiale
     * @param ersterMitarbeiter erster beschaeftigter Mitarbeiter
     * @param katalog Preis- und Typkatalog dieser Filiale
     * @throws IllegalArgumentException wenn ersterMitarbeiter oder katalog null ist
     */
    public Laden(String filialID, String name, String adresse, Mitarbeiter ersterMitarbeiter, Katalog katalog) {
        if (ersterMitarbeiter == null) {
            throw new IllegalArgumentException("ersterMitarbeiter darf nicht null sein");
        }
        if (katalog == null) {
            throw new IllegalArgumentException("katalog darf nicht null sein");
        }
        this.filialID = filialID;
        this.name = name;
        this.adresse = adresse;
        this.mitarbeiter = new ArrayList<>();
        this.mitarbeiter.add(ersterMitarbeiter);
        this.reparaturen = new ArrayList<>();
        this.katalog = katalog;
    }

    /**
     * Liefert die eindeutige Kennung dieser Filiale.
     *
     * @return die Filial-ID
     */
    public String getFilialID() {
        return filialID;
    }

    /**
     * Liefert den Namen dieser Filiale.
     *
     * @return der Name
     */
    public String getName() {
        return name;
    }

    /**
     * Setzt den Namen dieser Filiale.
     *
     * @param name der neue Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Liefert den Standort dieser Filiale.
     *
     * @return die Adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Setzt den Standort dieser Filiale, z.B. bei einem Umzug.
     *
     * @param adresse die neue Adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     * Nimmt einen bereits existierenden Mitarbeiter in diese Filiale auf
     * (Aggregation, der Mitarbeiter wird dabei nicht erzeugt).
     *
     * @param mitarbeiter der aufzunehmende Mitarbeiter
     * @throws IllegalArgumentException wenn mitarbeiter null ist
     */
    public void beschaeftige(Mitarbeiter mitarbeiter) {
        if (mitarbeiter == null) {
            throw new IllegalArgumentException("mitarbeiter darf nicht null sein");
        }
        this.mitarbeiter.add(mitarbeiter);
    }

    /**
     * Entfernt einen Mitarbeiter aus dem Laden (Aggregation: der
     * Mitarbeiter selbst bleibt bestehen, nur die Zuordnung entfaellt).
     * Verhindert, dass der Laden dadurch personallos wuerde
     * (Multiplizitaet 1..*).
     *
     * @param mitarbeiter der zu entfernende Mitarbeiter
     * @throws IllegalStateException wenn dies der letzte Mitarbeiter der Filiale ist
     */
    public void removeMitarbeiter(Mitarbeiter mitarbeiter) {
        if (this.mitarbeiter.size() <= 1) {
            throw new IllegalStateException("Laden braucht mindestens einen Mitarbeiter");
        }
        this.mitarbeiter.remove(mitarbeiter);
    }

    /**
     * Liefert alle in dieser Filiale beschaeftigten Mitarbeiter.
     *
     * @return eine schreibgeschuetzte Sicht auf die Mitarbeiter
     */
    public List<Mitarbeiter> getMitarbeiter() {
        return Collections.unmodifiableList(mitarbeiter);
    }

    /**
     * Erfasst einen neuen Reparaturauftrag in dieser Filiale.
     *
     * @param reparatur der zu erfassende Auftrag
     */
    public void addReparatur(Reparatur reparatur) {
        reparaturen.add(reparatur);
    }

    /**
     * Liefert die Auftragshistorie dieser Filiale.
     *
     * @return eine schreibgeschuetzte Sicht auf die Reparaturauftraege
     */
    public List<Reparatur> getReparaturen() {
        return Collections.unmodifiableList(reparaturen);
    }

    /**
     * Liefert den Preis- und Typkatalog dieser Filiale.
     *
     * @return der Katalog, nie null
     */
    public Katalog getKatalog() {
        return katalog;
    }
}
