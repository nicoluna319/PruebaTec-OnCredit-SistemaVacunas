import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Child } from 'src/app/models/child.model';
import { ChildService } from 'src/app/services/child.service';

@Component({
  selector: 'app-child-list',
  templateUrl: './child-list.component.html',
  styleUrls: ['./child-list.component.css']
})
export class ChildListComponent implements OnInit, OnDestroy {

  children: Child[] = [];
  private childrenUpdatedSub: Subscription | undefined;

  constructor(private childService: ChildService) { }

  ngOnInit(): void {

    // Cargar la lista de niños
    this.loadChildren();

    


    this.childService.getChildren().subscribe({
      next: (data: Child[]) => {
        this.children = data;
      },
      error: (error) => {
        console.error('Error al obtener los niños:', error);
      }
    });
  }

    // Método para cargar la lista de niños
    loadChildren(): void {
      this.childService.getChildren().subscribe({
        next: (data: Child[]) => {
          this.children = data;
        },
        error: (error) => {
          console.error('Error al obtener los niños:', error);
        }
      });
    }
  
    ngOnDestroy(): void {

      if (this.childrenUpdatedSub) {
        this.childrenUpdatedSub.unsubscribe();
      }
    }

}
