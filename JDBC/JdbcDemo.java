/*@Author- Ashutosh Satapathy
Software Develepment Engineer
Date-04/11/21
*/
package JdbcPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class JdbcDemo
{
	public static void main(String[] args) throws Exception
	{
		//mydb is the name of my database
		String url="jdbc:mysql://localhost:3306/mydb";
		String username="Enter your usename-";
		String pass="Enter your password-";
		
		//Quereis to be executed
		String query1="select * from jdbc_prac;";
		String query2="insert into jdbc_prac (id,fname,lname) values (4,'Ronnie','Satapathy');";
		String query3="alter table jdbc_prac add primary key(id);";
		String query4="alter table jdbc_prac add salary int;";
		String query5="update jdbc_prac set salary=2500 where id=1;";
		String query6="alter table jdbc_prac modify id int not null auto_increment;";
		
		//Step1-Load and register drivers
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		//Step2- Create connection
		Connection con= DriverManager.getConnection(url, username, pass);
		
		//Step3- Create Statement
		Statement st= con.createStatement();
		
		//Step4-Execute
		//Update/delete/insert operatrions
		int res= st.executeUpdate(query6);
		
		//Step-5-Printing results
		System.out.println(res+"->rows affected");
		
		long res= st.executeLargeUpdate(query4);
		System.out.println(res+"->updated");*/
		
		//select statement
		ResultSet res= st.executeQuery(query1);	
		
		//Printing the table using while loop
		String userdata="";
		while(res.next())
		{
			userdata= res.getInt("id")+":"+res.getString("fname")+":"+res.getString("lname");
			System.out.println(userdata);
		}
		
		//Step-6-Closing the connection
		con.close();
		
	}
}
