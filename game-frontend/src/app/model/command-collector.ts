import {Command} from "./command";


export class CommandCollector {
  playerName: string;
  commands: Array<Command>;

  constructor() {
    this.playerName = '';
    this.commands = new Array<Command>();
  }
}
