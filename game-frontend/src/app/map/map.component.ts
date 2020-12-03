import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {GameService} from '../service/game.service';
import {InventoryComponent} from '../inventory/inventory.component';
import {InventoryService} from '../service/inventory.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit, OnInit {
  // Service Map
  public tabTiles: Array<number>;
  public nomJoueur: string;
  public score: number;
  public nextScore: number;

  // Service Inventory
  public availableCityBlock: Array<number>;
  private typeName: Array<string>;
  private typeCityBlock: Array<string>;

  // 4 dictionaries to calculate the scores
  private neighbourPointsCircus: Map<string, number> = new Map();
  private neighbourPointsHouse: Map<string, number> = new Map();
  private neighbourPointsFountain: Map<string, number> = new Map();
  private neighbourPointsWindTurbine: Map<string, number> = new Map();
  private tabDictionariesScore: Array<Map<string, number>> = new Array<Map<string, number>>(4);

  constructor(public gameService: GameService, private inventoryService: InventoryService) {
    // Initialisation of tabTiles
    this.tabTiles = new Array<number>(100);
    for (let i = 0; i < 100; i++) {
      this.tabTiles[i] = this.getRandomInt(3);
    }
    console.log(this.tabTiles);

    // Initialisation score
    this.score = 0;
    this.nextScore = 10;
  }

  ngOnInit(): void {
    this.nomJoueur = 'Paul';
    this.typeCityBlock = this.inventoryService.typeCityBlock;
    this.typeName = this.inventoryService.typeName;
    this.availableCityBlock = this.inventoryService.availableCityBlock;
    this.neighbourPointsCircus = this.inventoryService.neighbourPointsCircus;
    this.neighbourPointsFountain = this.inventoryService.neighbourPointsFountain;
    this.neighbourPointsHouse = this.inventoryService.neighbourPointsHouse;
    this.neighbourPointsWindTurbine = this.inventoryService.neighbourPointsWindTurbine;
    this.tabDictionariesScore = this.inventoryService.tabDictionariesScore;
  }

  ngAfterViewInit(): void {
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

  private cityBlockToTypeTile(typeCityBlock: number): number {
    const nameCityBlock = this.typeCityBlock[typeCityBlock];
    return this.typeName.indexOf(nameCityBlock);
  }

  private typeTileToCityBlock(type: number): number {
    const name = this.typeName[type];
    return this.typeCityBlock.indexOf(name);
  }

  public addCityBlock(x: number, y: number): void {
    if (this.inventoryService.cityBlockSelected !== undefined && this.availableCityBlock[this.inventoryService.cityBlockSelected] > 0) {
      const pos = y * 10 + x;
      if (this.typeName[this.tabTiles[pos]] === 'empty') {
        const t = this.cityBlockToTypeTile(this.inventoryService.cityBlockSelected);
        this.tabTiles[pos] = t;
        this.availableCityBlock[this.inventoryService.cityBlockSelected]--;
        console.log('score du cityBlock : ' + this.computeScore(x, y));
      }
    }
  }

  public computeScore(x: number, y: number): number {
    console.log('y : ' + y + ' x : ' + x);
    let scoreCityBlock = 0;
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
              scoreCityBlock += scoreRadius;
            }
          }
        }
      }
    }
    this.score += scoreCityBlock;
    this.updateScore();
    return scoreCityBlock;
  }

  public updateScore(): void {
    while (this.score > this.nextScore) {
      this.nextScore += 10;
      this.updateInventory();
    }
  }

  public updateInventory(): void {
    for (let i = 0; i < this.inventoryService.availableCityBlock.length; i++) {
      this.inventoryService.availableCityBlock[i]++;
    }
  }
}
