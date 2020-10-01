import {AfterViewInit, Component} from '@angular/core';
import {GameService} from '../service/game.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements AfterViewInit {

  constructor(public gameService: GameService) {
  }

  ngAfterViewInit(): void {
  }
}
