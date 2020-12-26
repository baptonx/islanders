import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { InventoryComponent } from '../inventory/inventory.component';
import {MapComponent} from '../map/map.component';
import {InfogameService} from '../service/infogame.service';
import {AnonCmd, buttonBinder} from 'interacto';
import {LeaderboardService} from '../service/leaderboard.service';
import {MapService} from "../service/map.service";
import {HomeService} from "../service/home.service";

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

  constructor(public infogameService: InfogameService, public leaderboardService: LeaderboardService, public mapService: MapService, public homeService: HomeService) { }

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
        console.log(this.infogameService.nomJoueur);
        this.leaderboardService.addScore(this.infogameService.nomJoueur, this.infogameService.score);
        this.leaderboardService.changeSpecificTabScores(this.homeService.tabMap[this.homeService.indexCurrentMap].tabScores, this.leaderboardService.tabScores);
        this.mapService.router.navigate(['/home']);
      }))
      .bind();

    buttonBinder()
      .on(this.buttonChangeName.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        console.log(this.buttonChangeName.nativeElement.value);
        this.infogameService.nomJoueur = this.inputNomJoueur.nativeElement.value;
      }))
      .bind();
  }

}
