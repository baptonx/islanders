import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  public typeName = ['empty', 'tree', 'water', 'circus', 'house', 'fountain', 'wind-turbine', 'circus', 'house', 'fountain', 'wind-turbine'];
  public typeCityBlock = ['circus', 'house', 'fountain', 'wind-turbine'];
  public availableCityBlock = [0, 1, 0, 0];
  public arrayScoreCityBlock = [10, 5, 4, 8];
  public neighbourPointsCircus: Map<string, number> = new Map();
  public neighbourPointsHouse: Map<string, number> = new Map();
  public neighbourPointsFountain: Map<string, number> = new Map();
  public neighbourPointsWindTurbine: Map<string, number> = new Map();
  public tabDictionariesScore: Array<Map<string, number>> = new Array<Map<string, number>>(4);
  public cityBlockSelected = undefined;

  constructor() {
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

  public getCityBlockRemaining(x: number): number {
    return this.availableCityBlock[x];
  }

  public getPathNameWithName(name: string): string {
    return 'assets/' + name + '.svg';
  }
  public getWinterPathNameWithName(name: string): string {
    return 'assets/' + name + '-winter.svg';
  }

  public initialize(): void {
    this.availableCityBlock = [0, 1, 0, 0];
    this.cityBlockSelected = undefined;
  }

  /***public getCityBlockSvg(x: number): string {
    if (x === 0) {
      return 'assets/house.svg';
    } else if (x === 1) {
      return 'assets/fountain.svg';
    } else if (x === 2) {
      return 'assets/wind-turbine.svg';
    } else if (x === 3) {
      return 'assets/circus.svg';
    }
  }***/
}
