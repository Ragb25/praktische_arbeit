package model;

import java.util.List;

/**
 * Vertrag der Model-Schicht fuer die Verwaltung der Reparaturauftraege.
 * Controller und View kennen ausschliesslich diese Schnittstelle, nie
 * eine konkrete Verwaltungsklasse.
 */
public interface IReparaturVerwaltung {

    /**
     * Sucht eine Reparatur anhand ihrer Auftragsnummer.
     *
     * @param auftragsnummer Kennung der gesuchten Reparatur
     * @return die gefundene Reparatur, oder null wenn keine mit dieser
     *         Kennung existiert oder auftragsnummer null ist
     */
    public Reparatur reparaturLaden(String auftragsnummer);

    /**
     * Nimmt eine Reparatur in den Bestand auf. Ist sie bereits enthalten,
     * geschieht nichts.
     *
     * @param reparatur die aufzunehmende Reparatur
     * @throws IllegalArgumentException wenn reparatur null ist
     */
    public void reparaturSpeichern(Reparatur reparatur);

    /**
     * Liefert alle Reparaturen, die noch nicht abgeschlossen sind.
     *
     * @return eine neue Liste der offenen Reparaturen, nie null,
     *         aber ggf. leer
     */
    public List<Reparatur> offeneReparaturen();
}
