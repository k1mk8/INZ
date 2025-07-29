import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

interface News {
  id: number;
  title: string;
  content: string;
  published_at?: string;
  is_published: boolean;
}

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  newsList: News[] = [];
  pagedItems: News[] = [];
  loadingList = false;
  currentPage = 0;
  pageSize = 10;
  totalPages = 0;

  formModel = { title: '', content: '', publishedDate: '', isPublished: false };
  editingId: number | null = null;
  private baseUrl = 'http://localhost:8082/admin/news';

  constructor(
    private router: Router,
    private http: HttpClient,
    private cookieService: CookieService
  ) {}

  ngOnInit(): void {
    this.loadNews();
  }

  deleteCookies(): void {
    this.cookieService.deleteAll();
    this.router.navigate(['login']);
  }

  loadNews(): void {
    this.loadingList = true;
    this.http.get<News[]>(this.baseUrl).subscribe({
      next: data => {
        this.newsList = data;
        this.totalPages = Math.ceil(this.newsList.length / this.pageSize);
        this.setPagedItems();
        this.loadingList = false;
      },
      error: () => { this.loadingList = false; alert('Błąd przy ładowaniu listy'); }
    });
  }

  setPagedItems(): void {
    const start = this.currentPage * this.pageSize;
    this.pagedItems = this.newsList.slice(start, start + this.pageSize);
  }

  prevPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.setPagedItems();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.setPagedItems();
    }
  }

  onSubmit(): void {
    const payload = {
      title: this.formModel.title,
      content: this.formModel.content,
      is_published: this.formModel.isPublished,
      published_at: this.formModel.publishedDate
        ? new Date(this.formModel.publishedDate).toISOString()
        : null
    };
    const request$ = this.editingId === null
      ? this.http.post(this.baseUrl, payload)
      : this.http.put(`${this.baseUrl}/${this.editingId}`, payload);
    request$.subscribe({ next: () => { this.resetForm(); this.loadNews(); },
      error: () => alert(this.editingId === null ? 'Błąd przy dodawaniu' : 'Błąd przy edycji')
    });
  }

  startEdit(item: News): void {
    this.editingId = item.id;
    this.formModel = {
      title: item.title,
      content: item.content,
      isPublished: item.is_published,
      publishedDate: item.published_at ? item.published_at.slice(0,10) : ''
    };
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  deleteNews(id: number): void {
    if (!confirm('Na pewno usunąć?')) return;
    this.http.delete(`${this.baseUrl}/${id}`).subscribe({ next: () => this.loadNews(), error: () => alert('Błąd przy usuwaniu') });
  }

  cancelEdit(): void { this.resetForm(); }

  private resetForm(): void {
    this.editingId = null;
    this.formModel = { title: '', content: '', publishedDate: '', isPublished: false };
  }
}