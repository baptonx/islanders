import {Injectable, OnInit} from '@angular/core';
import {MapService} from './map.service';
import {MapImpl} from '../model/map-impl';
import {LeaderboardService} from './leaderboard.service';
import {observable, Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BackendService} from './backend.service';

@Injectable({
  providedIn: 'root'
})
export class HomeService {
  public tabMap: Array<MapImpl>;
  public mapNames: Array<string>;
  public tabMapTestFrontEnd: Array<MapImpl>;
  public indexCurrentMap;

  constructor(public mapService: MapService, public leaderboardService: LeaderboardService, public backendService: BackendService) {
    //  ICI remplir tabMap avec le back-end
    this.tabMap = new Array<MapImpl>();
    this.mapNames = new Array<string>();

    this.indexCurrentMap = 0;

    //  Uniquement pour les tests du front-end :
    this.tabMapTestFrontEnd = new Array<MapImpl>(3);
    for (let i = 0; i < this.tabMapTestFrontEnd.length; i++) {
      this.tabMapTestFrontEnd[i] = new MapImpl();
      this.tabMapTestFrontEnd[i].name = 'Map' + i;
    }
  }


  public initialize(): void {
    this.indexCurrentMap = 0;
    this.changeMap(0);
  }

  public changeMap(index: number): void {
    console.log(this.tabMap.length);
    if (this.tabMap.length > 0) {
      console.log('change map > 0');
      this.mapService.map = this.tabMap[index];
      // this.leaderboardService.changeTabScores(this.mapService.map.tabScores);
    }
  }

  public addMap(): void {
    this.tabMap.push(new MapImpl());
  }
}
