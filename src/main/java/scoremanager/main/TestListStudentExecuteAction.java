package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        TestDao tDao = new TestDao();
        SubjectDao sDao = new SubjectDao();
        StudentDao stDao = new StudentDao();
        TestListStudentDao tlsStdDao = new TestListStudentDao();

        String f5 = req.getParameter("f5");

        // 学生検索実行
        if (f5 != null && !f5.trim().isEmpty()) {
            Student student = stDao.get(f5.trim());
            req.setAttribute("selected_student", student);
            
            if (student == null) {
            	req.setAttribute("student_not_found", "学生情報が存在しませんでした");
            } else {
            	List<TestListStudent> studentTests = tlsStdDao.filter(student);
            	if (studentTests != null && !studentTests.isEmpty()) {
            		req.setAttribute("student_tests", studentTests);
            	} else {
            		req.setAttribute("student_not_found", "成績情報が存在しませんでした");
            	}
            }
        }

        // フォーム状態の維持
        req.setAttribute("ent_year_set", tDao.filterEntYear(school));
        req.setAttribute("class_num_set", tDao.filterClassNum(school));
        req.setAttribute("subjects", sDao.filter(school));
        req.setAttribute("f5", f5);

        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}