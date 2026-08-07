package controller;

import view.IAuftragsBeobachter;

/**
 * Hier werden die notwendige Methode zur Reparatursteurung bereitgestellt.
 * @author: Ramane
 * @e-mail: ragb25@gmail.com
 */
public interface IReparaturSteuerung {

    /**
     * Legt einen neuen Reparaturauftrag fuer einen Kunden und ein
     * Raumschiff an und benachrichtigt anschliessend alle angemeldeten
     * Beobachter.
     *
     * @param Kundennummer
     * @param raumschifID
     */
    public String auftragAnlegen(String Kundennummer, String raumschifID);

    /**
     * Meldet einen Beobachter an, der ab sofort über neue Aufträge
     * informiert wird. Eine bereits angemeldete Instanz wird nicht
     * doppelt angemeldet.
     * @throws IllegalArgumentException wenn beobachter null ist
     */
    public void beobachterAnmelden(IAuftragsBeobachter beobachter);

    /**
     * Wenn der Kunde die defekte Komponente kennt, kann er hier melden
     *
     * @param auftragsnummer Kennung des bestehenden Auftrags
     * @param seriennummer Kennung der defekten Komponente
     * @throws IllegalArgumentException wenn auftragsnummer oder seriennummer
     *         leer/null sind, der Auftrag nicht gefunden wird oder die
     *         Komponente nicht im Raumschiff des Auftrags gefunden wird
     */
    public void defekteKomponenteMelden(String auftragsnummer, String seriennummer);
}
