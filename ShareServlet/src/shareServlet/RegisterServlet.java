package shareServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");

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
        PrintWriter out = response.getWriter();

      	String andrew_id = (String) request.getParameter("andrew_id");
          String create_password = (String) request.getParameter("password");
                    
 
        	  String connectionURL = "jdbc:mysql://localhost:3306/";
              Connection connection=null;
              response.setContentType("text/html");
              try {
            	  Class.forName("com.mysql.jdbc.Driver");
            	  connection = DriverManager.getConnection(connectionURL + "WeShareDatabase", "root", ""); 
            	  
            	  String sql = "SELECT PASSWORD FROM USER WHERE ANDREWID="+"'"+andrew_id+"'";
            	  
            	  Statement stmt = connection.createStatement();
	             
	              
	              ResultSet rs = stmt.executeQuery(sql);
	              //stmt.getResultSet
	              
	              if(rs.next()){
	            	//  String an = rs.getString("ANDREWID");
	            	//  System.out.println(an);
	            	 out.println("user name existed"); 
	              }
	              
	              
	              else {
	            	  String sqll = "INSERT INTO USER values (?,?)";
	            	  
	            	  
	            	  PreparedStatement pst = connection.prepareStatement(sqll);
	            	  
	            	  
	            	  pst.setString(1, andrew_id);
	            	  pst.setString(2, create_password);

	            	  System.out.println("andrewid"+andrew_id);
	            	  System.out.println("password"+create_password);
	            	  
	            	  
	            	  int numRowsChanged = pst.executeUpdate();
	            	  System.out.println(numRowsChanged );
	            	  pst.close();
	              
	            	   
	              }
            	  
            	  
            	  

            	  
            	  
              }catch(ClassNotFoundException e){
            	  out.println("Couldn't load database driver: " + e.getMessage());
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
