import {CommandBase, Undoable} from 'interacto';

export class PutCityBlockUndoable extends CommandBase implements Undoable {
  public constructor(private dataService: DataService) {
    super();
  }

  // Create the memento for undoing the command
  protected createMemento() {
    // We copy the current state of the DataService
    this.mementoDataServiceText = this.dataService.text;
  }
}
