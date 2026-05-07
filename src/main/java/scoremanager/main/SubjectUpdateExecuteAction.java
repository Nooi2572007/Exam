package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

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
		Map<String, String> errors = new HashMap<>();
		SubjectDao sDao = new SubjectDao();
		
		// 画面の入力フォームから送られてきたデータ（cd と name）を受け取る
		String cd = request.getParameter("cd");
		String name = request.getParameter("name");
		
		if (sDao.get(cd, teacher.getSchool()) == null) {
			errors.put("1", "科目が存在していません");
			// リクエストにエラーメッセージをセット
			request.setAttribute("errors", errors);
		} else {
			// 変更用のデータ箱（Bean）を作成
			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());
			
			// 金庫番（DAO）を呼んで、データベースに保存してもらう
			sDao.update(subject);
		}
		
		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			request.getRequestDispatcher("subject_update_done.jsp").forward(request, response);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			request.getRequestDispatcher("SubjectUpdate.action").forward(request, response);
		}
	}
}
