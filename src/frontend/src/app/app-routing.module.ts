import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NewsDetailComponent } from './news-detail/news-detail.component';
import { ContactComponent } from './contact/contact.component';
import { MainPageComponent } from './main-page/main-page.component';
import { NewsComponent } from './news/news.component';
import { LoginComponent } from './login/login.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { PortfolioListComponent } from './portfolio/portfolio-list.component';
import { DocAnalizyComponent } from './portfolio/doc-analizy/doc-analizy.component';
import { ZarzadzanieComponent } from './portfolio/zarzadzanie/zarzadzanie.component';
import { DokumentyComponent } from './portfolio/dokumenty/dokumenty.component';
import { TeamComponent } from './team/team.component';
import { AdminComponent } from './admin/admin.component';
import { AdminGuard } from './admin.guard';

const routes: Routes = [
  {
    path: '',
    component: MainPageComponent
  },
  {
    path: 'contact',
    component: ContactComponent
  },
  {
    path: 'main',
    component: MainPageComponent
  },
  {
    path: 'news',
    component: NewsComponent
  }, 
  { path: 'news/:id',
    component: NewsDetailComponent },
  {
    path: 'login',
    component: LoginComponent
  }, 
  {
    path: 'team',
    component: TeamComponent
  }, 
  {
    path: 'aboutus',
    component: AboutusComponent
  }, 
  {
    path: 'portfolio',
    component: PortfolioListComponent
  },
  { path: 'portfolio/doc-analizy', component: DocAnalizyComponent },
  { path: 'portfolio/zarzadzanie', component: ZarzadzanieComponent },
  { path: 'portfolio/dokumenty', component: DokumentyComponent },  
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AdminGuard]
  },
  {path:'**',redirectTo:"/"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
