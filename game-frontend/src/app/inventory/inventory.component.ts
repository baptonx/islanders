import {Component, Input, OnInit} from '@angular/core';
import {InventoryService} from '../service/inventory.service';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit {

  private availableCityBlock: Array<number>;
  private typeCityBlock: Array<string>;

  constructor(private invetoryService: InventoryService) {
  }

  ngOnInit(): void {
    this.availableCityBlock = this.invetoryService.availableCityBlock;
    this.typeCityBlock = this.invetoryService.typeCityBlock;
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
  public getPathNameWithName(name: string): string {
    return 'assets/' + name + '.svg';
  }

  public inventoryOnClick(x: number): void {
    this.invetoryService.cityBlockSelected = x;
  }

}
