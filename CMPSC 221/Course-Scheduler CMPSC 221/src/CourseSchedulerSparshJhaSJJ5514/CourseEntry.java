/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CourseSchedulerSparshJhaSJJ5514;

/**
 *
 * @author Sparsh
 */
public class CourseEntry {
    String Semester;
    String CourseCode;
    String Description;
    int seats;

    public CourseEntry(String Semester, String CourseCode, String Description, int seats) {
        this.Semester = Semester;
        this.CourseCode = CourseCode;
        this.Description = Description;
        this.seats = seats;
    }

    public String getSemester() {
        return Semester;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public String getDescription() {
        return Description;
    }

    public int getSeats() {
        return seats;
    }
    
    
}
