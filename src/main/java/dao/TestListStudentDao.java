package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
	
	private String baseSql = "select * from test";
	
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		
		
		try {
			// リザルトセットを全権走査
			while (rSet.next()) {
				// 学生インスタンスを初期化
				TestListStudent teststudent = new TestListStudent();
				// 学生インスタンスに検索結果をセット
				teststudent.setSubjectName(rSet.getString("subject_name"));
				teststudent.setSubjectCd(rSet.getString("subject_cd"));
				teststudent.setNum(rSet.getInt("no"));
				teststudent.setPoint(rSet.getInt("point"));
				
				// リストに追加
				list.add(teststudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<TestListStudent> filter(Student student) throws Exception {

		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;
		// SQL文の条件
		String join = " join subject on test.subject_cd = subject.subject_cd";
	    String condition = " where student_no = ?";
		// SQL文のソート
		String order = " order by subject_cd asc";
		
		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + join +  condition + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, student.getStudentNo());
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
