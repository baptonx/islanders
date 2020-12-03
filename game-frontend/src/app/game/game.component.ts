import { Component, OnInit } from '@angular/core';
import { InventoryComponent } from '../inventory/inventory.component';
import {MapComponent} from '../map/map.component';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
