import {ElementRef, Injectable, ViewChild} from '@angular/core';
import {InventoryService} from './inventory.service';
import {InfogameService} from './infogame.service';
import {MapImpl} from '../model/map-impl';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  map: MapImpl;

  constructor(public inventoryService: InventoryService, public infogameService: InfogameService) {
    this.map = new MapImpl();
  }
}
