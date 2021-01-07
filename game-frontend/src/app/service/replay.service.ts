import { Injectable } from '@angular/core';
import {CommandCollector} from "../model/command-collector";
import {MapService} from "./map.service";
import {InfogameService} from "./infogame.service";

@Injectable({
  providedIn: 'root'
})
export class ReplayService {
  public undoCollector: CommandCollector;
  public redoCollector: CommandCollector;

  constructor(public mapService: MapService, public infogameService: InfogameService) {
    this.undoCollector = new CommandCollector();
    this.redoCollector = new CommandCollector();
  }

  public initializeUndoRedoCollector(): void {
    for (const c of this.mapService.map.commandsCollectors) {
      if (c.playerName === this.infogameService.nomJoueur) {
        this.redoCollector = c;
      }
    }
  }
}
