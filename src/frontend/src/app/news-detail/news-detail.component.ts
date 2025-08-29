// src/app/news-detail/news-detail.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface News {
  id: number;
  title: string;
  content: string;
  published_at: string | null;
  is_published: boolean;
}

@Component({
  selector: 'app-news-detail',
  templateUrl: './news-detail.component.html',
  styleUrls: ['./news-detail.component.css']
})
export class NewsDetailComponent implements OnInit {
  item: News | null = null;
  loading = true;
  error = '';

  constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      this.error = 'Brak identyfikatora artykułu';
      this.loading = false;
      return;
    }
    this.http.get<News>(`http://localhost:8082/news/${id}`).subscribe({
      next: data => {
        this.item = data;
        this.loading = false;
      },
      error: err => {
        console.error('Błąd pobierania artykułu', err);
        this.error = 'Nie znaleziono artykułu.';
        this.loading = false;
      }
    });
  }

  back(): void {
    // wróć do listy newsów
    this.router.navigate(['/news']);
  }
}
