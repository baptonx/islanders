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
    this.mapService.initializeMoveBlock();
    this.mapService.inventoryService.initialize();
    this.infogameService.initializeScore();
  }

  ngAfterViewInit(): void {
    buttonBinder()
      .on(this.buttonHome.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        /***console.log(this.infogameService.nomJoueur);
         this.leaderboardService.addScore(this.infogameService.nomJoueur, this.infogameService.score);
         this.leaderboardService
         .changeSpecificTabScores(this.homeService.mapService.map.tabScores, this.leaderboardService.tabScores);***/

        // DONNER AU POST : NomMap, NomJoueur, Score, UndoCollector
        // C'est le POST qui s'occupe de regarder si le score du joueur pour la map donnée est plus grand que son
        // ancien score, si oui met à jour le score et le undoCollector
        // Attention pour le UndoCollector passer une listCommands
        this.gameService.postGame(this.mapService.map.name, this.infogameService.nomJoueur,
          this.infogameService.score, this.gameService.undoCollector.commands);


        // Puis faire ViewReplays : refaire un select qui se met a jour quand on change de map dans le home.
        // Puis quand on selectionne nom d'un joueur, charge un component replay (comme component play mais avec
        // moins de trucs)

        this.mapService.router.navigate(['/home']);
      }))
      .bind();

    buttonBinder()
      .on(this.buttonChangeName.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        //console.log(this.buttonChangeName.nativeElement.value);
        this.gameService.undoCollector.commands.push(new CommandRename(this.infogameService, this.inputNomJoueur.nativeElement.value));
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

}
