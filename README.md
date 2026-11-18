# PHONE STUDIO — prototype 0.1

Prototype Android (Kotlin + Jetpack Compose) pour commencer le launcher PHONE STUDIO.

## Compiler en ligne avec GitHub

1. Crée un dépôt GitHub vide nommé `PHONE-STUDIO`.
2. Dans le dépôt, choisis **Add file > Upload files**.
3. Décompresse cette archive sur ton ordinateur et envoie tout le contenu du dossier `PHONE-STUDIO` dans le dépôt, y compris le dossier caché `.github`.
4. Valide avec **Commit changes**.
5. Ouvre l'onglet **Actions**. Le workflow **Build PHONE STUDIO APK** démarre automatiquement après l'envoi. Tu peux aussi le lancer avec **Run workflow**.
6. Quand la compilation réussit (coche verte), ouvre l'exécution, descends à **Artifacts**, puis télécharge `phone-studio-debug-apk`.
7. Décompresse l'artefact et transfère `app-debug.apk` sur ton Pixel 6a pour l'installer. Android peut demander d'autoriser l'installation depuis cette source.

## Modifier le code dans le navigateur

Dans GitHub, ouvre un fichier et clique sur le crayon **Edit** pour le modifier. Pour ouvrir un éditeur web complet, appuie sur `.` depuis la page du dépôt sur ordinateur : cela ouvre github.dev.

## État du prototype

L'écran d'accueil, un tiroir d'applications de démonstration, un éditeur rapide, un éditeur complet, le choix de couleur, un interrupteur d'effet verre et un réglage de grille sont présents.

Les icônes sont encore des exemples visuels. Le lancement des vraies applications, la sauvegarde des thèmes, les widgets système, les fonctions IA, les fonds d'écran automatisés et les animations physiques restent à développer.

## Compatibilité

`minSdk 26`, `targetSdk 35`, `compileSdk 36`. La compilation cloud utilise JDK 17 et Gradle 8.13.
