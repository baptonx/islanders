import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {InventoryService} from '../service/inventory.service';
import {HomeService} from "../service/home.service";
import {AnonCmd, buttonBinder} from "interacto";

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
  @ViewChild('inputNamePlayer')
  inputNamePlayer: ElementRef<HTMLButtonElement>;

  constructor(public homeService: HomeService) {
  }

  ngOnInit(): void {
    this.homeService.initialize();
  }

  ngAfterViewInit(): void {
    buttonBinder()
      .on(this.arrowLeft.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        if (this.homeService.indexCurrentMap > 0) {
          this.homeService.indexCurrentMap--;
        }
        else {
          this.homeService.indexCurrentMap = this.homeService.tabMap.length - 1;
        }
        this.homeService.changeMap(this.homeService.indexCurrentMap);
      }))
      .bind();

    buttonBinder()
      .on(this.arrowRight.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        if (this.homeService.indexCurrentMap < this.homeService.tabMap.length-1) {
          this.homeService.indexCurrentMap++;
        }
        else {
          this.homeService.indexCurrentMap = 0;
        }
        this.homeService.changeMap(this.homeService.indexCurrentMap);
      }))
      .bind();


    buttonBinder()
      .on(this.buttonAddMap.nativeElement)
      .toProduce(i => new AnonCmd(() => {
        this.homeService.addMap();
        this.homeService.tabMap[this.homeService.tabMap.length - 1].name = this.inputNamePlayer.nativeElement.value;
      }))
      .bind();
  }
}
