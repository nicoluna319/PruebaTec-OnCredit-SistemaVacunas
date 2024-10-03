import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ChildListComponent } from './components/child-list/child-list.component';
import { ChildDetailComponent } from './components/child-detail/child-detail.component';
import { ChildCreateComponent } from './components/child-create/child-create.component';
import { MunicipalityListComponent } from './components/municipality-list/municipality-list.component';
import { ChildEditComponent } from './components/child-edit/child-edit.component';
import { VaccineService } from './services/vaccine.service';

@NgModule({
  declarations: [
    AppComponent,
    ChildListComponent,
    ChildDetailComponent,
    ChildCreateComponent,
    MunicipalityListComponent,
    ChildEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [VaccineService],
  bootstrap: [AppComponent]
})
export class AppModule { }
