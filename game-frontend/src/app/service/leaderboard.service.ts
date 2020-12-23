import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  public tabScores: Map<string, number> = new Map();
  constructor() {
    this.tabScores.set('-', 0);
    this.tabScores.set('--', 0);
    this.tabScores.set('---', 0);
    this.tabScores.set('----', 0);
    this.tabScores.set('-----', 0);
  }

  public changeTabScores(newScores: Map<string, number>): void {
    const mapSort = new Map([...newScores.entries()].sort((a, b) => b[1] - a[1]));
    console.log(mapSort);
    /*
    for (let [key, value] of mapSort) {     // get data sorted
      this.tabScores.ke
    }

     */
  }
}
