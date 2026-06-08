import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-timetable',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimeTableComponent implements OnInit {
  timeTables: any[] = [];
  filteredTimeTables: any[] = [];
  loading: boolean = true;
  error: string = '';
  selectedDay: string = '';
  days: string[] = ['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY'];
  
  private http = inject(HttpClient);
  private router = inject(Router);
  
  ngOnInit() {
    this.loadTimeTables();
  }
  
  loadTimeTables() {
    this.loading = true;
    this.http.get('http://localhost:8083/timetable/getTimeTable')
      .subscribe({
        next: (res: any) => {
          this.timeTables = res;
          this.filteredTimeTables = res;
          this.loading = false;
          console.log('TimeTables:', this.timeTables);
        },
        error: (err) => {
          console.error('Error:', err);
          this.error = 'Failed to load timetable';
          this.loading = false;
        }
      });
  }
  
  filterByDay(day: string) {
    this.selectedDay = day;
    if (day === '') {
      this.filteredTimeTables = this.timeTables;
    } else {
      this.filteredTimeTables = this.timeTables.filter(t => t.day === day);
    }
  }
  
  resetFilter() {
    this.selectedDay = '';
    this.filteredTimeTables = this.timeTables;
  }
  
  goBack() {
    this.router.navigate(['/admin']);
  }
  
  formatTime(time: string): string {
    if (!time) return '';
    return time.substring(0, 5);
  }
}