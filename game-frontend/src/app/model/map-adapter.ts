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
    mapImpl.setTabTiles(this.tilesToNumbers(mapImpl.tabTiles));
    mapImpl.setScores(map.scores);
    mapImpl.setCommandsCollectors(map.commandsCollectors);
    return mapImpl;
  }

  public static mapImplToMapRessource(map: MapImpl): MapRessource {
    const mapRessource = new MapRessource(map.name);
    mapRessource.setTabTiles(this.generateTabTiles());
    mapRessource.setScores(map.scores);
    mapRessource.setCommandsCollectors(map.commandsCollectors);
    return mapRessource;
  }

  private static numbersToTiles(tiles: Array<Tile>): Array<number> {
    const tab = new Array<number>();
    tiles.forEach((tile) => {
        if (tile instanceof Grass) {
          tab.push(0);
        } else if (tile instanceof Tree) {
          tab.push(1);
        } else {
          tab.push(2);
        }
      }
    );
    return tab;
  }

  private static tilesToNumbers(tiles: Array<Tile>): Array<number> {
    const tab = new Array<number>();
    tiles.forEach((tile) => {
        if (tile instanceof Grass) {
          tab.push(0);
        } else if (tile instanceof Tree) {
          tab.push(1);
        } else {
          tab.push(2);
        }
      }
    );
    return tab;
  }
}
