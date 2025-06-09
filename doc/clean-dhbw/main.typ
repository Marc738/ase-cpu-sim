#import "@preview/clean-dhbw:0.2.1": *
#import "@preview/dashy-todo:0.0.3": todo
#import "acronyms.typ": acronyms
#import "glossary.typ": glossary

#show: clean-dhbw.with(
  title: "CPU Simulation",
  authors: (
    (
      name: "Marc Schillinger",
      student-id: "3449698",
      course: "TINF22B2",
      course-of-studies: "Informatik",
      company: (
        (name: "Atruvia AG", post-code: "76131", city: "Karlsruhe")
      ),
    ),
  ),
  type-of-thesis: "Programmentwurf",
  acronyms: acronyms,
  at-university: false, // if true the company name on the title page and the confidentiality statement are hidden
  bibliography: bibliography("sources.bib"),
  date: datetime.today(),
  glossary: glossary, // displays the glossary terms defined in the glossary dictionary
  language: "de", // en, de
  supervisor: (university: "Lars Briem"),
  university: "Duale Hochschule Baden-Württemberg",
  university-location: "Karlsruhe",
  university-short: "DHBW",
  // for more options check the package documentation (https://typst.app/universe/package/clean-dhbw)
)

#outline(title: "TODOs", target: figure.where(kind: "todo"))

= Kapitel 1: Einführung

== Übersicht über die Applikation
//[Was macht die Applikation?]

#figure(
  table(
    columns: 2,
    [*Entwicklungsumgebung*], [IntelliJ IDEA 2024.1.2 (Community Edition)],
    [*Build-Management-Tool*], [Gradle 8.12],
    [*Repository*], [#link("https://github.com/Marc738/ase-cpu-sim.git")[ase-cpu-sim]],
  ),
  caption: "Eckdaten des Projekts",
)

Die Applikation ist eine Simulation einer CPU. Betrachtet wurden wesentliche Komponenten, welche in der Betriebssystemevorlesung aus Semester 4 besprochen wurden. Zur Vereinfachung wurde ein abgesteckter Rahmen gewählt und Funktionen in der Tiefe nicht vergleichbar wie bei echten CPUs umgesetzt.

Anhand des Codes und der Bedienung der Applikation kann ein Basisverständnis von abläufen innerhalb einer CPU erlangt werden. Funktionen wie das Lesen und Schreiben von einzelnen Byte, sowie einfache Rechenoperationen, wie dem Addieren und Subtrahieren wurden umgesetzt. Die Applikation wurde so konzipiert das Erweiterungen leicht hinzugefügt werden können. Weitere Funktionen, wie das Multiplizieren oder das logische Und sind somit umsetzbar.

Zentral ist die ControlUnit für alle Abläufe innerhalb der simulierten CPU zuständig. Sie nutzt den Decoder, um Commands (Nutzereingaben) zu decoden in Instructions, welche dann von den einzelnen Komponenten der CPU ausgeführt werden können. Zu diesen Komponenten gehören in dieser Implementierung die ALU und ein Register.

#figure(
  image("assets/svg/CPUSimulation Overview.svg"),
  caption: "CPUSimulation grobe Übersicht",
)

== Wie startet man die Applikation?

+ Starte einen Terminal
+ Navigiere in das Root-Verzeichnis des Projekts
+ Führe den Command aus @appstartcommand aus

#figure(
  sourcecode[```
    ./gradlew run --console=plain
    ```],
  caption: "Applikation Start-Command",
) <appstartcommand>

== Wie testet man die Applikation?
+ Starte einen Terminal
+ Navigiere in das Root-Verzeichnis des Projekts
+ Führe den Command aus @apptestcommand aus

#figure(
  sourcecode[```
    ./gradlew test
    ```],
  caption: "Applikation Test-Command",
) <apptestcommand>

= Kapitel 2: Clean Architecture

== Was ist Clean Architecture?
Clean Architecture strebt an möglichst langlebigen Code zu strukturieren und zu schreiben. Langlebiger Code wird durch Flexibilität, Skalierbarkeit und guter Wartung definiert. Hierfür wird ein mehrschichtiges System angestrebt, welches klare Schnittstellen und Abhängigkeiten vorsieht. Gedacht wird dieses System wie eine Zwiebel. Im Kern befindet sich die Anwendungslogik und in der äußeren Schichten die Peripheriegeräte. Abhängigkeiten gelten immer nur von einer äußeren zu einer weiter innen liegenden Schicht.

#todo(position: right)[Kann man noch erweitern]

== Analyse der Dependency Rule

=== Positiv-Beispiel
// [UML und Analyse]
#todo[Beispiel heraussuchen]

=== Negativ-Beispiel
// [UML und Analyse]

#todo(
  position: "inline",
)[ControlUnit und StorageManager: ControlUnit ist zuständig für ProcessingUnits und StoredValue, aber StorageManager benötigt ProcessingUnits und StoredValue um Datenoperationen auszuführen.]

== Analyse der Schichten

=== Schicht: Interface-Schicht
// [UML, Aufgabe, Einordnung]

#figure(
  image("assets/classes/InterfaceLayer.svg"),
  caption: "Adapters-Schicht UML",
) <adapterlayer>

Die Adapter-Schicht liegt zwischen der Plugin-Schicht, welche Geräte, Drittsysteme oder GUIs umfasst, und dem Application Code. Sie vermittelt zwischen den Plugins und den Use Cases.

In diesem Fall befinden sich die gezeigten Klassen in der Adapter-Schicht (siehe @adapterlayer). InputHanlder und OutputHandler sind dafür verantwortlich mit den Plugins zu kommunizieren. Die InputHandlerImpl-Klasse greift hierbei auf die Tastatureingaben zu. Die OutputHandlerImpl-Klasse sorgt dafür dass die Ausgaben des Systems an die Console weitergeleitet werden.

Die Klasse `CPUSimulation` kümmert sich um die Verbindung zu den Use Cases und koordiniert die Laufreihenfolge der einzelnen Komponenten. Ergo wann auf eine Eingabe gewartet wird, was mit dieser geschieht und was wiederum dem Nutzer zurückgemeldet wird.

=== Schicht: Application Code
// [UML, Aufgabe, Einordnung]

// ControlUnit ist teil der Domain Logic, steuert und koordiniert Use-Cases, also ALU, Register und Decoder. Diese sind Domänenmodelle (halten Zustand und führen Operationen aus)

#figure(
  image("assets/classes/ApplicationCode.svg"),
  caption: "Application Code UML",
) <applicationcode>

Die Application-Code-Schicht liegt zwischen der Adapter-Schicht und dem Domain Code. Sie beschreibt die Anwendungsregeln, unabhängig von Ein- oder Ausgabegeräten, Datenbanken oder Frameworks.

In dieser Schicht befinden sich die Use Cases des Systems (siehe @applicationcode). Sie definieren, was das System tun soll, nicht wie es technisch umgesetzt wird. Die Klassen in dieser Schicht orchestrieren den Ablauf von Prozessen, rufen Entities auf und koordinieren deren Zusammenarbeit.

In diesem Fall übernimmt die Klasse `ControlUnit` die Rolle eines zentralen Use Cases. Sie verarbeitet eingehende Befehle, lässt diese über einen Decoder in ausführbare Anweisungen übersetzen und verteilt diese an die zuständigen Einheiten (z.B. ALU oder Register).

= Kapitel 3: SOLID

== Analyse Single-Responsibility-Principle (SRP)

=== Positiv-Beispiel
// [UML, Beschreibung]

#figure(
  image("assets/classes/AddCommandDecoder.svg"),
  caption: "AddCommandDecoder-Klasse UML",
) <addcommanddecoder>

Die AddCommandDecoder-Klasse ist allein dafür zuständig einen Command, bestehend aus einem Keyword und mehreren Parametern (InstructionValues), in eine Instruction umzuwandeln.

Jeder Command der decodiert werden kann wird von einer eigenständigen Klasse decodiert.

=== Negativ-Beispiel

#figure(
  image("assets/classes/StorageManager.svg"),
  caption: "StorageManager-Klasse UML",
) <storagemanager>

Der StorageManager (siehe @storagemanager) ist Teil der ControlUnit und ist dafür zuständig Daten zu Setzen, Holen und Zwischenzuspeichern. Er übernimmt die Ausführung aller drei Funktionen selbst und verwendet nicht weitere Unterklassen, um die jeweilige Umsetzung von sich zu trennen.

== Analyse Open-Closed-Principle (OCP)

=== Positiv-Beispiel

#figure(
  image("assets/classes/Decoder.svg"),
  caption: "Decoder-Klasse UML",
) <decoder>

Die Decoder-Klasse (siehe @decoder) zeigt hierfür ein gutes Beispiel. Um offen für Erweiterungen und geschlossen für Modifizierungen zu sein nutzt der Decoder die Klasse CommandDecoder (siehe auch @addcommanddecoder). Diese ermöglicht es das unterschiedliche CommandDecoder eingesetzt werden können. Die Funktion eines Decoders bleibt immer gleich, aber es bleiben verschiedene Implementierungen eines CommandDeocders somit möglich.

=== Negativ-Beispiel
// [UML, Analyse, Lösungsvorschlag]

#figure(
  image("assets/classes/StorageManager.svg"),
  caption: "StorageManager-Klasse UML",
) <storagemanageruml>

Der StorageManager ist ein negatives Beispiel für OCP. Er kümmert sich um mehrere `Instruction`'s, das sind:
- `SET`: Setzt einen Speicherplatz (`StorageSpace`) auf einen Wert
- `GET`: Holt den Wert eines Speicherplatzes (`StorageSpace`)
- `STORE`: Speichert einen Wert aus einer Nutzereingabe zwischen in der ControlUnit

Um OCP zu erfüllen müssten diese einzelnen Funktionalitäten voneinander getrennt werden und nicht wie in diesem Beispiel alle in einer Klasse behandelt werden. Somit könnte deren Funktionalität auch unabhängiger getestet werden.

== Analyse LSP, ISP oder DIP

=== Positiv-Beispiel
// [UML, Analyse]

#figure(
  image("assets/classes/InputHandler.svg"),
  caption: "InputHandler-Klasse UML",
) <inputhandler>

Für Liskov Substitution Principle ist der InputHandler ein gutes Beispiel (siehe @inputhandler). Er wird genutzt um in der Auswahl der Eingabegeräte flexibel zu bleiben. Erwartet wird ein String als Rückgabe. Aber wie genau dieser String aufgenommen wird ist hierbei nicht relevant. Da die aktuelle Implementierung in der Console stattfindet wurde die Klasse InputHandlerImpl umgesetzt, welche diese Funktionalität umsetzt und die Eingabedaten schließlich mit der Scanner-Klasse einholt. Falls sich das Eingabegerät ändern sollte kann problemlos eine weitere Implementierung für diesen Fall umgesetzt werden.

=== Positiv-Beispiel
// [UML, Analyse]

#figure(
  image("assets/classes/InputConverter.svg"),
  caption: "InputConverter-Klasse",
) <inputconverter>

Die `InputConverter`-Klasse (siehe @inputconverter) nutzt ebenfalls ein Interface, um flexibel in seiner Implementierung zu bleiben. Sobald sich eine neue Anforderung an die Erstellung von Commands aus dem Texteingaben des Nutzers ergibt, kann eine andere Implementierung umgesetzt werden. Somit erfüllt die `InputConverter`-Klasse ebenfalls das Liskov Substitution Principle.

= Kapitel 4: Weitere Prinzipien

== Analyse GRASP: Geringe Kopplung

=== Positiv-Beispiel
// [UML, Begründung]

#figure(
  sourcecode(```
  public class Main {

    public static void main(String[] args) {
      ...
      Decoder decoder = new Decoder(new ArgDecoder(), new CommandDecoder[]{
              new AddCommandDecoder(),
              new SubtractCommandDecoder(),
              new SetCommandDecoder(),
              new GetCommandDecoder(),
              new StoreCommandDecoder(),
      });
    ...
  ```),
  caption: "Erstellung Decoder in main-Methode",
)

Ein positives Beispiel für eine geringe Kopplung nach GRASP ist die Decoder-Klasse (siehe @decoder). Der Decoder bekommt alle seine CommandDecoder, sowie den ArgDecoder bei Erstellung übergeben und muss sich nicht selbst darum kümmern. Somit können bspw. bei Tests gezieht Funktionen getestet werden ohne alle Funktionalitäten aus der vollständig instanziierten Laufzeitumgebung zu übernehmen.

=== Negativ-Beispiel
// [UML, Verbesserungsvorschlag]

#figure(
  image("assets/classes/ArithmeticProcessingUnit.svg"),
  caption: "ArithmeticProcessingUnit-Klasse",
) <arithmeticprocessingunit>

#figure(
  sourcecode(```
  public class ArithmeticSubUnit implements SubUnit {

    private Operator[] operators;

    public ArithmeticSubUnit() {
        operators = new Operator[]{new AddOperator(), new SubtractOperator()};
    }
    ...
  ```),
  caption: "ArithmeticSubUnit Constructor",
) <codearithmeticsubunit>

GRASP findet sich in der ArithmeticProcessingUnit-Klasse nicht wieder (siehe @arithmeticprocessingunit). Innerhalb des Constructors der ArithmeticSubUnit (siehe @codearithmeticsubunit) sieht man das die verwendeten Operatoren der ArithmeticSubUnit nicht über die Contstructor-Attribute emfangen werden, sondern bereits festgelegt sind. Somit ist die Zusammensetzung der ArithmeticProcessingUnit-Klasse nicht flexibel. Diese Implementierung verletzt GRASP wurde aber bewusst so umgesetzt. Diese Klasse ist per Definition immer an die selben Klassen gebunden und somit wurde sich entschieden das diese direkt im eigenen Code eingebettet werden.

Es wäre auch denkbar, wie bei dem positiven Beispiel, die Klassen als Attribute über den Constructor zu empfangen. Somit wäre GRASP nicht mehr verletzt.

== Analyse GRASP: Hohe Kohäsion
// [UML, Begründung]

// Hohe Kohäsion bedeutet, dass eine Klasse oder ein Modul nur eng zusammenhängende Aufgaben übernimmt und ihre Methoden und Daten stark auf ein gemeinsames Ziel ausgerichtet sind.

#figure(
  image("assets/classes/AddCommandDecoder.svg"),
  caption: "AddCommandDecoder-Klasse",
) <addcommanddecoderuml>

Die AddCommandDecoder-Klasse ist ein einfaches Beispiel für hohe Kohäsion (siehe @addcommanddecoderuml). Sie umfasst lediglich eine Methode neben dem Constructor und ist somit maximal auf die Umsetzung einer Aufgabe fokussiert.

== Don't Repeat Yourself (DRY)
// [Commit angeben, vorher/nachher Code zeigen und Auswirkung begründen]

#todo(position: right)[Commit raussuchen]

= Kapitel 5: Unit Tests

== 10 Unit Tests

#table(
  columns: 2,
  table.header([*Unit Test*], [*Beschreibung*]),
  [`StorageSpaceTest > testConstructorWithWord()`],
  [Testet ob die StorageSpace-Klasse die im Constructor übergebenen Werte übernimmt.],

  [`AddOperatorTest > testeEinfacheAddition()`],
  [Testet ob AddOperator eine einfache Addition (ohne Übertrag) zweier Words ausführen kann.],

  [`AddOperatorTest > testeAdditionMitÜberlauf()`],
  [Testet ob AddOperator eine Addition mit Übertrag zweier Words ausführen kann.],

  [`ALUTest > testCanProcess()`],
  [Testet ob die ALU ein valides Keyword verarbeiten kann und ein invalides Keyword nicht verarbeiten kann.],

  [`ALUTest > testProcessAddition()`],
  [Testet ob eine Addition-Instruction erfolgreich verarbeitet werden kann. Die ALU reicht diese Anfrage an den AddOperator weiter. Diese Weiterleitung wird getestet.],

  [`AddCommandDecoderTest > decode_shouldReturnInstructions-_whenValidArgs()`],
  [Testet ob der AddCommand in die richtige Kombination aus Instructions übersetzt wird.],

  [`GetCommandDecoderTest > testMissingAddress()`],
  [Testet ob ein Fehler auftritt beim decoden, wenn die Adresse nicht spezifiziert wurde.],

  [`ArgsDecoderTest > testDecodeWordAndAddress()`],
  [Testet ob die übergebenen Argumente (Word und Address) richtig erkannt und übertragen werden in ihre Klassenrepräsentation.],

  [`CommandTest > testeNurMitKeyword()`], [Testet ob ein Command nur von einem Keyword erstellen kann.],
  [`CommandTest > testeCommandErstellungMitCommandBuilder()`],
  [Testet ob man einen Command über den CommandBuilder erstellen kann.],
)

| Klasse=Methode | [Beschreibung] |
| ... | ... |

#todo[Verstehen und Tabelle bauen]

== ATRIP: Automatic
// [Begründung]
#todo[Begründung aussuchen]

== ATRIP: Thorough

=== Positiv-Beispiel
// [Code, Analyse]
#todo[Beispiel aussuchen]

=== Negativ-Beispiel
// [Code, Analyse]
#todo[Beispiel aussuchen]

== ATRIP: Professional

=== Positiv-Beispiel
// [Code, Analyse]
#todo[Beispiel aussuchen]

=== Negativ-Beispiel
// [Code, Analyse]
#todo[Beispiel aussuchen]

== Code Coverage
// [Analyse]

// Code Coverage

#let coverages = (
  "de.dbhw": (80, 89, 78, 70),
  "io": (100, 100, 94, 70),
  "units": (100, 100, 93, 77),
  "utils.data": (100, 100, 100, 100),
)

#let toColoredTableCell(i) = {
  if (i < 70) {
    [#table.cell([#i%], fill: red)]
  } else if (i < 80) {
    [#table.cell([#i%], fill: orange)]
  } else if (i < 90) {
    [#table.cell([#i%], fill: yellow)]
  } else {
    [#table.cell([#i%], fill: green)]
  }
}

#table(
  columns: 5,
  fill: (x, y) => {
    if (0 < x and 0 < y) {
      red
    } else {
      white
    }
  },
  table.header([*Element*], [*Classes*], [*Methods*], [*Lines*], [*Branches*]),
  ..for (e, covs) in coverages {
    ([#e], ..covs.map(c => toColoredTableCell(c)))
  }
)

Ermittelt wurde die Code Coverage durch Gradle `test` und die IntelliJ-Funktion `Run with Coverage`.

Auf das gesamte Projekt `de.dhbw` betrachtet ist die Code Coverage nicht besonders hoch mit 80% aller Klassen und 70% der Branches.

Hierfür gibt es folgende Betründungen:
- Oft werden Results zurückgegeben, welche durch einen Instanzenvergleich geprüft werden. Hierbei kann es technisch betrachtet mehr als die implementierten Fälle `Ok` und `Err` kommen. Somit wurden manche Branches für die syntaktische Vollständigkeit der jeweiligen Klasse erstellt, obwohl sie nicht auftreten können im normalen Betrieb.
- Entity-Klassen wurden nicht getestet, da `set`- und `get`-Methodentests nicht relevant sind.

== Fakes und Mocks
// [2 Beispiele mit UML und Begründung]

=== Beispiel 1
#figure(
  sourcecode[```
    @Test
    public void testeProcessSetErfolgreich() {
        Address a1 = new Address("r", 1);
        Word w = new Word();
        InstructionValue v1 = new InstructionValue(a1, null);
        Instruction instr = new Instruction("set", new InstructionValue[]{v1});

        ProcessingUnit mockUnit = mock(ProcessingUnit.class);
        when(mockUnit.read(a1)).thenReturn(Result.ok(new Word()));
        when(mockUnit.write(a1, w)).thenReturn(Result.ok());

        StorageManager sm = new StorageManager();
        Result<?> res = sm.process(new ProcessingUnit[]{mockUnit}, w, instr);

        assertTrue(res instanceof Result.Ok<?>);
    }
    ```],
  caption: "StorageManagerTest-Klasse Mockbeispiel",
)

`StorageManagerTest` verwendet in Test `testeProcessSetErfolgreich()` einen Mock für die verwendete `ProcessingUnit`. Dieser Test prüft ob der StorageManager die richtige Speicheradresse findet zu einer Speicherinstruktion (`SetInstruction` genannt). Da keine spezifische ProcessingUnit hier mitgetestet werden soll wird ein Mock verwendet mit entsprechender `when(...).then(...)`-Konfiguration. Siehe die StorageManager-Klasse unter @storagemanageruml.

=== Beispiel 2
#figure(
  image("assets/classes/ControlUnit.svg"),
  caption: "ControlUnit-Klasse UML",
) <controlunituml>

In der Testklasse `ControlUnitTest` für die Klasse `ControlUnit`, werden Mocks verwendet. Die Klasse bekommt über ihren Construktor mehrere Klassen übergeben (siehe @controlunituml). Diese wurden für die Tests alle durch Mocks umgesetzt, um gezieltes Testen der Funktionen zu ermöglichen.

= Kapitel 6: Domain Driven Design

== Ubiquitous Language

#table(
  columns: 3,
  table.header([*Bezeichnung*], [*Bedeutung*], [*Begründung*]),
  [Keyword], [Ist das erste Teil einer Nutzereingabe. Endet ab erstem Leerzeichen.], [],
  [Command], [Strukturierte Nutzereingabe. Unterteilt in Keyword und Argumente], [],
  [Instruction],
  [Ein oder mehrere Instructions entstehen aus einem Command. Diese werden genutzt als elementare Operationen auf der CPU],
  [],

  [InstructionValue], [Gehört zu einer Instruction. Enthält eine Address oder einen Word eines Commands], [],
  [Word],
  [Ist die Klasse die den niedrigsten Wert in der CPU symbolisiert. Enthält eine Liste an Wahr- und Falschwerten (Boolean), diese repräsentieren Bits. Ein Word ist ein Byte in dieser Konfiguration],
  [],

  [Address],
  [Jeder Speicherplatz in der CPU hat eine addressierbare Address. Sie ist definiert über ein Prefix (bspw. "r" für Register) und einen Index (bspw. "5" für die 6. Stelle innerhalb des Registers)],
  [],

  [StoredValue],
  [Ist ein besonderer Speicherplatz innerhalb der ControlUnit, welcher dafür genutzt wird um einen Wert zwischenzuspeichern für weitere Instructions.],
  [],

  [Unit],
  [Ist eine Klasse welche der ControlUnit unterstellt ist. Diese kann schreiben, lesen und Instructions auführen, sowie zurückgeben ob sie eine Instruction überhaupt ausführen kann.],
  [],

  [SubUnit],
  [SubUnits sind spezifisch für die ALU. Die ALU nutzt diese um die unterschiedlichen Teilgebiete der Rechenoperationen zu gliedern. Bspw. ArithmeticSubUnit für die Verarbeitung von Additionen und Subtraktionen oder LogicalSubUnit für Gleichheitsoperationen (bspw. "AND") und ähnliche.],
  [],

  [Operator],
  [Operator ist ebenfalls spezifisch für die ALU. Sie repräsentieren die einzelnen Operationen die die ALU ausführen kann, wie Addition und Subtraktion. Sie können zurückgeben ob sie eine Instruction verarbeiten können und sie im positiven Fall dann verarbeiten in einer weiteren Funktion.],
  [],

  [Decoder], [Ist zuständig dafür Commands in Instructions umzuwandeln], [],
  [ALU], [Ist eine Unit und ist zuständig für die arithmetischen Operationen der CPU], [],
  [Register], [Ist eine Unit welche nur Daten lesen und schreiben kann], [],
  [StorageManager],
  [Ist eine Klasse welche von der ControlUnit verwaltet wird. Sie ist zuständig für Instructions welche Daten verschieben (wie bpsw. "set", "get", "store"). Sie erhält Zugriff auf die ProcessingUnits und die StoredValue um Dateninstructions ausführen zu können.],
  [],
)
#todo[Begründung hinzufügen]

== Entities
// [UML, Beschreibung, Begründung]

// StorageSpace (durch Address)

#figure(
  image("assets/classes/StorageSpace.svg"),
  caption: "Entities UML",
) <entitiesuml>

Die in @entitiesuml gezeigte Klasse `StorageSpace` ist die einzige Klasse sie sich als Entity-Klasse ermitteln lässt. Sie zeichnet sich dadurch aus, das sie durch eine Adresse eindeutig identifizieren lässt und der gespeicherte Wert sich ändern kann.

== Value Objects
// [UML, Beschreibung, Begründung]

// Address, Command, Word, GetInstruction, SetInstruction, StoreInstruction, Instruction, InstructionValue, Result

#figure(
  image("assets/svg/ValueObjects.svg"),
  caption: "Value Objects UML",
)
#todo[Beschreibung hinzufügen]

== Repositories
// [UML, Beschreibung, Begründung]

Es wurde keine passende Klasse in der Rolle eines Repositories ermittelt.

== Aggregates
// [UML, Beschreibung, Begründung]

// ControlUnit, Decoder, StorageManager, ArithmeticSubUnit, CPUSimulator

#figure(
  image("assets/svg/Aggregates.svg"),
  caption: "Value Objects UML",
)
#todo[Beschreibung hinzufügen]

// Domain Service
// AddDecoder, SubtractDecoder, SetDecoder, GetDecoder, StoreDecoder, ArgDecoder, AddOperator, SubtractOperator, Operator

= Kapitel 7: Refactoring

== Code Smells

=== Code Smell 1
// [Beispiel, Lösungsvorschlag]

Der StorageManager enthält einen Code Smell. Dieser hat eine verhältnismässig große Methode `process`. Diese Verarbeitet alle drei Fälle `set`, `get` und `store`. Das bringt folgendes Problem mit sich. Es wird nicht das Single-Responsibility-Principle, sowie das Open-Closed-Principle erfüllt.

Verbessert werden kann das durch das Verteilen der Zuständigkeit auf einzelne Klassen. Somit würde der StorageManager eine Liste an Klassen halten, welche sich je um einen Fall kümmern. Diese Liste könnte einfach erweitert oder verkleinert werden.

=== Code Smell 2
// [Beispiel, Lösungsvorschlag]
#todo[Single-Responsibility-Principle bei OutputHandler]

== 2 Refactorings

=== Refactoring 1
// [Vorher/Nachher UML, Begründung]
#todo[StorageManager SET GET STORE]

=== Refactoring 2
// [Vorher/Nachher UML, Begründung]
#todo(
  position: "inline",
)[OutputHandler soll nicht mehr Result empfangen, dass soll eine andere Klasse vorher machen um nur noch den String weitergeben]

= Kapitel 8: Entwurfsmuster

== Entwurfsmuster: Builder
// [UML, Begründung]

#figure(
  image("/assets/classes/Command.svg"),
  caption: "Command UML",
) <commanduml>

Ein Command (siehe @commanduml) kann über einen Erbauer, den Commandbuilder, gebaut werden. Er ermöglicht das Commands sequenziell erstellt werden können.

== Entwurfsmuster: Strategie
// [UML, Begründung]

#figure(
  image("/assets/classes/InputHandler.svg"),
  caption: "InputHandler UML",
) <entwurfsmusterinputhandleruml>

Der InputHandler ist ein gutes Beispiel für das Entwurfsmuster der Strategie. Bei `InputHandler` handelt es sich um ein Interface, welches voraussetzt, das ein String bei Aufruf der `read`-Methode zurückgeliefert wird. Somit wird ermöglicht das unterschiedliche Eingabequellen einfach realisiert und eingebunden werden können.
