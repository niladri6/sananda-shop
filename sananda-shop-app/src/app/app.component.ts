import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Event, NavigationEnd, NavigationStart, Router } from '@angular/router';
import { SharedService } from './service/shared.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent implements OnInit {
  appTitle: string;
  
  
  constructor(private router: Router, private route: ActivatedRoute, private sharedService: SharedService) { }

  ngOnInit(): void {
    // this.route.url.subscribe(() => {
    //   console.log(this.route.snapshot.firstChild.data);
    // });
    //this.appTitle = this.route.snapshot.data['title'];
    
    this.sharedService.getTitle().subscribe(data => {
      console.log('title: ', data);
      this.appTitle = data;
    });

    this.router.events.forEach((event: Event) => {
      if(event instanceof NavigationStart) {
        console.log('url: ', event.url);
      }



      // NavigationEnd
      // if(event instanceof NavigationEnd) {
      // }

      // NavigationCancel
      // NavigationError
      // RoutesRecognized
    });
  }
}
