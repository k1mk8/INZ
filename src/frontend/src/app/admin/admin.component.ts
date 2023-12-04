import { Component, OnInit  } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  constructor(private router: Router, private http: HttpClient, private cookieservice: CookieService) {
  }
  registerDimensions = '';
  registerPrice = '';
  registerType = '';
  registerName = '';
  registerDescription = '';
  message = '';
  registerImage = '';
  type: string[] = [];
  name: string[] = [];

  async ngOnInit(): Promise<void> {
    try {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const orderResponse: any = await this.http.get('http://localhost:8082/getProducts').toPromise();
        for (const value of orderResponse) {
          if(value.is_active === "t"){
            this.type.push(value.type);
            this.name.push(value.name);
          }
        }
    } catch (error) {
      console.error('Błąd podczas pobierania produktów', error);
    }
  }

  async registerProduct(): Promise<void> {
    const productData = {
      dimension: this.registerDimensions,
      price: this.registerPrice,
      type: this.registerType,
      name: this.registerName,
      description: this.registerDescription,
      image: this.registerImage
    };
    try {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      const response: any = await this.http.post('http://localhost:8082/addProduct', productData).toPromise();

      if (response === true) {
        console.log('Rejestracja produktu zakończona sukcesem', response);
        this.message = 'rejestracja produktu zakończona sukcesem';
        this.router.navigate(['/products', this.registerName]);
      } else {
        this.message = 'Produkt o takiej nazwie istnieje w systemie';
        console.log('Produkt o takiej nazwie istnieje w systemie');
      }
    } catch (error) {
      console.error('Błąd podczas rejestracji', error);
      this.message = 'Błąd podczas rejestracji produktu. Proszę spróbować ponownie.';
    }
  }
  
  async removeProduct(name: string): Promise<void> {
    try {
      const product = {
        name: name,
        newStatus: false
      };
      await this.http.post('http://localhost:8082/manageProductStatus', product).toPromise();
      const index = this.name.indexOf(name);
      if (index !== -1) {
        this.type.splice(index, 1);
        this.name.splice(index, 1);
      }
    } catch (error) { 
      console.error('Błąd podczas usuwania produktu z oferty', error);
    }
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  onImageSelected(event: any) {
    const selectedFile = event.target.files[0];
    if (selectedFile) {
      if (selectedFile.type === 'image/jpeg') {
        console.log('Zdjęcie zostało wybrane:', selectedFile);
        const reader = new FileReader();

        console.log('test');
        reader.onloadend = () => {
          // 'result' contains the Base64-encoded image
          this.registerImage = reader.result as string;
        };
        console.log(this.registerImage);
        reader.readAsDataURL(selectedFile);
      } else {
        console.log('Wybrany plik nie jest w formacie JPG.');
      }
    }
  }

  deleteCookies(){
    this.cookieservice.deleteAll();
    this.router.navigate(['login']);
  }
}