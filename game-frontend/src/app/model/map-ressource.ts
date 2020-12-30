import {Score} from './score';
import {MapImpl} from './map-impl';

export class MapRessource {
  topScores: Array<Score>;
  scores: Array<Score>;
  tabTiles: Array<object>;
  commandsCollectors: Array<string>;
  name: string;

  constructor() {
    this.scores = new Array<Score>();
    this.tabTiles = new Array<object>();
    this.commandsCollectors = new Array<string>();
  }

  public setTabTiles(tab: Array<string>): void {
  }

  public setCommandsCollectors(tab: Array<string>): void {
    this.commandsCollectors = tab;
  }

  public setScores(tab: Array<Score>): void {
    this.scores = tab;
  }
  public generateTabTiles(): Array<number> {
    const tab = new Array<number>();
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

  public toMapImpl(): MapImpl {
    const mapImpl = new MapImpl();
    mapImpl.setTabTiles(this.generateTabTiles());
    mapImpl.setScores([new Score(), new Score()]);
    mapImpl.setCommandsCollectors(this.commandsCollectors);
    return mapImpl;
  }


}
