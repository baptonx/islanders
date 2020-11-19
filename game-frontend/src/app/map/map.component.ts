import {AfterViewInit, Component} from '@angular/core';
import {GameService} from '../service/game.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {

  public tabTiles: Array<number>;

  constructor(public gameService: GameService) {
    this.tabTiles = new Array<number>(100);
    for (let i = 0; i < 100; i++) {
      this.tabTiles[i] = this.getRandomInt(3);
    }
    console.log(this.tabTiles);
    this.addCityBlock(3, 3, 0);
  }

  ngAfterViewInit(): void {
  }

  private getRandomInt(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }

  public addCityBlock(x: number, y: number, type: number): void {
    if (type === 0) {
      this.tabTiles[y * 10 + x] = 3;
    }
    else if (type === 1) {
      this.tabTiles[y * 10 + x] = 4;
    }
  }

  public getTileSvg(x: number, y: number): string {
    const type = this.tabTiles[y * 10 + x];
    if (type === 0) {
      return 'assets/empty.svg';
    }
    else if (type === 1) {
      return 'assets/tree.svg';
    }
    else if (type === 2) {
      return 'assets/water.svg';
    }
    else if (type === 3) {
      return 'assets/circus.svg';
    }
    else if (type === 4) {
      return 'assets/house.svg';
    }
  }

}
