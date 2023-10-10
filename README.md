# Správa záverečných prác

![Java 1.8](https://img.shields.io/badge/Java-1.8-red)
![EclipseLink 2.7.11](https://img.shields.io/badge/EclipseLink-2.7.11-blue)
![PostgreSQL Driver](https://img.shields.io/badge/PostgreSQL-42.5.4-green)
![Jersey JAX-RS](https://img.shields.io/badge/Jersey-2.39.1-orange)

Tento projekt je rozdelený do dvoch častí, pričom prvá časť sa zaoberá implementáciou backendu pomocou technológie JPA s pripojením na SQL databázu a druhá časť sa týka implementácie jednoduchej webovej aplikácie s REST API podľa definovanej špecifikácie.

## Časť 1: JPA Backend
### Funkcionalita
Táto časť projektu sa zaoberá implementáciou aplikácie pre správu záverečných prác na vysokej škole. Aplikácia zabezpečuje CRUD operácie nad nasledujúcimi entitami: Študent, Pedagóg a Záverečná práca. Medzi týmito entitami sú dodržané nasledujúce vzťahy:

* Študent môže mať zapísanú maximálne jednu záverečnú prácu.
* Pedagóg môže mať vypísaných viacero záverečných prác, ale aj žiadnu.
* Záverečná práca musí mať práve jedného pedagóga a môže mať najviac jedného študenta vypracovateľa.

## Časť 2: REST API

### Funkcionalita

Druhá časť projektu zahŕňa implementáciu jednoduchej webovej aplikácie, ktorá publikuje REST API podľa definovanej [**špecifikácie**](./src/main/resources/openapi3.spec.yaml). Aplikácia zabezpečuje nasledujúce funkcie:

* CRUD operácie cez publikované webové služby.
* Webové služby pre priradenie a odovzdanie záverečnej práce.
* Webové služby pre vyhľadanie záverečných prác podľa platných kritérií (napr. podľa študenta alebo učiteľa).

### Špecifikácia
* Aplikácia publikuje REST webové služby podľa dodanej [**OpenAPI 3 špecifikácie**](./src/main/resources/openapi3.spec.yaml).
* Pre komunikáciu sa používa protokol HTTP/1.1.
* Formát prenášaných objektov je application/json s UTF-8 kódovaním.
