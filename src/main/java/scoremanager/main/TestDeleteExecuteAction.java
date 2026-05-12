package scoremanager.main;

import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		StudentDao stdao = new StudentDao();
		SubjectDao sudao = new SubjectDao();
		
		String studentno = request.getParameter("student_no");
		String cd = request.getParameter("cd");
		int num = Integer.parseInt(request.getParameter("num"));
		
		// 削除用の空箱を作って、コードと学校情報だけ詰める（名前はなくても削除できます）
		
		// DAOを呼んで、データベースから物理的に削除（DELETE）してもらう
		Test test = new Test();
		test.setStudent(stdao.get(studentno));
		test.setSubject(sudao.get(cd, teacher.getSchool()));
		test.setNo(num);
		test.setSchool(teacher.getSchool());
		TestDao tDao = new TestDao();
		tDao.delete(test);
		
		// 削除が終わったら科目一覧画面へ戻る
		request.getRequestDispatcher("test_delete_done.jsp").forward(request, response);
	}
}