package scoremanager.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        TestDao tDao = new TestDao();
        SubjectDao sDao = new SubjectDao();
        TestListSubjectDao tlsSubDao = new TestListSubjectDao();

        // パラメータ取得
        String f1Str = req.getParameter("f1");
        String f2 = req.getParameter("f2");
        String f3 = req.getParameter("f3");
        int f1 = (f1Str != null && !f1Str.equals("0")) ? Integer.parseInt(f1Str) : 0;

        // 科目検索実行
        if (f1 != 0 && f2 != null && !f2.equals("0") && f3 != null && !f3.equals("0")) {
            Subject subject = sDao.get(f3, school);
            List<TestListSubject> tsList = tlsSubDao.filter(f1, f2, subject, school);

            // 学生ごとに回数別点数をマージ
            Map<String, TestListSubject> mergeMap = new LinkedHashMap<>();
            if (tsList != null) {
                for (TestListSubject ts : tsList) {
                    String key = ts.getStudentNo();
                    if (mergeMap.containsKey(key)) {
                        mergeMap.get(key).getPoints().putAll(ts.getPoints());
                    } else {
                        mergeMap.put(key, ts);
                    }
                }
            }
            req.setAttribute("subject_tests", new ArrayList<>(mergeMap.values()));
            req.setAttribute("selected_subject", subject);

            // 画像② 検索条件はOKだが結果が0件の場合
            if (mergeMap.isEmpty()) {
                req.setAttribute("subject_no_result", "学生情報が存在しませんでした");
            }
        } else {
            // 画像① 3条件のいずれかが未選択の場合
            req.setAttribute("subject_error", "入学年度とクラスと科目を選択してください");
        }

        // ドロップダウンと選択値の維持
        req.setAttribute("ent_year_set", tDao.filterEntYear(school));
        req.setAttribute("class_num_set", tDao.filterClassNum(school));
        req.setAttribute("subjects", sDao.filter(school));
        req.setAttribute("f1", f1Str);
        req.setAttribute("f2", f2);
        req.setAttribute("f3", f3);

        req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
    }
}