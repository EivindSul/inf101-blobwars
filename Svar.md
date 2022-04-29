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