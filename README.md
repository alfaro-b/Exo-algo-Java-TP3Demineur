# TP2 - Démineur 

## Présentation
Ce projet est réalisé dans le cadre d'un exercice d'algorithmique en Java.   
L'objectif est de développer une application permettant de jouer au démineur.   
Le projet est réalisé sans programmation orientée objet.   

## Enoncé
L’exercice à réaliser est un jeu de démineur simplifié, dont les dimensions sont de 6 lignes par 12 colonnes.    
9 bombes sont présentes dans la grille, leur position étant aléatoire.    

La grille et le nombre de bombes restant à découvrir sont affichés au démarrage, ainsi qu'après chaque essai du joueur.    
Le joueur indique la case qu'il souhaite voir découverte en entrant ses coordonnées.    
Plusieurs cas sont alors à prendre en compte, suivant le contenu de la case, pour le réaffichage de la grille qui suit :    
• bombe : un message indique au joueur qu'il a perdu la partie,    
et dans la grille affichée, toutes les cases contenant des bombes sont découvertes, puis le jeu s'arrête ;    
• vide : le nombre des bombes se trouvant dans les huit cases contiguës est affiché dans la case alors découverte lors du nouvel affichage de la grille    
(0 peut être remplacé par un vide) ;    
en outre, s'il s'agissait de la dernière case vide à découvrir, un message indique au joueur qu'il a gagné, et le jeu s'arrête ;    

En fonction du temps disponible, une ou plusieurs des fonctionnalités suivantes pourront être ajoutées :    
• lors de la découverte d'une case pour laquelle aucune bombe ne se trouve dans les cases adjacentes,    
cette case, ainsi que toutes celles dans le même cas qui lui sont directement ou indirectement reliées, seront découvertes ;    
puis, toutes les cases directement reliées aux cases venant d'être découvertes le seront à leur tour    
(ces dernières cases formant une frontière entourant les premières cases) ;    
• le joueur peut indiquer, après avoir spécifié les coordonnées d'une case, l'action qu'il souhaite voir réaliser sur celle-ci, parmi :    
◦ la découvrir (même traitement précédemment décrit),    
◦ indiquer qu'elle contient une bombe    
(il faudra alors indiquer à la fin de la partie, que le joueur ait gagné ou pas, les bombes qui avaient été incorrectement indiquées) ;    
• Proposer de faire varier les dimensions de la grille et de choisir parmi plusieurs niveaux de difficultés    
(le nombre de bombes variant alors selon ces 2 paramètres).
