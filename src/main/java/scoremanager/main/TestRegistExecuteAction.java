package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher; // インポートを追加
import bean.Test;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        
        // セッションからTeacherオブジェクトを取得し、そこからSchoolを取得するよう修正
        Teacher teacher = (Teacher) session.getAttribute("user"); 
        School school = teacher.getSchool(); 

        // リクエストパラメータの取得
        String subjectCd = req.getParameter("subject_cd");
        int testNo = Integer.parseInt(req.getParameter("num"));
        String classNum = req.getParameter("class_num");
        String[] studentNos = req.getParameterValues("student_no_set[]");
        String[] points = req.getParameterValues("point_set[]");

        List<Test> testList = new ArrayList<>();

        // 学生番号の配列が存在する場合、リストを作成
        if (studentNos != null) {
            for (int i = 0; i < studentNos.length; i++) {
                Test test = new Test();
                
                // 学生情報のセット
                Student student = new Student();
                student.setStudentNo(studentNos[i]);
                
                // 科目情報のセット
                Subject subject = new Subject();
                subject.setCd(subjectCd);

                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
                
                test.setNo(testNo);
                test.setClassNum(classNum);
                
                // 点数のセット（空文字や未入力のチェック）
                if (points != null && i < points.length && points[i] != null && !points[i].isEmpty()) {
                    test.setPoint(Integer.parseInt(points[i]));
                } else {
                    test.setPoint(0);
                }

                testList.add(test);
            }
        }

        // DAOを使ってデータベースに保存
        TestDao tDao = new TestDao();
        tDao.save(testList);

        // 登録完了後に完了画面へ遷移
        // ※このファイルが存在するか、WebContent(webapp)フォルダ内を確認してください
        req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
    }
}