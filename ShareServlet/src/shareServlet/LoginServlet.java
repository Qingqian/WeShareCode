package shareServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("text/html");

	      //  ObjectOutputStream out = new ObjectOutputStream(response
	         //       .getOutputStream());
	        Enumeration paramNames = request.getParameterNames();
	        String params[]= new String[2];
	        int i=0;
	        while(paramNames.hasMoreElements()) {
	            String paramName = (String)paramNames.nextElement();

	            System.out.println(paramName );
	            String[] paramValues = request.getParameterValues(paramName);
	            params[i] = paramValues[0];

	            System.out.println(params[i]);
	            i++;

	        }
	        
	        String andrewId = (String) request.getParameter("AndrewId");
	      
	          String password = (String) request.getParameter("Password");
	          String connectionURL = "jdbc:mysql://localhost:3306/";
	          Connection connection=null;
	          response.setContentType("text/html");
	          PrintWriter out = response.getWriter();
	          try {
	        	  Class.forName("com.mysql.jdbc.Driver");
	        	  connection = DriverManager.getConnection(connectionURL + "WeShareDatabase", "root", ""); 
	        	 // String sql = "INSERT INTO USER values (?,?)";
	        	  String sql = "SELECT * FROM USER WHERE ANDREWID="+"'"+andrewId+"'"+" AND PASSWORD="+password;
	        	  System.out.println(sql);
	        	 Statement stmt = connection.createStatement(); 
	              ResultSet rs = stmt.executeQuery(sql);
	              if(rs.next()){
	            	//  String an = rs.getString("ANDREWID");
	            	//  System.out.println(an);
	            	 out.println("correct username and password"); 
	              }else{
	            	  out.println("wrong username or password");
	              }
	              
	        	  }
	        	  catch(ClassNotFoundException e){
	        	  out.println("Couldn't load database driver: " 
	        	  + e.getMessage());
	        	  }
	        	  catch(SQLException e){
	        	  out.println("SQLException caught: " 
	        	  + e.getMessage());
	        	  }
	        	  catch (Exception e){
	        	  out.println(e);
	        	  }
	        	  finally {
	        	  // Always close the database connection.
	        	  try {
	        	  if (connection != null) connection.close();
	        	  }
	        	  catch (SQLException ignored){
	        	  out.println(ignored);
	        	  }
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
