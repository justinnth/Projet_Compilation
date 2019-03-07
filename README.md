# Projet Compilation
Projet en compilation visant à créer un compilateur et un analyseur lexical.

#### Auteurs
Justin North\
Benjamin Cligny\
Quentin Dulery

## Différentes classes

### Moteur
Classe représentant le moteur d'analyse lexical, c'est lui qui traite
l'automate qui lui est fournit ainsi que les mots qui lui sont fournit par la suite.

### Etat
Classe représentant un Etat de l'automate, le moteur se sert
des ces différentes classe pour construire son analyseur.

### Transition
Classe représentant les transitions liées à un état. C'est grâce à cette classe que
l'on peut connaître la sortie généré par l'automate après avoir entré un mot.
