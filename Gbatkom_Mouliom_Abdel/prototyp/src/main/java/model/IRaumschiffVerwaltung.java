package model;

/**
 * Vertrag der Model-Schicht fuer die Verwaltung des Raumschiffbestands.
 * Controller und View kennen ausschliesslich diese Schnittstelle, nie
 * eine konkrete Verwaltungsklasse.
 */
public interface IRaumschiffVerwaltung {

    /**
     * Sucht ein Raumschiff anhand seiner Registrierungsnummer.
     *
     * @param registrierungsnummer Kennung des gesuchten Raumschiffs
     * @return das gefundene Raumschiff, oder null wenn keines mit dieser
     *         Kennung existiert oder registrierungsnummer null ist
     */
    public Raumschiff raumschiffLaden(String registrierungsnummer);

    /**
     * Nimmt ein Raumschiff in den Bestand auf. Ist es bereits enthalten,
     * geschieht nichts.
     *
     * @param raumschiff das aufzunehmende Raumschiff
     * @throws IllegalArgumentException wenn raumschiff null ist
     */
    public void raumschiffSpeichern(Raumschiff raumschiff);

    /**
     * Sucht eine Systemkomponente ueber ihr Raumschiff und ihre
     * Seriennummer.
     *
     * @param raumschiffID Kennung des Raumschiffs
     * @param seriennummer Kennung der gesuchten Komponente
     * @return die gefundene Systemkomponente, oder null wenn das
     *         Raumschiff oder die Komponente nicht gefunden wird
     */
    public Systemkomponente komponenteLaden(String raumschiffID, String seriennummer);

}
