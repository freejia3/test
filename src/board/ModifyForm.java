package board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.DAO;

public class ModifyForm implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {
		ActionData data = new ActionData();
		DAO dao = new DAO();
		int id = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("data", dao.detail(id));
		request.setAttribute("main", "modifyForm.jsp");
		dao.close();
		return data;
	}

}
