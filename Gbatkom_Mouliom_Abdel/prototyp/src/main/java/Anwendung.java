import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import controller.IReparaturSteuerung;
import controller.ReparaturController;
import model.Bonuskarte;
import model.ChemischerAntrieb;
import model.IRaumschiffVerwaltung;
import model.IReparaturVerwaltung;
import model.IonenbasierterAntrieb;
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
import model.Teleskop;
import view.Auftragsmaske;
import view.WerkstattAnsicht;

/**
 * Einstiegsklasse des Prototyps. Einzige Klasse im Projekt, die konkrete
 * Typen aus model, view und controller kennt; verdrahtet sie ausschliesslich
 * ueber Konstruktor-Injektion und zeigt die Swing-Oberflaeche.
 * Bewusste Vereinfachung gegenueber der Architektur: die Schichten laufen
 * hier im selben Prozess und werden ueber lokale Methodenaufrufe verdrahtet,
 * nicht ueber HTTP-Kommunikation zwischen verteilten Diensten.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Anwendung {

    // Testdaten, die zwischen testdatenAnlegen() und verdrahten() weitergereicht werden.
    private static Katalog katalog;
    private static Laden laden;
    private static Raumschiff raumschiffRS004;
    private static Raumschiff raumschiffRS007;
    private static Kunde kundeErika;
    private static Kunde kundeMax;
    private static Mitarbeiter mitarbeiterHans;
    private static Reparatur reparatur1;
    private static Reparatur reparatur2;
    private static Reparatur reparatur3;

    // Fertige Sichten, die zwischen verdrahten() und guiZeigen() weitergereicht werden.
    private static Auftragsmaske auftragsmaske;
    private static WerkstattAnsicht werkstattAnsicht;

    /**
     * Fuehrt die drei Phasen streng nacheinander aus: Testdaten anlegen,
     * Schichten verdrahten, Oberflaeche zeigen.
     *
     * @param args Kommandozeilenargumente, werden nicht ausgewertet
     */
    public static void main(String[] args) {
        testdatenAnlegen();
        verdrahten();
        guiZeigen();
    }

    /**
     * Baut Katalog, Raumschiffe, Laden, Kunden und Reparaturen als
     * Ausgangszustand des Prototyps auf.
     */
    private static void testdatenAnlegen() {
        Komponententyp typXR7 = new Komponententyp("XR-7", "Rumpf XR-7", 5000, 20);
        Komponententyp typChem3 = new Komponententyp("CHEM-3", "Chemischer Antrieb", 3000, 80);
        Komponententyp typIon9 = new Komponententyp("ION-9", "Ionenantrieb", 8000, 150);
        Komponententyp typTel2 = new Komponententyp("TEL-2", "Teleskop", 2000, 30);

        katalog = new Katalog("Standardkatalog", typXR7);
        katalog.addTyp(typChem3);
        katalog.addTyp(typIon9);
        katalog.addTyp(typTel2);

        // RS-004: funktionsfaehig, 200 >= 150 + 30. Rumpf.erfordertSpezialAntrieb steht hier fuer "ionenantriebgeeignet".
        Rumpf rumpf004 = new Rumpf("SN-0001", typXR7, 200, true);
        IonenbasierterAntrieb antrieb004 = new IonenbasierterAntrieb("SN-0002", typIon9, 500, 1000);
        Teleskop teleskop004 = new Teleskop("SN-0003", typTel2, "Astronomie", 1500);
        raumschiffRS004 = new Raumschiff("RS-004", "Kolibri", rumpf004);
        raumschiffRS004.addSystemkomponente(antrieb004);
        raumschiffRS004.addSystemkomponente(teleskop004);

        // RS-007: nicht funktionsfaehig, 100 < 80 + 30.
        Rumpf rumpf007 = new Rumpf("SN-0011", typXR7, 100, false);
        ChemischerAntrieb antrieb007 = new ChemischerAntrieb("SN-0012", typChem3, 300, "Hydrazin");
        Teleskop teleskop007 = new Teleskop("SN-0013", typTel2, "Astronomie", 1500);
        raumschiffRS007 = new Raumschiff("RS-007", "Adler", rumpf007);
        raumschiffRS007.addSystemkomponente(antrieb007);
        raumschiffRS007.addSystemkomponente(teleskop007);

        mitarbeiterHans = new Mitarbeiter("Hans Ober", 0);
        laden = new Laden("L-001", "Sternenwerft", "Hauptstrasse 1", mitarbeiterHans, katalog);

        kundeErika = new Kunde("K-001", "Erika Mustermann");
        kundeErika.setBonuskarte(new Bonuskarte("BK-100", new Date(), kundeErika));
        kundeMax = new Kunde("K-002", "Max Kunde");

        reparatur1 = new Reparatur("R-2026-0001", raumschiffRS004, Reparaturstatus.ANGELEGT);
        reparatur2 = new Reparatur("R-2026-0002", raumschiffRS007, Reparaturstatus.IN_BEARBEITUNG);
        reparatur3 = new Reparatur("R-2026-0003", raumschiffRS004, Reparaturstatus.ABGESCHLOSSEN);

        kundeErika.addBeauftragteReparatur(reparatur1);
        kundeMax.addBeauftragteReparatur(reparatur2);
    }

    /**
     * Erzeugt die drei Verwaltungen und den Controller, befuellt sie mit
     * den Testdaten und meldet die Werkstattansicht als Beobachter an.
     */
    private static void verdrahten() {
        IRaumschiffVerwaltung raumschiffVerwaltung = new RaumschiffVerwaltung(katalog);
        raumschiffVerwaltung.raumschiffSpeichern(raumschiffRS004);
        raumschiffVerwaltung.raumschiffSpeichern(raumschiffRS007);

        PersonenVerwaltung personenVerwaltung = new PersonenVerwaltung();
        personenVerwaltung.kundeSpeichern(kundeErika);
        personenVerwaltung.kundeSpeichern(kundeMax);
        personenVerwaltung.mitarbeiterSpeichern(mitarbeiterHans);

        IReparaturVerwaltung reparaturVerwaltung = new ReparaturVerwaltung(laden);
        reparaturVerwaltung.reparaturSpeichern(reparatur1);
        reparaturVerwaltung.reparaturSpeichern(reparatur2);
        reparaturVerwaltung.reparaturSpeichern(reparatur3);

        IReparaturSteuerung controller = new ReparaturController(raumschiffVerwaltung, reparaturVerwaltung,
                personenVerwaltung);

        auftragsmaske = new Auftragsmaske(controller);
        werkstattAnsicht = new WerkstattAnsicht(reparaturVerwaltung);
        controller.beobachterAnmelden(werkstattAnsicht);

        // Zeigt beim Start beide Faelle: eine offene Reparatur mit gemeldetem Defekt, eine ohne.
        controller.defekteKomponenteMelden(reparatur1.getReparaturID(), "SN-0002");
    }

    /**
     * Baut den JFrame aus den beiden fertigen Sichten und zeigt ihn auf
     * dem Event-Dispatch-Thread.
     */
    private static void guiZeigen() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Raumschiff-Reparatur");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, 2));
            frame.add(auftragsmaske);
            frame.add(werkstattAnsicht);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
