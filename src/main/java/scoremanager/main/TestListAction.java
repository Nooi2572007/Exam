package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 1. プルダウン用のデータを準備（ダミー含む）
		SubjectDao sDao = new SubjectDao();
		List<Subject> subjects = sDao.filter(teacher.getSchool());
		
		List<Integer> entYearList = new ArrayList<>();
		for (int i = 2020; i <= 2026; i++) entYearList.add(i);
		
		List<String> classNumList = new ArrayList<>();
		classNumList.add("101"); classNumList.add("102"); classNumList.add("201");

		request.setAttribute("subjects", subjects);
		request.setAttribute("ent_year_list", entYearList);
		request.setAttribute("class_num_list", classNumList);

		// 2. 検索実行時の処理（ダミーデータ）
		String studentNo = request.getParameter("student_no");
		String subjectCd = request.getParameter("subject_cd");
		
		List<Test> scores = new ArrayList<>();
		
		// 何かしら検索パラメータがある場合にダミーを表示
		if ((studentNo != null && !studentNo.isEmpty()) || (subjectCd != null && !subjectCd.equals("0"))) {
			
			// 検索対象の番号と名前（設計書の「氏名：〇〇(番号)」用）
			request.setAttribute("target_no", studentNo != null ? studentNo : "A001");
			request.setAttribute("student_name", "大原 太郎");

			// ダミーデータ生成（回数:no, 点数:point）
			Test t1 = new Test();
			t1.setSubjectCd("A01");
			t1.setNo(1); // 1回目
			t1.setPoint(85);
			scores.add(t1);

			Test t2 = new Test();
			t2.setSubjectCd("A01");
			t2.setNo(2); // 2回目
			t2.setPoint(92);
			scores.add(t2);
		}

		request.setAttribute("scores", scores);
		request.getRequestDispatcher("test_list.jsp").forward(request, response);
	}
}