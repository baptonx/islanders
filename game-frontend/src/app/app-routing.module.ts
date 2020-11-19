import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MapComponent} from './map/map.component';
import {GameComponent} from './game/game.component';
import {InventoryComponent} from './inventory/inventory.component';

const routes: Routes = [
  {path: 'map', component: MapComponent},
  {path: 'hugo', component: GameComponent},
  {path: 'inventory', component: InventoryComponent},
  {
    path: '',
    redirectTo: '/map',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
