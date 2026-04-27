package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションからログイン中の先生の情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// 画面の入力フォームから送られてきたデータ（cd と name）を受け取る
		String cd = request.getParameter("cd");
		String name = request.getParameter("name");
		
		// 新しい科目箱（Bean）を作って、受け取ったデータを入れる
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());
		
		// 金庫番（DAO）を呼んで、データベースに保存してもらう
		SubjectDao sDao = new SubjectDao();
		sDao.insert(subject);
		
		// 登録完了画面（JSP）へ転送する
		request.getRequestDispatcher("subject_create_done.jsp").forward(request, response);
	}
}