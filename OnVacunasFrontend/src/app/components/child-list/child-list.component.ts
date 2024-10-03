import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
  childrenSummary: any[] = [];
  averageAge: number | null = null;
  selectedMunicipalityId: number | null = null;
  private childrenUpdatedSub: Subscription | undefined;

  constructor(private childService: ChildService, private router: Router) { }

  ngOnInit(): void {
    this.loadChildren();
  }

  // Cargar la lista de niños
  loadChildren(): void {
    this.childService.getChildren(0, 10).subscribe({
      next: (data) => {
        this.children = data['content']; 
      },
      error: (error) => {
        console.error('Error al cargar los niños:', error);
      }
    });
  }

  // Obtener el resumen de niños agrupados por municipio
  loadChildrenSummary(): void {
    this.childService.getChildrenSummaryByMunicipality().subscribe({
      next: (data) => {
        this.childrenSummary = data;
        console.log('Resumen de niños por municipio:', this.childrenSummary);
      },
      error: (error) => {
        console.error('Error al obtener el resumen de niños:', error);
      }
    });
  }

  // Obtener el promedio de edad por municipio
  loadAverageAge(): void {
    if (this.selectedMunicipalityId) {
      this.childService.getAverageAgeByMunicipality(this.selectedMunicipalityId).subscribe({
        next: (data: number) => {
          this.averageAge = data;
          console.log(`Promedio de edad para el municipio ${this.selectedMunicipalityId}:`, this.averageAge);
        },
        error: (error) => {
          console.error('Error al obtener el promedio de edad:', error);
        }
      });
    }
  }

  // Redirigir a la página de creación de un niño
  createChild(): void {
    this.router.navigate(['/children/create']);
  }

  // Eliminar un niño
  deleteChild(childId: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar este niño?')) {
      this.childService.deleteChild(childId).subscribe(
        () => {
          this.loadChildren(); // Recargar la lista tras eliminar
        },
        (error) => {
          console.error('Error al eliminar el niño:', error);
        }
      );
    }
  }

  // Redirigir al detalle de un niño
  viewChildDetails(childId: number): void {
    this.router.navigate([`/children/${childId}`]);
  }

  // Navegar a la página de actualización de un niño
  updateChild(childId: number): void {
    this.router.navigate([`/children/update/${childId}`]);
  }

  ngOnDestroy(): void {
    if (this.childrenUpdatedSub) {
      this.childrenUpdatedSub.unsubscribe();
    }
  }
}
