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

public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (sid, firstname, lastname) values (? ,? ,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<>();
        
        try
        {
            getAllStudents = connection.prepareStatement("select * from app.student");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
            student.add(new StudentEntry(resultSet.getString("sid"), resultSet.getString("firstname"), resultSet.getString("lastname")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    
        return student;
    }
    
    public static StudentEntry getStudent(String studentID)
    {
    connection = DBConnection.getConnection();
    StudentEntry student = null;
    try
    {
     getStudent = connection.prepareStatement("select * from app.student where sid= ?");
     getStudent.setString(1, studentID);
     resultSet = getStudent.executeQuery();
     while(resultSet.next())
        {
            return(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
        }
        
    }
    catch(SQLException sqlException)
    {
    sqlException.printStackTrace();
    }
    return student;
    }
    
    public static void dropStudent(String StudentID)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where sid = ?");
            dropStudent.setString(1, StudentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
}
