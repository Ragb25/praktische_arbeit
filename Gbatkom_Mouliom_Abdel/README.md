# Raumschiff-Reparatursystem - Prototyp

Prototypische Java/Swing-Anwendung fuer ein Raumschiff-Reparatursystem.
Umgesetzt ist der Anwendungsfall **"Reparatur beauftragen"** - ein Kunde
laesst fuer ein Raumschiff einen Reparaturauftrag anlegen - mit der
optionalen «extend»-Erweiterung **"Defekte Komponente melden"**: beim
Beauftragen kann zusaetzlich gleich eine bekannte defekte Komponente des
Raumschiffs erfasst werden.

## Vorbedingungen

- **Java 17** oder hoeher (siehe `maven.compiler.release` in `prototyp/pom.xml`)
- Sonst nichts weiter noetig - keine Datenbank, kein Server, keine
  externen Laufzeit-Abhaengigkeiten
- **Maven** wird nur gebraucht, wenn aus dem Quellcode gebaut oder die
  Tests ausgefuehrt werden sollen (siehe Abschnitte weiter unten); in
  `pom.xml` ist keine Mindestversion festgelegt - nicht verifizierbar,
  welche Version genau erforderlich waere

## Starten (empfohlen, ohne Maven)

```
java -jar prototype/src/deploy/raumschiff-reparatur.jar
```

Es oeffnet sich ein Swing-Fenster **"Raumschiff-Reparatur"**. Pfad und
Datei sind verifiziert: Die Jar liegt tatsaechlich unter
`prototype/src/deploy/raumschiff-reparatur.jar`.

## Bedienung

Das Fenster zeigt zwei Bereiche nebeneinander:

- **links:** Auftragsmaske - Kundennummer, Raumschiff-ID und optional die
  Seriennummer einer defekten Komponente eingeben, dann auf "Beauftragen"
  klicken
- **rechts:** Werkstatt-Ansicht - Liste der offenen Reparaturauftraege
  inklusive gemeldeter Defekte. Diese Liste aktualisiert sich automatisch,
  sobald links etwas angelegt oder geaendert wird - dafuer ist nichts
  weiter zu tun.

Das Seriennummernfeld ist **optional** - das ist der «extend»-Fall
"Defekte Komponente melden". Bleibt es leer, wird nur der Auftrag
angelegt.

Vorhandene Testdaten (aus `Anwendung.java`):

- Kunden: `K-001` (Erika Mustermann), `K-002` (Max Kunde)
- Raumschiffe: `RS-004` (funktionsfaehig), `RS-007` (nicht funktionsfaehig)
- Seriennummern der Komponenten von `RS-004`: `SN-0001` (Rumpf), `SN-0002`
  (Ionenantrieb, beim Programmstart bereits als Defekt gemeldet), `SN-0003`
  (Teleskop)

**Beispiel gueltige Eingabe:** Kundennummer `K-001`, Raumschiff-ID `RS-004`,
Seriennummer `SN-0003` -> es erscheint eine neue Auftragsnummer, der
Auftrag taucht sofort rechts in der Liste auf, mit dem Zusatz
`defekt: SN-0003`.

**Beispiel Fehleingabe Kundennummer:** eine nicht existierende
Kundennummer, z. B. `K-999` -> die Auftragsmaske zeigt unten eine
Fehlermeldung, die Eingabefelder bleiben dabei erhalten.

**Beispiel Fehleingabe Seriennummer:** eine nicht existierende
Seriennummer, z. B. `SN-9999` -> der Auftrag wird trotzdem angelegt, die
Maske zeigt aber zusaetzlich an, dass die Komponente nicht erfasst werden
konnte.

## Aus dem Quellcode bauen (optional, benoetigt Maven)

```
cd prototyp
mvn package
java -jar target/raumschiff-reparatur.jar
```

Hinweis: Das Maven-Projekt (`pom.xml`) liegt unter `prototyp/` (ohne "e"),
nicht unter `prototype/` (mit "e" - das ist ausschliesslich der
Abgabeordner fuer die fertige Jar unter `prototype/src/deploy`). Der
Jar-Name `raumschiff-reparatur.jar` ist in `pom.xml` als `finalName`
festgelegt.

## Tests ausfuehren (benoetigt Maven)

```
cd prototyp
mvn test
```

32 JUnit-5-Tests fuer die Anwendungsfaelle "Reparatur beauftragen" und
"Defekte Komponente melden".

## JavaDoc erzeugen (benoetigt Maven)

```
cd prototyp
mvn javadoc:javadoc
```

Ausgabe unter `prototyp/target/site/apidocs/index.html`.

## Weitere Dokumentation

Code-Metriken: [`prototyp/docs/Metriken.md`](prototyp/docs/Metriken.md)
