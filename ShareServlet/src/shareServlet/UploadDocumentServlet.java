package shareServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UploadDocumentServlet
 */
@WebServlet("/UploadDocumentServlet")
public class UploadDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadDocumentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String document = (String)request.getParameter("document");
		System.out.println(document);
	    String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
		String fileName = "/User/qingqianzhao/WeShare/Document/TXT_" + timeStamp + ".txt";		
	    PrintWriter writer = new PrintWriter(fileName);
	    writer.println(document);
	    writer.flush();
	    writer.close();
	    insertIntoDatabase(fileName);
	}
	protected void insertIntoDatabase(String fileName)
	{
		String connectionURL = "jdbc:mysql://localhost:3306/";
        Connection connection=null;

        
      	  try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionURL + "WeShareDatabase", "root", "");
	      	  String sql = "INSERT INTO USER values (?,?)";
	      	  PreparedStatement pst = connection.prepareStatement(sql);
	      	  pst.setString(1, "DOCUMENT");
	      	  pst.setString(2, fileName);
	      	  pst.executeUpdate();
	      	  pst.close();
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
		doGet(request,response);
	}

}
