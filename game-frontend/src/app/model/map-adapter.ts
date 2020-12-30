import {MapImpl} from './map-impl';
import {Score} from './score';
import {MapRessource} from './map-ressource';

export class MapAdapter {
  public static mapRessourceToMapImpl(map: MapRessource): MapImpl {
    const mapImpl = new MapImpl();
    mapImpl.setTabTiles(map.generateTabTiles());
    mapImpl.setScores([new Score(), new Score()]);
    mapImpl.setCommandsCollectors(map.commandsCollectors);
    return mapImpl;
  }
}
