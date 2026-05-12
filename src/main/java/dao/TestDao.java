package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
	
	private String baseSql = "select student.student_no, student.student_name, student.ent_year, student.class_num, test.no, test.point, test.subject_cd";
	
	public List<Integer> filterEntYear(School school) throws Exception {
	    List<Integer> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    String sql = "select distinct ent_year from student where school_cd = ? order by ent_year asc";

	    try {
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, school.getSchoolCd());
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	            list.add(rs.getInt("ent_year"));
	        }
	    } finally {
	    	if (statement != null) {
				try { statement.close(); } catch (SQLException sqle) { throw sqle; }
			}
			if (connection != null) {
				try { connection.close(); } catch (SQLException sqle) { throw sqle; }
			}
	    }
	    return list;
	}

	public List<String> filterClassNum(School school) throws Exception {
	    List<String> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    String sql = "select distinct class_num from student where school_cd = ? order by class_num asc";

	    try {
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, school.getSchoolCd());
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	            list.add(rs.getString("class_num"));
	        }
	    } finally {
			if (statement != null) {
				try { statement.close(); } catch (SQLException sqle) { throw sqle; }
			}
			if (connection != null) {
				try { connection.close(); } catch (SQLException sqle) { throw sqle; }
			}
	    }
	    return list;
	}
	
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
	    List<Test> list = new ArrayList<>();
	    try {
	        while (rSet.next()) {
	            Test test = new Test();
	            
	            // --- 学生情報のセット ---
	            Student student = new Student();
	            // 学籍番号
	            student.setStudentNo(rSet.getString("student_no")); 
	            // 氏名（rSet.getString("name") で取得）
	            student.setStudentName(rSet.getString("student_name")); 
	            // 入学年度（rSet.getInt("ent_year") で取得）
	            student.setEntYear(rSet.getInt("ent_year"));
	            // クラス
	            student.setClassNum(rSet.getString("class_num"));
	            
	            test.setStudent(student);
	            // -----------------------

	            Subject subject = new Subject();
	            subject.setCd(rSet.getString("subject_cd"));
	            test.setSubject(subject);
	            
	            test.setSchool(school);
	            test.setNo(rSet.getInt("no"));
//	            test.setPoint(rSet.getInt("point"));
	            Object pointObj = rSet.getObject("point");
	            if (pointObj == null) {
	                test.setPoint(-1); // 未登録の場合は-1をセット
	            } else {
	                test.setPoint(rSet.getInt("point")); // 登録済みの場合はそのまま
	            }
	            test.setClassNum(rSet.getString("class_num"));
	            
	            list.add(test);
	        }
	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }
	    return list;
	}

	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		String order = " order by student_no asc";
		String join = " from student left join test on student.student_no = test.student_no "
	             + "and test.subject_cd = ? and test.no = ? and test.school_cd = student.school_cd ";
		String condition = " where student.school_cd = ? and student.ent_year = ? and student.class_num = ? ";
	
		try {
			statement = connection.prepareStatement(baseSql + join + condition + order);
			statement.setString(1, subject.getCd()); // ONの1つ目
			statement.setInt(2, num);                // ONの2つ目
			statement.setString(3, school.getSchoolCd()); // WHEREの1つ目
			statement.setInt(4, entYear);                 // WHEREの2つ目
			statement.setString(5, classNum);
			resultSet = statement.executeQuery();
			
			list = postFilter(resultSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try { statement.close(); } catch (SQLException sqle) { throw sqle; }
			}
			if (connection != null) {
				try { connection.close(); } catch (SQLException sqle) { throw sqle; }
			}
		}
		return list;
	}
	
	public boolean save(List<Test> list) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;

	    try {
	        for (Test test : list) {
	            boolean isExist = false;
	            String checkSql = "select count(*) from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";
	            statement = connection.prepareStatement(checkSql);
	            
	            statement.setString(1, test.getStudent().getStudentNo());
	            statement.setString(2, test.getSubject().getCd());
	            statement.setString(3, test.getSchool().getSchoolCd());
	            statement.setInt(4, test.getNo());
	            
	            ResultSet rs = statement.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                isExist = true;
	            }
	            statement.close();

	            if (isExist) {
	                String updateSql = "update test set point = ? where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";
	                statement = connection.prepareStatement(updateSql);
	                statement.setInt(1, test.getPoint());
	                statement.setString(2, test.getStudent().getStudentNo());
	                statement.setString(3, test.getSubject().getCd());
	                statement.setString(4, test.getSchool().getSchoolCd());
	                statement.setInt(5, test.getNo());
	            } else {
	                String insertSql = "insert into test (student_no, subject_cd, school_cd, no, point, class_num) values (?, ?, ?, ?, ?, ?)";
	                statement = connection.prepareStatement(insertSql);
	                statement.setString(1, test.getStudent().getStudentNo());
	                statement.setString(2, test.getSubject().getCd());
	                statement.setString(3, test.getSchool().getSchoolCd());
	                statement.setInt(4, test.getNo());
	                statement.setInt(5, test.getPoint());
	                statement.setString(6, test.getClassNum());
	            }
	            count += statement.executeUpdate();
	            statement.close();
	        }
	    } catch (Exception e) {
	        throw e;
	    } finally {
	        if (connection != null) {
	            try { connection.close(); } catch (SQLException sqle) { throw sqle; }
	        }
	    }
	    return count > 0;
	}
	
	public boolean delete(Test test) throws Exception {
		Connection connection = getConnection();
		
		String sql = "delete from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, test.getStudent().getStudentNo());
		statement.setString(2, test.getSubject().getCd());
		statement.setString(3, test.getSchool().getSchoolCd());
		statement.setInt(4, test.getNo());
		
		int count = statement.executeUpdate();
		
		statement.close();
		connection.close();
		
		return count > 0;
	}
}