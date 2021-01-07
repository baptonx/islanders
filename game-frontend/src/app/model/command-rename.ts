import {Undoable} from 'interacto';
import {InfogameService} from '../service/infogame.service';
import {Command} from './command';

export class CommandRename extends Command implements Undoable {
  type = 'game.model.CommandRename';
  nouveauNomJoueur: string;
  public mementoNomJoueur: string;

  public constructor(nouveauNomJoueur: string) {
    super();
    this.nouveauNomJoueur = nouveauNomJoueur;
  }
}
