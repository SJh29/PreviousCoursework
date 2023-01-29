/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CourseSchedulerSparshJhaSJJ5514;

/**
 *
 * @author Sparsh
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    //Part2 statements
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement checkNextWaitlist;
    private static PreparedStatement checkEmptySlot;
    private static PreparedStatement updateScheduleEntry;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            addScheduleEntry = connection.prepareStatement("insert into app.schedule (semester , studentid, coursecode, status, timestamp) values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getStudentID());
            addScheduleEntry.setString(3, entry.getCourseCode());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimeStamp());
            addScheduleEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    
    
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();
        
        try
        {
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where semester = ? and studentid = ?");
            getScheduleByStudent.setString(1, semester);
            getScheduleByStudent.setString(2, studentID);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next())
            {
            schedule.add(new ScheduleEntry(
                                            resultSet.getString("semester"), resultSet.getString("coursecode"), 
                                            resultSet.getString("studentid"), resultSet.getString("status"), 
                                            resultSet.getTimestamp("timestamp")));
            
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedule;
    }
    
    public static int getScheduledStudentCount(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        String studentNum = "";
        try
        {
            getScheduledStudentCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            getScheduledStudentCount.setString(1, semester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            while(resultSet.next()){
                studentNum = resultSet.getString(1);
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return Integer.parseInt(studentNum);
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode)
    {
        ArrayList<ScheduleEntry> scheduledStudents = new ArrayList<>();
        
        connection = DBConnection.getConnection();
        try
        {
            getScheduledStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = ? and coursecode = ? and status = 'S'");
            getScheduledStudentsByCourse.setString(1, semester);
            getScheduledStudentsByCourse.setString(2, courseCode);
            resultSet = getScheduledStudentsByCourse.executeQuery();
            while(resultSet.next())
            {
                scheduledStudents.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(3), resultSet.getString(2), 
                                                        resultSet.getString(4), resultSet.getTimestamp(5)));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduledStudents;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String coursecode)
    {
        ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<>();
        connection = DBConnection.getConnection();
        try
        {
            getWaitlistedStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = ? and coursecode = ? and status = 'W' order by timestamp");
            getWaitlistedStudentsByCourse.setString(1, semester);
            getWaitlistedStudentsByCourse.setString(2, coursecode);
            resultSet = getWaitlistedStudentsByCourse.executeQuery();
            
            while(resultSet.next())
            {
                waitlistedStudents.add(new ScheduleEntry(resultSet.getString("semester"), resultSet.getString("courseCode"), resultSet.getString("studentid"), 
                                                        resultSet.getString("status"), resultSet.getTimestamp("timestamp")));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlistedStudents;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String coursecode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ? and studentid = ?");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, coursecode);
            dropStudentScheduleByCourse.setString(3, studentID);
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
        sqlException.printStackTrace();
        }
    }
    
    
    public static void dropScheduleByCourse(String Semester, String coursecode)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
            dropScheduleByCourse.setString(1, Semester);
            dropScheduleByCourse.setString(2, coursecode);
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {   
                updateScheduleEntry = connection.prepareStatement("update app.schedule set status = 'S' where semester = ? and studentid = ? and coursecode = ?");
                updateScheduleEntry.setString(1, semester);
                updateScheduleEntry.setString(2,entry.getStudentID());
                updateScheduleEntry.setString(3, entry.getCourseCode());
                updateScheduleEntry.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

   
}
