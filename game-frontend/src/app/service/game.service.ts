import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Undoable} from 'interacto';
import {Command} from "../model/command";
import {CommandCollector} from "../model/command-collector";

@Injectable({
  providedIn: 'root'
})
export class GameService {
  // private game: Game;
  public undoCollector: CommandCollector;
  public redoCollector: CommandCollector;
  //public undoArray: Array<Command>;
  //public redoArray: Array<Command>;

  constructor(private http: HttpClient) {
    this.undoCollector = new CommandCollector();
    this.redoCollector = new CommandCollector();
    //this.undoArray = new Array<Command>();
    //this.redoArray = new Array<Command>();
    // this.game = new GameImpl();
  }

  /*
  postGame(): void {

    this.http.post()
  }
   */

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
