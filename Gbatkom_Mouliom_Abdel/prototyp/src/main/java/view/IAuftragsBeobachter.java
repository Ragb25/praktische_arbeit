package view;

/**
 * Vertrag fuer View-Komponenten, die sich beim Controller anmelden,
 * um ueber neu angelegte Reparaturauftraege informiert zu werden
 * (Beobachter-Muster).
 *
 * @author: Ramane
 * @e-mail: ragb25@gmail.com
 */
public interface IAuftragsBeobachter {

    /**
     * Wird vom Controller aufgerufen, nachdem ein neuer Reparaturauftrag
     * bereits vollstaendig gespeichert wurde.
     *
     * @param auftragsnummer Kennung des neu angelegten Auftrags
     */
    public void neuerAuftrag(String auftragsnummer);

    /**
     * Wird vom Controller aufgerufen, nachdem ein bestehender
     * Reparaturauftrag geaendert wurde (z.B. durch eine gemeldete
     * defekte Komponente). Getrennt von neuerAuftrag, da ein geaenderter
     * Auftrag kein neuer Auftrag ist.
     *
     * @param auftragsnummer Kennung des geaenderten Auftrags
     */
    public void auftragGeaendert(String auftragsnummer);
}
