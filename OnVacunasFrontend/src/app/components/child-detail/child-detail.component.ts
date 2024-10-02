import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Child } from 'src/app/models/child.model';
import { ChildService } from 'src/app/services/child.service';

@Component({
  selector: 'app-child-detail',
  templateUrl: './child-detail.component.html',
  styleUrls: ['./child-detail.component.css']
})
export class ChildDetailComponent implements OnInit {

  child: Child | undefined;

  constructor(private route: ActivatedRoute, private childService: ChildService) { }

  ngOnInit(): void {

    // Obtenemos el ID de la ruta
    const childId = this.route.snapshot.paramMap.get('id');

    if (childId) {
      // Obtenemos el niño por su ID
      this.childService.getChildById(+childId).subscribe({
        next: (data: Child) => {
          this.child = data;
        },
        error: (error) => {
          console.error('Error al obtener el niño:', error);
        }
      });
    }
  }

}
