package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import javax.security.auth.Subject;

import bean.School;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
	
	private String baseSql = "select * from test";
	
	public List<Integer> filterEntYear(School school) throws Exception {
	    List<Integer> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    // 重複を除いて、その学校の生徒の入学年度だけを取得
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

    // ③ 検索条件「クラス」のリストを取得するメソッド
	public List<String> filterClassNum(School school) throws Exception {
	    List<String> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;

	    // その学校に存在するクラス名だけを取得
	    String sql = "select distinct class_num from student where school_cd = ? order by class_num asc";

	    try {
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, school.getSchoolCd());
	        ResultSet rs = statement.executeQuery();
	        while (rs.next()) {
	            list.add(rs.getString("class_num"));
	        }
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
	

	
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		try {
			// リザルトセットを全権走査
			while (rSet.next()) {
				// 学生インスタンスを初期化
				Test test = new Test();
				// 学生インスタンスに検索結果をセット
				test.setStudentNo(rSet.getString("student_no"));
				test.setSchoolCd(rSet.getString("school_cd"));
				test.setSubjectCd(rSet.getString("subject_cd"));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setClassNum(rSet.getString("class_num"));
				// リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {

		// リストを初期化
		List<Test> list = new ArrayList<>();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// リザルトセット
		ResultSet resultSet = null;
		// SQL文の条件
//		String condition = "and subject_cd = ? and no = ? and class_num = ?";
		String join = " join student on test.student_no = student.student_no";
	    String condition = " where test.school_cd = ? and student.ent_year = ? "
	                     + "and test.class_num = ? and test.subject_cd = ? and test.no = ?";
		// SQL文のソート
		String order = " order by student_no asc";

		// 在学フラグがtrueの場合
		
		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + join + condition + order);
			// プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getSchoolCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subject.getCd());
			statement.setInt(5, num);
			// プリペアードステートメントを実行
			resultSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(resultSet, school);
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
	
	public boolean save(List<Test> list) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;

	    try {
	        // リストの中身（学生のテスト結果）を1件ずつ取り出して処理する
	        for (Test test : list) {

	            // 1. まずは「その学生の、その科目・回数のデータ」が既に存在するかチェック
	            boolean isExist = false;
	            String checkSql = "select count(*) from test where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";
	            statement = connection.prepareStatement(checkSql);
	            statement.setString(1, test.getStudentNo());
	            statement.setString(2, test.getSubjectCd());
	            statement.setString(3, test.getSchoolCd());
	            statement.setInt(4, test.getNo());
	            
	            ResultSet rs = statement.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                isExist = true; // データがあった！
	            }
	            statement.close(); // 一旦閉じる

	            // 2. データが存在するかどうかで、実行するSQLを分ける
	            if (isExist) {
	                // 既に存在する場合 ＝ 得点を「更新（UPDATE）」する
	                String updateSql = "update test set point = ? where student_no = ? and subject_cd = ? and school_cd = ? and no = ?";
	                statement = connection.prepareStatement(updateSql);
	                statement.setInt(1, test.getPoint());        // ここが得点！
	                statement.setString(2, test.getStudentNo());
	                statement.setString(3, test.getSubjectCd());
	                statement.setString(4, test.getSchoolCd());
	                statement.setInt(5, test.getNo());
	            } else {
	                // 存在しない場合 ＝ 新しく「登録（INSERT）」する
	                String insertSql = "insert into test (student_no, subject_cd, school_cd, no, point, class_num) values (?, ?, ?, ?, ?, ?)";
	                statement = connection.prepareStatement(insertSql);
	                statement.setString(1, test.getStudentNo());
	                statement.setString(2, test.getSubjectCd());
	                statement.setString(3, test.getSchoolCd());
	                statement.setInt(4, test.getNo());
	                statement.setInt(5, test.getPoint());        // ここが得点！
	                statement.setString(6, test.getClassNum());
	            }

	            // 3. 組み立てたSQLを実行
	            count += statement.executeUpdate();
	            statement.close();
	        }

	    } catch (Exception e) {
	        throw e;
	    } finally {
	        // コネクションを閉じる
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    // 1件でも更新/登録できていれば true を返す
	    return count > 0;
	}
}
