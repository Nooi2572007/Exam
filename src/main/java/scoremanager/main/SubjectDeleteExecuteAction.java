package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		
		String cd = request.getParameter("cd");
		
		// 削除用の空箱を作って、コードと学校情報だけ詰める（名前はなくても削除できます）
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setSchool(teacher.getSchool());
		
		// DAOを呼んで、データベースから物理的に削除（DELETE）してもらう
		SubjectDao sDao = new SubjectDao();
		sDao.delete(subject);
		
		// 削除が終わったら科目一覧画面へ戻る
		request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
	}
}