import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-teacher-assign',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './teacher-assign.component.html',
  styleUrls: ['./teacher-assign.component.css']
})
export class TeacherAssignComponent implements OnInit {
  
  courses: any[] = [];
  teachers: any[] = [];        // 🔥 Teachers from /teacher API
  teacherAssignCourses: any[] = [];
  
  selectedCourseId: number = 0;
  selectedTeacherId: number = 0;
  selectedAssignId: number = 0;
  
  loading: boolean = false;
  error: string = '';
  success: string = '';
  
  private http = inject(HttpClient);
  private router = inject(Router);
  
  ngOnInit() {
    this.loadCourses();
    this.loadTeachers();        // 🔥 Load teachers from teacher service
    this.loadTeacherAssignCourses();
  }
  
  // Load all courses
  loadCourses() {
    this.http.get('http://localhost:8083/courses/getAllforTeachers')
      .subscribe({
        next: (res: any) => {
          this.courses = res;
          console.log('Courses:', this.courses);
        },
        error: (err) => {
          console.error('Error loading courses:', err);
        }
      });
  }
  
  // 🔥 Load all teachers from Teacher Service
  loadTeachers() {
    this.http.get('http://localhost:8083/teacher')
      .subscribe({
        next: (res: any) => {
          this.teachers = res;
          console.log('Teachers from teacher service:', this.teachers);
        },
        error: (err) => {
          console.error('Error loading teachers:', err);
          this.error = 'Failed to load teachers';
        }
      });
  }
  
  // Load existing teacher-course assignments
  loadTeacherAssignCourses() {
    this.http.get('http://localhost:8083/courses/getTeacherAssignments')
      .subscribe({
        next: (res: any) => {
          this.teacherAssignCourses = res;
          console.log('Assignments:', this.teacherAssignCourses);
        },
        error: (err) => {
          console.error('Error loading assignments:', err);
        }
      });
  }
  
  // Assign teacher to course
  assignTeacherToCourse() {
    if (!this.selectedAssignId || !this.selectedCourseId || !this.selectedTeacherId) {
      this.error = 'Please select all fields';
      setTimeout(() => this.error = '', 3000);
      return;
    }
    
    this.loading = true;
    this.error = '';
    this.success = '';
    
    const url = `http://localhost:8083/courses/assignCourseToTeacher/${this.selectedAssignId}/${this.selectedCourseId}/${this.selectedTeacherId}`;
    
    this.http.patch(url, {})
      .subscribe({
        next: (res: any) => {
          console.log('Assignment success:', res);
          this.success = `Course assigned to teacher successfully!`;
          this.loadTeacherAssignCourses();
          this.resetForm();
          this.loading = false;
          setTimeout(() => this.success = '', 3000);
        },
        error: (err) => {
          console.error('Error:', err);
          this.error = err.error?.message || 'Failed to assign teacher to course';
          this.loading = false;
          setTimeout(() => this.error = '', 3000);
        }
      });
  }
  
  resetForm() {
    this.selectedAssignId = 0;
    this.selectedCourseId = 0;
    this.selectedTeacherId = 0;
  }
  
  goBack() {
    this.router.navigate(['/admin']);
  }
}