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
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// DAOのインスタンス化
		SubjectDao sDao = new SubjectDao();
		TestDao tDao = new TestDao();
		TestListStudentDao sTestDao = new TestListStudentDao();

		// 1. プルダウン用のデータを取得
		List<Subject> subjects = sDao.filter(teacher.getSchool());
		List<Integer> entYearList = tDao.filterEntYear(teacher.getSchool());
		List<String> classNumList = tDao.filterClassNum(teacher.getSchool());

		request.setAttribute("subjects", subjects);
		request.setAttribute("ent_year_list", entYearList);
		request.setAttribute("class_num_list", classNumList);

		// 2. 検索パラメータの取得
		String entYearStr = request.getParameter("ent_year"); // JSPのname属性に合わせる
		String classNum = request.getParameter("class_num");
		String subjectCd = request.getParameter("subject_cd");
		String studentNo = request.getParameter("student_no");

		// 異なる型のリストを格納できるよう、ワイルドカード（?）を使用
		List<?> scores = null;

		// 3. 検索ロジックの実行
		
		// 【パターンA】科目情報で検索された場合
		if (entYearStr != null && !entYearStr.equals("0") && subjectCd != null && !subjectCd.equals("0")) {
			int entYear = Integer.parseInt(entYearStr);
			Subject subject = sDao.get(subjectCd, teacher.getSchool());
			
			// 回数(no)はJSP側で指定がない場合、一旦1回目を表示
			int num = 1; 
			
			scores = tDao.filter(entYear, classNum, subject, num, teacher.getSchool());
			
		} 
		// 【パターンB】学生番号で検索された場合
		else if (studentNo != null && !studentNo.isEmpty()) {
			// 学生情報を準備
			Student student = new Student();
			student.setStudentNo(studentNo);
			student.setSchool(teacher.getSchool());
			
			// 米倉さんの新しいDAOで成績リストを取得
			scores = sTestDao.filter(student);
			
			request.setAttribute("target_no", studentNo);
			request.setAttribute("student_name", "検索結果"); 
		}

		// 4. 結果をセットしてJSPへ転送
		request.setAttribute("scores", scores);
		request.getRequestDispatcher("test_list.jsp").forward(request, response);
	}
}