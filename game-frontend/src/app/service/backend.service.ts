import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MapImpl} from '../model/map-impl';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(public http: HttpClient) {
  }

  // request an array of all existing map names
  public getMapNames(): void {
    this.http.get<Array<string>>('/game/api/v1/maps').subscribe(
      {
        next: data => {
          console.log('the data :' + JSON.stringify(data));
          // this.tabMap = data.total;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  public getMapFromName(name: string): void {
    const uri = `/game/api/v1/maps/${name}`;
    this.http.get<MapImpl>(uri).subscribe(
      {
        next: data => {
          console.log('the data :' + JSON.stringify(data));
          // this.tabMap = data.total;
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

  public postMap(map: MapImpl): void {
    const uri = `/game/api/v1/maps`;
    console.log(JSON.stringify(map));
    this.http.put(uri, JSON.stringify(map)).subscribe(
      {
        next: data => {
          console.log('the data :' + JSON.stringify(data));
        },
        error: error => {
          console.error('There was an error!', error.message);
        }
      }
    );
  }

}
