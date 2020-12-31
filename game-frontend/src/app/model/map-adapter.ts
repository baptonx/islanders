import {MapImpl} from './map-impl';
import {Score} from './score';
import {MapRessource} from './map-ressource';
import {Tile} from './tile';
import {Grass} from './grass';
import {Tree} from './tree';
import {Water} from './water';
import {Command} from './command';
import {CommandRename} from './command-rename';
import {PutCityBlockImpl} from './put-city-block-impl';
import {PutCityBlock} from './put-city-block';
import {MoveCityBlockImpl} from './move-city-block-impl';
import {MoveCityBlock} from './move-city-block';

export class MapAdapter {

  public static commandImplToCommand(command: Command): Command {
    if (command instanceof CommandRename) {
      return command;
    } else if (command instanceof PutCityBlockImpl) {
      const pcbi: PutCityBlockImpl = command as PutCityBlockImpl;
      const pcb = new PutCityBlock(pcbi.x, pcbi.y);
      pcb.mementoAvailableCityBlock = pcbi.mementoAvailableCityBlock;
      pcb.mementoCityBlockSelected = pcbi.mementoCityBlockSelected;
      pcb.mementoNomJoueur = pcbi.mementoNomJoueur;
      pcb.mementoHasMovedBlock = pcbi.mementoHasMovedBlock;
      pcb.mementoScore = pcbi.mementoScore;
      pcb.mementoNextScore = pcbi.mementoNextScore;
      pcb.mementoPosMoveBlock = pcbi.mementoPosMoveBlock;
      pcb.typeCityBlock = pcbi.typeCityBlock;
      pcb.position = pcbi.position;
      pcb.map = MapAdapter.numbersToTiles(pcbi.map.tabTiles);
      console.log(JSON.stringify(pcb));
    } else if (command instanceof MoveCityBlockImpl) {
      const pcbi: MoveCityBlockImpl = command as MoveCityBlockImpl;
      const pcb = new MoveCityBlock(pcbi.x, pcbi.y);
      pcb.mementoAvailableCityBlock = pcbi.mementoAvailableCityBlock;
      pcb.mementoCityBlockSelected = pcbi.mementoCityBlockSelected;
      pcb.mementoNomJoueur = pcbi.mementoNomJoueur;
      pcb.mementoHasMovedBlock = pcbi.mementoHasMovedBlock;
      pcb.mementoScore = pcbi.mementoScore;
      pcb.mementoNextScore = pcbi.mementoNextScore;
      pcb.mementoPosMoveBlock = pcbi.mementoPosMoveBlock;
      pcb.posAfter = pcbi.posAfter;
      pcb.posBefore = pcbi.posBefore;
      pcb.map = MapAdapter.numbersToTiles(pcbi.map.tabTiles);
      console.log(pcb);
    }
  }

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

  public static numbersToTiles(tiles: Array<number>): Array<Tile> {
    const tab = new Array<Tile>();
    tiles.forEach((tile) => {
      switch (tile) {
        case 0: {
          tab.push(new Grass());
          break;
        }
        case 1: {
          tab.push(new Tree());
          break;
        }
        case 2: {
          tab.push(new Water());
          break;
        }
        default: {
          tab.push(new Grass());
        }
      }
    });
    return tab;
  }

  public static tilesToNumbers(tiles: Array<Tile>): Array<number> {
    const tab = new Array<number>();
    tiles.forEach((tile) => {
        switch (tile.type) {
          case 'game.model.Grass': {
            tab.push(0);
            break;
          }
          case 'game.model.Tree': {
            tab.push(1);
            break;
          }
          case 'game.model.Water': {
            tab.push(2);
            break;
          }
          default: {
            tab.push(0);
          }
        }
      }
    );
    return tab;
  }
}
