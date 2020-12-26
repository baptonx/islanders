import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {GameService} from '../service/game.service';
import {InventoryComponent} from '../inventory/inventory.component';
import {InventoryService} from '../service/inventory.service';
import {AnonCmd, buttonBinder} from 'interacto';
import {MapService} from '../service/map.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent{
  /*
  // Service Map
  public tabTiles: Array<number>;
  public nomJoueur: string;
  public score: number;
  public nextScore: number;
  public tabScores: Map<string, number> = new Map();
   */

  /*
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
  private cityBlockSelected;
   */

  constructor(public mapService: MapService) {
  }

  private getRandomInt(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }

  public getTileSvg(x: number, y: number): string {
    const type = this.mapService.map.tabTiles[y * 10 + x];
    return this.getPathNameWithName(this.mapService.inventoryService.typeName[type]);
  }

  public getPathNameWithName(name: string): string {
    return 'assets/' + name + '.svg';
  }

  private cityBlockToTypeTile(typeCityBlock: number): number {
    const nameCityBlock = this.mapService.inventoryService.typeCityBlock[typeCityBlock];
    return this.mapService.inventoryService.typeName.indexOf(nameCityBlock);
  }

  private typeTileToCityBlock(type: number): number {
    const name = this.mapService.inventoryService.typeName[type];
    return this.mapService.inventoryService.typeCityBlock.indexOf(name);
  }

  public addCityBlock(x: number, y: number): void {
    console.log(this.mapService.inventoryService.cityBlockSelected);
    //Check Move city block before
    if (this.mapService.typeMoveBlock !== undefined && this.mapService.hasMovedBlock === false) {
      const pos = y * 10 + x;
      if (this.mapService.inventoryService.typeName[this.mapService.map.tabTiles[pos]] === 'empty') {
        this.mapService.map.tabTiles[this.mapService.posMoveBlock] = 0;
        this.mapService.map.tabTiles[pos] = this.mapService.typeMoveBlock;
        this.computeScore(x, y);
        this.mapService.hasMovedBlock = true;
        this.mapService.typeMoveBlock = undefined;
      }
    }
    else if (this.mapService.typeMoveBlock === undefined && this.mapService.inventoryService.cityBlockSelected !== undefined && this.mapService.inventoryService.availableCityBlock[this.mapService.inventoryService.cityBlockSelected] > 0) {
      const pos = y * 10 + x;
      if (this.mapService.inventoryService.typeName[this.mapService.map.tabTiles[pos]] === 'empty') {
        const t = this.cityBlockToTypeTile(this.mapService.inventoryService.cityBlockSelected);
        this.mapService.map.tabTiles[pos] = t;
        this.mapService.inventoryService.availableCityBlock[this.mapService.inventoryService.cityBlockSelected]--;
        console.log('score du cityBlock : ' + this.computeScore(x, y));
      }
    }
  }

  public computeScore(x: number, y: number): number {
    console.log('y : ' + y + ' x : ' + x);
    let scoreCityBlock = 0;
    const pos = y * 10 + x;
    const type = this.mapService.map.tabTiles[pos];
    const typeCityBlock = this.typeTileToCityBlock(type);
    const dict = this.mapService.inventoryService.tabDictionariesScore[typeCityBlock];
    const radius = dict.get('radius');
    console.log('radius : ' + radius);


    for (let yRadius = -radius; yRadius <= radius; yRadius++) {
      for (let xRadius = -radius; xRadius <= radius; xRadius++) {
        if (!(xRadius === 0 && yRadius === 0)) {
          const newY = y + yRadius;
          const newX = x + xRadius;
          if (newY >= 0 && newY < 10 && newX >= 0 && newX < 10) {
            // console.log('newY : ' + newY + ' newX : ' + newX);
            const typeRadius = this.mapService.map.tabTiles[newY * 10 + newX];
            const scoreRadius = dict.get(this.mapService.inventoryService.typeName[typeRadius]);
            if (scoreRadius !== undefined) {
              scoreCityBlock += scoreRadius;
            }
          }
        }
      }
    }
    this.mapService.infogameService.score += scoreCityBlock;
    this.updateScore();
    return scoreCityBlock;
  }

  public updateScore(): void {
    while (this.mapService.infogameService.score >= this.mapService.infogameService.nextScore) {
      this.mapService.infogameService.nextScore += 10;
      this.updateInventory();
    }
  }

  public updateInventory(): void {
    for (let i = 0; i < this.mapService.inventoryService.availableCityBlock.length; i++) {
      this.mapService.inventoryService.availableCityBlock[i]++;
    }
  }

  public moveBlock(x: number, y: number): void {
    const pos = y * 10 + x;
    this.mapService.typeMoveBlock = this.mapService.map.tabTiles[pos];
    this.mapService.posMoveBlock = pos;
  }
}
