import {Component, Input, OnInit} from '@angular/core';
import {InventoryService} from '../service/inventory.service';
import {MapService} from '../service/map.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent {

  constructor(private inventoryService: InventoryService, public mapService: MapService) {
  }

  public inventoryOnClick(x: number): void {
    this.inventoryService.cityBlockSelected = x;
    this.mapService.typeMoveBlock = undefined;
  }
}
