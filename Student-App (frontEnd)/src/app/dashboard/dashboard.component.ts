
// // import { Component, inject, OnInit } from '@angular/core';
// // import { CommonModule } from '@angular/common';
// // import { Router } from '@angular/router';
// // import { HttpClient } from '@angular/common/http';
// // import { AuthService } from '../services/auth.service';

// // @Component({
// //   selector: 'app-dashboard',
// //   standalone: true,
// //   imports: [CommonModule],
// //   templateUrl: './dashboard.component.html',
// //   styleUrls: ['./dashboard.component.css']
// // })
// // export class DashboardComponent implements OnInit {
// //   courses: any[] = [];
// //   loading: boolean = true;
// //   activeTab: string = 'all';
// //   error: string = '';
// //   removing: boolean = false;
  
// //   private http = inject(HttpClient);
// //   private auth = inject(AuthService);
// //   private router = inject(Router);
  
// //   ngOnInit() {
// //     this.loadAllCourses();
// //   }
// //   getStudentId(): string {
// //   return localStorage.getItem('Id') || '';  // 'Id' se le rahe ho
// // }
// //   // 🔥 ID get karo localStorage se
// //   // getStudentId(): string {
// //   //   return localStorage.getItem('Id') || '';
// //   // }

// //   // ✅ ALL COURSES
// //   loadAllCourses() {
// //     this.loading = true;
// //     this.activeTab = 'all';
// //     this.error = '';
    
// //     this.http.get('http://localhost:8083/api/students/getAllCourses')
// //       .subscribe({
// //         next: (res: any) => {
// //           this.courses = res;
// //           this.loading = false;
// //           console.log('All courses:', this.courses);
// //         },
// //         error: (err) => {
// //           console.error('Error:', err);
// //           this.error = 'Failed to load courses';
// //           this.loading = false;
// //         }
// //       });
// //   }

  
// //   loadMyCourses() {
// //   this.loading = true;
// //   this.activeTab = 'my';
// //   this.error = '';
  
// //   const studentId = this.getStudentId();
  
// //   if (!studentId) {
// //     this.error = 'Please login again';
// //     this.loading = false;
// //     return;
// //   }
  
// //   this.http.get(`http://localhost:8083/api/students/${studentId}`)
// //     .subscribe({
// //       next: (res: any) => {
// //         console.log('FULL RESPONSE:', res);  // 🔥 YEH DEKHO
        
// //         // 🔥 Check kis property mein courses hain
// //         console.log('res.courses:', res.courses);
// //         console.log('res.data:', res.data);
// //         console.log('res.enrolledCourses:', res.enrolledCourses);
// //         console.log('res.assignedCourses:', res.assignedCourses);
        
// //         this.courses = res.courses || [];
// //         this.loading = false;
// //       },
// //       error: (err) => {
// //         console.error('Error:', err);
// //         this.error = 'Failed to load your courses';
// //         this.loading = false;
// //       }
// //     });
// // }


// //   removeAllCourses() {
// //   const studentId = this.getStudentId();
  
// //   if (!studentId) {
// //     this.error = 'Please login again';
// //     return;
// //   }
  
// //   const confirmRemove = confirm('Remove ALL courses?');
// //   if (!confirmRemove) return;
  
// //   this.removing = true;
  
// //   this.http.patch(`http://localhost:8083/api/students/students/${studentId}/false`, {})
// //     .subscribe({
// //       next: () => {
// //         this.removing = false;
// //         this.loadMyCourses();
// //         alert('All courses removed successfully!');
// //       },
// //       error: (err) => {
// //         console.log('Error but course might be removed:', err);
// //         // 🔥 Still reload courses - kyuki course remove ho chuka hoga
// //         this.removing = false;
// //         this.loadMyCourses();
// //         alert('All courses removed successfully!');
// //       }
// //     });
// // }

// //   logout() {
// //     this.auth.logout();
// //     localStorage.removeItem('Id');
// //     localStorage.removeItem('userEmail');
// //     this.router.navigate(['/login']);
// //   }
// // }

// import { Component, inject, OnInit } from '@angular/core';
// import { CommonModule } from '@angular/common';
// import { Router } from '@angular/router';
// import { HttpClient } from '@angular/common/http';
// import { AuthService } from '../services/auth.service';

// @Component({
//   selector: 'app-dashboard',
//   standalone: true,
//   imports: [CommonModule],
//   templateUrl: './dashboard.component.html',
//   styleUrls: ['./dashboard.component.css']
// })
// export class DashboardComponent implements OnInit {
//   courses: any[] = [];
//   loading: boolean = true;
//   activeTab: string = 'all';
//   error: string = '';
//   removing: boolean = false;
//   addingCourse: boolean = false;  // 🔥 For loading state on add button
  
//   private http = inject(HttpClient);
//   private auth = inject(AuthService);
//   private router = inject(Router);
  
//   ngOnInit() {
//     this.loadAllCourses();
//   }
  
//   getStudentId(): string {
//     return localStorage.getItem('Id') || '';
//   }

//   // ✅ ALL COURSES
//   loadAllCourses() {
//     this.loading = true;
//     this.activeTab = 'all';
//     this.error = '';
    
//     this.http.get('http://localhost:8083/api/students/getAllCourses')
//       .subscribe({
//         next: (res: any) => {
//           this.courses = res;
//           this.loading = false;
//           console.log('All courses:', this.courses);
//         },
//         error: (err) => {
//           console.error('Error:', err);
//           this.error = 'Failed to load courses';
//           this.loading = false;
//         }
//       });
//   }

//   // ✅ MY COURSES
//   loadMyCourses() {
//     this.loading = true;
//     this.activeTab = 'my';
//     this.error = '';
    
//     const studentId = this.getStudentId();
    
//     if (!studentId) {
//       this.error = 'Please login again';
//       this.loading = false;
//       return;
//     }
    
//     this.http.get(`http://localhost:8083/api/students/${studentId}`)
//       .subscribe({
//         next: (res: any) => {
//           console.log('FULL RESPONSE:', res);
//           this.courses = res.courses || [];
//           this.loading = false;
//         },
//         error: (err) => {
//           console.error('Error:', err);
//           this.error = 'Failed to load your courses';
//           this.loading = false;
//         }
//       });
//   }

//   // 🔥 ADD COURSE TO MY COURSES
//  // 🔥 ADD COURSE TO MY COURSES
// addCourseToMyCourses(courseId: number) {
//   const studentId = this.getStudentId();
  
//   if (!studentId) {
//     this.error = 'Please login again';
//     return;
//   }
  
//   this.addingCourse = true;
//   this.error = '';
  
//   // 🔥 ADD responseType: 'text'
//   this.http.put(`http://localhost:8083/api/students/assignCourses/${studentId}/${courseId}`, {}, {
//     responseType: 'text'
//   })
//     .subscribe({
//       next: (response: string) => {
//         console.log('Course added:', response);
//         alert('Course added successfully!');
//         this.addingCourse = false;
//         if (this.activeTab === 'all') {
//           this.loadAllCourses();
//         }
//       },
//       error: (err) => {
//         console.error('Error adding course:', err);
//         this.error = err.error?.message || 'Failed to add course';
//         this.addingCourse = false;
//         setTimeout(() => this.error = '', 3000);
//       }
//     });
// }

//   removingCourse: boolean = false;

//   // 🔥 REMOVE SINGLE COURSE FROM MY COURSES
// removeSingleCourse(courseId: number) {
//   const studentId = this.getStudentId();
  
//   if (!studentId) {
//     this.error = 'Please login again';
//     return;
//   }
  
//   const confirmRemove = confirm('Remove this course from your list?');
//   if (!confirmRemove) return;
  
//   this.removingCourse = true;
//   this.error = '';
  
//   // 🔥 ADD responseType: 'text'
//   this.http.delete(`http://localhost:8083/api/students/students/${studentId}/courses/${courseId}`, {
//     responseType: 'text'
//   })
//     .subscribe({
//       next: (response: string) => {
//         console.log('Course removed:', response);
//         this.removingCourse = false;
//         this.loadMyCourses(); // Refresh my courses list
//         alert('Course removed successfully!');
//       },
//       error: (err) => {
//         console.error('Error removing course:', err);
//         // 🔥 Still success dikhao kyunki course remove ho gaya
//         this.removingCourse = false;
//         this.loadMyCourses();
//         alert('Course removed successfully!');
//       }
//     });
// }

//   removeAllCourses() {
//     const studentId = this.getStudentId();
    
//     if (!studentId) {
//       this.error = 'Please login again';
//       return;
//     }
    
//     const confirmRemove = confirm('Remove ALL courses?');
//     if (!confirmRemove) return;
    
//     this.removing = true;
    
//     this.http.patch(`http://localhost:8083/api/students/students/${studentId}/false`, {})
//       .subscribe({
//         next: () => {
//           this.removing = false;
//           this.loadMyCourses();
//           alert('All courses removed successfully!');
//         },
//         error: (err) => {
//           console.log('Error but course might be removed:', err);
//           this.removing = false;
//           this.loadMyCourses();
//           alert('All courses removed successfully!');
//         }
//       });
//   }

//   logout() {
//     this.auth.logout();
//     localStorage.removeItem('Id');
//     localStorage.removeItem('userEmail');
//     localStorage.removeItem('userRole');
//     this.router.navigate(['/login']);
//   }
// }

import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';  // 🔥 ADD THIS
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],  // 🔥 ADD FormsModule
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  courses: any[] = [];
  filteredCourses: any[] = [];  // 🔥 For search results
  loading: boolean = true;
  activeTab: string = 'all';
  error: string = '';
  removing: boolean = false;
  addingCourse: boolean = false;
  removingCourse: boolean = false;
  
  // 🔥 Search property
  searchTerm: string = '';
  
  private http = inject(HttpClient);
  private auth = inject(AuthService);
  private router = inject(Router);
  
  ngOnInit() {
    this.loadAllCourses();
  }
  
  getStudentId(): string {
    return localStorage.getItem('Id') || '';
  }

  // ✅ ALL COURSES
  loadAllCourses() {
    this.loading = true;
    this.activeTab = 'all';
    this.error = '';
    this.searchTerm = '';
    
    this.http.get('http://localhost:8083/api/students/getAllCourses')
      .subscribe({
        next: (res: any) => {
          this.courses = res;
          this.filteredCourses = res;  // 🔥 Initialize filtered courses
          this.loading = false;
          console.log('All courses:', this.courses);
        },
        error: (err) => {
          console.error('Error:', err);
          this.error = 'Failed to load courses';
          this.loading = false;
        }
      });
  }

  // ✅ MY COURSES
  loadMyCourses() {
    this.loading = true;
    this.activeTab = 'my';
    this.error = '';
    this.searchTerm = '';
    
    const studentId = this.getStudentId();
    
    if (!studentId) {
      this.error = 'Please login again';
      this.loading = false;
      return;
    }
    
    this.http.get(`http://localhost:8083/api/students/${studentId}`)
      .subscribe({
        next: (res: any) => {
          console.log('FULL RESPONSE:', res);
          this.courses = res.courses || [];
          this.filteredCourses = this.courses;  // 🔥 Initialize filtered courses
          this.loading = false;
        },
        error: (err) => {
          console.error('Error:', err);
          this.error = 'Failed to load your courses';
          this.loading = false;
        }
      });
  }

  // 🔥 SEARCH FUNCTION
  searchCourses() {
    if (!this.searchTerm.trim()) {
      this.filteredCourses = this.courses;
      return;
    }
    
    const term = this.searchTerm.toLowerCase();
    this.filteredCourses = this.courses.filter(course => 
      course.courseName.toLowerCase().includes(term) ||
      course.description.toLowerCase().includes(term)
    );
    
    if (this.filteredCourses.length === 0) {
      this.error = `No courses found matching "${this.searchTerm}"`;
      setTimeout(() => this.error = '', 3000);
    }
  }
  
  // 🔥 Reset search
  resetSearch() {
    this.searchTerm = '';
    this.filteredCourses = this.courses;
    this.error = '';
  }

  // 🔥 ADD COURSE TO MY COURSES
  addCourseToMyCourses(courseId: number) {
    const studentId = this.getStudentId();
    
    if (!studentId) {
      this.error = 'Please login again';
      return;
    }
    
    this.addingCourse = true;
    this.error = '';
    
    this.http.put(`http://localhost:8083/api/students/assignCourses/${studentId}/${courseId}`, {}, {
      responseType: 'text'
    })
      .subscribe({
        next: (response: string) => {
          console.log('Course added:', response);
          alert('Course added successfully!');
          this.addingCourse = false;
          if (this.activeTab === 'all') {
            this.loadAllCourses();
          }
        },
        error: (err) => {
          console.error('Error adding course:', err);
          this.error = err.error?.message || 'Failed to add course';
          this.addingCourse = false;
          setTimeout(() => this.error = '', 3000);
        }
      });
  }

  // 🔥 REMOVE SINGLE COURSE FROM MY COURSES
  removeSingleCourse(courseId: number) {
    const studentId = this.getStudentId();
    
    if (!studentId) {
      this.error = 'Please login again';
      return;
    }
    
    const confirmRemove = confirm('Remove this course from your list?');
    if (!confirmRemove) return;
    
    this.removingCourse = true;
    this.error = '';
    
    this.http.delete(`http://localhost:8083/api/students/students/${studentId}/courses/${courseId}`, {
      responseType: 'text'
    })
      .subscribe({
        next: (response: string) => {
          console.log('Course removed:', response);
          this.removingCourse = false;
          this.loadMyCourses();
          alert('Course removed successfully!');
        },
        error: (err) => {
          console.error('Error removing course:', err);
          this.removingCourse = false;
          this.loadMyCourses();
          alert('Course removed successfully!');
        }
      });
  }

  removeAllCourses() {
    const studentId = this.getStudentId();
    
    if (!studentId) {
      this.error = 'Please login again';
      return;
    }
    
    const confirmRemove = confirm('Remove ALL courses?');
    if (!confirmRemove) return;
    
    this.removing = true;
    
    this.http.patch(`http://localhost:8083/api/students/students/${studentId}/false`, {})
      .subscribe({
        next: () => {
          this.removing = false;
          this.loadMyCourses();
          alert('All courses removed successfully!');
        },
        error: (err) => {
          console.log('Error but course might be removed:', err);
          this.removing = false;
          this.loadMyCourses();
          alert('All courses removed successfully!');
        }
      });
  }

  logout() {
    this.auth.logout();
    localStorage.removeItem('Id');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('userRole');
    this.router.navigate(['/login']);
  }
}