import {Undoable} from 'interacto';
import {InfogameService} from '../service/infogame.service';
import {Command} from './command';

export class CommandRename extends Command implements Undoable {
  type = 'game.Model.MoveCityBlock';
  public mementoNomJoueur: string;

  public constructor(private infoGameService: InfogameService, private nouveauNomJoueur: string) {
    super();
    this.createMemento();
    this.execution();
  }


  // Create the memento for undoing the command
  protected createMemento() {
    // We copy the current state of the MapService
    this.mementoNomJoueur = this.infoGameService.nomJoueur;
  }


  protected execution(): void {
    this.redo();
  }

  undo(): void {
    this.infoGameService.nomJoueur = this.mementoNomJoueur;
  }

  redo(): void {
    this.infoGameService.nomJoueur = this.nouveauNomJoueur;
  }

  getUndoName(): string {
    return 'Undo name';
  }
}
