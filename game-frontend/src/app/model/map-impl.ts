import {InventoryService} from '../service/inventory.service';
import {InfogameService} from '../service/infogame.service';
import {Score} from './score';
import {MapRessource} from './map-ressource';

export class MapImpl {
  public tabTiles: Array<any>;
  public name: string;
  public tabScores: Array<Array<number | string>>;
  private scores: Array<Score>;
  private commandsCollectors: Array<string>;
  private topScores: Array<Score>;

  constructor() {
    this.name = 'Beerus';
    this.scores = [];
    this.tabTiles = Array<any>();
    this.commandsCollectors = Array<string>();
    this.topScores = [];

    this.tabScores = new Array<Array<number | string>>(5);
    this.tabScores[0] = new Array<number | string>(2);
    this.tabScores[0][0] = '-';
    this.tabScores[0][1] = 0;
    this.tabScores[1] = new Array<number | string>(2);
    this.tabScores[1][0] = '--';
    this.tabScores[1][1] = 0;
    this.tabScores[2] = new Array<number | string>(2);
    this.tabScores[2][0] = '---';
    this.tabScores[2][1] = 0;
    this.tabScores[3] = new Array<number | string>(2);
    this.tabScores[3][0] = '----';
    this.tabScores[3][1] = 0;
    this.tabScores[4] = new Array<number | string>(2);
    this.tabScores[4][0] = '-----';
    this.tabScores[4][1] = 0;
  }

  private getRandomInt(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }

  public generateRandomMap(): Array<number> {
    const tab = new Array<number>(100);
    for (let i = 0; i < 100; i++) {
      tab[i] = this.getRandomInt(3);
    }
    return tab;
  }

  public generateTabTiles(): Array<any> {
    const tab = new Array<any>(100);
    for (let i = 0; i < 100; i++) {
      switch (this.getRandomInt(3)) {
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
    console.log(tab);
    return tab;
  }


  public toMapRessource(): MapRessource {
    const mapRessource = new MapRessource(this.name);
    mapRessource.setTabTiles(this.generateTabTiles());
    mapRessource.setScores([new Score(), new Score()]);
    mapRessource.setCommandsCollectors(this.commandsCollectors);
    return mapRessource;
  }

  public setTabTiles(numbers: Array<number>): void {
    this.tabTiles = numbers;
  }

  public adaptTabTiles(): void {
    const tab = new Array<number>();
    this.tabTiles.forEach((tile) => {
      switch (JSON.stringify(tile)) {
        case '{"type":"game.model.Grass"}':
          tab.push(0);
          break;
        case '{"type":"game.model.Tree"}':
          tab.push(1);
          break;
        case '{"type":"game.model.Water"}':
          tab.push(2);
          break;
      }
    });
    this.tabTiles = tab;
  }

  public setScores(scores1: Array<Score>): void {
    this.scores = scores1;
  }

  public setCommandsCollectors(cc: Array<string>): void {
    this.commandsCollectors = cc;
  }

  public getTabTiles(): Array<any> {
    return this.tabTiles;
  }

}
