package shareServlet;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.rngom.parse.host.Base;



/**
 * Servlet implementation class DownloadPhotoServlet
 */
@WebServlet("/DownloadPhotoServlet")
public class DownloadPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadPhotoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = this.DownloadPhoto();
		File file=new File(path);
         FileInputStream fis = new FileInputStream(file);
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         byte[] buf = new byte[1024];
         try {
             for (int readNum; (readNum = fis.read(buf)) != -1;) {
                 bos.write(buf, 0, readNum); 
             }
         } catch (IOException ex) {
        	 ex.printStackTrace();
         }
         byte[] bytes = bos.toByteArray();
        String image=org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
        response.getWriter().write(image);
		bos.flush();
        bos.close();

	}
	protected String DownloadPhoto()
	{
		String path=null;
		String connectionURL = "jdbc:mysql://localhost:3306/";
        Connection connection=null;
      	  try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionURL + "WeShareDatabase", "root", "");
	      	  String sql = "SELECT PASSWORD FROM USER WHERE ANDREWID='IMAGE';";
	      	 Statement stmt = connection.createStatement(); 
             ResultSet resultSet = stmt.executeQuery(sql);
             if (resultSet.next()) {
                 path = resultSet.getString("PASSWORD");
             }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          return path;
	}

}
