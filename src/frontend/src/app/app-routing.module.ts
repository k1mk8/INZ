import { HtmlParser } from '@angular/compiler';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { ContactComponent } from './contact/contact.component';
import { MainPageComponent } from './main-page/main-page.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { LoginComponent } from './login/login.component';
import { RegistryComponent } from './registry/registry.component';
import { Venus3DLComponent } from './venus3-dl/venus3-dl.component';
import { BasketComponent } from './basket/basket.component';
import { HistoryComponent } from './history/history.component';
import { OliwiaIII3dlComponent } from './oliwia-iii3dl/oliwia-iii3dl.component';
import { VenusFotelComponent } from './venus-fotel/venus-fotel.component';
import { MatrixComponent } from './matrix/matrix.component';
import { SaraComponent } from './sara/sara.component';
import { ClassiccouchComponent } from './classiccouch/classiccouch.component';
import { DivankubaComponent } from './divankuba/divankuba.component';
import { DivanjasComponent } from './divanjas/divanjas.component';

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
    path: 'myaccount',
    component: MyAccountComponent
  }, 
  {
    path: 'login',
    component: LoginComponent
  }, 
  {
    path: 'registry',
    component: RegistryComponent
  }, 
  {
    path: 'venus3dl',
    component: Venus3DLComponent
  }, 
  {
    path: 'basket',
    component: BasketComponent
  }, 
  {
    path: 'history',
    component: HistoryComponent
  },
  {
    path: 'oliwiaIII3dl',
    component: OliwiaIII3dlComponent
  }, 
  {
    path: 'venusfotel',
    component: VenusFotelComponent
  },
  {
    path: 'matrix',
    component: MatrixComponent
  },
  {
    path: 'sara',
    component: SaraComponent
  }, 
  {
    path: 'classiccouch',
    component: ClassiccouchComponent
  },
  {
    path: 'divankuba',
    component: DivankubaComponent
  },
  {
    path: 'divanjas',
    component: DivanjasComponent
  },                        
  // otherwise redirect to home page
  {path:'**',redirectTo:"/"}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
