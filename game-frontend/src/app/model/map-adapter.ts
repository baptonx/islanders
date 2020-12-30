import {MapImpl} from './map-impl';
import {Score} from './score';
import {MapRessource} from './map-ressource';
import {Tile} from './tile';
import {Grass} from './grass';
import {Tree} from './tree';
import {Water} from './water';

export class MapAdapter {

  public static mapRessourceToMapImpl(map: MapRessource): MapImpl {
    const mapImpl = new MapImpl(map.name);
    mapImpl.setTabTiles(this.tilesToNumbers(map.tabTiles));
    mapImpl.setScores(map.scores);
    mapImpl.setCommandsCollectors(map.commandsCollectors);
    return mapImpl;
  }

  public static mapImplToMapRessource(map: MapImpl): MapRessource {
    const mapRessource = new MapRessource(map.name);
    mapRessource.setTabTiles(this.numbersToTiles(map.tabTiles));
    mapRessource.setScores(map.scores);
    mapRessource.setCommandsCollectors(map.commandsCollectors);
    return mapRessource;
  }

  private static numbersToTiles(numbers: Array<number>): Array<Tile> {
    const tab = new Array<Tile>();
    numbers.forEach((n) => {
        switch (n) {
          case 0:
            tab.push(new Grass());
            break;
          case 1:
            tab.push(new Tree());
            break;
          case 2:
            tab.push(new Water());
            break;
        }
      }
    );
    return tab;
  }


  private static tilesToNumbers(tiles: Array<Tile>): Array<number> {
    const tab = new Array<number>();
    tiles.forEach((tile) => {
        switch (tile.type) {
          case 'game.model.Grass':
            tab.push(0);
            break;
          case 'game.model.Tree':
            tab.push(1);
            break;
          case 'game.model.Water':
            tab.push(2);
            break;
        }
      }
    );
    return tab;
  }
}
