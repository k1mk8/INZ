import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface News {
  id: number;
  title: string;
  content: string;
  published_at: string | null;
  is_published: boolean;
}

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent implements OnInit {
  newsList: News[] = [];
  pagedNews: News[] = [];
  loading = true;
  error = '';
  currentPage = 0;
  pageSize = 3;
  pageCount = 0;

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.fetchNews();
  }

  private fetchNews(): void {
    this.http.get<News[]>('http://localhost:8082/news').subscribe({
      next: data => {
        const published = data
          .filter(item => item.is_published)
          .sort((a,b) => {
            const da = a.published_at ? new Date(a.published_at).getTime() : 0;
            const db = b.published_at ? new Date(b.published_at).getTime() : 0;
            return db - da;
          });
        this.newsList = published;
        this.pageCount = Math.max(1, Math.ceil(this.newsList.length / this.pageSize));
        this.currentPage = 0;
        this.updatePagedNews();
        this.loading = false;
      },
      error: err => {
        console.error('Błąd przy pobieraniu newsów', err);
        this.error = 'Nie udało się pobrać aktualności.';
        this.loading = false;
      }
    });
  }

  updatePagedNews(): void {
    const start = this.currentPage * this.pageSize;
    this.pagedNews = this.newsList.slice(start, start + this.pageSize);
  }

  prevPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.updatePagedNews();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.pageCount - 1) {
      this.currentPage++;
      this.updatePagedNews();
    }
  }

  goTo(page: number): void {
    this.currentPage = page;
    this.updatePagedNews();
  }

  open(item: News): void {
    // przejdź do strony szczegółów
    this.router.navigate(['/news', item.id]);
  }
}
