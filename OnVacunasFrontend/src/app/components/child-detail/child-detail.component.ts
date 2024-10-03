import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'; // Importa Router
import { ChildService } from '../../services/child.service';
import { VaccineService } from '../../services/vaccine.service';

@Component({
  selector: 'app-child-detail',
  templateUrl: './child-detail.component.html',
  styleUrls: ['./child-detail.component.css']
})
export class ChildDetailComponent implements OnInit {
  child: any;
  vaccines: any[] = [];
  selectedVaccineId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private childService: ChildService,
    private vaccineService: VaccineService, 
    private router: Router 
  ) {}

  ngOnInit(): void {
    const childId = this.route.snapshot.paramMap.get('id');
  
    if (childId) {
      this.childService.getChildById(+childId).subscribe({
        next: (data) => {
          this.child = data;
        },
        error: (error) => {
          console.error('Error al obtener el niño:', error);
        }
      });
  
      this.vaccineService.getVaccines().subscribe({
        next: (data) => {
          if (Array.isArray(data)) {
            this.vaccines = data;
          } else {
            console.error('Error: Los datos de vacunas no son un array:', data);
          }
        },
        error: (error) => {
          console.error('Error al obtener las vacunas:', error);
        }
      });
    }
  }

  // Aplicar una vacuna al niño
  applyVaccine(): void {
    if (this.selectedVaccineId && this.child.id) {
      console.log(`Aplicando vacuna a niño con ID: ${this.child.id} y vacuna con ID: ${this.selectedVaccineId}`);
      this.childService.applyVaccineToChild(this.child.id, this.selectedVaccineId).subscribe({
        next: () => {
          alert('Vacuna aplicada exitosamente');
          this.router.navigate(['/children']); 
        },
        error: (error) => {
          console.error('Error al aplicar la vacuna:', error);
        }
      });
    }
  }

  // Cargar los detalles del niño
  loadChild(childId: number): void {
    this.childService.getChildById(childId).subscribe({
      next: (data) => {
        this.child = data;
      },
      error: (error) => {
        console.error('Error al obtener los detalles del niño:', error);
      }
    });
  }
}
