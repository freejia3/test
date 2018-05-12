package board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;
import model.DAO;
import model.VO;

public class ModifyReg implements Action {

	@Override
	public ActionData execute(HttpServletRequest request, HttpServletResponse response) {


		int page = Integer.parseInt(request.getParameter("page"));
		int id = Integer.parseInt(request.getParameter("id"));
		DAO dao = new DAO();
		VO vo = new VO();
		vo.setId(id);
		vo.setPw(request.getParameter("pw"));
		vo.setPname(request.getParameter("pname"));
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
		
		VO res = dao.sch(vo);
		String msg = "암호 인증 실패";
		String url = "ModifyForm?page="+page+"&id="+vo.getId();
		if(res!=null) {
			if(request.getParameter("upfile")!=null) {
				vo.setUpfile(request.getParameter("upfile"));
			}else {
				vo.setUpfile(new InsertReg().fileUpload(request));
			}
			msg="수정 성공";
			url="Detail?id="+vo.getId();
			dao.modify(vo);
		}
		
		dao.close();
		
		request.setAttribute("main", "alert.jsp");
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);

		return new ActionData();
	}

}
