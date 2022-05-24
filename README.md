# Wargame Prosjekt

## Seksjoner

1. Innledning
2. Kravspesifikasjon
   1. Del 1
   2. Del 2
   3. Del 3
3. Design
4. Implementasjon
5. Prosess
6. Refleksjon
7. Konklusjon


## Innledning


## Kravspesifikasjon
Oppgaven var gitt ut i 3 deler utover semesteret.
Hver del hadde rundt 5 del-oppgaver som ledet gjennom utviklingen av programmet.
Delene under beskriver kravene for hver av oppgavene.

* Enhetstesting
  * Enhetstester må opprettes for delen av koden som er sentralt for å oppfylle sentrale krav.
    Det er ikke nødvendig å utvikle enhetstester direkte knyttet til brukergrensesnittet,
    ettersom det ikke er tema i faget.
* Unntakshåndtering
  * Uønskede hendelser og tilstander som forstyrrer normal flyt skal håndteres på en god måte.
* Maven
  * Prosjektet skal være et Maven-prosjekt. (JDK 11 eller høyere) Filer lagres i standard oppsett.
* Versjonskontroll
  * Koden skal være koplet mot et sentralt repo.

### Del 1
* Maven og Git
  * Opprette et Maven-prosjekt med fornuftig group og artefact-ID.
    Det skal være mulig å bygge, teste og kjøre Maven uten feil.
  * Legge til prosjektet under lokal versjonskontroll.
    Hver av oppgavene skal ha minst ett commit som beskriver endringene på en kort og konsis måte.
    ".git" katalogen skal være med i innlevering.
* Superklassen Unit
  * Det skal opprettes en superklasse som representerer en enkel enhet.
    Den abstrakte klassen skal ha beskrivende egenskaper og enkelte metoder
    for å kalkulere et angrep mot en annen enhet.
* InfantryUnit, CavalryUnit, RangedUnit og CommanderUnit
  * Implementere spesialiserte typer enheter som bruker Unit som superklasse.
    De skal implementere abstrakte klasser for individuelle bonuser.
    Disse bonusene kan i enkelte tilfeller ha spesiel logikk.
* Army-klassen
  * En arme skal være en klasse for å samle og håndtere en liste med enheter.
    Klassen skal implementere metoder som blir brukbar for senere simulering/kamp.
* Simulering av et slag med klassen Battle
  * Implementere en klasse for å holde på to armeer og utføre en simulering.
    Tilfeldige enheter skal angripe hverandre og fjernes ved 0 hp, til en arme er tom.

### Del 2
* Versjonskontroll med git
  * Prosjektet skal underlegges et sentralt repo.
* Nye metoder i Army-klassen
  * Metodene skal kunne returnere alle enheter av en spesifik type, 
    ved bruk av streams og lambda-uttrykk.
* Filhåndtering
  * Det skal være mulig å lagre og lese en fil som skal følge et spesifkt format.
    Filtypen skal være ".csv" (Comma Seperated Values).
* GUI
  * Utarbeide skisser av et grafisk brukergrensesnitt med fornuftige og oversiktligeegenskaper.
    Grensesnittet skal implementeres i del 3.

### Del 3
* UnitFactory
  * Klassen skal anvende Factory design pattern med to metoder for å produsere enheter.
    Man skal kunne opprette en eller n antall enheter med type, navn og helseverdi.
* Terrain
  * Innføre terreng som variabel i utregningen til enhetene.
    Ulike terreng (hill, plains, forest) skal ha ulik effekt på de ulike enhets-typene.
* GUI
  * Implementere brukergrensesnitt skissene i javaFX ved bruk av FXML eller manuell kode.
    Man må også kunne velge terreng før man kjører simuleringen.
* Videre arbeid
  * Oppfordring for å være kreativ å implementere videre.

## Design


## Implementasjon


## Prosess
#### "Du dømmer ikke din nestes commit tider"


## Refleksjon


## Konklusjon
