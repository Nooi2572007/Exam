package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDao; // 米倉さんの新しいDAO
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションからユーザー情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 【重要：全エラー解決の鍵】ログインチェック
		// これがないと、再起動直後などに teacher.getSchool() で NullPointerException が出ます
		if (teacher == null) {
			// ログインしていない場合はログイン画面へ強制送還
			response.sendRedirect(request.getContextPath() + "/scoremanager/main/Login.action");
			return;
		}

		// DAOのインスタンス化
		SubjectDao sDao = new SubjectDao();
		TestDao tDao = new TestDao();
		TestListStudentDao sTestDao = new TestListStudentDao();

		// 1. プルダウン用のデータを本物のDAOから取得
		List<Subject> subjects = sDao.filter(teacher.getSchool());
		List<Integer> entYearList = tDao.filterEntYear(teacher.getSchool());
		List<String> classNumList = tDao.filterClassNum(teacher.getSchool());

		request.setAttribute("subjects", subjects);
		request.setAttribute("ent_year_list", entYearList);
		request.setAttribute("class_num_list", classNumList);

		// 2. 検索パラメータの取得
		String entYearStr = request.getParameter("ent_year");
		String classNum = request.getParameter("class_num");
		String subjectCd = request.getParameter("subject_cd");
		String studentNo = request.getParameter("student_no");

		// 検索結果用リスト（異なるBeanに対応できるようワイルドカードを使用）
		List<?> scores = null;

		// 3. 検索ロジックの実行
		
		// 【パターンA】科目検索（入学年度と科目が選ばれている場合）
		if (entYearStr != null && !entYearStr.equals("0") && subjectCd != null && !subjectCd.equals("0")) {
			int entYear = Integer.parseInt(entYearStr);
			Subject subject = sDao.get(subjectCd, teacher.getSchool());
			
			// テスト回数は一旦1回目固定（JSPに合わせて調整してください）
			int num = 1; 
			
			// TestDaoを使用して科目別の成績を取得
			scores = tDao.filter(entYear, classNum, subject, num, teacher.getSchool());
			
		} 
		// 【パターンB】学生検索（学生番号が入力されている場合）
		else if (studentNo != null && !studentNo.isEmpty()) {
			Student student = new Student();
			student.setStudentNo(studentNo);
			student.setSchool(teacher.getSchool());
			
			// 米倉さんのTestListStudentDaoを使用して、その学生の全成績を取得
			scores = sTestDao.filter(student);
			
			request.setAttribute("target_no", studentNo);
			request.setAttribute("student_name", "検索結果"); 
		}

		// 4. 結果をセットしてJSPへ転送
		request.setAttribute("scores", scores);
		request.getRequestDispatcher("test_list.jsp").forward(request, response);
	}
}