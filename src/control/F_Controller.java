package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Action;
import model.ActionData;

/**
 * Servlet implementation class F_Controller
 */
@WebServlet("/board/*")
@MultipartConfig(
	location="C:\\tomcat\\temp",
	maxFileSize=1024*5000,
	maxRequestSize=1024*1024*100,
	fileSizeThreshold=1024*1024*50
)
public class F_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	//오늘은 토요일인데... 자고싶다.asdfadsfasdf
	///자고싶다고!!
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public F_Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");
		 String service = request.getRequestURI().substring(("/board/"+request.getContextPath()).length());
		 System.out.println("service: "+service);
		 
		 try {
			Action action = (Action)Class.forName("board."+service).newInstance();
			ActionData data = action.execute(request, response);
			
			if(data!=null) {
				if(data.isRedirect()) {
					//response.redirect
					response.sendRedirect(data.getPath());
				}else {
					//request.dispatcher.forward
					request.getRequestDispatcher("../view/template.jsp").forward(request, response);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
