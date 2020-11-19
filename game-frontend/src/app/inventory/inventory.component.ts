import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  availableCityBlock: Array<number> = [0, 0, 0, 0];

  constructor() {
  }

  ngOnInit(): void {
  }

}
