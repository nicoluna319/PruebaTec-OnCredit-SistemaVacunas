import { Component, OnInit } from '@angular/core';
import { MunicipalityService } from '../../services/municipality.service';
import { DepartmentService } from '../../services/department.service';
import { DepartmentResponse } from 'src/app/models/department.model';
import { MunicipalityResponse } from 'src/app/models/municipality.model';

@Component({
  selector: 'app-municipality-list',
  templateUrl: './municipality-list.component.html',
  styleUrls: ['./municipality-list.component.css']
})
export class MunicipalityListComponent implements OnInit {
  departments: any[] = [];
  municipalities: any[] = [];
  selectedDepartmentId: number | null = null;

  constructor(
    private municipalityService: MunicipalityService,
    private departmentService: DepartmentService
  ) {}

  ngOnInit(): void {
    this.loadDepartments();
  }

  // Cargar departamentos
  loadDepartments(): void {
    this.departmentService.getDepartments().subscribe({
      next: (response: DepartmentResponse) => {
        this.departments = response.departments;
      },
      error: (error: any) => {
        console.error('Error fetching departments', error);
      }
    });
  }


  // Cargar municipios por departamento
  loadMunicipalities(departmentId: number | null): void {
    if (departmentId !== null) {
      this.municipalityService.getMunicipalitiesByDepartment(departmentId).subscribe({
        next: (response: MunicipalityResponse) => {
          this.municipalities = response.municipalities;
        },
        error: (error: any) => {
          console.error('Error al cargar los municipios:', error);
        }
      });
    } else {
      this.municipalities = [];
    }
  }
}
