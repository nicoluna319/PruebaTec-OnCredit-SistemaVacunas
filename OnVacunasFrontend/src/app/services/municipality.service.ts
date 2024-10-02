import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MunicipalityService {

  private apiUrl = 'http://localhost:8080/api/v1/municipalities'; // URL del backend

  constructor(private http: HttpClient) { }

  getMunicipalities(): Observable<any[]> {
    // Extraer la propiedad `content` del objeto devuelto por el backend
    return this.http.get<{ content: any[] }>(this.apiUrl).pipe(
      map(response => response.content) // Extraemos solo el array `content`
    );
  }
}
