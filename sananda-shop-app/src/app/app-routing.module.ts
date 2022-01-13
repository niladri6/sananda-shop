import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'brand', pathMatch: 'full' },
  // {
  //   path: 'home',
  //   loadChildren: () => import('./home/home.module').then( m => m.HomePageModule)
  // },
  // {
  //   path: 'brand',
  //   loadChildren: () => import('./pages/brand/brand.module').then( m => m.BrandPageModule)
  //   //loadChildren: './pages/brand/brand.module#BrandPageModule'
  // },
  // {
  //   path: ':brandId/category',
  //   loadChildren: () => import('./pages/category/category.module').then( m => m.CategoryPageModule)
  // },
  {
    path: 'brand',
    children: [
      {
        path: '',
        loadChildren: () => import('./pages/brand/brand.module').then( m => m.BrandPageModule),
        data: { title: 'Brand'}
      },
      {
        path: ':brandId/category',
        loadChildren: () => import('./pages/category/category.module').then( m => m.CategoryPageModule),
        data: { title: 'Category'}
      },
    ]
  },


  
  
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
