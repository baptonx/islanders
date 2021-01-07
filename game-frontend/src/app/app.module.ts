import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MapComponent} from './map/map.component';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatBadgeModule} from '@angular/material/badge';
import {OverlayModule} from '@angular/cdk/overlay';
import {MatListModule} from '@angular/material/list';
import { GameComponent } from './game/game.component';
import { InventoryComponent } from './inventory/inventory.component';
import {InventoryService} from './service/inventory.service';
import { HomeComponent } from './home/home.component';
import { LeaderboardComponent } from './leaderboard/leaderboard.component';
import {FormsModule} from '@angular/forms';
import { ReplayComponent } from './replay/replay.component';


@NgModule({
  declarations: [
    AppComponent,
    MapComponent,
    GameComponent,
    InventoryComponent,
    HomeComponent,
    LeaderboardComponent,
    ReplayComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatBadgeModule,
        OverlayModule,
        MatListModule,
        FormsModule
    ],
  providers: [InventoryService],
  bootstrap: [AppComponent]
})
export class AppModule { }
