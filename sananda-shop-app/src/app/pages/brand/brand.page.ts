import { Component, OnInit } from '@angular/core';
import { SharedService } from 'src/app/service/shared.service';

@Component({
  selector: 'app-brand',
  templateUrl: './brand.page.html',
  styleUrls: ['./brand.page.scss'],
})
export class BrandPage implements OnInit {

  title: string = 'brand';
  constructor(private sharedService: SharedService) { }

  ngOnInit() {
    this.sharedService.changeTitle(this.title);
  }

}
