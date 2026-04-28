package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		// 消したい科目のコードを受け取る
		String cd = request.getParameter("cd");
		
		// 削除する科目のデータを探してくる（変更の時と同じ get メソッドを使います！）
		SubjectDao sDao = new SubjectDao();
		Subject subject = sDao.get(cd, teacher.getSchool());
		
		request.setAttribute("subject", subject);
		
		// 確認画面へ
		request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
	}
}