import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Undoable} from 'interacto';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  // private game: Game;
  public undoArray: Array<Undoable>;
  public redoArray: Array<Undoable>;

  constructor(private http: HttpClient) {
    this.undoArray = new Array<Undoable>();
    this.redoArray = new Array<Undoable>();
    // this.game = new GameImpl();
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
