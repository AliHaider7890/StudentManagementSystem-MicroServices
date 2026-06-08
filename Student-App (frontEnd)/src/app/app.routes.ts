// // import { Routes } from '@angular/router';
// // import { LoginComponent } from './login/login.component';
// // import { DashboardComponent } from './dashboard/dashboard.component';

// // export const routes: Routes = [
// //   { path: '', redirectTo: '/login', pathMatch: 'full' },
// //   { path: 'login', component: LoginComponent },
// //   { path: 'dashboard', component: DashboardComponent },
// //   { path: '**', redirectTo: '/login' }
// // ];

// import { Routes } from '@angular/router';
// import { LoginComponent } from './login/login.component';
// import { DashboardComponent } from './dashboard/dashboard.component';
// import { AdminComponent } from './admin/admin.component';

// export const routes: Routes = [
//   { path: '', redirectTo: '/login', pathMatch: 'full' },
//   { path: 'login', component: LoginComponent },
//   { path: 'dashboard', component: DashboardComponent },
//   { path: 'admin', component: AdminComponent },
//   { path: '**', redirectTo: '/login' }
// ];

import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminComponent } from './admin/admin.component';
import { TeacherComponent } from './teacher/teacher.component';
import { TimeTableComponent } from './timetable/timetable.component';
import { TeacherAssignComponent } from './teacher-assign/teacher-assign.component';
import { AssignSlotComponent } from './assign-slot/assign-slot.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'admin', component: AdminComponent },
    { path: 'teacher', component: TeacherComponent },
//  { path: 'teacher', component: TeacherComponent },
    { path: 'teacher-assign', component: TeacherAssignComponent },
  {path : 'timetable' , component : TimeTableComponent },
      { path: 'assign-slot', component: AssignSlotComponent },

  { path: '**', redirectTo: '/login' },

];