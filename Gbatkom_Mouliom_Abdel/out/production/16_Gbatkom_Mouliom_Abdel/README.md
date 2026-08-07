# Raumschiff-Reparatursystem - Prototyp

Prototypische Java/Swing-Anwendung fuer ein Raumschiff-Reparatursystem.
Umgesetzt ist genau ein Anwendungsfall: **"Reparatur beauftragen"** - ein
Kunde laesst fuer ein Raumschiff einen Reparaturauftrag anlegen, der
sofort in der Werkstatt-Ansicht sichtbar wird.

## Vorbedingungen

- **Java 17** oder hoeher (siehe `maven.compiler.release` in `prototyp/pom.xml`)
- **Maven** (in `prototyp/pom.xml` ist keine Mindestversion festgelegt -
  nicht verifiziert, welche Version genau erforderlich ist)
- Keine Datenbank, kein Server noetig - alle Daten sind In-Memory-Testdaten,
  die beim Start im Code angelegt werden

## Starten

```
cd prototyp
mvn compile
java -cp target/classes Anwendung
```

Es oeffnet sich ein Swing-Fenster **"Raumschiff-Reparatur"**.

## Bedienung

Das Fenster zeigt zwei Bereiche nebeneinander:

- **links:** Auftragsmaske - Kundennummer und Raumschiff-ID eingeben,
  dann auf "Beauftragen" klicken
- **rechts:** Werkstatt-Ansicht - Liste der offenen Reparaturauftraege.
  Diese Liste aktualisiert sich automatisch, sobald links ein neuer
  Auftrag angelegt wird - dafuer ist nichts weiter zu tun.

Vorhandene Testdaten (aus `Anwendung.java`):

- Kunden: `K-001` (Erika Mustermann), `K-002` (Max Kunde)
- Raumschiffe: `RS-004`, `RS-007`

**Beispiel gueltige Eingabe:** Kundennummer `K-001`, Raumschiff-ID `RS-004`
-> es erscheint eine neue Auftragsnummer, und der Auftrag taucht sofort
rechts in der Liste auf.

**Beispiel Fehleingabe:** eine nicht existierende Kundennummer, z. B.
`K-999`, mit einer beliebigen Raumschiff-ID -> die Auftragsmaske zeigt
unten eine Fehlermeldung, die Eingabefelder bleiben dabei erhalten.

## Tests ausfuehren

```
cd prototyp
mvn test
```

19 JUnit-5-Tests fuer den Anwendungsfall "Reparatur beauftragen".

## JavaDoc erzeugen

```
cd prototyp
mvn javadoc:javadoc
```

Ausgabe unter `prototyp/target/site/apidocs/index.html`.

## Weitere Dokumentation

Code-Metriken: [`prototyp/docs/Metriken.md`](prototyp/docs/Metriken.md)
