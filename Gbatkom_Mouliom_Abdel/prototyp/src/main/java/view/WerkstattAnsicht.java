package view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.IReparaturVerwaltung;
import model.Reparatur;
import model.Systemkomponente;

/**
 * View-Klasse: Mitarbeitersicht auf die offenen Reparaturauftraege.
 * Reine Anzeige, keine Eingabe. Meldet sich als Beobachter beim
 * Controller an, um bei neuen Auftraegen automatisch zu aktualisieren.
 * Bekannte Abweichung von der Schichtung: greift direkt auf
 * IReparaturVerwaltung zu statt ausschliesslich ueber den Controller.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class WerkstattAnsicht extends JPanel implements IAuftragsBeobachter {

    private final IReparaturVerwaltung reparaturVerwaltung;
    private final DefaultListModel<String> listenModell;
    private final JList<String> listeOffeneAuftraege;

    /**
     * Bindet die Ansicht an die Verwaltung und zeigt sofort den
     * aktuellen Datenstand, statt leer zu starten.
     *
     * @param reparaturVerwaltung Verwaltung, aus der die offenen Auftraege gelesen werden
     * @throws IllegalArgumentException wenn reparaturVerwaltung null ist
     */
    public WerkstattAnsicht(IReparaturVerwaltung reparaturVerwaltung) {
        if (reparaturVerwaltung == null) {
            throw new IllegalArgumentException("reparaturVerwaltung darf nicht null sein");
        }
        this.reparaturVerwaltung = reparaturVerwaltung;
        this.listenModell = new DefaultListModel<>();
        this.listeOffeneAuftraege = new JList<>(listenModell);

        setBorder(BorderFactory.createTitledBorder("Offene Reparaturauftraege"));
        setLayout(new BorderLayout());
        add(new JScrollPane(listeOffeneAuftraege), BorderLayout.CENTER);

        aktualisieren();
    }

    /**
     * {@inheritDoc}
     * Wird vom Controller nach jedem neuen Auftrag aufgerufen, damit
     * die Anzeige ohne manuelles Nachladen aktuell bleibt.
     */
    @Override
    public void neuerAuftrag(String auftragsnummer) {
        aktualisieren();
    }

    /**
     * {@inheritDoc}
     * Wird vom Controller nach jeder Aenderung eines bestehenden Auftrags
     * aufgerufen (z.B. einer gemeldeten defekten Komponente), damit die
     * Anzeige ohne manuelles Nachladen aktuell bleibt.
     */
    @Override
    public void auftragGeaendert(String auftragsnummer) {
        aktualisieren();
    }

    /**
     * Laedt die offenen Auftraege neu und baut die Listenzeilen aus den
     * tatsaechlichen Reparatur-Gettern auf, inklusive gemeldeter Defekte.
     */
    private void aktualisieren() {
        listenModell.clear();
        List<Reparatur> offeneReparaturen = reparaturVerwaltung.offeneReparaturen();
        for (Reparatur reparatur : offeneReparaturen) {
            StringBuilder zeile = new StringBuilder();
            zeile.append(reparatur.getReparaturID()).append(" | ")
                    .append(reparatur.getRaumschiff().getRaumschiffID()).append(" | ")
                    .append(reparatur.getStatus());
            List<Systemkomponente> defekte = reparatur.getDefekteKomponenten();
            if (!defekte.isEmpty()) {
                zeile.append(" | defekt: ");
                for (int i = 0; i < defekte.size(); i++) {
                    if (i > 0) {
                        zeile.append(", ");
                    }
                    zeile.append(defekte.get(i).getSeriennummer());
                }
            }
            listenModell.addElement(zeile.toString());
        }
    }
}
