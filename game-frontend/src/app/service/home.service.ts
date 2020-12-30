import {Injectable, OnInit} from '@angular/core';
import {MapService} from './map.service';
import {MapImpl} from '../model/map-impl';
import {LeaderboardService} from './leaderboard.service';
import {observable, Observable, throwError} from 'rxjs';
import {catchError, retry} from 'rxjs/operators';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {BackendService} from './backend.service';
import {MapRessource} from '../model/map-ressource';

@Injectable({
  providedIn: 'root'
})
export class HomeService {
  public tabMap: Array<MapImpl>;
  public mapNames: Array<string>;
  public tabMapTestFrontEnd: Array<MapImpl>;
  public indexCurrentMap;

  constructor(public mapService: MapService, public http: HttpClient) {
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
    console.log(name);
    const tab = new Array<number>(100);
    const uri = `/game/api/v1/maps/${name}`;
    this.http.get<MapImpl>(uri).subscribe(
      {
        next: data => {
          data.tabTiles.forEach((tile) => {
            switch (tile) {
              case {type: 'game.model.Grass'}:
                tab.push(0);
                break;
              case {type: 'game.model.Tree'}:
                tab.push(1);
                break;
              case {type: 'game.model.Water'}:
                tab.push(2);
                break;
            }
          });
          /**
           * Les maps reçues du backend possèdent comme tableau de tile des objets Grass, Tree et Water
           * Les MapImpl du frontend utilisent seulement les entiers 0 1 2, il faut donc faire la correspondance entre les deux
           */
          this.mapService.map = data;
          this.mapService.map.setTabTiles(tab);
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
    const tab = new Array<number>();
    const uri = `/game/api/v1/maps/${name}`;
    this.http.get<MapImpl>(uri).subscribe(
      {
        next: data => {
          console.log(JSON.stringify(data.tabTiles[0]));
          data.tabTiles.forEach((tile) => {
            switch (JSON.stringify(tile)) {
              case '{"type":"game.model.Grass"}':
                tab.push(0);
                break;
              case '{"type":"game.model.Tree"}':
                tab.push(1);
                break;
              case '{"type":"game.model.Water"}':
                tab.push(2);
                break;
            }
          });

          this.mapService.map = data;
          this.mapService.map.tabTiles = tab;
          console.log(this.mapService.map);
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  public addMap(): void {
    const uri = `/game/api/v1/maps/random`;
    this.http.get(uri).subscribe(
      {
        next: data => {
          console.log('Random Map :' + JSON.stringify(data));
          // this.tabMap = data.total;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }
}
