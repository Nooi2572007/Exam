package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 特にデータを用意する必要はないので、そのまま登録画面（JSP）へ転送するだけ
		request.getRequestDispatcher("subject_create.jsp").forward(request, response);
	}
}