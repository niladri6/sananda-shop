import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  // private defaultTitle = 'Sananda App';
  // private titleSubject: BehaviorSubject<string> = new BehaviorSubject(this.defaultTitle);
  // public title: Observable<string>; 

  private titleSource = new BehaviorSubject<string>('');
  currentTitle = this.titleSource.asObservable();

  constructor() { }

  changeTitle(title: string) {
    this.titleSource.next(title);
  }
  getTitle() {
    return this.currentTitle;
  }

}
