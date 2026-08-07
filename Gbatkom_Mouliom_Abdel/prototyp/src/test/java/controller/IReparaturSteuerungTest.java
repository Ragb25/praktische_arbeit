package controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.IReparaturSteuerung;
import controller.ReparaturController;
import model.IRaumschiffVerwaltung;
import model.IReparaturVerwaltung;
import model.Katalog;
import model.Komponententyp;
import model.Kunde;
import model.Laden;
import model.Mitarbeiter;
import model.PersonenVerwaltung;
import model.Raumschiff;
import model.RaumschiffVerwaltung;
import model.Reparatur;
import model.ReparaturVerwaltung;
import model.Reparaturstatus;
import model.Rumpf;
import model.Systemkomponente;
import model.Teleskop;
import view.IAuftragsBeobachter;

/**
 * Testet die Schnittstelle IReparaturSteuerung (Implementierung:
 * ReparaturController) fuer den Anwendungsfall "Reparatur beauftragen".
 * Verwendet die echten Verwaltungsklassen als In-Memory-Fakes, kein Mocking.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
class IReparaturSteuerungTest {

    private IReparaturSteuerung controller;
    private IRaumschiffVerwaltung raumschiffVerwaltung;
    private ReparaturVerwaltung reparaturVerwaltung;
    private PersonenVerwaltung personenVerwaltung;
    private Raumschiff raumschiffTest;
    private Raumschiff raumschiffAnderes;
    private Kunde kundeTest;

    // Baut fuer jeden Test einen frischen Katalog, zwei Raumschiffe (fuer den Fremdschiff-Fall), einen Kunden, einen Mitarbeiter und einen Laden auf.
    @BeforeEach
    void setUp() {
        Komponententyp typ = new Komponententyp("TYP-TEST", "Testkomponente", 100, 10);
        Katalog katalog = new Katalog("Testkatalog", typ);

        Rumpf rumpf = new Rumpf("SN-TEST-1", typ, 100, false);
        raumschiffTest = new Raumschiff("RS-TEST", "Testmodell", rumpf);
        Teleskop teleskopTest = new Teleskop("SN-TEST-2", typ, "Astronomie", 500);
        raumschiffTest.addSystemkomponente(teleskopTest);

        Rumpf rumpfAnderes = new Rumpf("SN-ANDERES-1", typ, 100, false);
        raumschiffAnderes = new Raumschiff("RS-ANDERES", "Anderes Testmodell", rumpfAnderes);

        Mitarbeiter mitarbeiter = new Mitarbeiter("Test Mitarbeiter", 0);
        Laden laden = new Laden("L-TEST", "Testladen", "Teststrasse 1", mitarbeiter, katalog);

        kundeTest = new Kunde("K-TEST", "Test Kunde");

        raumschiffVerwaltung = new RaumschiffVerwaltung(katalog);
        raumschiffVerwaltung.raumschiffSpeichern(raumschiffTest);
        raumschiffVerwaltung.raumschiffSpeichern(raumschiffAnderes);

        personenVerwaltung = new PersonenVerwaltung();
        personenVerwaltung.kundeSpeichern(kundeTest);
        personenVerwaltung.mitarbeiterSpeichern(mitarbeiter);

        reparaturVerwaltung = new ReparaturVerwaltung(laden);

        controller = new ReparaturController(raumschiffVerwaltung, reparaturVerwaltung, personenVerwaltung);
    }

    // Sucht die per Auftragsnummer erzeugte Reparatur im Bestand, um ihre Attribute zu pruefen.
    private Reparatur findReparatur(String auftragsnummer) {
        return reparaturVerwaltung.getReparaturen().stream()
                .filter(r -> r.getReparaturID().equals(auftragsnummer))
                .findFirst()
                .orElseThrow();
    }

    /**
     * Zaehlt Aufrufe von neuerAuftrag(...) und merkt sich Auftragsnummer
     * sowie den Stand der offenen Reparaturen zum Aufrufzeitpunkt, um die
     * Reihenfolge "erst speichern, dann benachrichtigen" zu pruefen.
     */
    private static class FakeBeobachter implements IAuftragsBeobachter {

        private final IReparaturVerwaltung reparaturVerwaltung;
        private int anzahlAufrufe = 0;
        private String letzteAuftragsnummer;
        private int offeneReparaturenBeiAufruf = -1;
        private int anzahlAufrufeGeaendert = 0;
        private String letzteAuftragsnummerGeaendert;

        FakeBeobachter(IReparaturVerwaltung reparaturVerwaltung) {
            this.reparaturVerwaltung = reparaturVerwaltung;
        }

        @Override
        public void neuerAuftrag(String auftragsnummer) {
            anzahlAufrufe++;
            letzteAuftragsnummer = auftragsnummer;
            offeneReparaturenBeiAufruf = reparaturVerwaltung.offeneReparaturen().size();
        }

        @Override
        public void auftragGeaendert(String auftragsnummer) {
            anzahlAufrufeGeaendert++;
            letzteAuftragsnummerGeaendert = auftragsnummer;
        }

        int getAnzahlAufrufe() {
            return anzahlAufrufe;
        }

        String getLetzteAuftragsnummer() {
            return letzteAuftragsnummer;
        }

        int getOffeneReparaturenBeiAufruf() {
            return offeneReparaturenBeiAufruf;
        }

        int getAnzahlAufrufeGeaendert() {
            return anzahlAufrufeGeaendert;
        }

        String getLetzteAuftragsnummerGeaendert() {
            return letzteAuftragsnummerGeaendert;
        }
    }

    @Test
    @DisplayName("Gueltige Eingabe liefert eine nicht-leere Auftragsnummer")
    void auftragAnlegen_mitGueltigerEingabe_liefertNichtLeereAuftragsnummer() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");
        assertNotNull(nummer);
        assertFalse(nummer.isEmpty());
    }

    @Test
    @DisplayName("Zwei gueltige Aufrufe liefern zwei verschiedene Auftragsnummern")
    void auftragAnlegen_zweimalAufgerufen_liefertUnterschiedlicheAuftragsnummern() {
        String erste = controller.auftragAnlegen("K-TEST", "RS-TEST");
        String zweite = controller.auftragAnlegen("K-TEST", "RS-TEST");
        assertNotEquals(erste, zweite);
    }

    @Test
    @DisplayName("Gueltige Eingabe erzeugt einen Auftrag in den offenen Reparaturen")
    void auftragAnlegen_mitGueltigerEingabe_erscheintInOffenenReparaturen() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");
        boolean gefunden = reparaturVerwaltung.offeneReparaturen().stream()
                .anyMatch(r -> r.getReparaturID().equals(nummer));
        assertTrue(gefunden);
    }

    @Test
    @DisplayName("Gueltige Eingabe setzt den Status auf ANGELEGT")
    void auftragAnlegen_mitGueltigerEingabe_setztStatusAngelegt() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");
        assertEquals(Reparaturstatus.ANGELEGT, findReparatur(nummer).getStatus());
    }

    @Test
    @DisplayName("Gueltige Eingabe verweist auf das uebergebene Raumschiff")
    void auftragAnlegen_mitGueltigerEingabe_verweistAufUebergebenesRaumschiff() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");
        assertSame(raumschiffTest, findReparatur(nummer).getRaumschiff());
    }

    @Test
    @DisplayName("Gueltige Eingabe verankert den Auftrag beim Kunden")
    void auftragAnlegen_mitGueltigerEingabe_istBeimKundenVerankert() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");
        boolean gefunden = kundeTest.getReparaturen().stream()
                .anyMatch(r -> r.getReparaturID().equals(nummer));
        assertTrue(gefunden);
    }


    @Test
    @DisplayName("Unbekannte Kundennummer wirft IllegalArgumentException")
    void auftragAnlegen_mitUnbekannterKundennummer_wirftException() {
        assertThrows(IllegalArgumentException.class, () -> controller.auftragAnlegen("K-UNBEKANNT", "RS-TEST"));
    }

    @Test
    @DisplayName("Unbekannte Raumschiff-ID wirft IllegalArgumentException")
    void auftragAnlegen_mitUnbekannterRaumschiffId_wirftException() {
        assertThrows(IllegalArgumentException.class, () -> controller.auftragAnlegen("K-TEST", "RS-UNBEKANNT"));
    }

    @Test
    @DisplayName("Kundennummer null wirft IllegalArgumentException")
    void auftragAnlegen_mitKundennummerNull_wirftException() {
        assertThrows(IllegalArgumentException.class, () -> controller.auftragAnlegen(null, "RS-TEST"));
    }

    @Test
    @DisplayName("Raumschiff-ID null wirft IllegalArgumentException")
    void auftragAnlegen_mitRaumschiffIdNull_wirftException() {
        assertThrows(IllegalArgumentException.class, () -> controller.auftragAnlegen("K-TEST", null));
    }

    @Test
    @DisplayName("Leere Kundennummer wirft IllegalArgumentException")
    void auftragAnlegen_mitLeererKundennummer_wirftException() {
        assertThrows(IllegalArgumentException.class, () -> controller.auftragAnlegen("", "RS-TEST"));
    }

    @Test
    @DisplayName("Unbekannter Kunde speichert keine zusaetzliche Reparatur")
    void auftragAnlegen_mitUnbekanntemKunden_speichertKeineZusaetzlicheReparatur() {
        int vorher = reparaturVerwaltung.getReparaturen().size();
        assertThrows(IllegalArgumentException.class, () -> controller.auftragAnlegen("K-UNBEKANNT", "RS-TEST"));
        assertEquals(vorher, reparaturVerwaltung.getReparaturen().size());
    }

    @Test
    @DisplayName("Angemeldeter Beobachter wird genau einmal mit der Auftragsnummer benachrichtigt")
    void auftragAnlegen_mitAngemeldetemBeobachter_benachrichtigtGenauEinmalMitDerAuftragsnummer() {
        FakeBeobachter beobachter = new FakeBeobachter(reparaturVerwaltung);
        controller.beobachterAnmelden(beobachter);

        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");

        assertEquals(1, beobachter.getAnzahlAufrufe());
        assertEquals(nummer, beobachter.getLetzteAuftragsnummer());
    }

    @Test
    @DisplayName("Ohne angemeldeten Beobachter wirft auftragAnlegen keine Exception")
    void auftragAnlegen_ohneAngemeldetenBeobachter_wirftKeineException() {
        assertDoesNotThrow(() -> controller.auftragAnlegen("K-TEST", "RS-TEST"));
    }

    @Test
    @DisplayName("Unbekannter Kunde benachrichtigt den Beobachter nicht")
    void auftragAnlegen_mitUnbekanntemKunden_benachrichtigtBeobachterNicht() {
        FakeBeobachter beobachter = new FakeBeobachter(reparaturVerwaltung);
        controller.beobachterAnmelden(beobachter);

        assertThrows(IllegalArgumentException.class, () -> controller.auftragAnlegen("K-UNBEKANNT", "RS-TEST"));

        assertEquals(0, beobachter.getAnzahlAufrufe());
    }

    @Test
    @DisplayName("Bei Benachrichtigung ist der Auftrag bereits gespeichert")
    void auftragAnlegen_mitGueltigerEingabe_benachrichtigtErstNachDemSpeichern() {
        FakeBeobachter beobachter = new FakeBeobachter(reparaturVerwaltung);
        controller.beobachterAnmelden(beobachter);

        controller.auftragAnlegen("K-TEST", "RS-TEST");

        assertEquals(1, beobachter.getOffeneReparaturenBeiAufruf());
    }

    @Test
    @DisplayName("beobachterAnmelden mit null wirft IllegalArgumentException")
    void beobachterAnmelden_mitNull_wirftException() {
        assertThrows(IllegalArgumentException.class, () -> controller.beobachterAnmelden(null));
    }

    @Test
    @DisplayName("Derselbe Beobachter zweimal angemeldet wird nur einmal benachrichtigt")
    void beobachterAnmelden_mitDemselbenBeobachterZweimal_benachrichtigtNurEinmal() {
        FakeBeobachter beobachter = new FakeBeobachter(reparaturVerwaltung);
        controller.beobachterAnmelden(beobachter);
        controller.beobachterAnmelden(beobachter);

        controller.auftragAnlegen("K-TEST", "RS-TEST");

        assertEquals(1, beobachter.getAnzahlAufrufe());
    }

    @Test
    @DisplayName("Zwei verschiedene Beobachter werden beide benachrichtigt")
    void beobachterAnmelden_mitZweiVerschiedenenBeobachtern_benachrichtigtBeide() {
        FakeBeobachter beobachter1 = new FakeBeobachter(reparaturVerwaltung);
        FakeBeobachter beobachter2 = new FakeBeobachter(reparaturVerwaltung);
        controller.beobachterAnmelden(beobachter1);
        controller.beobachterAnmelden(beobachter2);

        controller.auftragAnlegen("K-TEST", "RS-TEST");

        assertEquals(1, beobachter1.getAnzahlAufrufe());
        assertEquals(1, beobachter2.getAnzahlAufrufe());
    }

    @Test
    @DisplayName("Gueltige Meldung erscheint in getDefekteKomponenten")
    void defekteKomponenteMelden_mitGueltigerEingabe_erscheintInDefekteKomponenten() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");

        controller.defekteKomponenteMelden(nummer, "SN-TEST-1");

        boolean gefunden = findReparatur(nummer).getDefekteKomponenten().stream()
                .anyMatch(k -> k.getSeriennummer().equals("SN-TEST-1"));
        assertTrue(gefunden);
    }

    @Test
    @DisplayName("Unbekannte Auftragsnummer wirft IllegalArgumentException")
    void defekteKomponenteMelden_mitUnbekannterAuftragsnummer_wirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> controller.defekteKomponenteMelden("R-UNBEKANNT", "SN-TEST-1"));
    }

    @Test
    @DisplayName("Unbekannte Seriennummer wirft IllegalArgumentException")
    void defekteKomponenteMelden_mitUnbekannterSeriennummer_wirftException() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");

        assertThrows(IllegalArgumentException.class,
                () -> controller.defekteKomponenteMelden(nummer, "SN-UNBEKANNT"));
    }

    @Test
    @DisplayName("Seriennummer aus einem anderen Raumschiff wirft IllegalArgumentException")
    void defekteKomponenteMelden_mitSeriennummerAusAnderemRaumschiff_wirftException() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");

        assertThrows(IllegalArgumentException.class,
                () -> controller.defekteKomponenteMelden(nummer, "SN-ANDERES-1"));
    }

    @Test
    @DisplayName("Auftragsnummer null wirft IllegalArgumentException")
    void defekteKomponenteMelden_mitAuftragsnummerNull_wirftException() {
        assertThrows(IllegalArgumentException.class,
                () -> controller.defekteKomponenteMelden(null, "SN-TEST-1"));
    }

    @Test
    @DisplayName("Seriennummer null wirft IllegalArgumentException")
    void defekteKomponenteMelden_mitSeriennummerNull_wirftException() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");

        assertThrows(IllegalArgumentException.class,
                () -> controller.defekteKomponenteMelden(nummer, null));
    }

    @Test
    @DisplayName("Leere Seriennummer wirft IllegalArgumentException")
    void defekteKomponenteMelden_mitLeererSeriennummer_wirftException() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");

        assertThrows(IllegalArgumentException.class,
                () -> controller.defekteKomponenteMelden(nummer, ""));
    }

    @Test
    @DisplayName("Zwei verschiedene Komponenten desselben Schiffs erscheinen beide")
    void defekteKomponenteMelden_mitZweiVerschiedenenKomponenten_erscheinenBeide() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");

        controller.defekteKomponenteMelden(nummer, "SN-TEST-1");
        controller.defekteKomponenteMelden(nummer, "SN-TEST-2");

        List<String> seriennummern = findReparatur(nummer).getDefekteKomponenten().stream()
                .map(Systemkomponente::getSeriennummer)
                .collect(Collectors.toList());
        assertEquals(2, seriennummern.size());
        assertTrue(seriennummern.contains("SN-TEST-1"));
        assertTrue(seriennummern.contains("SN-TEST-2"));
    }

    @Test
    @DisplayName("Dieselbe Komponente zweimal gemeldet erscheint nur einmal")
    void defekteKomponenteMelden_mitDerselbenKomponenteZweimal_erscheintNurEinmal() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");

        controller.defekteKomponenteMelden(nummer, "SN-TEST-1");
        controller.defekteKomponenteMelden(nummer, "SN-TEST-1");

        assertEquals(1, findReparatur(nummer).getDefekteKomponenten().size());
    }

    @Test
    @DisplayName("Gueltige Meldung benachrichtigt den Beobachter genau einmal mit auftragGeaendert")
    void defekteKomponenteMelden_mitGueltigerEingabe_benachrichtigtMitAuftragGeaendert() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");
        FakeBeobachter beobachter = new FakeBeobachter(reparaturVerwaltung);
        controller.beobachterAnmelden(beobachter);

        controller.defekteKomponenteMelden(nummer, "SN-TEST-1");

        assertEquals(1, beobachter.getAnzahlAufrufeGeaendert());
        assertEquals(nummer, beobachter.getLetzteAuftragsnummerGeaendert());
    }

    @Test
    @DisplayName("Gueltige Meldung loest kein neuerAuftrag beim Beobachter aus")
    void defekteKomponenteMelden_mitGueltigerEingabe_loestKeinNeuerAuftragAus() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");
        FakeBeobachter beobachter = new FakeBeobachter(reparaturVerwaltung);
        controller.beobachterAnmelden(beobachter);

        controller.defekteKomponenteMelden(nummer, "SN-TEST-1");

        assertEquals(0, beobachter.getAnzahlAufrufe());
    }

    @Test
    @DisplayName("Unbekannter Auftrag benachrichtigt den Beobachter nicht")
    void defekteKomponenteMelden_mitUnbekanntemAuftrag_benachrichtigtBeobachterNicht() {
        FakeBeobachter beobachter = new FakeBeobachter(reparaturVerwaltung);
        controller.beobachterAnmelden(beobachter);

        assertThrows(IllegalArgumentException.class,
                () -> controller.defekteKomponenteMelden("R-UNBEKANNT", "SN-TEST-1"));

        assertEquals(0, beobachter.getAnzahlAufrufeGeaendert());
    }

    @Test
    @DisplayName("Basisfall bleibt intakt: Auftrag ohne Defektmeldung hat leere Defektliste")
    void auftragAnlegen_ohneDefekteKomponenteMelden_hatLeereDefektliste() {
        String nummer = controller.auftragAnlegen("K-TEST", "RS-TEST");

        assertTrue(findReparatur(nummer).getDefekteKomponenten().isEmpty());
    }
}
