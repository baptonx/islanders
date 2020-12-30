import { MapAdapter } from './map-adapter';
import {MapRessource} from './map-ressource';

describe('MapAdapter', () => {
  it('should create an instance', () => {
    expect(new MapAdapter()).toBeTruthy();
  });
  const mapR = new MapRessource('name');
  const mapI = MapAdapter.mapRessourceToMapImpl(mapR);
  expect(mapR.name).toBe(mapI.name);
  expect(mapR.scores).toBe(mapI.scores);
  expect(mapR.commandsCollectors).toBe(mapI.commandsCollectors);
  expect(mapR.tabTiles).toBe();
});
