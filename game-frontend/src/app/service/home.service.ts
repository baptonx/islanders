import {Injectable, OnInit} from '@angular/core';
import {MapService} from './map.service';
import {MapImpl} from '../model/map-impl';
import {LeaderboardService} from './leaderboard.service';
import {observable, Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BackendService} from './backend.service';
import {MapRessource} from '../model/map-ressource';
import {MapAdapter} from '../model/map-adapter';

@Injectable({
  providedIn: 'root'
})
export class HomeService {
  public tabMap: Array<MapImpl>;
  public mapNames: Array<string>;
  public indexCurrentMap;

  // tslint:disable-next-line:max-line-length
  constructor(public mapService: MapService, public leaderboardService: LeaderboardService, public backendService: BackendService, public http: HttpClient) {
    //  ICI remplir tabMap avec le back-end
    this.tabMap = new Array<MapImpl>();
    this.mapNames = new Array<string>();
    this.indexCurrentMap = 0;
  }


  public initialize(): void {
    this.backendService.postMap(new MapRessource('Test'));
    this.http.get<Array<string>>('/game/api/v1/maps').subscribe(
      {
        next: data => {
          this.mapNames = data;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  public changeMap(name: string): void {
    const uri = `/game/api/v1/maps/${name}`;
    this.http.get<MapRessource>(uri).subscribe(
      {
        next: data => {
          const res = new MapRessource(data.name);
          res.setCommandsCollectors(data.commandsCollectors);
          res.setScores(data.scores);
          res.setTabTiles(data.tabTiles);
          this.mapService.map = MapAdapter.mapRessourceToMapImpl(res);
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  /**
   * Les maps reçues du backend possèdent comme tableau de tile des objets Grass, Tree et Water
   * Les MapImpl du frontend utilisent seulement les entiers 0 1 2, il faut donc faire la correspondance entre les deux
   */
  public loadMap(name: string): void {
    const uri = `/game/api/v1/maps/${name}`;
    this.http.get<MapRessource>(uri).subscribe(
      {
        next: data => {
          this.mapService.map = MapAdapter.mapRessourceToMapImpl(data);
          console.log(this.mapService.map);
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }


  public addMap(): void {
    /*
    const uri = `/game/api/v1/maps/random`;
    this.http.get<MapImpl>(uri, {}).subscribe({
        next: data => {
          this.mapNames.push(data.name);
          this.mapService.map = data;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
    this.mapService.map.adaptTabTiles();
     */
  }
}
