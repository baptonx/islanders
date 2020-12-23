import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { InventoryComponent } from '../inventory/inventory.component';
import {MapComponent} from '../map/map.component';
import {InfogameService} from '../service/infogame.service';
import {AnonCmd, buttonBinder} from 'interacto';
import {LeaderboardService} from '../service/leaderboard.service';
import {MapService} from "../service/map.service";

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit, AfterViewInit {

  @ViewChild('changernom')
  changerNom: ElementRef<HTMLButtonElement>;
  @ViewChild('inputNomJoueur')
  inputNomJoueur: ElementRef<HTMLInputElement>;

  constructor(public infogameService: InfogameService, public leaderboardService: LeaderboardService, public mapService: MapService) { }

  ngOnInit(): void {
    this.mapService.map = Object.assign({}, this.mapService.map);
    this.mapService.map.tabTiles = Object.assign([], this.mapService.map.tabTiles);
    this.mapService.inventoryService.initialize();
  }

  ngAfterViewInit(): void {

    buttonBinder()
      .on(this.changerNom.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        this.inputNomJoueur.nativeElement.disabled = !this.inputNomJoueur.nativeElement.disabled;
        if (this.inputNomJoueur.nativeElement.disabled){
          this.infogameService.nomJoueur = this.inputNomJoueur.nativeElement.value;
        }
      }))
      .bind();
  }

}
