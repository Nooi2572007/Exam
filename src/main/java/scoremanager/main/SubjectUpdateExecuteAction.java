package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションから教員情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// 画面の入力フォームから送られてきたデータ（cd と name）を受け取る
		String cd = request.getParameter("cd");
		String name = request.getParameter("name");
		
		// 変更用のデータ箱（Bean）を作成
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());
		
		// DAOを呼んで、データベースを更新（UPDATE）してもらう
		SubjectDao sDao = new SubjectDao();
		sDao.update(subject);
		
		// 変更が終わったら、科目一覧画面へ戻る
		// （「完了画面」を作っても良いですが、一覧に戻るのが一般的です）
		request.getRequestDispatcher("SubjectList.action").forward(request, response);
	}
}