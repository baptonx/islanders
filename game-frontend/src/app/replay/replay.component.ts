import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {GameService} from "../service/game.service";
import {InfogameService} from "../service/infogame.service";
import {LeaderboardService} from "../service/leaderboard.service";
import {MapService} from "../service/map.service";
import {HomeService} from "../service/home.service";
import {ClonerService} from "../service/cloner.service";
import {AnonCmd, buttonBinder} from "interacto";
import {Command} from "../model/command";
import {MapAdapter} from "../model/map-adapter";
import {ReplayService} from "../service/replay.service";

@Component({
  selector: 'app-replay',
  templateUrl: './replay.component.html',
  styleUrls: ['./replay.component.css']
})
export class ReplayComponent implements OnInit {

  @ViewChild('buttonHome')
  buttonHome: ElementRef<HTMLInputElement>;

  constructor(public replayService: ReplayService, public mapService: MapService, public infogameService: InfogameService) {
  }

  ngOnInit(): void {
    this.replayService.initializeUndoRedoCollector();
    this.mapService.initialize();
    this.mapService.inventoryService.initialize();
    this.infogameService.initializeScore();
  }

  ngAfterViewInit(): void {
    buttonBinder()
      .on(this.buttonHome.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        this.mapService.router.navigate(['/home']);
      }))
      .bind();
  }

  public undoFct(): void {
    if (this.replayService.undoCollector.commands.length > 0) {
      let c: Command;
      c = this.replayService.undoCollector.commands.pop();
      c.undo();
      this.replayService.redoCollector.commands.push(c);
    }
  }

  public redoFct(): void {
    if (this.replayService.redoCollector.commands.length > 0) {
      let c: Command;
      c = this.replayService.redoCollector.commands.pop();
      c.redo();
      this.replayService.undoCollector.commands.push(c);
    }
  }

}
