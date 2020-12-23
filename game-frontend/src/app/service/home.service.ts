import { Injectable } from '@angular/core';
import {MapService} from "./map.service";
import {MapImpl} from "../model/map-impl";

@Injectable({
  providedIn: 'root'
})
export class HomeService {
  public tabMap: Array<MapImpl>;
  public indexCurrentMap;

  constructor(public mapService: MapService) {
    this.tabMap = new Array<MapImpl>(3);
    for (let i = 0; i < this.tabMap.length; i++) {
      this.tabMap[i] = new MapImpl();
      this.tabMap[i].name = "Map" + i;
    }
    this.indexCurrentMap = 0;
  }

  public initialize(): void {
    this.indexCurrentMap = 0;
    this.changeMap(0);
  }

  public changeMap(index: number): void {
    this.mapService.map = this.tabMap[index];
  }
}
