import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-teacher',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './teacher.component.html',
  styleUrls: ['./teacher.component.css']
})
export class TeacherComponent implements OnInit {
  
  // Teacher Info
  teacherId: string = '';
  teacherName: string = '';
  teacherEmail: string = '';
  
  // Timetable Data
  timetable: any[] = [];
  loading: boolean = true;
  error: string = '';
  
  // Days order for sorting
  daysOrder: string[] = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY', 'SUNDAY'];
  
  private http = inject(HttpClient);
  private auth = inject(AuthService);
  private router = inject(Router);
  
  ngOnInit() {
    this.loadTeacherInfo();
    this.loadTeacherTimetable();
  }
  
  loadTeacherInfo() {
    this.teacherId = localStorage.getItem('Id') || '';
    this.teacherEmail = localStorage.getItem('userEmail') || '';
    this.teacherName = localStorage.getItem('teacherName') || this.teacherEmail;
    
    console.log('Teacher ID:', this.teacherId);
  }
  
  loadTeacherTimetable() {
    if (!this.teacherId) {
      this.error = 'Teacher ID not found';
      this.loading = false;
      return;
    }
    
    this.loading = true;
    this.error = '';
    
    this.http.get(`http://localhost:8083/teacher/mytimetable/${this.teacherId}`)
      .subscribe({
        next: (res: any) => {
          this.timetable = res;
          this.loading = false;
          console.log('Teacher Timetable:', this.timetable);
        },
        error: (err) => {
          console.error('Error loading timetable:', err);
          this.error = 'Failed to load timetable';
          this.loading = false;
        }
      });
  }
  
  // Group timetable by day
  getTimetableByDay(day: string): any[] {
    return this.timetable.filter(t => t.day === day);
  }
  
  // Check if timetable has entries for a day
  hasTimetableForDay(day: string): boolean {
    return this.getTimetableByDay(day).length > 0;
  }
  
  formatTime(time: string): string {
    if (!time) return '';
    return time.substring(0, 5);
  }
  getUniqueDaysCount(): number {
  const uniqueDays = new Set(this.timetable.map(t => t.day));
  return uniqueDays.size;
}
  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}