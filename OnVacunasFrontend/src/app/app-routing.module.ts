import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChildListComponent } from './components/child-list/child-list.component';
import { ChildDetailComponent } from './components/child-detail/child-detail.component';
import { ChildCreateComponent } from './components/child-create/child-create.component';

const routes: Routes = [
  { path: 'children', component: ChildListComponent },
  { path: 'children/create', component: ChildCreateComponent },
  { path: 'children/:id', component: ChildDetailComponent},
  { path: '', redirectTo: '/children', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
