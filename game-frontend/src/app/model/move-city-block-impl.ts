import {Undoable} from 'interacto';
import {MapService} from '../service/map.service';
import {ClonerService} from '../service/cloner.service';
import {MapImpl} from './map-impl';
import {Command} from './command';


export class MoveCityBlockImpl extends Command implements Undoable {
  posBefore: number;
  posAfter: number;
  mementoHasMovedBlock: boolean;
  mementoTypeMoveBlock: number | undefined;
  mementoPosMoveBlock: number;
  //map: MapImpl;

  public mementoAvailableCityBlock: Array<number>;
  public mementoCityBlockSelected = undefined;

  public mementoNomJoueur: string;
  public mementoScore: number;
  public mementoNextScore: number;

  public mementoGameOver: boolean;

  public constructor(private mapService: MapService, public x: number, public y: number, private clonerService: ClonerService, public doMemExec = true) {
    super();
    if (doMemExec) {
      this.createMemento();
      this.execution();
    }
  }

  // Create the memento for undoing the command
  protected createMemento() {
    // We copy the current state of the MapService
    this.mementoHasMovedBlock = this.mapService.hasMovedBlock;
    this.mementoTypeMoveBlock = this.mapService.typeMoveBlock;
    this.mementoPosMoveBlock = this.mapService.posMoveBlock;
    //this.map = this.clonerService.deepClone(this.mapService.map);
    this.mementoAvailableCityBlock = Object.assign([], this.mapService.inventoryService.availableCityBlock);
    this.mementoCityBlockSelected = this.mapService.inventoryService.cityBlockSelected;
    this.mementoNomJoueur = this.mapService.infogameService.nomJoueur;
    this.mementoScore = this.mapService.infogameService.score;
    this.mementoNextScore = this.mapService.infogameService.nextScore;
    this.mementoGameOver = this.mapService.isGameOver;

    this.posBefore = -1;
    this.posAfter = -1;
  }


  protected execution(): void {
    this.redo();
  }

  undo(): void {
    this.mapService.hasMovedBlock = this.mementoHasMovedBlock;
    this.mapService.typeMoveBlock = this.mementoTypeMoveBlock;
    this.mapService.posMoveBlock = this.mementoPosMoveBlock;

    const pos = this.y * 10 + this.x;
    this.mapService.map.tabTiles[pos] = 0;
    this.mapService.map.tabTiles[this.mementoPosMoveBlock] = this.mementoTypeMoveBlock;

    this.mapService.inventoryService.availableCityBlock[0] = this.mementoAvailableCityBlock[0];
    this.mapService.inventoryService.availableCityBlock[1] = this.mementoAvailableCityBlock[1];
    this.mapService.inventoryService.availableCityBlock[2] = this.mementoAvailableCityBlock[2];
    this.mapService.inventoryService.availableCityBlock[3] = this.mementoAvailableCityBlock[3];
    this.mapService.inventoryService.cityBlockSelected = this.mementoCityBlockSelected;

    this.mapService.infogameService.nomJoueur = this.mementoNomJoueur;
    this.mapService.infogameService.score = this.mementoScore;
    this.mapService.infogameService.nextScore = this.mementoNextScore;

    this.mapService.isGameOver = this.mementoGameOver;
  }

  redo(): void {
    const pos = this.y * 10 + this.x;
    this.mapService.map.tabTiles[this.mementoPosMoveBlock] = 0;
    this.mapService.map.tabTiles[pos] = this.mementoTypeMoveBlock;
    this.computeScore(this.x, this.y);
    this.mapService.hasMovedBlock = true;
    this.mapService.typeMoveBlock = undefined;
    this.mapService.updateGameOver();
    this.mapService.resetTextArrayScore();
  }

  getUndoName(): string {
    return 'Undo move';
  }

  private cityBlockToTypeTile(typeCityBlock: number): number {
    const nameCityBlock = this.mapService.inventoryService.typeCityBlock[typeCityBlock];
    return this.mapService.inventoryService.typeName.indexOf(nameCityBlock);
  }

  private typeTileToCityBlock(type: number): number {
    const name = this.mapService.inventoryService.typeName[type];
    return this.mapService.inventoryService.typeCityBlock.indexOf(name);
  }

  public computeScore(x: number, y: number): number {
    console.log('y : ' + y + ' x : ' + x);
    const pos = y * 10 + x;
    const type = this.mapService.map.tabTiles[pos];
    const typeCityBlock = this.typeTileToCityBlock(type);
    const dict = this.mapService.inventoryService.tabDictionariesScore[typeCityBlock];
    const radius = dict.get('radius');
    console.log('radius : ' + radius);
    let scoreCityBlock = this.mapService.inventoryService.arrayScoreCityBlock[typeCityBlock];


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

}
