import {Component, OnInit} from '@angular/core';
import {LeaderboardService} from '../service/leaderboard.service';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {

  public hidden = false;

  constructor(public leaderboardService: LeaderboardService) {
  }

  ngOnInit(): void {
  }

  toggleDisplay(): void {
    this.hidden = !this.hidden;
    console.log(this.hidden);
  }

  isHidden(): boolean {
    console.log(this.hidden);
    return this.hidden;
  }

}
