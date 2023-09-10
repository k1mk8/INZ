import { Component } from '@angular/core';

@Component({
  selector: 'app-venus3-dl',
  templateUrl: './venus3-dl.component.html',
  styleUrls: ['./venus3-dl.component.css']
})
export class Venus3DLComponent {
  openImageInNewWindow() {
    window.open('../../assets/venus3dl.jpg', '_blank');
  }
}
