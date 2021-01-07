import {ElementRef, Injectable, ViewChild} from '@angular/core';
import {InventoryService} from './inventory.service';
import {InfogameService} from './infogame.service';
import {MapImpl} from '../model/map-impl';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  map: MapImpl;
  hasMovedBlock: boolean;
  typeMoveBlock: number|undefined;
  posMoveBlock: number;
  isGameOver: boolean;

  constructor(public inventoryService: InventoryService, public infogameService: InfogameService, public router: Router) {
    this.initializeMoveBlock();
  }

  public initializeMoveBlock(): void {
    this.hasMovedBlock = false;
    this.typeMoveBlock = undefined;
    this.posMoveBlock = 0;
    this.isGameOver = false;
  }
}
