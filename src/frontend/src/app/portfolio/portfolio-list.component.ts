import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-portfolio-list',
  templateUrl: './portfolio-list.component.html',
  styleUrls: ['./portfolio-list.component.css']
})
export class PortfolioListComponent {
  tiles = [
    {
      title: 'Opracowanie dokumentacji aplikacyjnej i niezbędnych analiz celem pozyskania finansowania',
      slug: 'doc-analizy',
      desc: ''
    },
    {
      title: 'Zarządzanie projektami i rozliczenia finansowe projektów',
      slug: 'zarzadzanie',
      desc: ''
    },
    {
      title: 'Dokumenty strategiczne i planistyczne',
      slug: 'dokumenty',
      desc: ''
    }
  ];

  constructor(private router: Router) {}

  openCategory(slug: string) {
    this.router.navigate(['/portfolio', slug]);
  }
}
