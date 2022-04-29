# Svar på spørsmål

*I denne filen legger du inn svar på spørsmålene som stilles i oppgaveteksten, eventuelt kommentarer og informasjon om kreative løsninger*

## Samarbeidet med:
*Brage Løvfall Aasen - baa027* på:
BlobWars klassen.

*Magnus Sponnich Brørby - vux018* på:
Klassediagram og tester.

*Marcus H - vix017* på:
Debugging i BlobWars.
   
## Oppgave 1

*Viktige klasser*
Viktige klasser er blant annet 
- Grid, siden alle spillene er basert på strukturen og metodene i Grid.
- Location, siden alle spillene bruker Location til å referere til brettet.
- GameGUI, eller alternativt ConsolePlayerInput, siden det går ikke an å spille uten en klasse til å håndtere input
- Naturligvis Main-klassene, siden noe må initialisere spillet. 

Mange av de andre klassene kunne blitt inkludert i disse, selv om det da hadde blitt repetitiv og uoversiktlig kode. 

*Begreper*
- Abstraction brukes i IGrid, siden dette er et interface. Game er en abstrakt klasse. Graphics er et interface. Input og TerminalInput er abstrakte klasser. AbstractPlayer er abstrakt. Player er et interface. 

- Encapsulation brukes over hele programmet. Lettest å notere seg er i BlobLocation, der man henter fromLoc og toLoc ved getter-metoder mange ganger i løpet av Blob Wars spillet. Selve dataen er satt til private, men getter-metodene er public. For eksempel BlobWars.score er også satt til public, selv om det implementert noe teller i gui'en eller terminalen. (Score telles for å bestemme vinner, da.)

- Inheritance brukes ved at klasser extender en annen klasse. For eksempel i AI implementeringen, der de forskjellige AI'ene utvider AbstractPlayer. 

- Composition brukes mellom MainMenu og Game til å starte opp spillet. 

- Polymorphism er i Game.java, siden det er en generisk klasse.

*Andre spill*
- 4 på rad kan enkelt utvides til så stort man har lyst til. 

- Sjakk kan veldig enkelt implementeres, og kan bruke BlobLocation for å håndtere input. Det trenger da en ny klasse for brikkene, men kan bruke mange av klassene som allerede finnes. 

- Tetris er bygd på et grid, det kan rimelig lett implementeres. 

- Tredimensjonale spill kan ikke bygges på samme strukturen. 

- Spill som krever et større kart enn det som vises begynner å bli vanskelig å implementere. For eksempel Super Mario, Mario kart, Pokemon, osv. 

*SOLID-prinsippene*
- Single Responibility priciple:
    BlobLocation har kun et ansvar, som er å relatere to plasseringer med hverandre. Det eneste som skjer i klassen er at man kan hente ut hver plassering. 

- Open-Closed Principle:
    Man vil aldri endre på AbstractPlayer, men den kan utvides ved arv. Samme med de forskjellige spillene, de endrer ikke på Game.java. 

- Liskov Substitution Principle:
    Man kan bruke to Location-objekter i stedet for en BlobLocation over alt untatt der input hentes. Der kunne den vært en liste med Locations da, så BlobLocation kan utelates. 

- Interface Segregation Principle:
    De forskjellige spillene følger dette prinsippet. Det gir mening at metoder som for eksempel sjekker om en brikke er på brettet er tilknyttet brettet, men for eksempel hvordan flytting av brikker fungerer er individuelt i hvert spill. Derfor finnes den ene funskjonen i GameBoard, men ikke den andre.

- Dependency Inversion Principle:
    Dette gjelder blant annet på forholdet mellom Game og spillene. Å legge til et nytt spill krever ikke endring i Game.java, så lenge spillet er i samme kategori som de andre spillene. For eksempel sjakk kan legges til uten å røre koden, fordi Game er helt uavhengig av hva som vil stå i sjakk.java.


## Oppgave 2

Klassediagram er inkludert i information-mappen.

For å implementere blobwars så trenger vi så klart en blobwars klasse for å implementere reglene til spillet, og vi trenger en ny klasse for å håndtere inputet. 
Input består av to plasseringer: 
- den brikken som skal flyttes, og 
- hvor den skal flyttes til. 
Denne klassen kaller vi BlobLocation. Her implementerer vi metoder for å hente ut location from, og location to. 

Vi må utvide både GUI og terminal-menyene for å tillate brukeren å starte blob wars spillet. 
GUI.Input.java og GameGUI.java må også få litt endringer for å tillate brukeren å velge flere ruter, slik at programmet tar imot to input og returnerer dem i et BlobLocation objekt. Dette er implementeringen av getBlobLocation, som viker nesten helt likt som getLocation. 

Etter alt dette kan det enkelt legges til at bruker velger vanskelighetsgrad, det er bare å kopiere og justere litt kode i menyene. 


## Oppgave 3

Skulle implementert random AI difficulty i terminal, men det ble vanskelig å jobbe med innebygde funksjoner der. Skulle gjøre at default verdi når man skal velge blir random AI, 0 blir dumbplayer, og alle andre tall blir alphabetaplayer, men det er vanskelig å få den til å bruke et tomt felt som input. 

Skulle gjerne brukt kode om igjen eller gjort den mer dynamisk i GUI main menu, der man velger AI vanskelighetsgrad (linje 143 og nedover). Her er det veldig repetitiv kode, og jeg liker egentlig ikke løsningen min der. Merker også at jeg har valgt veldig høye tall på AlphaBeta, siden den bruker evig lenge på å tenke. 

Det finnes også noen tester jeg kunne lagd for å enklere debugge spillet. Jeg hadde en feil i BlobWars.getPossibleMoves metoden, som gjorde at spillet ble avsluttet når eneste mulige trekk var å bevege brikken to bortover. For å løse dette kunne jeg godt ha skrevet en test som satte opp alle brikkene til spiller X på en linje, og alle til spiller O for å blokkere, slik at jeg enklere kunne sjekket om løsningen min fungerte. Sånn jeg løste det, så måtte jeg spille blob wars i et par minutter hver gang jeg skulle sjekke om jeg hadde fikset problemet. En test for å sjekke at brikker ble flippet som de skulle kunne også vært nyttig, men dette var veldig lett å teste ved å bare spille. 

Jeg brukte eksisterende funksjoner bra i BlobWars, spesielt allNeighbors metoden fra Location. Denne brukte jeg i getPossibleMoves og getOpponentNeighbors. Jeg skrev også en metode i Location for å få absolutt distanse mellom to plasseringer, absoluteDistanceTo.