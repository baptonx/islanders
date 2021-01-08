import {ElementRef, Injectable, ViewChild} from '@angular/core';
import {InventoryService} from './inventory.service';
import {InfogameService} from './infogame.service';
import {MapImpl} from '../model/map-impl';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  public textScoreArray: Array<HTMLDivElement>;
  map: MapImpl;
  hasMovedBlock: boolean;
  typeMoveBlock: number | undefined;
  posMoveBlock: number;
  isGameOver: boolean;
  winterMode = false;

  constructor(public inventoryService: InventoryService, public infogameService: InfogameService, public router: Router) {
    this.initialize();
  }

  public initialize(): void {
    this.hasMovedBlock = false;
    this.typeMoveBlock = undefined;
    this.posMoveBlock = 0;
    this.isGameOver = false;
  }

  public resetTextArrayScore(): void {
    for (const e of this.textScoreArray) {
      e.innerText = '';
    }
  }

  public toggleWinterMode(): void {
    this.winterMode = !this.winterMode;
  }

  public updateGameOver(): boolean {

    let inventoryAvailable = false;

    // Si un element dans inventaire
    for (const cityBlock of this.inventoryService.availableCityBlock) {
      if (cityBlock > 0) {
        inventoryAvailable = true;
      }
    }

    if (inventoryAvailable === false && this.hasMovedBlock === true) {
      // console.log('game over : inventaire nul');
      this.isGameOver = true;
      return true;
    }

    // inventaire disponible ou moveBlock, cherche case vide
    for (let i = 0; i < this.map.tabTiles.length; i++) {
      if (this.map.tabTiles[i] === 0) {
        // console.log('Pas de game over : tile disponible et inventaire disponible ou move block');
        this.isGameOver = false;
        return false;
      }
    }

    // inventaire disponible mais pas de case vide
    // console.log('Game over : inventaire disponible ou move block mais pas de tile disponible');
    this.isGameOver = true;
    return true;
  }
}
