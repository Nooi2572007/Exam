package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// ローカル変数の指定 1
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher)session.getAttribute("user");
		int ent_year = 0; // 選択された入学年度
		String student_no = ""; // 入力された学生番号
		String student_name = ""; // 入力された氏名
		String class_num = ""; // 選択されたクラス番号
		String isAttend = "";
		Boolean IsAttend = false;
		Student student = new Student();
		StudentDao studentDao = new StudentDao();
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ

		// リクエストパラメーターの取得 2
		ent_year = Integer.parseInt(req.getParameter("ent_year"));
		student_no = req.getParameter("no");

		student_name = req.getParameter("name");
		class_num = req.getParameter("class_num");
		isAttend = req.getParameter("is_attend");
		IsAttend = "t".equals(isAttend);
		// DBからデータ取得 3
		// なし

		// ビジネスロジック 4
		
		// studentに学生情報をセット
	 	student.setStudentNo(student_no);
		student.setStudentName(student_name);
		student.setClassNum(class_num);
		student.setAttend(IsAttend);
		// saveメソッドで情報を登録
		student.setSchool(teacher.getSchool());
		studentDao.save(student);

		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}

}