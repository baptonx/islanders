# Mock-Up
* Home montre le menu principal. On peut rentrer son pseudo, choisir notre map, visualiser les meilleurs scores... On peut également générer une map aléatoirement.
* Game représente la partie en cours. On peut voir le nombre de blocs disponibles, le score à atteindre, et il y a un bouton pour voir les meilleurs scores.

# Diagramme UML
* On a 3 commandes qui peuvent être Undoable, ces dernières implémentent donc l'interface Undoable. Elles prennent en paramètre les éléments dont elles ont besoin pour s'éxécuter.

# Diagrammes de séquence
* DiagSequence1 décrit le fonctionnement de la commande AddBlock lancée par le player. Le diagramme montre également la façon dont le score est calculé.
* DiagSequence2 décrit le fonctionnement de la commande RenamePlayer. Cette commande étant Undoable, on montre comment elle fonctionne. A noter qu'on ajoute la commande RenamePlayer dans le collecteur d'Undoable, situé dans la classe Game.
* DiagSequence3 décrit le fonctionnement de la route REST qui ajoute un score fait par un joueur sur une map.

Back-end : 

# Route pour récupérer les noms des cartes disponibles (et leur id)
GET api/v1/map/all_names => {"names" : [{"name" : map_name, "id" : map_id}, ...]}

# Route pour obtenir une map depuis l'id
GET api/v1/map/{id}  => {"map": {"id" : id,...}}

# Route pour obtenir les topScores d'une map depuis l'id
GET api/v1/map/topScores/{id} => {"topScores": [0,0,0,0,0]}

# Route pour ajouter le score d'un joueur sur une map
POST api/v1/scores/{score}/{player_name}/{id_map} => 200 OK 

# Route pour obtenir une map générée aléatoirement par le back-end
GET api/v1/map/random => {"map": {"id" : 45123, "name":"random", "topScores" : [0,0,0,0,0], "tabTiles":[...]}}

# Route pour créer une nouvelle map
POST api/v1/map
Body : {"name" : "nom", "tabTiles" : [...]} 

# Route pour ajouter la liste des commandes faites par un joueur durant une partie
POST api/v1/replays
Body : {"player_name" : "Paul", "map_id" : 741, "undos" : [{"putCityBlock" : { "position" : 74, "typeCityBlock" : 1}},...]}

# Route pour récupérer l'ensemble des id et le score des replays d'un joueur sur une map donnée par son id
GET api/v1/replays/{map_id} => {"replays" : [{"id" : 475487, "score" : 754}, ...]}

# Route pour récupérer l'ensemble des commandes faites par un joueur depuis l'id d'un replay
GET api/v1/replays/{id} => {"id" : 475487, "player_name" : "Paul", "map_id" : 741, "score" : 754, "undos" : [{"putCityBlock" : { "position" : 74, "typeCityBlock" : 1}},...]}