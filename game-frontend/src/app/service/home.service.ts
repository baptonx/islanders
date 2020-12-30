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

  constructor(public mapService: MapService, public leaderboardService: LeaderboardService, public backendService: BackendService, public http: HttpClient) {
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
    const uri = `/game/api/v1/maps/${name}`;
    this.http.get<MapImpl>(uri).subscribe(
      {
        next: data => {
          this.mapService.map = data;
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
