import {Score} from './score';
import {MapImpl} from './map-impl';
import {Grass} from './grass';
import {Tile} from './tile';
import {CommandCollector} from './command-collector';

export class MapRessource {
  scores: Array<Score>;
  tabTiles: Array<Tile>;
  commandsCollectors: Array<CommandCollector>;

  constructor(public name: string) {
    this.scores = new Array<Score>();
    this.tabTiles = new Array<Grass>();
    this.tabTiles.push(new Grass());
    this.commandsCollectors = new Array<string>();
  }

  public setTabTiles(tab: Array<Tile>): void {
    this.tabTiles = tab;
  }

  public setCommandsCollectors(tab: Array<CommandCollector>): void {
    this.commandsCollectors = tab;
  }

  public setScores(tab: Array<Score>): void {
    this.scores = tab;
  }
  public generateTabTiles(): Array<number> {
    const tab = new Array<any>();
    for (let i = 0; i < 100; i++) {
      switch (this.tabTiles[i]) {
        case {type: 'game.model.Grass'}:
          tab.push(0);
          break;
        case {type: 'game.model.Tree'}:
          tab.push(1);
          break;
        case {type: 'game.model.Water'}:
          tab.push(2);
          break;
      }
    }
    return tab;
  }

}
