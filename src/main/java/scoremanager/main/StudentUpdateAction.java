package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");

		// ローカル変数の指定 1
		StudentDao studentDao = new StudentDao(); 
//		Student student = new Student();
		ClassNumDao classNumDao = new ClassNumDao(); // クラス番号Daoを初期化
		// リクエストパラメーターの取得 2
		// なし
		String no = req.getParameter("no");
		Student student = studentDao.get(no);
		// DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = classNumDao.filter(teacher.getSchool());

		// リクエストに学生リストをセット
		// リクエストにデータをセット
		req.setAttribute("student", student);
		req.setAttribute("class_num_set", list);
		// JSPへフォワード 7
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}

}

