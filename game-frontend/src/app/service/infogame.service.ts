import { Injectable } from '@angular/core';
import faker from 'faker';

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
    this.nomJoueur = faker.name.firstName();
  }

  public initializeScore(): void {
    this.score = 0;
    this.nextScore = 10;
  }
}
