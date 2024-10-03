import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ChildService } from '../../services/child.service';
import { MunicipalityService } from '../../services/municipality.service';
import { DepartmentService } from '../../services/department.service';
import { Child } from '../../models/child.model';
import { Department } from '../../models/department.model';
import { Municipality } from '../../models/municipality.model';

@Component({
  selector: 'app-child-edit',
  templateUrl: './child-edit.component.html',
  styleUrls: ['./child-edit.component.css']
})
export class ChildEditComponent implements OnInit {
  childForm: FormGroup;
  selectedDepartmentId: string = '';
  departments: Department[] = [];
  municipalities: Municipality[] = [];
  childId: number;

  constructor(
    private fb: FormBuilder,
    private childService: ChildService,
    private municipalityService: MunicipalityService,
    private departmentService: DepartmentService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.childForm = this.fb.group({
      name: ['', Validators.required],
      age: [null, [Validators.required, Validators.min(1)]],
      municipalityId: [null, Validators.required],
      departmentId: [null, Validators.required]
    });

    this.childId = this.route.snapshot.params['id'];
  }

  onDepartmentChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const departmentId = selectElement.value;

    if (departmentId) {
      const id = parseInt(departmentId, 10);
      if (!isNaN(id)) {
        this.municipalityService.getMunicipalitiesByDepartment(id).subscribe({
          next: (data) => {
            console.log('Municipios recibidos:', data);
            this.municipalities = Array.isArray(data) ? data : [];
          },
          error: (error: any) => {
            console.error('Error al obtener los municipios:', error);
          }
        });
      }
    }
  }

  ngOnInit(): void {
    // Cargar los departamentos
    this.departmentService.getDepartments().subscribe({
      next: (data: any) => {
        console.log('Departamentos recibidos:', data); 
        this.departments = Array.isArray(data.content) ? data.content : [];
      },
      error: (error: any) => {
        console.error('Error al obtener los departamentos:', error);
      }
    });
  
    // Cargar el niño por ID
    this.childService.getChildById(this.childId).subscribe({
      next: (data: Child) => {
        console.log('Datos del niño recibidos:', data);
        this.childForm.patchValue(data);
        this.selectedDepartmentId = data.departmentId?.toString() || '';
        this.onDepartmentChange({ target: { value: this.selectedDepartmentId } } as any);
      },
      error: (error: any) => {
        console.error('Error al obtener el niño:', error);
      }
    });
  }

  onSubmit(): void {
    if (this.childForm.valid) {
      console.log('Formulario enviado:', this.childForm.value); 
      
      this.childService.updateChild(this.childId, this.childForm.value).subscribe({
        next: (response: any) => {
          console.log('Niño actualizado exitosamente:', response);
          this.router.navigate(['/children']);
        },
        error: (error: any) => {
          console.error('Error al actualizar el niño:', error);
        }
      });
    }
  }
}
