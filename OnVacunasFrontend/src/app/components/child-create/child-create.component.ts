import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ChildService } from 'src/app/services/child.service';
import { MunicipalityService } from 'src/app/services/municipality.service';

@Component({
  selector: 'app-child-create',
  templateUrl: './child-create.component.html',
  styleUrls: ['./child-create.component.css']
})
export class ChildCreateComponent implements OnInit {

  childForm: FormGroup;
  municipalities: any[] = []; // Lista de municipios

  constructor(private fb: FormBuilder,
    private childService: ChildService,
    private municipalityService: MunicipalityService,
    private router: Router) {
      this.childForm = this.fb.group({

        name: ['', Validators.required],
        age: [null, [Validators.required, Validators.min(1)]],
        municipalityId: [null, Validators.required]
      });
     }

     ngOnInit(): void {
      // Cargar municipios al iniciar
      this.municipalityService.getMunicipalities().subscribe(
        (data) => {
          this.municipalities = data; // Ya es un array, porque lo extraemos con `content`
        },
        (error) => {
          console.error('Error al obtener los municipios:', error);
        }
      );
    }

  onSubmit(): void {
    if (this.childForm.valid) {
      const newChild = this.childForm.value;

      // Llamada al servicio para crear el niño
      this.childService.createChild(newChild).subscribe(
        (response) => {
          console.log('Niño creado exitosamente:', response);
          // Redirigir a la lista de niños después de crear el niño
          this.router.navigate(['/children']);
        },
        (error) => {
          console.error('Error al crear el niño:', error);
        }
      );
    }
  }
  

}
