import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ChildListComponent } from './components/child-list/child-list.component';
import { ChildDetailComponent } from './components/child-detail/child-detail.component';
import { ChildCreateComponent } from './components/child-create/child-create.component';

@NgModule({
  declarations: [
    AppComponent,
    ChildListComponent,
    ChildDetailComponent,
    ChildCreateComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
