export interface TeacherAssignCourse {
  id: number;
  teacherName: string;
  courseName: string;
  courseId: number;
  teacherId: number;
}

export interface TimeTable {
    
  id: number;
  day: string;  // MONDAY, TUESDAY...
  startTime: string;  // "09:00:00"
  endTime: string;    // "11:00:00"
  roomNo: string;
  teacherAssignCourse: TeacherAssignCourse | null;
}