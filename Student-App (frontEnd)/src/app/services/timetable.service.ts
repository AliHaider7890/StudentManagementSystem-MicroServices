import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TimeTable } from '../models/timetable.model';

@Injectable({ providedIn: 'root' })
export class TimeTableService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8083/timetable';

  getAllTimeTables(): Observable<TimeTable[]> {
    return this.http.get<TimeTable[]>(`${this.apiUrl}/getTimeTable`);
  }

  getByDay(day: string): Observable<TimeTable[]> {
    return this.http.get<TimeTable[]>(`${this.apiUrl}/day/${day}`);
  }
}