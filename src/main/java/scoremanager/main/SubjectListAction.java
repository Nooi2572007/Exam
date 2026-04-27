package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッション（ログイン情報）を取り出す
		HttpSession session = request.getSession();
		// ログイン中の教員情報を取得
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// データベースから科目一覧を取得するための準備
		SubjectDao sDao = new SubjectDao();
		// 教員の所属する学校の科目をDAOに取ってきてもらう
		List<Subject> subjects = sDao.filter(teacher.getSchool());
		
		// 取得した科目リストを、画面（JSP）で使えるように名前を付けて保存
		request.setAttribute("subjects", subjects);
		
		// 科目一覧画面（JSP）へ移動する
		request.getRequestDispatcher("subject_list.jsp").forward(request, response);
	}
}