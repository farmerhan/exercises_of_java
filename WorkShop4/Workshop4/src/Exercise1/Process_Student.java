package Exercise1;

import java.sql.*;
import java.util.*;
//import javax.swing.*;

public class Process_Student {

    private Connection con = getCon();

    public Connection getCon() {
        try {
//          Nap Drive 
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/db_student", "root", "tranvanvu2001");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return con;
    }

    public ArrayList<Student> prepareAndReturn(ArrayList<Student> lsstudent, String sql) {
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
//          ResultSet đại diện cho tập kết quả trả về khi thực thi câu lệnh sql
            ResultSet rs = ps.executeQuery();
//          Nếu còn dòng tiếp theo thì di chuyển
            while (rs.next()) {
//              Đọc lần lượt giá trị trong ResultSet đặt cho Student
                Student st = new Student();
                st.setID(rs.getString("ID"));
                st.setName(rs.getString("Name"));
                st.setGender(rs.getBoolean("Gender"));
                st.setClassID(rs.getString("ClassID"));
                st.setMark(rs.getDouble("Mark"));
                lsstudent.add(st);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return lsstudent;
    }

//  Chuẩn bị câu lệnh để cập nhật vào trong cơ sở dữ liệu
    public boolean prepareAndUpdate(String sql) {
        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public ArrayList<Student> getListStudent() {
        return prepareAndReturn(new ArrayList<Student>(), "select * from tbstudent");
    }

    public ArrayList<Student> getStudent_byClass(String ClassID) {
        return prepareAndReturn(new ArrayList<Student>(), String.format("select * from tbstudent where ClassID='" + ClassID + "'"));
    }

    public ArrayList<Student> getStudent_byID(String ID) {
        return prepareAndReturn(new ArrayList<Student>(), String.format("select * from tbstudent where ID='" + ID + "'"));
    }

    public boolean insertStudent(String ID, String Name, boolean Gender, String ClassID, Double Mark) {
        return prepareAndUpdate("insert into tbstudent values ('" + ID + "','" + Name + "'," + Gender + ",'" + ClassID + "'," + Mark + ")");
    }

    public boolean updateStudent(String ID, String Name, boolean Gender, String ClassID, Double Mark) {
        return prepareAndUpdate("update tbstudent set Name='" + Name + "',Gender=" + Gender + ",ClassID='" + ClassID + "',Mark=" + Mark + " Where ID='" + ID + "'");
    }

    public boolean delStudent(String ID) {
        return prepareAndUpdate("delete from tbstudent Where ID='" + ID + "'");
    }
}
