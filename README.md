# Erstes Licht · First Light · Première lueur

Kostenlose App ohne Werbung, ohne Abo und ohne Konto: Menschen schenken einander ein „Licht“ – ein paar persönliche Worte,
im Browser verschlüsselt –, dazu jeden Tag eine wahre Geschichte und im Advent ein Kalender mit 24 wahren Weihnachtsgeschichten.
Auf Deutsch, Englisch und Französisch.

- Web-App: https://humane-weltformel.de/licht/
- Herausgeber: Edition Resonanz, Bernd Blasius

## Dieses Verzeichnis

`android/` ist die Android-Hülle für Google Play, eine Trusted Web Activity (erzeugt mit Bubblewrap 1.27.0,
androidbrowserhelper 2.7.4). Sie zeigt die Web-App im Vollbild. Benachrichtigungen kommen per Web-Push vom eigenen Server
und erscheinen unter dem Namen der App. Gebaut wird mit GitHub Actions (`.github/workflows/android.yml`), das unsignierte
Paket liegt danach im Zweig `pakete`. Signiert wird außerhalb von GitHub.

## Lizenz

Code: MIT-Lizenz (siehe LICENSE). Dateien aus der Bubblewrap-Vorlage stehen unter der Apache-Lizenz 2.0 (siehe Kopfzeilen der Dateien).
Texte, Bilder, Namen und Symbole von „Erstes Licht“: © Bernd Blasius, alle Rechte vorbehalten.
