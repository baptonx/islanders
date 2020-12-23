import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InfogameService {

  public nomJoueur: string;
  public score: number;
  public nextScore: number;
  public tabScores: Map<string, number> = new Map();

  constructor() {
    this.score = 0;
    this.nextScore = 10;
    this.nomJoueur = 'Paul';
    this.tabScores.set('Paul', 10);
    this.tabScores.set('Hugo', 20);
  }
}
