#import "@preview/clean-dhbw:0.2.1": *
#import "acronyms.typ": acronyms
#import "glossary.typ": glossary

#show: clean-dhbw.with(
  title: "CPU Simulation",
  authors: (
    (name: "Marc Schillinger", student-id: "3449698", course: "TINF22B2", course-of-studies: "Informatik", company: (
      (name: "Atruvia AG", post-code: "76131", city: "Karlsruhe")
    )),
  ),
  type-of-thesis: "Programmentwurf",
  acronyms: acronyms, // displays the acronyms defined in the acronyms dictionary
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

// Edit this content to your liking

= Allgemeine Anmerkungen

- Es darf nicht auf andere Kapitel als Leistungsnachweis verwiesen werden.
- Alles muss in UTF-8 codiert sein (Text und Code).
- Schriftliche Aufgaben haben Vorrang vor mündlichen Aussagen.
- Alles (Code, Ausarbeitung etc.) muss ins Repository.
- Beispiele sollten vom aktuellen Stand genommen werden.
- Falls keine Negativ-Beispiele vorhanden sind, müssen zusätzliche Positiv-Beispiele gebracht werden.
- Code-Beispiele = Code in das Dokument kopieren.

---

= Kapitel 1: Einführung

== Übersicht über die Applikation
[Was macht die Applikation?]

== Wie startet man die Applikation?
[Schritt-für-Schritt Anleitung]

== Wie testet man die Applikation?
[Schritt-für-Schritt Anleitung]

---

= Kapitel 2: Clean Architecture

== Was ist Clean Architecture?
[Beschreibung]

== Analyse der Dependency Rule

=== Positiv-Beispiel
[UML und Analyse]

=== Negativ-Beispiel
[UML und Analyse]

== Analyse der Schichten

=== Schicht: [Name]
[UML, Aufgabe, Einordnung]

=== Schicht: [Name]
[UML, Aufgabe, Einordnung]

---

= Kapitel 3: SOLID

== Analyse Single-Responsibility-Principle (SRP)

=== Positiv-Beispiel
[UML, Beschreibung]

=== Negativ-Beispiel
[UML, Beschreibung, Lösungsvorschlag]

== Analyse Open-Closed-Principle (OCP)

=== Positiv-Beispiel
[UML, Analyse]

=== Negativ-Beispiel
[UML, Analyse, Lösungsvorschlag]

== Analyse LSP, ISP oder DIP

=== Positiv-Beispiel
[UML, Analyse]

=== Negativ-Beispiel
[UML, Analyse]

---

= Kapitel 4: Weitere Prinzipien

== Analyse GRASP: Geringe Kopplung

=== Positiv-Beispiel
[UML, Begründung]

=== Negativ-Beispiel
[UML, Verbesserungsvorschlag]

== Analyse GRASP: Hohe Kohäsion

[UML, Begründung]

== Don't Repeat Yourself (DRY)

[Commit angeben, vorher/nachher Code zeigen und Auswirkung begründen]

---

= Kapitel 5: Unit Tests

== 10 Unit Tests

| Unit Test | Beschreibung |
|:---|:---|
| Klasse=Methode | [Beschreibung] |
| ... | ... |

== ATRIP: Automatic
[Begründung]

== ATRIP: Thorough

=== Positiv-Beispiel
[Code, Analyse]

=== Negativ-Beispiel
[Code, Analyse]

== ATRIP: Professional

=== Positiv-Beispiel
[Code, Analyse]

=== Negativ-Beispiel
[Code, Analyse]

== Code Coverage
[Analyse]

== Fakes und Mocks
[2 Beispiele mit UML und Begründung]

---

= Kapitel 6: Domain Driven Design

== Ubiquitous Language

| Bezeichnung | Bedeutung | Begründung |
|:---|:---|:---|
| | | |
| | | |

== Entities
[UML, Beschreibung, Begründung]

== Value Objects
[UML, Beschreibung, Begründung]

== Repositories
[UML, Beschreibung, Begründung]

== Aggregates
[UML, Beschreibung, Begründung]

---

= Kapitel 7: Refactoring

== Code Smells

=== Code Smell 1
[Beispiel, Lösungsvorschlag]

=== Code Smell 2
[Beispiel, Lösungsvorschlag]

== 2 Refactorings

=== Refactoring 1
[Vorher/Nachher UML, Begründung]

=== Refactoring 2
[Vorher/Nachher UML, Begründung]

---

= Kapitel 8: Entwurfsmuster

== Entwurfsmuster: [Name]
[UML, Begründung]

== Entwurfsmuster: [Name]
[UML, Begründung]
