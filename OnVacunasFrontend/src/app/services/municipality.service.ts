import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Municipality, MunicipalityResponse } from '../models/municipality.model';

@Injectable({
  providedIn: 'root'
})
export class MunicipalityService {

  private apiUrl = 'http://localhost:8080/api/v1/municipalities'; 

  constructor(private http: HttpClient) { }

    // Obtener todos los municipios
    getMunicipalities(): Observable<any[]> {
      return this.http.get<any[]>(this.apiUrl);
    }

    getMunicipalitiesByDepartment(departmentId: number): Observable<MunicipalityResponse> {
      return this.http.get<MunicipalityResponse>(`${this.apiUrl}/by-department/${departmentId}`);
    }
  
    // Crear un nuevo municipio
    createMunicipality(municipality: any): Observable<any> {
      return this.http.post<any>(this.apiUrl, municipality);
    }
  
    // Actualizar un municipio por ID
    updateMunicipality(id: number, municipality: any): Observable<any> {
      return this.http.put<any>(`${this.apiUrl}/${id}`, municipality);
    }
  
    // Eliminar un municipio por ID
    deleteMunicipality(id: number): Observable<any> {
      return this.http.delete<any>(`${this.apiUrl}/${id}`);
    }
  
}
