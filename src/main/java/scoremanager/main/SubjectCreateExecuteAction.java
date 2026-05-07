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

public class SubjectCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// セッションからログイン中の先生の情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		Map<String, String> errors = new HashMap<>();
		Subject subject = new Subject();
		SubjectDao sDao = new SubjectDao();
		
		// 画面の入力フォームから送られてきたデータ（cd と name）を受け取る
		String cd = request.getParameter("cd");
		String name = request.getParameter("name");
		
		if (cd.length() != 3) { // 入学年度が未選択だった場合
			errors.put("1", "科目コードは3文字で入力してください");
			// リクエストにエラーメッセージをセット
			request.setAttribute("errors", errors);
		} else {
			if (sDao.get(cd, teacher.getSchool()) != null) { // 学生番号が重複している場合
				errors.put("1", "科目コードが重複しています");
				// リクエストにエラーメッセージをセット
				request.setAttribute("errors", errors);
			} else {
				// 新しい科目箱（Bean）を作って、受け取ったデータを入れる
				subject.setCd(cd);
				subject.setName(name);
				subject.setSchool(teacher.getSchool());
				
				// 金庫番（DAO）を呼んで、データベースに保存してもらう
				sDao.insert(subject);
			}
		}
		
		if (errors.isEmpty()) { // エラーメッセージがない場合
			// 登録完了画面にフォワード
			request.getRequestDispatcher("subject_create_done.jsp").forward(request, response);
		} else { // エラーメッセージがある場合
			// 登録画面にフォワード
			request.getRequestDispatcher("SubjectCreate.action").forward(request, response);
		}
	}
}