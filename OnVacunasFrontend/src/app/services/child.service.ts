import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Child } from '../models/child.model';

@Injectable({
  providedIn: 'root'
})
export class ChildService {

  private apiUrl = 'http://localhost:8080/api/v1/children';
  private childrenUpdated = new Subject<void>();

  constructor(private http: HttpClient) { }

  getChildrenUpdated(): Observable<void> {
    return this.childrenUpdated.asObservable();
  }

  // Obtener todos los niños con paginación
  getChildren(page: number, size: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}?page=${page}&size=${size}`);
  }


// Obtener un niño por ID
  getChildById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Crear un niño
  createChild(child: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, child);
  }

  // Actualizar los datos de un niño por ID
  updateChild(id: number, child: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, child);
  }

  // Eliminar un niño por ID
  deleteChild(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

 // Método para asociar una vacuna a un niño
 applyVaccineToChild(childId: number, vaccineId: number): Observable<any> {
  const url = `http://localhost:8080/api/v1/children/${childId}/apply-vaccine/${vaccineId}`;
  console.log('Llamando a la URL:', url);  // Depurar la URL construida
  return this.http.post(url, {});
}

  // Obtener el resumen de niños agrupados por municipio
  getChildrenSummaryByMunicipality(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/summary`);
  }

  // Obtener el promedio de edad de los niños por municipio
  getAverageAgeByMunicipality(municipalityId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/average-age/${municipalityId}`);
  }

}


