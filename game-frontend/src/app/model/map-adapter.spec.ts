import {MapAdapter} from './map-adapter';
import {MapRessource} from './map-ressource';
import {Tile} from './tile';

describe('MapAdapter', () => {
  it('should create an instance', () => {
    expect(new MapAdapter()).toBeTruthy();
  });
  it('should transform to a MapImpl', () => {
    const mapR = new MapRessource('name');
    const mapI = MapAdapter.mapRessourceToMapImpl(mapR);
    expect(mapR.name).toBe(mapI.name);
    expect(mapR.scores).toBe(mapI.scores);
    expect(mapR.commandsCollectors).toBe(mapI.commandsCollectors);
    expect(mapR.tabTiles).toBe(Array<Tile>(100));
  });

});
