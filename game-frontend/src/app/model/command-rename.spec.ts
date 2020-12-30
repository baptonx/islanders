import { CommandRename } from './command-rename';
import {InfogameService} from '../service/infogame.service';

describe('CommandRename', () => {
  it('should create an instance', () => {
    expect(new CommandRename(new InfogameService(), 'player1')).toBeTruthy();
  });
});
