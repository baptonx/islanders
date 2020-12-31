export abstract class Command {
  undo(): void {}
  redo(): void {}
  getUndoName(): string {return 'default'; }
}
