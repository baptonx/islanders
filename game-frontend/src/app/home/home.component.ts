import {Component, OnInit} from '@angular/core';
import {InventoryService} from '../service/inventory.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private typeName: Array<string>;
  public tabTiles: Array<number>;

  constructor(private inventoryService: InventoryService) {
    this.tabTiles = new Array<number>(100);
    for (let i = 0; i < 100; i++) {
      this.tabTiles[i] = this.getRandomInt(3);
    }
    this.typeName = this.inventoryService.typeName;
  }

  ngOnInit(): void {
  }

  private getRandomInt(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }

  public getTileSvg(x: number, y: number): string {
    const type = this.tabTiles[y * 10 + x];
    return this.getPathNameWithName(this.typeName[type]);
  }

  public getPathNameWithName(name: string): string {
    return 'assets/' + name + '.svg';
  }

}
