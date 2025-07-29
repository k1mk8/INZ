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
        // ↓ gdy przewijasz w dół, pokaż poniżej linię, usuń warunek `&& currY > 20` jeśli chcesz próg 0px
        if (currY > prevY /* && currY > 20 */) {
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