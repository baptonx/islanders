import {Component, Input, OnInit} from '@angular/core';
import {InventoryService} from '../service/inventory.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent{

  constructor(private inventoryService: InventoryService) {
  }
}
