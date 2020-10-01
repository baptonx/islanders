# Mock-Up
 * Home montre le menu principal. On rentre son pseudo pour jouer, on sélectionne sa map, on peut atteindre le bouton replay pour voir les replays disponibles sur la map selectionnée et jouer sur une map aléatoire.
 * Play représente la partie en cours. On voit les cityBlocks avec leur nombre disponibles, le score actuel à atteindre, les top scores.

# DiagClasse
 * On possède des commandes Undoables (putCityBlock, moveCityBlock, renamePlayer) stockées dans un collecteur de commande. Les commandes ont accès aux données du game pour mettre à jour le score et les tiles.
 * AvalaibleCityBlock représente le nombre de cityblock valables avec quatres entiers correspondant aux quatres cityBlock
 * Dans cityBlock le dictionnaire représente les points attribués en fonction du nom des cityBlock autour d'elle-même

# Diagrammes de séquence
 * Put décrit le fonctionnement de la commande putCityBlock. Cette commande ajoute un cityBlock sur la tile de position passée en paramètre et met à jour le score.
 * RenamePlayer permet de renommer le joueur.
 * Score montre la route Rest utilisée pour stocker le score fait par un joueur (représenté par son nom) sur une certain map.
 * Move décrit le fonctionnement de la commande moveCityBlock. Cette commande prend en paramètre une position de départ et d'arrivée. Elle déplace le cityBlock entre les deux positions et met à jour le score.

# Back-end : 

# Route pour obtenir les noms des cartes disponibles (et leur id)
 * GET api/v1/maps => {"names" : [{"name" : map_name, "id" : map_id}, ...]}

# Route pour obtenir une map depuis l'id
 * GET api/v1/maps/{map_id}  => {"map": {"id" : id,...}}

# Route pour obtenir les topScores d'une map depuis l'id (il prend les cinq premiers scores de l'attribut scores)
 * GET api/v1/maps/topScores/{map_id} => {"topScores": [0,0,0,0,0]}

# Route pour ajouter le score d'un joueur sur une map
 * POST api/v1/maps/{map_id}/{player_name}/{score} => 200 OK 

# Route pour obtenir une map générée aléatoirement par le back-end
 * GET api/v1/maps/random => {"map": {"id" : 45123, "name":"random", "scores" : [5,4,3,3,3,.....], "tabTiles":[...]}}

# Route pour ajouter une nouvelle map
 * POST api/v1/maps
 * Body : {"name" : "nom", "tabTiles" : [...]} 

# Route pour ajouter la liste des commandes faites par un joueur durant une partie
 * POST api/v1/replays
 * Body : {"player_name" : "Paul", "map_id" : 741, "undos" : [{"putCityBlock" : { "position" : 74, "typeCityBlock" : 1}},...]}

# Route pour récupérer l'ensemble des id et le score des replays d'un joueur sur une map donnée par son id
 * GET api/v1/replays/{map_id} => {"replays" : [{"id" : 475487, "score" : 754}, ...]}

# Route pour récupérer l'ensemble des commandes faites par un joueur depuis l'id d'un replay
 * GET api/v1/replays/{replay_id} => {"id" : 475487, "player_name" : "Paul", "map_id" : 741, "score" : 754, "undos" : [{"putCityBlock" : { "position" : 74, "typeCityBlock" : 1}},...]}