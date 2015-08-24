

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class customers
 */
@WebServlet("/customers")
public class customers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public customers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String search = request.getParameter("person");
		String sql = "";
		String name = "";
		String message = "";
		ResultSet result = null;
		//System.out.println(as_type);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    //URL of Oracle database server
	    String url = "jdbc:oracle:thin:testuser/password@localhost"; 

	    
	    //properties for creating connection to Oracle database
	    Properties props = new Properties();
	    props.setProperty("user", "testdb");
	    props.setProperty("password", "password");
	  
	    //creating connection to Oracle database using JDBC
	    Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,props);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	  
		
		System.out.println(search);
		
			sql ="select customers.title, customers.firstname, customers.lastname, customers.STREETADDRESS,"+ 
					"locations.city, locations.states, customers.ZIPCODE,  customers.EMAILADDRESS,"+ 
					"companies.COMPANY, customers.POSITION  from customers, companies, locations"+
					" where lastname like '"+search+"' and"+
					" locations.id = customers.city and"+
					" customers.company = companies.id";
			
			
			
			//"select * from students where  a_type ='"+as_type+"'";
			System.out.println(sql);
		    //creating PreparedStatement object to execute query
		
		    PreparedStatement preStatement = null;
			try {
				preStatement = conn.prepareStatement(sql);

				result = preStatement.executeQuery();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		
			
			try {
				if (result.next())
				{
					try {
					message= "<thead><tr><th>Title</th><th>Firstname</th><th>Lastname</th><th>Street Address</th><th>City</th><th>State</th><th>Zipcode</th><th>Email Address</th><th>Company</th><th>Position in Company</th></tr></thead>";
					while(result.next()){
						
						//message += "<tr><td>"+result.getString("TITLE")+"</td><td>"+result.getString("FIRSTNAME")+"</td><td>"+result.getString("LASTNAME")+"</td><td>"+result.getString("STREETADDRESS")+"</td><td>"+result.getString("locations.CITY")+"</td><td>"+result.getString("locations.STATES")+"</td><td>"+result.getString("customers.ZIPCODE")+"</td><td>"+result.getString("customers.EMAILADDRESS")+"</td><td>"+result.getString("companies.COMPANY")+"</td><td>"+result.getString("customers.POSITION")+"</td></tr>\n";
						message += "<tr><td>"+result.getString(1)+"</td><td>"+result.getString(2)+"</td><td>"+result.getString(3)+"</td><td>"+result.getString(4)+"</td><td>"+result.getString(5)+"</td><td>"+result.getString(6)+"</td><td>"+result.getString(7)+"</td><td>"+result.getString(8)+"</td><td>"+result.getString(9)+"</td><td>"+result.getString(10)+"</td></tr>\n";
						//name = result.getString("firstname");
						
						}
					}
				 catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
   }
				
				else
				{
					
					sql = "select  customers.title, customers.firstname, customers.lastname, customers.STREETADDRESS,"+ 
							" locations.city, locations.states, customers.ZIPCODE,  customers.EMAILADDRESS,"+
							" companies.COMPANY, customers.POSITION from customers, companies, locations"+
							" where (lastname like '%"+search+"%' or companies.company like '%"+search+"%') and"+
							" (locations.ID = customers.CITY and customers.company = companies.id)";
					
					//"select * from students where  a_type ='"+as_type+"'";
					System.out.println(sql);
				    //creating PreparedStatement object to execute query
				
				   preStatement = null;
					try {
						preStatement = conn.prepareStatement(sql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						result = preStatement.executeQuery();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						message= "<thead><tr><th>Student ID</th><th>Class</th><th>Assignment</th><th>Assignment Type</th><th>Date</th><th>Garde</th></tr></thead>";
						while(result.next()){
							message += "<tr><td>"+result.getString(1)+
									"</td><td>"+result.getString(2)+
									"</td><td>"+result.getString(3)+
									"</td><td>"+result.getString(4)+
									"</td><td>"+result.getString(5)+
									"</td><td>"+result.getString(6)+
									"</td><td>"+result.getString(7)+
									"</td><td>"+result.getString(8)+
									"</td><td>"+result.getString(9)+
									"</td><td>"+result.getString(10)+"</td></tr>\n";
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				
  
     try {
				    conn.close();
				} catch (SQLException e){ 
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 //System.out.println("NAme is" +name);
		     request.setAttribute("message", message);
		     getServletContext()
		     .getRequestDispatcher("/output.jsp")
		     .forward(request,  response);
		     
	    } 
	      
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);}
	

}
