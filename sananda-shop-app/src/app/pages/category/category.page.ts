import { Component, OnInit } from '@angular/core';
import { SharedService } from 'src/app/service/shared.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.page.html',
  styleUrls: ['./category.page.scss'],
})
export class CategoryPage implements OnInit {

  title: string = 'Category';
  constructor(private sharedService: SharedService) { }

  ngOnInit() {
    this.sharedService.changeTitle(this.title);
  }

}
