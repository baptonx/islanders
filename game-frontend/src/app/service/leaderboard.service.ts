import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LeaderboardService {
  public tabScores: Map<string, number> = new Map();
  constructor() {
    this.tabScores.set('Paul', 10);
    this.tabScores.set('Hugo', 20);
  }
}
