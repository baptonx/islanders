import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {InventoryService} from '../service/inventory.service';
import {HomeService} from '../service/home.service';
import {AnonCmd, buttonBinder} from 'interacto';
import {BackendService} from '../service/backend.service';
import {MapImpl} from '../model/map-impl';
import {PutCityBlock} from '../model/put-city-block';
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
  @ViewChild('buttonStart')
  buttonStart: ElementRef<HTMLButtonElement>;
  mapNames: Array<string>;

  constructor(public homeService: HomeService) {
  }

  ngOnInit(): void {
    this.mapNames = this.homeService.mapNames;
    this.homeService.mapService.infogameService.errorOutput = '';
  }


  public clickArrowLeft(): void {
    if (this.homeService.indexCurrentMap > 0) {
      this.homeService.indexCurrentMap--;
    } else {
      this.homeService.indexCurrentMap = this.homeService.mapNames.length - 1;
    }
    this.homeService.changeMap(this.homeService.mapNames[this.homeService.indexCurrentMap]);
  }

  public clickArrowRight(): void {
    if (this.homeService.indexCurrentMap < this.homeService.mapNames.length - 1) {
      this.homeService.indexCurrentMap++;
    } else {
      this.homeService.indexCurrentMap = 0;
    }
    this.homeService.changeMap(this.homeService.mapNames[this.homeService.indexCurrentMap]);
  }

  public clickStart(): void{
    if (this.homeService.mapService.infogameService.nomJoueur !== '') {
      this.homeService.mapService.router.navigate(['/play']);
    } else {
      this.homeService.mapService.infogameService.errorOutput = 'Error : Name of the player is null';
      this.homeService.mapService.infogameService.isErrorOutputRed = true;
    }
  }

  public addNewMap(): void {
    this.homeService.addMap();
  }

  public callToChangeMap(nameCurrentMap: string): void{
    this.homeService.changeMap(nameCurrentMap);
  }

  public callToStartReplay(nameCurrentPlayerRep: string): void{
    console.log("start replay");
  }

  ngAfterViewInit(): void {
    this.homeService.initialize();
    this.mapNames = this.homeService.mapNames;
  }
}
