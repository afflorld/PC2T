# PC2T project
## Databázový systém zaměstnanců

To run this project use

```Zsh
// To compile the project
make compile

// To run the project
make run
```

## Assignment

Předpokládejme databázi zaměstnanců technologické firmy. Každý zaměstnanec má svéidentifikační číslo, jméno, příjmení a rok narození. Každý zaměstnanec si vede seznam spolupracovníků, kde u každého eviduje úroveň spolupráce (špatná, průměrná dobrá).

### Skupiny zaměstnanců

* a) Datoví analytici – dokážou určit, s kterých spolupracovníkem mají nejvíce společných spolupracovníků.

* b) Bezpečnostní specialisté – dokážou vyhodnotit rizikovost spolupráce na základě počtu spolupracovníků a průměrné kvality spolupráce a vypočítat rizikové skóre (vlastní algoritmus). 

Pri přijetí do firmy je každý zaměstnanec zařazen do jedné skupiny a nelze jej později přesunout.

### Funkcionalita programu

- [X] Přidání zaměstnance – uživatel vybere skupinu, zadá jméno, příjmení a rok narození. ID je přiděleno automaticky.
- [X] Přidání spolupráce – uživatel zadá ID zaměstnance, ID kolegy a úroveň spolupráce.
- [X] Odebrání zaměstnance – odstranění z databáze včetně všech vazeb.
- [X] Vyhledání zaměstnance dle ID – výpis základních informací a statistik spolupráce.
- [X] Spuštění dovednosti zaměstnance dle jeho skupiny.
- [X] Abecední výpis zaměstnanců podle příjmení ve skupinách.
- [X] Statistiky – převažující kvalita spolupráce a zaměstnanec s nejvíce vazbami.
- [X] Výpis počtu zaměstnanců ve skupinách.
- [ ] Uložení zaměstnance do souboru.
- [ ] Načtení zaměstnance ze souboru.
- [ ] Uložení všech dat do SQL databáze při ukončení programu.
- [ ] Načtení všech dat z SQL databáze při spuštění programu.

### Požadavky na program
Využití principů objektově orientovaného programování (OOP).
Použití alespoň jedné abstraktní třídy nebo rozhraní.
Použití alespoň jedné dynamické datové struktury.
