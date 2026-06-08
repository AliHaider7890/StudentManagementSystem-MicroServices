package com.example.Teacher.entity;

import com.example.Teacher.Enum.Day;

import java.time.LocalTime;

public class TimeTable {

    private Long id;

    private Day day;

    private LocalTime startTime;

    private LocalTime endTime;

    private String roomNo;

    public TimeTable() {
    }

    public TimeTable(Long id, Day day,
                     LocalTime startTime,
                     LocalTime endTime,
                     String roomNo) {

        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomNo = roomNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}