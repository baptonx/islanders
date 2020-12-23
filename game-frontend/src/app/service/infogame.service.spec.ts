import { TestBed } from '@angular/core/testing';

import { InfogameService } from './infogame.service';

describe('InfogameService', () => {
  let service: InfogameService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InfogameService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
