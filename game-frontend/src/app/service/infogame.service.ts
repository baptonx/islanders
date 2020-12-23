import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InfogameService {

  public nomJoueur: string;
  public score: number;
  public nextScore: number;

  constructor() {
    this.score = 0;
    this.nextScore = 10;
    this.nomJoueur = 'Paul';
  }
}
