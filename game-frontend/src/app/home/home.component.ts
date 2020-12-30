import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {InventoryService} from '../service/inventory.service';
import {HomeService} from '../service/home.service';
import {AnonCmd, buttonBinder} from 'interacto';
import {BackendService} from '../service/backend.service';
import {MapImpl} from '../model/map-impl';
import {CommandAdd} from '../model/command-add';
import {ClonerService} from '../service/cloner.service';
import {MapService} from '../service/map.service';
import {InfogameService} from '../service/infogame.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {
  @ViewChild('arrowLeft')
  arrowLeft: ElementRef<HTMLButtonElement>;
  @ViewChild('arrowRight')
  arrowRight: ElementRef<HTMLButtonElement>;
  @ViewChild('buttonAddMap')
  buttonAddMap: ElementRef<HTMLButtonElement>;
  @ViewChild('inputNameMap')
  inputNameMap: ElementRef<HTMLButtonElement>;
  @ViewChild('buttonStart')
  buttonStart: ElementRef<HTMLButtonElement>;
  @ViewChild('mapName')
  selectMap: ElementRef<HTMLOptionElement>;

  constructor(public backendService: BackendService, public homeService: HomeService) {
  }

  ngOnInit(): void {
    this.homeService.initialize();
    /** Test des routes du backend
     * Attention ces méthodes modifient
     * le mapTest.json du backend
     * à chaque fois qu'angular refresh la page web
     */
    const map = new MapImpl();
    // this.backendService.postMap(map.toMapRessource());
    // this.backendService.getMapNames();
    // this.backendService.getMapFromName('Beerus');
    // this.backendService.getTopScores('Beerus');
    // this.backendService.getRandomMap();
    // this.backendService.postGame('Beerus', 'Joueur1', 320);
    // this.backendService.getPlayerFromMap('Beerus');
    // this.backendService.getPlayerCommandFromMap('Beerus', 'Joueur1');
    this.homeService.mapService.infogameService.errorOutput = '';
  }

  public clickArrowLeft(): void {
    if (this.homeService.indexCurrentMap > 0) {
      this.homeService.indexCurrentMap--;
    } else {
      this.homeService.indexCurrentMap = this.homeService.tabMap.length - 1;
    }
    this.homeService.changeMap(this.homeService.indexCurrentMap);
  }

  public clickArrowRight(): void {
    if (this.homeService.indexCurrentMap < this.homeService.tabMap.length - 1) {
      this.homeService.indexCurrentMap++;
    } else {
      this.homeService.indexCurrentMap = 0;
    }
    this.homeService.changeMap(this.homeService.indexCurrentMap);
  }

  public addNewMap(): void {
    this.backendService.getRandomMap();
  }


  ngAfterViewInit(): void {

    buttonBinder()
      .on(this.buttonAddMap.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        if (this.inputNameMap.nativeElement.value !== '') {
          this.homeService.addMap();
          this.homeService.tabMap[this.homeService.tabMap.length - 1].name = this.inputNameMap.nativeElement.value;
          this.homeService.indexCurrentMap = this.homeService.tabMap.length - 1;
          this.homeService.changeMap(this.homeService.indexCurrentMap);

          this.homeService.mapService.infogameService.errorOutput = 'Map: ' + this.inputNameMap.nativeElement.value + ' added';
          this.homeService.mapService.infogameService.isErrorOutputRed = false;
        } else {
          this.homeService.mapService.infogameService.errorOutput = 'Error : Name of the map is null';
          this.homeService.mapService.infogameService.isErrorOutputRed = true;
        }
      }))
      .bind();

    buttonBinder()
      .on(this.buttonStart.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        if (this.homeService.mapService.infogameService.nomJoueur !== '') {
          this.homeService.mapService.router.navigate(['/play']);
        } else {
          this.homeService.mapService.infogameService.errorOutput = 'Error : Name of the player is null';
          this.homeService.mapService.infogameService.isErrorOutputRed = true;
        }
      }))
      .bind();
  }
}
