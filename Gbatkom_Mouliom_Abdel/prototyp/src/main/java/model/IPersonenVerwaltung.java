package model;

/**
 * Vertrag der Model-Schicht fuer die Verwaltung von Kunden und
 * Mitarbeitern. Controller und View kennen ausschliesslich diese
 * Schnittstelle, nie eine konkrete Verwaltungsklasse.
 */
public interface IPersonenVerwaltung {

    /**
     * Sucht einen Kunden anhand seiner Kundennummer.
     *
     * @param kundennummer Kennung des gesuchten Kunden
     * @return der gefundene Kunde, oder null wenn keiner mit dieser
     *         Kennung existiert oder kundennummer null ist
     */
    public Kunde kundeLaden(String kundennummer);

    /**
     * Nimmt einen Kunden in den Bestand auf. Ist er bereits enthalten,
     * geschieht nichts.
     *
     * @param kunde der aufzunehmende Kunde
     * @throws IllegalArgumentException wenn kunde null ist
     */
    public void kundeSpeichern(Kunde kunde);

    /**
     * Sucht einen Mitarbeiter anhand seines Namens.
     *
     * @param name Name des gesuchten Mitarbeiters
     * @return der gefundene Mitarbeiter, oder null wenn keiner mit
     *         diesem Namen existiert oder name null ist
     */
    public Mitarbeiter mitarbeiterLaden(String name);
}
