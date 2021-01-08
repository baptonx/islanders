import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {GameService} from '../service/game.service';
import {InventoryComponent} from '../inventory/inventory.component';
import {InventoryService} from '../service/inventory.service';
import {AnonCmd, buttonBinder} from 'interacto';
import {MapService} from '../service/map.service';
import {Router} from '@angular/router';
import {MoveCityBlock} from '../model/move-city-block';
import {ClonerService} from '../service/cloner.service';
import {PutCityBlock} from '../model/put-city-block';
import {MoveCityBlockImpl} from '../model/move-city-block-impl';
import {PutCityBlockImpl} from '../model/put-city-block-impl';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent {

  constructor(public mapService: MapService, public gameService: GameService, public clonerService: ClonerService, private elem: ElementRef) {
  }

  ngAfterViewInit(): void {
    this.mapService.textScoreArray = new Array<HTMLDivElement>();
    const elemHTML = this.elem.nativeElement.getElementsByClassName('infoTile');
    console.log(elemHTML.length);
    for (let i = 0; i < elemHTML.length; i++) {
      this.mapService.textScoreArray.push(elemHTML.item(i) as HTMLDivElement);
    }
    // console.log(this.mapService.textScoreArray);
  }

  private getRandomInt(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }

  public getTileSvg(x: number, y: number): string {
    const type = this.mapService.map.tabTiles[y * 10 + x];
    return this.getPathNameWithName(this.mapService.inventoryService.typeName[type]);
  }

  public mouseEnter(x: number, y: number): void {
    const pos = y * 10 + x;
    if (this.mapService.map.tabTiles[pos] === 0 && this.mapService.inventoryService.cityBlockSelected !== undefined && this.mapService.inventoryService.getCityBlockRemaining(this.mapService.inventoryService.cityBlockSelected) > 0) {
      this.mapService.map.tabTiles[pos] = this.cityBlockToTypeTileTemporary(this.mapService.inventoryService.cityBlockSelected);
      this.mapService.textScoreArray[pos].innerText = String(this.mapService.inventoryService.arrayScoreCityBlock[this.mapService.inventoryService.cityBlockSelected]);



      const dict = this.mapService.inventoryService.tabDictionariesScore[this.mapService.inventoryService.cityBlockSelected];
      const radius = dict.get('radius');

      for (let yRadius = -radius; yRadius <= radius; yRadius++) {
        for (let xRadius = -radius; xRadius <= radius; xRadius++) {
          if (!(xRadius === 0 && yRadius === 0)) {
            const newY = y + yRadius;
            const newX = x + xRadius;
            if (newY >= 0 && newY < 10 && newX >= 0 && newX < 10) {
              const typeRadius = this.mapService.map.tabTiles[newY * 10 + newX];
              const scoreRadius = dict.get(this.mapService.inventoryService.typeName[typeRadius]);
              if (scoreRadius !== undefined) {
                this.mapService.textScoreArray[newY * 10 + newX].innerText = String(scoreRadius);
              }
              else {
                this.mapService.textScoreArray[newY * 10 + newX].innerText = '0';
              }
            }
          }
        }
      }
    }
  }

  public mouseLeave(x: number, y: number): void {
    const pos = y * 10 + x;
    if (this.mapService.map.tabTiles[pos] > 6 && this.mapService.inventoryService.cityBlockSelected !== undefined) {
      this.mapService.map.tabTiles[pos] = 0;
      this.mapService.resetTextArrayScore();
    }
  }

  public getWinterSvg(x: number, y: number): string {
    const type = this.mapService.map.tabTiles[y * 10 + x];
    return this.getWinterPathNameWithName(this.mapService.inventoryService.typeName[type]);
  }

  public getPathNameWithName(name: string): string {
    return 'assets/' + name + '.svg';
  }

  public getWinterPathNameWithName(name: string): string {
    return 'assets/' + name + '-winter.svg';
  }

  private cityBlockToTypeTile(typeCityBlock: number): number {
    const nameCityBlock = this.mapService.inventoryService.typeCityBlock[typeCityBlock];
    return this.mapService.inventoryService.typeName.indexOf(nameCityBlock);
  }

  private cityBlockToTypeTileTemporary(typeCityBlock: number): number {
    if (typeCityBlock === 0) {
      return 7;
    } else if (typeCityBlock === 1) {
      return 8;
    } else if (typeCityBlock === 2) {
      return 9;
    } else if (typeCityBlock === 3) {
      return 10;
    }
  }

  private typeTileToCityBlock(type: number): number {
    const name = this.mapService.inventoryService.typeName[type];
    return this.mapService.inventoryService.typeCityBlock.indexOf(name);
  }

  public addCityBlock(x: number, y: number): void {
    console.log(this.mapService.inventoryService.cityBlockSelected);
    // Check Move city block before
    if (this.mapService.typeMoveBlock !== undefined && this.mapService.hasMovedBlock === false) {
      const pos = y * 10 + x;
      if (this.mapService.inventoryService.typeName[this.mapService.map.tabTiles[pos]] === 'empty') {
        // Creation d'une commande
        this.gameService.undoCollector.commands.push(new MoveCityBlockImpl(this.mapService, x, y, this.clonerService));
        this.gameService.redoCollector.commands = [];
      }
    } else if (this.mapService.typeMoveBlock === undefined
      && this.mapService.inventoryService.cityBlockSelected !== undefined
      && this.mapService.inventoryService.availableCityBlock[this.mapService.inventoryService.cityBlockSelected] > 0) {
      const pos = y * 10 + x;
      if (this.mapService.inventoryService.typeName[this.mapService.map.tabTiles[pos]] === 'empty' || this.mapService.map.tabTiles[pos] > 6) {
        // Creation d'une commande
        this.gameService.undoCollector.commands.push(new PutCityBlockImpl(this.mapService, x, y, this.clonerService));
        this.gameService.redoCollector.commands = [];
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
    if (this.mapService.map.tabTiles[pos] > 2) {
      this.mapService.typeMoveBlock = this.mapService.map.tabTiles[pos];
      this.mapService.posMoveBlock = pos;
    }
  }
}
