package scoremanager.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import bean.TestListSubject;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListStudentDao;
import dao.TestListSubjectDao;
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
        StudentDao stDao = new StudentDao();
        TestListSubjectDao tlsSubDao = new TestListSubjectDao();
        TestListStudentDao tlsStdDao = new TestListStudentDao();

        // ドロップダウンリスト用データ
        List<Integer> entYearSet = tDao.filterEntYear(school);
        List<String> classNumSet = tDao.filterClassNum(school);
        List<Subject> subjects = sDao.filter(school);

        // パラメータ取得
        String f1Str = req.getParameter("f1"); // 入学年度
        String f2    = req.getParameter("f2"); // クラス
        String f3    = req.getParameter("f3"); // 科目コード
        String f5    = req.getParameter("f5"); // 学生番号

        if (f1Str != null && f2 != null && f3 != null && !f3.equals("0")) {
            // 【科目検索】
            int f1 = Integer.parseInt(f1Str);
            Subject subject = sDao.get(f3, school);

            // DAOは行ごとに別オブジェクトを作るため、
            // 同じ学生番号のオブジェクトをLinkedHashMapでマージする
            List<TestListSubject> rawList = tlsSubDao.filter(f1, f2, subject, school);
            Map<String, TestListSubject> mergeMap = new LinkedHashMap<>();
            if (rawList != null) {
                for (TestListSubject ts : rawList) {
                    String key = ts.getStudentNo();
                    if (mergeMap.containsKey(key)) {
                        // 同じ学生の2件目以降 → pointsだけ追加
                        mergeMap.get(key).getPoints().putAll(ts.getPoints());
                    } else {
                        // 初出の学生 → そのままMapに登録
                        mergeMap.put(key, ts);
                    }
                }
            }
            List<TestListSubject> subjectTests = new ArrayList<>(mergeMap.values());

            req.setAttribute("subject_tests", subjectTests);
            req.setAttribute("selected_subject", subject);

        } else if (f5 != null && !f5.trim().isEmpty()) {
            // 【学生検索】
            Student student = stDao.get(f5.trim());
            if (student != null) {
                req.setAttribute("selected_student", student);
                List<TestListStudent> studentTests = tlsStdDao.filter(student);
                if (studentTests != null && !studentTests.isEmpty()) {
                    req.setAttribute("student_tests", studentTests);
                }
            } else {
                req.setAttribute("student_not_found", "学生情報が存在しませんでした");
            }
        }

        // JSPへ
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subjects", subjects);
        req.setAttribute("f1", f1Str);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);
        req.setAttribute("f5", f5);

        req.getRequestDispatcher("test_list.jsp").forward(req, res);
    }
}