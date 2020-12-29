import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {MapImpl} from '../model/map-impl';
import {Score} from '../model/score';
import {Command, Undoable} from 'interacto';
import {MapRessource} from '../model/map-ressource';

@Injectable({
  providedIn: 'root'
})
export class BackendService {
  tabMap: Array<MapImpl>;

  constructor(public http: HttpClient) {
    this.tabMap = new Array<MapImpl>();
  }

  /**
   * Return an array of all existing map names
   */
  public getMapNames(): void {
    this.http.get<Array<string>>('/game/api/v1/maps').subscribe(
      {
        next: data => {
          console.log('MapNames Array :' + JSON.stringify(data));
          // this.tabMap = data.total;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  /**
   * Return the map with the name given in parameter
   */
  public getMapFromName(name: string): MapRessource {
    const uri = `/game/api/v1/maps/${name}`;
    let res = new MapRessource('');
    this.http.get<MapRessource>(uri).subscribe(
      {
        next: data => {
          console.log('Map :' + JSON.stringify(data));
          res = data;
          this.tabMap.push(data.toMapimpl());
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
    return res;
  }

  /**
   * Store a map in the backend
   */

  public postMap(map: MapRessource): void {
    const uri = `/game/api/v1/maps`;
    console.log(JSON.stringify(map));
    const body = {
      name: 'Goku',
      scores: [{player: 'Paul', score: 150}],
      tabTiles: [{type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Grass'}, {type: 'game.model.Tree'}, {type: 'game.model.Grass'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Grass'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Tree'}, {type: 'game.model.Grass'}, {type: 'game.model.Grass'}, {type: 'game.model.Tree'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Grass'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Grass'}, {type: 'game.model.Grass'}, {type: 'game.model.Grass'}, {type: 'game.model.Tree'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Water'}, {type: 'game.model.Tree'}, {type: 'game.model.Grass'}],
      commandsCollectors: [],
      topScores: [{player: 'Paul', score: 150}]
    };
    this.http.post<MapRessource>(uri, map, {}).subscribe(
      {
        next: data => {
          console.log('Map post :' + data);
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  /**
   * Return the 5 best scores of the map given in parameter
   */
  public getTopScores(name: string): void {
    const uri = `/game/api/v1/topScores/${name}`;
    this.http.get<Array<Score>>(uri).subscribe(
      {
        next: data => {
          console.log('topScores :' + JSON.stringify(data));
          // this.tabMap = data.total;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  /**
   * Return a random map generated by the backend
   */
  public getRandomMap(): void {
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

  /**
   * Add the current game to the backend using the map, the player, the score and the commands done
   */
  public postGame(mapName: string, playerName: string, score: number): void {
    const uri = `/game/api/v1/replays/${mapName}/${playerName}/${score}`;
    const commands = [{
      type: 'game.model.MoveCityBlock',
      posBefore: 0,
      posAfter: 1
    }, {
      type: 'game.model.PutCityBlock',
      position: 1,
      typeCityBlock: 2
    }, {
      type: 'game.model.MoveCityBlock',
      posBefore: 2,
      posAfter: 3
    }];
    this.http.post(uri, commands, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      })
    }).subscribe(
      {
        next: data => {
          console.log('Post Game :' + JSON.stringify(data));
          // this.tabMap = data.total;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  /**
   * Return all players that have replays on the map given in parameter
   */
  public getPlayerFromMap(mapName: string): void {
    const uri = `/game/api/v1/replays/${mapName}`;
    this.http.get(uri).subscribe(
      {
        next: data => {
          console.log('Player from Map :' + JSON.stringify(data));
          // this.tabMap = data.total;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  /**
   * Return the replay from the player given in parameter on the map given in parameter
   */
  public getPlayerCommandFromMap(mapName: string, playerName: string): void {
    const uri = `/game/api/v1/replays/${mapName}/${playerName}`;
    this.http.get(uri).subscribe(
      {
        next: data => {
          console.log('Player Command from Map :' + JSON.stringify(data));
          // this.tabMap = data.total;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }
}
