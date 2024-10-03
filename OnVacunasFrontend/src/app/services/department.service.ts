import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DepartmentResponse } from '../models/department.model';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  private apiUrl = 'http://localhost:8080/api/v1/departments';

  constructor(private http: HttpClient) {}

  // Método para obtener todos los departamentos
  getDepartments(page: number = 0, size: number = 10): Observable<DepartmentResponse> {
    return this.http.get<DepartmentResponse>(`${this.apiUrl}?page=${page}&size=${size}`);
  }

  // Crear un nuevo departamento
  createDepartment(department: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, department);
  }

  // Actualizar un departamento por ID
  updateDepartment(id: number, department: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, department);
  }

  // Eliminar un departamento por ID
  deleteDepartment(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // Aplicar una vacuna a un niño
  applyVaccineToChild(childId: number, vaccineId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/${childId}/vaccines/${vaccineId}`, {});
  }

  // Obtener el resumen de niños agrupados por municipio
  getChildrenSummaryByMunicipality(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/summary`);
  }

  // Obtener el promedio de edad de los niños por municipio
  getAverageAgeByMunicipality(municipalityId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/municipalities/${municipalityId}/average-age`);
  }

}
