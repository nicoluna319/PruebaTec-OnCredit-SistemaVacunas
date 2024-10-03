import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Vaccine } from '../models/vaccine.model';

@Injectable({
  providedIn: 'root'
})
export class VaccineService {

  private apiUrl = 'http://localhost:8080/api/v1/vaccines';

  constructor(private http: HttpClient) {}


  // // Método para asociar una vacuna a un niño
  // applyVaccineToChild(childId: number, vaccineId: number): Observable<any> {
  //   const url = `http://localhost:8080/api/v1/children/${childId}/apply-vaccine/${vaccineId}`;
  //   console.log('Llamando a la URL:', url);  // Depurar la URL construida
  //   return this.http.post(url, {});
  // }
  
  

  getVaccines(): Observable<Vaccine[]> {
    return this.http.get<{ content: Vaccine[] }>(this.apiUrl).pipe(
      map(response => {
        console.log('Respuesta de la API de vacunas:', response);
        if (response && Array.isArray(response.content)) {
          return response.content;
        } else {
          throw new Error('La respuesta no contiene un array de vacunas');
        }
      })
    );
  }

  // Crear una nueva vacuna
  createVaccine(vaccine: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, vaccine);
  }

  // Actualizar una vacuna por ID
  updateVaccine(id: number, vaccine: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, vaccine);
  }

  // Eliminar una vacuna por ID
  deleteVaccine(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }


}
