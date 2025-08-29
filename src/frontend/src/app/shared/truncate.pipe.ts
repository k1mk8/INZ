import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'truncate'
})
export class TruncatePipe implements PipeTransform {
  transform(value: string | null | undefined, limit = 100, trail = '…'): string {
    if (!value) return '';
    if (value.length <= limit) return value;
    const sub = value.substr(0, limit);
    const lastSpace = sub.lastIndexOf(' ');
    return sub.substr(0, lastSpace > 20 ? lastSpace : limit) + trail;
  }
}