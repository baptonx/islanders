import {InventoryService} from "../service/inventory.service";
import {InfogameService} from "../service/infogame.service";

export class MapImpl {
  public tabTiles: Array<number>;
  public name: string;
  public tabScores: Array<Array<number|string>>;

  constructor() {
    this.tabTiles = this.generateRandomMap();
    this.name = "nomParDefaut";
    this.tabScores = new Array<Array<number|string>>(5);

    this.tabScores[0] = new Array<number | string>(2);
    this.tabScores[0][0] = '-'; this.tabScores[0][1] = 0;
    this.tabScores[1] = new Array<number | string>(2);
    this.tabScores[1][0] = '--'; this.tabScores[1][1] = 0;
    this.tabScores[2] = new Array<number | string>(2);
    this.tabScores[2][0] = '---'; this.tabScores[2][1] = 0;
    this.tabScores[3] = new Array<number | string>(2);
    this.tabScores[3][0] = '----'; this.tabScores[3][1] = 0;
    this.tabScores[4] = new Array<number | string>(2);
    this.tabScores[4][0] = '-----'; this.tabScores[4][1] = 0;
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
}