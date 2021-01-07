import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {InventoryComponent} from '../inventory/inventory.component';
import {MapComponent} from '../map/map.component';
import {InfogameService} from '../service/infogame.service';
import {AnonCmd, buttonBinder, Redo, Undo, Undoable} from 'interacto';
import {LeaderboardService} from '../service/leaderboard.service';
import {MapService} from '../service/map.service';
import {HomeService} from '../service/home.service';
import {MoveCityBlock} from '../model/move-city-block';
import {ClonerService} from '../service/cloner.service';
import {GameService} from '../service/game.service';
import {CommandRename} from '../model/command-rename';
import {Command} from '../model/command';
import {MapAdapter} from '../model/map-adapter';
import {CommandRenameImpl} from "../model/command-rename-impl";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit, AfterViewInit {

  @ViewChild('inputNomJoueur')
  inputNomJoueur: ElementRef<HTMLInputElement>;
  @ViewChild('buttonHome')
  buttonHome: ElementRef<HTMLInputElement>;
  @ViewChild('buttonChangeName')
  buttonChangeName: ElementRef<HTMLInputElement>;
  @ViewChild('buttonUndo')
  buttonUndo: ElementRef<HTMLInputElement>;
  @ViewChild('buttonRedo')
  buttonRedo: ElementRef<HTMLInputElement>;
  @ViewChild('buttonCommand')
  buttonCommand: ElementRef<HTMLInputElement>;

  constructor(public gameService: GameService, public infogameService: InfogameService, public leaderboardService: LeaderboardService, public mapService: MapService, public homeService: HomeService, public clonerService: ClonerService) {
  }

  ngOnInit(): void {
    this.mapService.map = Object.assign({}, this.mapService.map);
    this.mapService.map.tabTiles = Object.assign([], this.mapService.map.tabTiles);
    this.mapService.initialize();
    this.mapService.inventoryService.initialize();
    this.infogameService.initializeScore();
    this.gameService.initializeUndoCollector();
  }

  public clickHome(): void {
    const body = Array<Command>();
    this.gameService.undoCollector.commands.forEach((command) => {
      body.push(MapAdapter.commandImplToCommand(command));
    });
    console.log(body);
    this.gameService.postGame(this.mapService.map.name, this.infogameService.nomJoueur,
      this.infogameService.score, body);
    this.removeGameOver();

    this.mapService.router.navigate(['/home']);
  }

  ngAfterViewInit(): void {
    buttonBinder()
      .on(this.buttonChangeName.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        //console.log(this.buttonChangeName.nativeElement.value);
        this.gameService.undoCollector.commands.push(new CommandRenameImpl(this.infogameService, this.inputNomJoueur.nativeElement.value));
        this.gameService.redoCollector.commands = [];
        this.infogameService.nomJoueur = this.inputNomJoueur.nativeElement.value;
      }))
      .bind();
  }

  public undoFct(): void {
    if (this.gameService.undoCollector.commands.length > 0) {
      let c: Command;
      c = this.gameService.undoCollector.commands.pop();
      c.undo();
      this.gameService.redoCollector.commands.push(c);
    }
  }

  public redoFct(): void {
    if (this.gameService.redoCollector.commands.length > 0) {
      let c: Command;
      c = this.gameService.redoCollector.commands.pop();
      c.redo();
      this.gameService.undoCollector.commands.push(c);
    }
  }

  removeGameOver(): void{
    this.mapService.isGameOver = false;
  }

}
