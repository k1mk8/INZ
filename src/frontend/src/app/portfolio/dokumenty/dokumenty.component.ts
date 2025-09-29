import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dokumenty',
  templateUrl: './dokumenty.component.html',
  styleUrls: ['./dokumenty.component.css']
})
export class DokumentyComponent {
  constructor(private router: Router) {}
  back() { this.router.navigate(['/portfolio']); }
}
