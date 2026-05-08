package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        TestDao tDao = new TestDao();
        SubjectDao sDao = new SubjectDao();

        // 検索フォーム用のデータを取得
        List<Integer> entYearSet = tDao.filterEntYear(school);
        List<String> classNumSet = tDao.filterClassNum(school);
        List<Subject> subjects = sDao.filter(school);

        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subjects", subjects);

        // 初期表示用JSPへ転送
        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}