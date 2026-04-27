package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.security.auth.Subject;

import bean.School;
import bean.Student;
import bean.Test;

public class TestDao extends Dao {
	
	private String baseSql = "select * from test where school_cd = ?";
	
	public Test get(Student student, Subject subject, School school, int no ) throws Exception {

		// 得点インスタンスを初期化
		Test test = new Test();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from test where student_no = ?");
			// プリペアードステートメントに学生番号をバインド
			statement.setInteger(1, no);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 学生インスタンスに検索結果をセット
				test.setStudentNo(resultSet.getString("student_no"));
				test.setSchoolCd(resultSet.getString("school_cd"));
				test.setSubjectCd(resultSet.getString("subject_cd"));
				test.setNo(resultSet.getInt("no"));
				test.setPoint(resultSet.getInt("point"));
				test.setClassNum(resultSet.getString("class_num"));
				// 学生フィールドには学校コードで検索した学校インスタンスをセット
			} else {
				// リザルトセットが存在しない場合
				// 学生インスタンスにnullをセット
				test = null;
			}
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

		return test;
	}
}
