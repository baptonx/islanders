import {Injectable} from '@angular/core';
import {CommandCollector} from "../model/command-collector";
import {MapService} from "./map.service";
import {InfogameService} from "./infogame.service";
import {MapAdapter} from "../model/map-adapter";
import {ClonerService} from "./cloner.service";

@Injectable({
  providedIn: 'root'
})
export class ReplayService {
  public undoCollector: CommandCollector;
  public redoCollector: CommandCollector;

  constructor(public mapService: MapService, public infogameService: InfogameService, public clonerService: ClonerService) {
    this.undoCollector = new CommandCollector();
    this.redoCollector = new CommandCollector();
  }

  public initializeUndoRedoCollector(): void {
    // console.log(this.mapService.map);
    for (const c of this.mapService.map.commandsCollectors) {
      if (c.playerName === this.infogameService.nomJoueur) {
        const commandCollectorPlayer = new CommandCollector();
        commandCollectorPlayer.playerName = c.playerName;
        for (const com of c.commands) {
          // console.log(MapAdapter.commandToCommandImpl(com, this.infogameService, this.mapService, this.clonerService));
          commandCollectorPlayer.commands.unshift(MapAdapter.commandToCommandImpl(com, this.infogameService, this.mapService, this.clonerService));
        }
        this.redoCollector = commandCollectorPlayer;
        this.infogameService.nomJoueur = this.redoCollector.commands[0].mementoNomJoueur;
        // console.log(this.redoCollector);
      }
    }
  }
}
