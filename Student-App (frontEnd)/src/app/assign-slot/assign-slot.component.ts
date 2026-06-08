import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-assign-slot',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './assign-slot.component.html',
  styleUrls: ['./assign-slot.component.css']
})
export class AssignSlotComponent implements OnInit {
  
  // Data arrays
  teacherAssignCourses: any[] = [];  // Already assigned teacher-course
  availableSlots: any[] = [];        // Empty timetable slots
  assignedSlots: any[] = [];         // Already assigned slots
  
  // Form data
  selectedTeacherAssignId: number = 0;
  selectedTimeTableId: number = 0;
  
  // UI states
  loading: boolean = false;
  error: string = '';
  success: string = '';
  
  private http = inject(HttpClient);
  private router = inject(Router);
  
  ngOnInit() {
    this.loadTeacherAssignCourses();
    this.loadAvailableSlots();
    this.loadAssignedSlots();
  }
  
  // Load already assigned teacher-course combinations
  loadTeacherAssignCourses() {
    this.http.get('http://localhost:8083/courses/getTeacherAssignments')
      .subscribe({
        next: (res: any) => {
          this.teacherAssignCourses = res;
          console.log('Teacher-Course Assignments:', this.teacherAssignCourses);
        },
        error: (err) => {
          console.error('Error:', err);
        }
      });
  }
  
  // Load available slots (where teacherAssignCourse is null)
  loadAvailableSlots() {
    this.http.get('http://localhost:8083/timetable/getTimeTable')
      .subscribe({
        next: (res: any) => {
          // Sirf wahi slots jinka teacherAssignCourse null hai
          this.availableSlots = res.filter((slot: any) => slot.teacherAssignCourse === null);
          console.log('Available Slots:', this.availableSlots);
        },
        error: (err) => {
          console.error('Error:', err);
        }
      });
  }
  
  // Load already assigned slots
  loadAssignedSlots() {
    this.http.get('http://localhost:8083/timetable/getTimeTable')
      .subscribe({
        next: (res: any) => {
          // Sirf wahi slots jinka teacherAssignCourse null nahi hai
          this.assignedSlots = res.filter((slot: any) => slot.teacherAssignCourse !== null);
          console.log('Assigned Slots:', this.assignedSlots);
        },
        error: (err) => {
          console.error('Error:', err);
        }
      });
  }
  
  // Assign Teacher-Course to TimeTable Slot
  assignSlot() {
    if (!this.selectedTeacherAssignId || !this.selectedTimeTableId) {
      this.error = 'Please select both Teacher-Course and Time Slot';
      setTimeout(() => this.error = '', 3000);
      return;
    }
    
    this.loading = true;
    this.error = '';
    this.success = '';
    
    const url = `http://localhost:8083/timetable/assignTimeTable/${this.selectedTeacherAssignId}/${this.selectedTimeTableId}`;
    
    this.http.patch(url, {})
      .subscribe({
        next: (res: any) => {
          console.log('Slot assigned:', res);
          this.success = `Slot assigned successfully!`;
          this.resetForm();
          this.loadAvailableSlots();  // Refresh available slots
          this.loadAssignedSlots();   // Refresh assigned slots
          this.loading = false;
          setTimeout(() => this.success = '', 3000);
        },
        error: (err) => {
          console.error('Error:', err);
          this.error = err.error?.message || 'Failed to assign slot';
          this.loading = false;
          setTimeout(() => this.error = '', 3000);
        }
      });
  }
  
  resetForm() {
    this.selectedTeacherAssignId = 0;
    this.selectedTimeTableId = 0;
  }
  
  goBack() {
    this.router.navigate(['/admin']);
  }

  formatTime(time: string): string {
  if (!time) return '';
  return time.substring(0, 5);
}
}