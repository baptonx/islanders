import {MapImpl} from './map-impl';
import {Score} from './score';
import {MapRessource} from './map-ressource';

export class MapAdapter {
  public static mapRessourceToMapImpl(map: MapRessource): MapImpl {
    const mapImpl = new MapImpl(map.name);
    mapImpl.setTabTiles(map.generateTabTiles());
    mapImpl.setScores(map.scores);
    mapImpl.setCommandsCollectors(map.commandsCollectors);
    return mapImpl;
  }

  public static mapImplToMapRessource(map: MapImpl): MapRessource{
    const mapRessource = new MapRessource(map.name);
    mapRessource.setTabTiles(map.generateTabTiles());
    mapRessource.setScores(map.scores);
    mapRessource.setCommandsCollectors(map.commandsCollectors);
    return mapRessource;
  }
}
