export abstract class Tile {
  type = 'game.model.Tile';

  constructor(type: string) {
    this.type = type;
  }
}
