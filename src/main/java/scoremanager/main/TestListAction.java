package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao; // 米倉さんのTestDaoをインポート
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// DAOのインスタンス化
		SubjectDao sDao = new SubjectDao();
		TestDao tDao = new TestDao();

		// 1. プルダウン用のデータを本物のDAOから取得
		// これで、データベースに登録されている年度やクラスが自動で表示されます！
		List<Subject> subjects = sDao.filter(teacher.getSchool());
		List<Integer> entYearList = tDao.filterEntYear(teacher.getSchool());
		List<String> classNumList = tDao.filterClassNum(teacher.getSchool());

		request.setAttribute("subjects", subjects);
		request.setAttribute("ent_year_list", entYearList);
		request.setAttribute("class_num_list", classNumList);

		// 2. 検索パラメータの取得
		String entYearStr = request.getParameter("f1");    // 入学年度
		String classNum = request.getParameter("f2");     // クラス
		String subjectCd = request.getParameter("f3");    // 科目コード
		String numStr = request.getParameter("f4");       // 回数（もしJSPにあれば）
		
		String studentNo = request.getParameter("student_no"); // 学生番号

		List<Test> scores = null;

		// 3. 検索ロジックの実行
		
		// 【パターンA】科目情報で検索された場合（米倉さんのfilterメソッドを使用）
		if (entYearStr != null && !entYearStr.equals("0") && subjectCd != null && !subjectCd.equals("0")) {
			int entYear = Integer.parseInt(entYearStr);
			// SubjectDaoを使って、コードからSubjectオブジェクトを特定する
			Subject subject = sDao.get(subjectCd, teacher.getSchool());
			// 米倉さんのDAOに合わせて回数(no)を指定（JSPに無ければ一旦1回目とする）
			int num = (numStr != null) ? Integer.parseInt(numStr) : 1;
			
			// 本物のデータ取得を実行！
			scores = tDao.filter(entYear, classNum, subject, num, teacher.getSchool());
			
		} 
		// 【パターンB】学生番号で検索された場合
		else if (studentNo != null && !studentNo.isEmpty()) {
			// ※メモ：現在のTestDaoには「学生番号で検索」するメソッドがまだ無いようです。
			// ここは以前のダミーを残しつつ、米倉さんにメソッド追加をお願いするか、
			// 自分でTestDaoに「filterByStudent」のようなメソッドを追記することになります。
			request.setAttribute("target_no", studentNo);
			request.setAttribute("student_name", "大原 太郎（学生検索DAO待ち）");
		}

		request.setAttribute("scores", scores);
		request.getRequestDispatcher("test_list.jsp").forward(request, response);
	}
}