import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactComponent } from './contact/contact.component';
import { MainPageComponent } from './main-page/main-page.component';
import { NewsComponent } from './news/news.component';
import { LoginComponent } from './login/login.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { ProductsComponent } from './products/products.component';
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
  {
    path: 'login',
    component: LoginComponent
  }, 
  {
    path: 'products/:id',
    component: ProductsComponent
  }, 
  {
    path: 'aboutus',
    component: AboutusComponent
  }, 
  {
    path: 'portfolio',
    component: PortfolioComponent
  },
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
