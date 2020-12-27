import {Undoable} from "interacto";
import {MapService} from "../service/map.service";
import {ClonerService} from "../service/cloner.service";
import {InfogameService} from "../service/infogame.service";

export class CommandRename implements Undoable  {
  public mementoNomJoueur: string;

  public constructor(private infoGameService: InfogameService, private nouveauNomJoueur: string) {
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
