package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

// Daoクラスを継承して、データベース接続機能を使えるようにします
public class SubjectDao extends Dao {

	/**
	 * ログイン中の学校の科目一覧を取得するメソッド
	 */
	public List<Subject> filter(School school) throws Exception {
		// 取り出した科目を入れておくための空のリスト（段ボール箱）を用意
		List<Subject> list = new ArrayList<>();
		
		// データベースへの接続（Daoクラスの機能を使います）
		Connection connection = getConnection();
		
		// 実行するSQL文（「この学校コードの科目を全部ちょうだい」という命令）
		String sql = "select * from subject where school_cd = ?";
		
		// SQLを実行する準備
		PreparedStatement statement = connection.prepareStatement(sql);
		// 「?」の部分に、引数で受け取った学校コードをセットする
		statement.setString(1, school.getSchoolCd());
		
		// SQLを実行して、結果（ResultSet）を受け取る
		ResultSet rSet = statement.executeQuery();
		
		// 結果がある限り、1行ずつ順番に取り出してBean（箱）に詰める
		// 結果がある限り、1行ずつ順番に取り出してBean（箱）に詰める
				while (rSet.next()) {
					Subject subject = new Subject();
					// データベースの"subject_cd"列の文字をセット
					subject.setCd(rSet.getString("subject_cd"));
					// データベースの"subject_name"列の文字をセット
					subject.setName(rSet.getString("subject_name"));
					// 学校情報はそのままセット
					subject.setSchool(school);
					
					list.add(subject);
				}
		
		// 最後に接続を閉じる（お片付け）
		statement.close();
		connection.close();
		
		// 科目が詰まったリストを返す
		return list;
	}
	/**
	 * 科目をデータベースに新規登録するメソッド
	 */
	public boolean insert(Subject subject) throws Exception {
		// データベースへの接続
		Connection connection = getConnection();
		
		// 実行するSQL文（「このデータを追加して」という命令）
		// 実行するSQL文（実際の列名に合わせる）
				String sql = "insert into subject (subject_cd, subject_name, school_cd) values (?, ?, ?)";
		
		// SQLを実行する準備
		PreparedStatement statement = connection.prepareStatement(sql);
		// 「?」の部分に、引数で受け取った科目データをセットする
		statement.setString(1, subject.getCd());
		statement.setString(2, subject.getName());
		statement.setString(3, subject.getSchool().getSchoolCd());
		
		// SQLを実行（更新された行数が返ってくる）
		int count = statement.executeUpdate();
		
		// お片付け
		statement.close();
		connection.close();
		
		// 1行以上追加されていれば成功(true)、そうでなければ失敗(false)を返す
		return count > 0;
	}
	/**
	 * 科目コードを指定して、1件分の科目データだけを取り出すメソッド（変更画面の表示用）
	 */
	public Subject get(String cd, School school) throws Exception {
		Subject subject = null;
		Connection connection = getConnection();
		
		// 「指定された科目コードと学校コードにピタリと一致する科目を1つ探して！」という命令
		String sql = "select * from subject where subject_cd = ? and school_cd = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, cd);
		statement.setString(2, school.getSchoolCd());
		
		ResultSet rSet = statement.executeQuery();
		
		// 見つかったら、Bean（箱）に詰める
		if (rSet.next()) {
			subject = new Subject();
			subject.setCd(rSet.getString("subject_cd"));
			subject.setName(rSet.getString("subject_name"));
			subject.setSchool(school);
		}
		
		statement.close();
		connection.close();
		
		return subject; // 見つかった科目データ（見つからなければnull）を返す
	}

	/**
	 * 変更された科目データをデータベースに上書き保存するメソッド
	 */
	public boolean update(Subject subject) throws Exception {
		Connection connection = getConnection();
		
		// 「指定した科目コードのデータを探して、科目名(subject_name)を新しいものに書き換えて！」という命令
		String sql = "update subject set subject_name = ? where subject_cd = ? and school_cd = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// ? にデータをセットする（※セットする順番に注意！）
		statement.setString(1, subject.getName()); // 1つ目の?（新しい科目名）
		statement.setString(2, subject.getCd());   // 2つ目の?（変更する科目のコード）
		statement.setString(3, subject.getSchool().getSchoolCd()); // 3つ目の?（学校コード）
		
		int count = statement.executeUpdate();
		
		statement.close();
		connection.close();
		
		return count > 0; // 1行以上書き換えに成功したらtrueを返す
	}
}