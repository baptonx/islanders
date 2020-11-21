import {AfterViewInit, Component} from '@angular/core';
import {GameService} from '../service/game.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {

  public tabTiles: Array<number>;

  private typeName: Array<string>;
  private typeCityBlock: Array<string>;

  // 4 dictionaries to calculate the scores
  private neighbourPointsCircus: Map<string, number> = new Map();
  private neighbourPointsHouse: Map<string, number> = new Map();
  private neighbourPointsFountain: Map<string, number> = new Map();
  private neighbourPointsWindTurbine: Map<string, number> = new Map();
  private tabDictionariesScore: Array<Map<string, number>> = new Array<Map<string, number>>(4);

  constructor(public gameService: GameService) {
    // Initialisation of tabTiles
    this.tabTiles = new Array<number>(100);
    for (let i = 0; i < 100; i++) {
      this.tabTiles[i] = this.getRandomInt(3);
    }
    console.log(this.tabTiles);

    // Initialisation of typeName
    this.typeName = ['empty', 'tree', 'water', 'circus', 'house', 'fountain', 'wind-turbine'];
    this.typeCityBlock = ['circus', 'house', 'fountain', 'wind-turbine'];

    // Initialisation of dictionaries
    this.neighbourPointsCircus.set('circus', -20);
    this.neighbourPointsCircus.set('house', 10);
    this.neighbourPointsCircus.set('radius', 3);

    this.neighbourPointsHouse.set('tree', 4);
    this.neighbourPointsHouse.set('house', 5);
    this.neighbourPointsHouse.set('circus', 8);
    this.neighbourPointsHouse.set('fountain', 10);
    this.neighbourPointsHouse.set('wind-turbine', -10);
    this.neighbourPointsHouse.set('radius', 1);

    this.neighbourPointsFountain.set('tree', 4);
    this.neighbourPointsFountain.set('house', 5);
    this.neighbourPointsFountain.set('circus', 6);
    this.neighbourPointsFountain.set('radius', 1);

    this.neighbourPointsWindTurbine.set('tree', -5);
    this.neighbourPointsWindTurbine.set('house', -7);
    this.neighbourPointsWindTurbine.set('water', 8);
    this.neighbourPointsWindTurbine.set('radius', 1);

    this.tabDictionariesScore[this.typeCityBlock.indexOf('circus')] = this.neighbourPointsCircus;
    this.tabDictionariesScore[this.typeCityBlock.indexOf('house')] = this.neighbourPointsHouse;
    this.tabDictionariesScore[this.typeCityBlock.indexOf('fountain')] = this.neighbourPointsFountain;
    this.tabDictionariesScore[this.typeCityBlock.indexOf('wind-turbine')] = this.neighbourPointsWindTurbine;
  }

  ngAfterViewInit(): void {
  }

  private getRandomInt(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }

  public getTileSvg(x: number, y: number): string {
    const type = this.tabTiles[y * 10 + x];
    return 'assets/' + this.typeName[type] + '.svg';
  }

  private cityBlockToTypeTile(typeCityBlock: number): number {
    const nameCityBlock = this.typeCityBlock[typeCityBlock];
    return this.typeName.indexOf(nameCityBlock);
  }

  private typeTileToCityBlock(type: number): number {
    const name = this.typeName[type];
    return this.typeCityBlock.indexOf(name);
  }

  public addCityBlock(x: number, y: number, typeCityBlock: number): void {
    const pos = y * 10 + x;
    if (this.typeName[this.tabTiles[pos]] === 'empty') {
      const t = this.cityBlockToTypeTile(typeCityBlock);
      this.tabTiles[pos] = t;
      console.log(this.computeScore(x, y));
    }
  }

  public computeScore(x: number, y: number): number {
    console.log('y : ' + y + ' x : ' + x);
    let score = 0;
    const pos = y * 10 + x;
    const type = this.tabTiles[pos];
    const typeCityBlock = this.typeTileToCityBlock(type);
    const dict = this.tabDictionariesScore[typeCityBlock];
    const radius = dict.get('radius');
    console.log('radius : ' + radius);


    for (let yRadius = -radius; yRadius <= radius; yRadius++) {
      for (let xRadius = -radius; xRadius <= radius; xRadius++) {
        if (!(xRadius === 0 && yRadius === 0)) {
          const newY = y + yRadius;
          const newX = x + xRadius;
          if (newY >= 0 && newY < 10 && newX >= 0 && newX < 10) {
            // console.log('newY : ' + newY + ' newX : ' + newX);
            const typeRadius = this.tabTiles[newY * 10 + newX];
            const scoreRadius = dict.get(this.typeName[typeRadius]);
            if (scoreRadius !== undefined) {
              score += scoreRadius;
            }
          }
        }
      }
    }
    return score;
  }
}
