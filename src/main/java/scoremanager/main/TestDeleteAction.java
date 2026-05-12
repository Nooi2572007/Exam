package scoremanager.main;

import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestDeleteAction extends Action {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		StudentDao stdao = new StudentDao();
		SubjectDao sudao = new SubjectDao();
		
		// 消したい科目のコードを受け取る
		String studentno = request.getParameter("student_no");
		String cd = request.getParameter("cd");
		int num = Integer.parseInt(request.getParameter("num"));
		
		// 削除する科目のデータを探してくる（変更の時と同じ get メソッドを使います！）
		Test test = new Test();
		test.setStudent(stdao.get(studentno));
		test.setSubject(sudao.get(cd, teacher.getSchool()));
		test.setNo(num);
		
		
		request.setAttribute("test", test);
		
		// 確認画面へ
		request.getRequestDispatcher("test_delete.jsp").forward(request, response);
	}
}
