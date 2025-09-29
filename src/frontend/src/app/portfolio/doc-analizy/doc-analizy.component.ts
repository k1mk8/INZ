import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-doc-analizy',
  templateUrl: './doc-analizy.component.html',
  styleUrls: ['./doc-analizy.component.css']
})
export class DocAnalizyComponent {
  constructor(private router: Router) {}
  back() { this.router.navigate(['/portfolio']); }
}
