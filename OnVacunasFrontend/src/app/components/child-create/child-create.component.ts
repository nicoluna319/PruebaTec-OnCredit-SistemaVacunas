import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MunicipalityService } from '../../services/municipality.service';
import { Municipality, MunicipalityResponse } from 'src/app/models/municipality.model';
import { Department, DepartmentResponse } from 'src/app/models/department.model';
import { DepartmentService } from 'src/app/services/department.service';
import { ChildService } from 'src/app/services/child.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-child-create',
  templateUrl: './child-create.component.html',
  styleUrls: ['./child-create.component.css']
})
export class ChildCreateComponent implements OnInit {
  childForm: FormGroup;
  selectedDepartmentId: string = '';
  departments: Department[] = [];
  municipalities: Municipality[] = [];

  constructor(
    private fb: FormBuilder,
    private childService: ChildService,
    private municipalityService: MunicipalityService,
    private departmentService: DepartmentService,
    private router: Router
  ) {
    this.childForm = this.fb.group({
      name: ['', Validators.required],
      age: [null, [Validators.required, Validators.min(1)]],
      departmentId: [null, Validators.required],
      municipalityId: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    // Cargar departamentos al inicializar
    this.departmentService.getDepartments().subscribe({
      next: (data: DepartmentResponse) => {
        this.departments = data.content; // Accedemos a 'content' que contiene los departamentos
      },
      error: (error: any) => {
        console.error('Error al obtener los departamentos:', error);
      }
    });
  }
  
  // Método para cargar municipios cuando se seleccione un departamento
  onDepartmentChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const departmentId = selectElement.value;

    if (departmentId) {
      const id = parseInt(departmentId, 10);
      if (!isNaN(id)) {
        this.municipalityService.getMunicipalitiesByDepartment(id).subscribe({
          next: (data) => {
            if (Array.isArray(data)) {
              this.municipalities = data;
            } else if (data && data.content && Array.isArray(data.content)) {
              this.municipalities = data.content;
            } else {
              console.error('La estructura recibida no es un array ni contiene un array en content');
              this.municipalities = [];
            }
          },
          error: (error) => {
            console.error('Error al obtener los municipios:', error);
          }
        });
      }
    }
  }

  onSubmit(): void {
    if (this.childForm.valid) {
      const formValue = this.childForm.value;
      console.log('Payload enviado:', formValue);
      this.childService.createChild(formValue).subscribe({
        next: (response: any) => {
          console.log('Niño creado exitosamente:', response);
          this.router.navigate(['/children']);
        },
        error: (error: any) => {
          console.error('Error al crear el niño:', error);
        }
      });
    }
  }
}
