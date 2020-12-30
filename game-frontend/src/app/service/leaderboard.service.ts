import { Injectable } from '@angular/core';
import {Score} from '../model/score';
import {HttpClient} from '@angular/common/http';
import {MapService} from './map.service';

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  public tabScores: Array<Score>;

  constructor(public http: HttpClient, public mapService: MapService) {

    this.tabScores = new Array<Score>();

    /***this.tabScores = new Array<Array<number|string>>(5);
    this.tabScores[0] = new Array<number | string>(2);
    this.tabScores[0][0] = '-'; this.tabScores[0][1] = 0;
    this.tabScores[1] = new Array<number | string>(2);
    this.tabScores[1][0] = '--'; this.tabScores[1][1] = 0;
    this.tabScores[2] = new Array<number | string>(2);
    this.tabScores[2][0] = '---'; this.tabScores[2][1] = 0;
    this.tabScores[3] = new Array<number | string>(2);
    this.tabScores[3][0] = '----'; this.tabScores[3][1] = 0;
    this.tabScores[4] = new Array<number | string>(2);
    this.tabScores[4][0] = '-----'; this.tabScores[4][1] = 0;***/
  }
  public getScore(): void{
    const uri = `/game/api/v1/topScores/${this.mapService.map.name}`;
    this.http.get<Array<Score>>(uri).subscribe(
      {
        next: data => {
          this.tabScores = data;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  /***public changeTabScores(newScores: Array<Array<number|string>>): void {
    this.tabScores[0][0] = newScores[0][0]; this.tabScores[0][1] = newScores[0][1];
    this.tabScores[1][0] = newScores[1][0]; this.tabScores[1][1] = newScores[1][1];
    this.tabScores[2][0] = newScores[2][0]; this.tabScores[2][1] = newScores[2][1];
    this.tabScores[3][0] = newScores[3][0]; this.tabScores[3][1] = newScores[3][1];
    this.tabScores[4][0] = newScores[4][0]; this.tabScores[4][1] = newScores[4][1];
  }

  public changeSpecificTabScores(tabScoreToChange: Array<Array<number|string>>, newScores: Array<Array<number|string>>): void {
    tabScoreToChange[0][0] = newScores[0][0]; tabScoreToChange[0][1] = newScores[0][1];
    tabScoreToChange[1][0] = newScores[1][0]; tabScoreToChange[1][1] = newScores[1][1];
    tabScoreToChange[2][0] = newScores[2][0]; tabScoreToChange[2][1] = newScores[2][1];
    tabScoreToChange[3][0] = newScores[3][0]; tabScoreToChange[3][1] = newScores[3][1];
    tabScoreToChange[4][0] = newScores[4][0]; tabScoreToChange[4][1] = newScores[4][1];
  }


  public addScore(name: number|string, score: number|string): void {
    let oldName: number|string;
    let oldScore: number|string;
    for (let i = 0; i < 5; i++) {
      if (score > this.tabScores[i][1]) {
        // 1 : sauvegarde
        oldName = this.tabScores[i][0];
        oldScore = this.tabScores[i][1];

        // 2 : ecrire
        this.tabScores[i][0] = name;
        this.tabScores[i][1] = score;

        // 3 : sauvegarde -> ecrit
        name = oldName;
        score = oldScore;

        for (let x = i + 1; x < 5; x++) {
          // 1 : sauvegarde
          oldName = this.tabScores[x][0];
          oldScore = this.tabScores[x][1];

          // 2 : ecrire
          this.tabScores[x][0] = name;
          this.tabScores[x][1] = score;

          // 3 : sauvegarde -> ecrit
          name = oldName;
          score = oldScore;
        }
        break;
      }
    }
  }***/
}
