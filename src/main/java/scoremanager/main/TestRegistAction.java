package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");
		School school = teacher.getSchool();

		// 画面からの入力値を取得
		String entYearStr = req.getParameter("f1"); // 入学年度
		String classNum = req.getParameter("f2");   // クラス
		String subjectCd = req.getParameter("f3");  // 科目コード
		String numStr = req.getParameter("f4");     // 回数

		TestDao tDao = new TestDao();
		SubjectDao sDao = new SubjectDao();

		// --- 検索処理 ---
		// 必要な項目がすべて入力されていたら検索を実行
		if (entYearStr != null && classNum != null && subjectCd != null && numStr != null 
				&& !entYearStr.equals("0") && !subjectCd.equals("0")) {

			int entYear = Integer.parseInt(entYearStr);
			int num = Integer.parseInt(numStr);

			// 1. まず科目コードから Subject オブジェクトを取得（DAOの定義に合わせるため）
			Subject subject = sDao.get(subjectCd, school);

			// 2. 引数の3番目に「subjectCd」ではなく「subject」を渡す（これで赤線が消えます）
			List<Test> tests = tDao.filter(entYear, classNum, subject, num, school);

			// JSPに渡すデータをセット
			req.setAttribute("tests", tests);
			req.setAttribute("subject", subject);
			req.setAttribute("num", num);
			req.setAttribute("class_num", classNum);

			// 現在の選択状態を維持するためにセット
			req.setAttribute("f1", entYear);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);
			req.setAttribute("f4", num);
		}

		// --- 画面表示の準備（ドロップダウン用） ---
		List<Integer> entYears = tDao.filterEntYear(school);
		List<String> classNums = tDao.filterClassNum(school);
		List<Subject> subjects = sDao.filter(school);

		req.setAttribute("ent_years", entYears);
		req.setAttribute("class_nums", classNums);
		req.setAttribute("subjects", subjects);

		// JSPへフォワード
		req.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(req, res);
	}
}