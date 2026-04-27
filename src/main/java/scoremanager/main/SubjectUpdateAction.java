package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションからログイン中の先生の情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// 一覧画面の「変更」リンクから送られてきた科目コード（?cd=〇〇）を受け取る
		String cd = request.getParameter("cd");
		
		// 金庫番（DAO）を呼んで、その科目コードのデータを1件だけ探してきてもらう
		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(cd, teacher.getSchool());
		
		// 見つけてきたデータを、画面（JSP）で使えるようにセットする
		request.setAttribute("subject", subject);
		
		// 変更入力画面（JSP）へ移動する
		request.getRequestDispatcher("subject_update.jsp").forward(request, response);
	}
}