export abstract class Command {
  public type: string;
  public mementoNomJoueur: string;
  undo(): void {}
  redo(): void {}
  getUndoName(): string {return 'default'; }
}
