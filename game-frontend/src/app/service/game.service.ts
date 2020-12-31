import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Undoable} from 'interacto';
import {Command} from '../model/command';
import {CommandCollector} from '../model/command-collector';
import {CommandRename} from '../model/command-rename';
import {PutCityBlock} from '../model/put-city-block';
import {MapAdapter} from '../model/map-adapter';
import {MoveCityBlock} from '../model/move-city-block';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  // private game: Game;
  public undoCollector: CommandCollector;
  public redoCollector: CommandCollector;
  // public undoArray: Array<Command>;
  // public redoArray: Array<Command>;

  constructor(private http: HttpClient) {
    this.undoCollector = new CommandCollector();
    this.redoCollector = new CommandCollector();
    // this.undoArray = new Array<Command>();
    // this.redoArray = new Array<Command>();
    // this.game = new GameImpl();
  }

  public postGame(mapName: string, playerName: string, score: number, commands: string): void {
    const uri = `/game/api/v1/replays/${mapName}/${playerName}/${score}`;
    const body = commands;
    this.http.post(uri, JSON.stringify(body), {
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


// You should start the development of the front-end without using
// a back-end: start by displaying the map with fake data:
// constructor() {
//   this.game = new GameImpl();
//   // Set up the game with fake data.
//   // ...
// }

// getCurrentGame(): Game {
//   return this.game;
// }
}
