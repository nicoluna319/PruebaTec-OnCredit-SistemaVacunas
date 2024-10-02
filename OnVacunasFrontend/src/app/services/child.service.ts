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

  getChildren(): Observable<Child[]> {
    return this.http.get<{ content: Child[] }>(this.apiUrl).pipe(
      map(response => response.content)
    );
  }

  createChild(child: Partial<Child>): Observable<Child> {
    return this.http.post<Child>(this.apiUrl, child).pipe(
      tap(() => this.childrenUpdated.next())  // Emitir evento de actualización
    );
  }

  // Método para obtener un niño por ID
  getChildById(id: number): Observable<Child> {
    return this.http.get<Child>(`${this.apiUrl}/${id}`);
  }

}


