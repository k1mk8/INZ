import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ContactComponent } from './contact/contact.component';
import { MainPageComponent } from './main-page/main-page.component';
import { NewsComponent } from './news/news.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { MenuComponent } from './menu/menu.component';
import {CookieService} from 'ngx-cookie-service';
import { AboutusComponent } from './aboutus/aboutus.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { ButtonsComponent } from './buttons/buttons.component';
import { BottomBarComponent } from './bottom-bar/bottom-bar.component';
import { TeamComponent } from './team/team.component';
import { AdminComponent } from './admin/admin.component';
import { NewsDetailComponent } from './news-detail/news-detail.component';
import { TruncatePipe } from './shared/truncate.pipe';

@NgModule({
  declarations: [
    AppComponent,
    ContactComponent,
    MainPageComponent,
    NewsComponent,
    LoginComponent,
    MenuComponent,
    AboutusComponent,
    PortfolioComponent,
    ButtonsComponent,
    BottomBarComponent,
    TeamComponent,
    AdminComponent,
    NewsDetailComponent,
    TruncatePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
  ],
  providers:
  [CookieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
