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
import {CommandRenameImpl} from "./command-rename-impl";
import {InfogameService} from "../service/infogame.service";
import {MapService} from "../service/map.service";
import {ClonerService} from "../service/cloner.service";

export class MapAdapter {

  public static commandToCommandImpl(command: Command, infoGameService: InfogameService, mapService: MapService, clonerService: ClonerService): Command {
    if (command.type === 'game.model.CommandRename') {
      const cr: CommandRename = command as CommandRename;
      const cri = new CommandRenameImpl(infoGameService, cr.nouveauNomJoueur, false);
      cri.mementoNomJoueur = cr.mementoNomJoueur;
      return cri;
    } else if (command.type === 'game.model.PutCityBlock') {
      const pcb: PutCityBlock = command as PutCityBlock;
      const pcbi = new PutCityBlockImpl(mapService, pcb.x, pcb.y, clonerService, false);
      pcbi.position = pcb.position;
      pcbi.typeCityBlock = pcb.typeCityBlock;
      pcbi.mementoHasMovedBlock = pcb.mementoHasMovedBlock;
      pcbi.mementoTypeMoveBlock = pcb.mementoTypeMoveBlock;
      pcbi.mementoPosMoveBlock = pcb.mementoPosMoveBlock;

      pcbi.mementoAvailableCityBlock = pcb.mementoAvailableCityBlock;
      pcbi.mementoCityBlockSelected = pcb.mementoCityBlockSelected;

      pcbi.mementoNomJoueur = pcb.mementoNomJoueur;
      pcbi.mementoScore = pcb.mementoScore;
      pcbi.mementoNextScore = pcb.mementoNextScore;

      pcbi.mementoGameOver = pcb.mementoGameOver;
      return pcbi;
    } else if (command.type === 'game.model.MoveCityBlock') {
      const pcb: MoveCityBlock = command as MoveCityBlock;
      const pcbi = new MoveCityBlockImpl(mapService, pcb.x, pcb.y, clonerService, false);
      pcbi.posAfter = pcb.posAfter;
      pcbi.posBefore = pcb.posBefore;

      pcbi.mementoHasMovedBlock = pcb.mementoHasMovedBlock;
      pcbi.mementoTypeMoveBlock = pcb.mementoTypeMoveBlock;
      pcbi.mementoPosMoveBlock = pcb.mementoPosMoveBlock;

      pcbi.mementoAvailableCityBlock = pcb.mementoAvailableCityBlock;
      pcbi.mementoCityBlockSelected = pcb.mementoCityBlockSelected;

      pcbi.mementoNomJoueur = pcb.mementoNomJoueur;
      pcbi.mementoScore = pcb.mementoScore;
      pcbi.mementoNextScore = pcb.mementoNextScore;

      pcbi.mementoGameOver = pcb.mementoGameOver;
      return pcbi;
    } else {
      return null;
    }
  }

  public static commandImplToCommand(command: Command): Command {
    if (command instanceof CommandRenameImpl) {
      const cri: CommandRenameImpl = command as CommandRenameImpl;
      const cr = new CommandRename(cri.nouveauNomJoueur);
      cr.mementoNomJoueur = cri.mementoNomJoueur;
      return cr;
    } else if (command instanceof PutCityBlockImpl) {
      const pcbi: PutCityBlockImpl = command as PutCityBlockImpl;
      const pcb = new PutCityBlock(pcbi.x, pcbi.y);
      pcb.mementoAvailableCityBlock = pcbi.mementoAvailableCityBlock;
      pcb.mementoCityBlockSelected = pcbi.mementoCityBlockSelected;
      pcb.mementoNomJoueur = pcbi.mementoNomJoueur;
      pcb.mementoHasMovedBlock = pcbi.mementoHasMovedBlock;
      pcb.mementoScore = pcbi.mementoScore;
      pcb.mementoTypeMoveBlock = pcbi.mementoTypeMoveBlock;
      pcb.mementoNextScore = pcbi.mementoNextScore;
      pcb.mementoPosMoveBlock = pcbi.mementoPosMoveBlock;
      pcb.typeCityBlock = pcbi.typeCityBlock;
      pcb.position = pcbi.position;
      pcb.mementoGameOver = pcbi.mementoGameOver;
      // console.log(JSON.stringify(pcb));
      return pcb;
    } else if (command instanceof MoveCityBlockImpl) {
      const pcbi: MoveCityBlockImpl = command as MoveCityBlockImpl;
      const pcb = new MoveCityBlock(pcbi.x, pcbi.y);
      pcb.posBefore = pcbi.posBefore;
      pcb.posAfter = pcbi.posAfter;

      pcb.mementoHasMovedBlock = pcbi.mementoHasMovedBlock;
      pcb.mementoTypeMoveBlock = pcbi.mementoTypeMoveBlock;
      pcb.mementoPosMoveBlock = pcbi.mementoPosMoveBlock;

      pcb.mementoAvailableCityBlock = pcbi.mementoAvailableCityBlock;
      pcb.mementoCityBlockSelected = pcbi.mementoCityBlockSelected;
      pcb.mementoNomJoueur = pcbi.mementoNomJoueur;
      pcb.mementoScore = pcbi.mementoScore;
      pcb.mementoNextScore = pcbi.mementoNextScore;
      pcb.mementoGameOver = pcbi.mementoGameOver;
      // console.log(pcb);
      return pcb;
    } else {
      return null;
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
