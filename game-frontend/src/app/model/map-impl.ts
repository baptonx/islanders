import {InventoryService} from "../service/inventory.service";
import {InfogameService} from "../service/infogame.service";

export class MapImpl {
  public tabTiles: Array<number>;
  public name: string;

  constructor() {
    this.tabTiles = this.generateRandomMap();
    this.name = "nomParDefaut";
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
