import {Score} from './score';
import {CommandCollector} from './command-collector';
import {Tile} from "./tile";

export class MapImpl {
  public tabTiles: Array<number>;
  //public tabScores: Array<Array<number | string>>;
  public scores: Array<Score>;
  public commandsCollectors: Array<CommandCollector>;
  public topScores: Array<Score>;

  constructor(public name: string) {
    this.scores = [];
    this.tabTiles = Array<number>();
    this.commandsCollectors = Array<CommandCollector>();
    this.topScores = [];
  }

  private getRandomInt(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }

  public generateRandomMap(): Array<number> {
    const tab = new Array<number>(100);
    for (let i = 0; i < 100; i++) {
      tab[i] = this.getRandomInt(3);
    }
    return tab;
  }


  public setTabTiles(numbers: Array<number>): void {
    this.tabTiles = numbers;
  }


  public setScores(scores1: Array<Score>): void {
    this.scores = scores1;
  }

  public setCommandsCollectors(cc: Array<CommandCollector>): void {
    this.commandsCollectors = cc;
  }

  public getTabTiles(): Array<any> {
    return this.tabTiles;
  }

}
