import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { fromEvent, Subscription } from 'rxjs';
import { throttleTime, map, pairwise } from 'rxjs/operators';

@Component({
  selector: 'app-bottom-bar',
  templateUrl: './bottom-bar.component.html',
  styleUrls: ['./bottom-bar.component.css']
})
export class BottomBarComponent implements OnInit, OnDestroy {
  isHidden = false;
  private scrollSub!: Subscription;

  constructor(private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.scrollSub = fromEvent(window, 'scroll')
      .pipe(
        throttleTime(100),
        map(() => window.pageYOffset || document.documentElement.scrollTop),
        pairwise()
      )
      .subscribe(([prevY, currY]) => {
        if (currY > prevY) {
          this.isHidden = true;
        } else {
          this.isHidden = false;
        }
        this.cdr.markForCheck();
      });
  }

  ngOnDestroy(): void {
    this.scrollSub.unsubscribe();
  }
}