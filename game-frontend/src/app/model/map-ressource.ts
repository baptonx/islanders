import {Score} from './score';

export class MapRessource {
  scores: Array<Score>;
  tabTiles: Array<any>;
  commandsCollectors: Array<string>;

  constructor(private name: string) {
    this.scores = new Array<Score>();
    this.tabTiles = new Array<string>();
    this.commandsCollectors = new Array<string>();
  }

  public setTabTiles(tab: Array<string>): void {
    this.tabTiles = tab;
  }

  public setCommandsCollectors(tab: Array<string>): void {
    this.commandsCollectors = tab;
  }

  public setScores(tab: Array<Score>): void {
    this.scores = tab;
  }
  public generateTabTiles(): Array<string> {
    const tab = new Array<any>();
    for (let i = 0; i < 100; i++) {
      switch (this.tabTiles[i]) {
        case 0:
          tab.push({type: 'game.model.Grass'});
          break;
        case 1:
          tab.push({type: 'game.model.Tree'});
          break;
        case 2:
          tab.push({type: 'game.model.Water'});
          break;
      }
    }
    return tab;
  }

  public toMapRessource(): MapRessource {
    const mapRessource = new MapRessource(this.name);
    mapRessource.setTabTiles(this.generateTabTiles());
    mapRessource.setScores([new Score(), new Score()]);
    mapRessource.setCommandsCollectors(this.commandsCollectors);
    return mapRessource;
  }


}
