import {ElementRef, Injectable, ViewChild} from '@angular/core';
import {InventoryService} from "./inventory.service";

@Injectable({
  providedIn: 'root'
})
export class MapService {
  public tabTiles: Array<number>;
  public nomJoueur: string;
  public score: number;
  public nextScore: number;
  public tabScores: Map<string, number> = new Map();

  constructor(public inventoryService: InventoryService) {
    // Initialisation of tabTiles
    this.tabTiles = new Array<number>(100);
    for (let i = 0; i < 100; i++) {
      this.tabTiles[i] = this.getRandomInt(3);
    }
    console.log(this.tabTiles);

    // Initialisation score
    this.score = 0;
    this.nextScore = 10;
    this.nomJoueur = 'Paul';
    this.tabScores.set('Paul', 10);
    this.tabScores.set('Hugo', 20);
  }

  private getRandomInt(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }
}
