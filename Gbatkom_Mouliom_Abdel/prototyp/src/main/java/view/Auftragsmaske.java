package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.IReparaturSteuerung;

/**
 * View-Klasse: Kundensicht fuer den Anwendungsfall "Reparatur beauftragen".
 * Kennt nur das Steuerungs-Interface, keine konkrete Controller-Klasse
 * und keine Domaenenklassen - arbeitet ausschliesslich mit String.
 *
 * @author: Ramane Gbatkom Mouliom
 * @E-mail: ragb25@tu-clausthal.de
 */
public class Auftragsmaske extends JPanel {

    private final IReparaturSteuerung reparaturSteuerung;
    private final JTextField feldKundennummer;
    private final JTextField feldRaumschiffId;
    private final JTextField feldSeriennummer;
    private final JButton buttonBeauftragen;
    private final JLabel anzeigeErgebnis;

    /**
     * Bindet die Maske an die Steuerung und baut das Formular auf; ohne
     * Steuerung kann kein Auftrag angelegt werden.
     *
     * @param reparaturSteuerung Steuerung, an die Auftraege delegiert werden
     * @throws IllegalArgumentException wenn reparaturSteuerung null ist
     */
    public Auftragsmaske(IReparaturSteuerung reparaturSteuerung) {
        if (reparaturSteuerung == null) {
            throw new IllegalArgumentException("reparaturSteuerung darf nicht null sein");
        }
        this.reparaturSteuerung = reparaturSteuerung;
        this.feldKundennummer = new JTextField();
        this.feldRaumschiffId = new JTextField();
        this.feldSeriennummer = new JTextField();
        this.buttonBeauftragen = new JButton("Beauftragen");
        this.anzeigeErgebnis = new JLabel(" ");

        setBorder(BorderFactory.createTitledBorder("Reparatur beauftragen"));
        setLayout(new BorderLayout());

        JPanel eingabePanel = new JPanel(new GridLayout(3, 2, 4, 4));
        eingabePanel.add(new JLabel("Kundennummer:"));
        eingabePanel.add(feldKundennummer);
        eingabePanel.add(new JLabel("Raumschiff-ID:"));
        eingabePanel.add(feldRaumschiffId);
        eingabePanel.add(new JLabel("Seriennummer defektes Teil (optional):"));
        eingabePanel.add(feldSeriennummer);
        add(eingabePanel, BorderLayout.NORTH);

        add(buttonBeauftragen, BorderLayout.CENTER);
        add(anzeigeErgebnis, BorderLayout.SOUTH);

        buttonBeauftragen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onBeauftragenGeklickt();
            }
        });
    }

    /**
     * Liest die Eingabefelder aus, legt den Auftrag ueber die Steuerung
     * an und zeigt Erfolg oder Fehlermeldung an. Ist die Seriennummer
     * nicht leer, wird anschliessend zusaetzlich der optionale
     * «extend»-Fall defekteKomponenteMelden aufgerufen; schlaegt dieser
     * fehl, bleibt der bereits angelegte Auftrag bestehen.
     */
    private void onBeauftragenGeklickt() {
        String kundennummer = feldKundennummer.getText();
        String raumschiffId = feldRaumschiffId.getText();
        String seriennummer = feldSeriennummer.getText();
        String auftragsnummer;
        try {
            auftragsnummer = reparaturSteuerung.auftragAnlegen(kundennummer, raumschiffId);
        } catch (IllegalArgumentException e) {
            anzeigeErgebnis.setText(e.getMessage());
            return;
        }
        feldKundennummer.setText("");
        feldRaumschiffId.setText("");
        if (seriennummer.isEmpty()) {
            anzeigeErgebnis.setText("Auftrag angelegt: " + auftragsnummer);
            return;
        }
        try {
            reparaturSteuerung.defekteKomponenteMelden(auftragsnummer, seriennummer);
            anzeigeErgebnis.setText("Auftrag angelegt: " + auftragsnummer);
            feldSeriennummer.setText("");
        } catch (IllegalArgumentException e) {
            anzeigeErgebnis.setText("Auftrag angelegt: " + auftragsnummer
                    + " - Komponente nicht erfasst: " + e.getMessage());
        }
    }
}
