import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InfogameService {

  public nomJoueur: string;
  public score: number;
  public nextScore: number;
  public errorOutput: string;
  public isErrorOutputRed: boolean;

  constructor() {
    this.initializeScore();
    this.nomJoueur = 'Paul';
  }

  public initializeScore(): void {
    this.score = 0;
    this.nextScore = 10;
  }
}
