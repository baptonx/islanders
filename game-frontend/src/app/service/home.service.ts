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
  public mapNames: Array<string>;
  public indexCurrentMap: number;
  public nameCurrentMap: string;

  constructor(public mapService: MapService, public leaderboardService: LeaderboardService, public http: HttpClient) {
    //  ICI remplir tabMap avec le back-end
    this.mapNames = new Array<string>();
    this.indexCurrentMap = 0;
  }


  public initialize(): void {
    this.http.get<Array<string>>('/game/api/v1/maps').subscribe(
      {
        next: data => {
          this.mapNames = data;
          this.changeMap(this.mapNames[0]);
          this.indexCurrentMap = 0;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  public changeMap(name: string): void {
    if (name !== undefined){
      const uri = `/game/api/v1/maps/${name}`;
      const res: MapRessource = new MapRessource('');
      this.http.get<MapRessource>(uri).subscribe(
        {
          next: data => {
            // console.log(data);
            res.name = data.name;
            res.setCommandsCollectors(data.commandsCollectors);
            res.setScores(data.scores);
            res.setTabTiles(data.tabTiles);
            this.mapService.map = MapAdapter.mapRessourceToMapImpl(res);
            this.indexCurrentMap = this.mapNames.indexOf(name);
            this.nameCurrentMap = name;
            this.leaderboardService.getScore();
          },
          error: error => {
            console.error('There was an error!', error.message);
          }
        }
      );
    }
  }

  public addMap(): void {
    const uri = `/game/api/v1/maps/random`;
    this.http.get<MapImpl>(uri, {}).subscribe({
        next: data1 => {
          this.http.get<Array<string>>('/game/api/v1/maps').subscribe(
            {
              next: data2 => {
                this.mapNames = data2;
                this.changeMap(this.mapNames[this.mapNames.length - 1]);
                this.indexCurrentMap = this.mapNames.length - 1;
              },
              error: error => {
                console.error('There was an error!', error.message);
              }
            }
          );
        }
        ,
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }
}
