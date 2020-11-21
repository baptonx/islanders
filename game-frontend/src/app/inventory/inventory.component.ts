import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  availableCityBlock: Array<number> = [12, 1, 2, 8];

  constructor() {
  }

  ngOnInit(): void {
  }

  public getCityBlockSvg(x: number): string {
    if (x === 0) {
      return 'assets/house.svg';
    } else if (x === 1) {
      return 'assets/fountain.svg';
    } else if (x === 2) {
      return 'assets/wind-turbine.svg';
    } else if (x === 3) {
      return 'assets/circus.svg';
    }
  }

  public getCityBlockRemaining(x: number): number {
    return this.availableCityBlock[x];
  }
}
