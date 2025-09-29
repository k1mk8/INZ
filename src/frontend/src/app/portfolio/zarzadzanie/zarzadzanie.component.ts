import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-zarzadzanie',
  templateUrl: './zarzadzanie.component.html',
  styleUrls: ['./zarzadzanie.component.css']
})
export class ZarzadzanieComponent {
  constructor(private router: Router) {}
  back() { this.router.navigate(['/portfolio']); }
}
