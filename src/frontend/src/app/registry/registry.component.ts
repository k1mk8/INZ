import { Component, OnInit } from '@angular/core';



@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.css']
})
export class RegistryComponent {
  registerEmail: string = '';
  registerPassword: string = '';

  register() {
    // Kod do rejestracji
    console.log('Zarejestrowano nowego użytkownika:');
    console.log('E-mail: ' + this.registerEmail);
    console.log('Hasło: ' + this.registerPassword);
    
    // Możesz teraz przekazać dane do serwera i wykonać rejestrację użytkownika.
  }
}