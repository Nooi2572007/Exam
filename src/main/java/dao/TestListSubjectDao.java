package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	
	private String baseSql = "select * from test";
	
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		
		
		try {
			// リザルトセットを全権走査
			while (rSet.next()) {
				// 学生インスタンスを初期化
				TestListSubject testsubject = new TestListSubject();
				// 学生インスタンスに検索結果をセット
				testsubject.setEntYear(rSet.getInt("ent_year"));
				testsubject.setClassNum(rSet.getString("class_num"));
				testsubject.setStudentNo(rSet.getString("student_no"));
				testsubject.setStudentName(rSet.getString("student_name"));
				Map<Integer, Integer> pointMap = testsubject.getPoints();
				pointMap.put(rSet.getInt("no"), rSet.getInt("point"));
				
				// リストに追加
				list.add(testsubject);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;
		// SQL文の条件
		String join = " join student on test.student_no = student.student_no";
	    String condition = " where test.school_cd = ? and student.ent_year = ? "
	                     + "and test.class_num = ? and test.subject_cd = ?";
		// SQL文のソート
		String order = " order by student_no asc";
		
		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + join + condition + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getSchoolCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subject.getCd());
			// プリペアードステートメントを実行
			resultSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(resultSet);
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		
		return list;
	}
}
