import {ElementRef, Injectable, ViewChild} from '@angular/core';
import {InventoryService} from './inventory.service';
import {InfogameService} from './infogame.service';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  public tabTiles: Array<number>;

  constructor(public inventoryService: InventoryService, public infogameService: InfogameService) {
    // Initialisation of tabTiles
    this.tabTiles = new Array<number>(100);
    for (let i = 0; i < 100; i++) {
      this.tabTiles[i] = this.getRandomInt(3);
    }
  }

  private getRandomInt(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }
}
