import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BoutonCityblockComponent } from './bouton-cityblock.component';

describe('BoutonCityblockComponent', () => {
  let component: BoutonCityblockComponent;
  let fixture: ComponentFixture<BoutonCityblockComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BoutonCityblockComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BoutonCityblockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
