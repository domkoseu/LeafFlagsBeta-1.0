# LeafFlags

Java (Paper 1.21.11) port tveho Skript souboru pro vlajky hracu podle GeoIP zeme.

## Build

```
mvn clean package
```

Vysledny JAR: `target/LeafFlags.jar` - hod ho do `plugins/` vedle PlaceholderAPI
(vyzaduje take expanzi `geolocation`, stejne jako drivejsi Skript pouzival
`%%geolocation_countryCode%%`).

## Prikazy (stejne jako v originale)

- `/setflag <kod> <hash>` - ulozi/aktualizuje texturu vlajky
- `/delflag <kod>` - smaze ulozenou vlajku
- `/flagtest <kod>` - zobrazi vlajku v chatu (jen hrac)

Tab-completion je stejna jako drive: `/setflag` nabizi kody BEZ vlajky,
`/delflag` a `/flagtest` nabizi kody, ktere uz vlajku MAJI.

## PlaceholderAPI - %flags%

- `%flags%` / `%flags_code%` -> dvoupismenny kod zeme hrace, napr. `cz`
  (prazdny retezec, pokud hrac jeste nema prirazenou vlajku)
- `%flags_has%` -> `yes` / `no`

Dulezite: PAPI vraci vzdy jen cisty text, ne bohaty Component, takze
`%flags%` NEMUZE zobrazit samotnou ikonu hlavy - tu porad resi primo
scoreboard-team suffix (FlagApplier), stejne jako to delal puvodni skript.
Pokud potrebujes vlajku jako skutecnou ikonu v necem, co cte MiniMessage
(napr. TAB), plugin navic (pokud je nainstalovany plugin "MiniPlaceholders")
registruje `<flags_flag>` a `<flags_code>`, ktere uz vraci realnou ikonu.

## Poznamky k portu

- `Component.object(...)` / `ObjectContents.playerHead()...` (embed hlavy
  primo v textu) vyzaduje Minecraft 1.21.9+ / Adventure 4.25.0+ - 1.21.11
  to splnuje.
- Seznam kodu zemi a logika "on join -> wait 1s -> applyFlag" jsou 1:1
  prevzate z puvodniho Skriptu.
- Data se ukladaji do `plugins/LeafFlags/flags.yml` (misto Skript promenne
  `{flags::*}`).
