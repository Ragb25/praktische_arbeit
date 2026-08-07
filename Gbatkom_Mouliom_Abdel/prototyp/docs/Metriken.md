# Code-Metriken: Raumschiff-Reparatursystem

Stand: 2026-07-17

## Erhebungsmethode

In dieser Sandbox sind weder Maven noch PMD, Checkstyle oder SpotBugs
installiert (`mvn`, `pmd`, `checkstyle`, `spotbugs` nicht auffindbar).
Internetzugriff auf Maven Central besteht jedoch. Damit ergab sich folgende
Vorgehensweise, die je Abschnitt unten gekennzeichnet ist:

- **Testabdeckung (Zeilenabdeckung):** real gemessen mit **JaCoCo 0.8.12**
  (Agent- und CLI-Jar direkt von Maven Central geladen, ohne Maven-Build:
  `-javaagent` beim Testlauf, anschliessend Report ueber `jacococli.jar`).
  Kein Eintrag in der `pom.xml` nötig, daher ohne Rückfrage moeglich.
  
  
- **Testfaelle:** Insgesamt 32 implementiert und alle sind grün kein rot


## 1. Umfang

| Metrik | Wert | Erhebung |
|---|---|---|
| Pakete | 4 (Default-Package/`Anwendung`, `controller`, `model`, `view`) | manuell |
| Klassen | 24 | manuell |
| Interfaces | 5 | manuell |
| Enums | 1 (`Reparaturstatus`) | manuell |
| LOC gesamt (Hauptquellcode, ohne Kommentare/Leerzeilen) | 978 | manuell |
| LOC `model` | 693 | manuell |
| LOC `controller` | 74 | manuell |
| LOC `view` | 104 | manuell |
| LOC Default-Package (`Anwendung`) | 107 | manuell |
| Durchschnittliche Klassengroesse (nur `class`, ohne Interfaces/Enum) | 39,3 LOC | manuell |
| Maximale Klassengroesse | 107 LOC (`Anwendung`) | manuell |



**Fuenf hoechste zyklomatische Komplexitaeten:**

| Rang | Methode | Klasse | CC |
|---|---|---|---|
| 1 | `komponenteLaden` | `model.RaumschiffVerwaltung` | 5 |
| 2 | `kundeLaden` | `model.PersonenVerwaltung` | 4 |
| 3 | `mitarbeiterLaden` | `model.PersonenVerwaltung` | 4 |
| 4 | `raumschiffLaden` | `model.RaumschiffVerwaltung` | 4 |
| 5 | `reparaturLaden` | `model.ReparaturVerwaltung` | 4 |

Zusätzlich auf demselben Niveau der Konstruktor von `ReparaturController` und `ReparaturController.auftragAnlegen`.
Alle uebrigen ca. 125 Methoden/Konstruktoren liegen bei CC 1-3, die
grosse Mehrheit bei CC 1.

## 3. Struktur

**Vererbungstiefe je Klasse** (0 = erbt nur von `Object`):

| Tiefe | Klassen |
|---|---|
| 2 (Maximum) | `Bodenscanner`, `ChemischerAntrieb`, `IonenbasierterAntrieb`, `Teleskop` |
| 1 | `Antrieb`, `Forschungsmodul`, `Rumpf`, `Auftragsmaske`, `WerkstattAnsicht` (Swing-Basisklasse `JPanel` nicht mitgezaehlt) |
| 0 | alle uebrigen 15 Klassen |

| Metrik | Wert | Erhebung |
|---|---|---|
| Methoden je Klasse (ohne Konstruktor, nur `class`) - Durchschnitt | 4,5 | manuell |
| Methoden je Klasse - Maximum | 11 (`Laden`) | manuell |

**Efferente Kopplung je Paket** (Anzahl anderer eigener Pakete, von denen
importiert wird):

| Paket | Abhaengig von | Anzahl |
|---|---|---|
| `model` | - | 0 |
| `controller` | `model`, `view` | 2 |
| `view` | `controller`, `model` | 2 |
| Default-Package (`Anwendung`) | `controller`, `model`, `view` | 3 |


