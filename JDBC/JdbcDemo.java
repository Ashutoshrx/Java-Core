package JdbcPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class JdbcDemo
{
	public static void main(String[] args) throws Exception
	{
		//Step1- Create connection
		String url="jdbc:mysql://localhost:3306/mydb";
		String username="root";
		String pass="root";
		String query1="select * from jdbc_prac;";
		String query2="insert into jdbc_prac (id,fname,lname) values (4,'Ronnie','Satapathy');";
		String query3="alter table jdbc_prac add primary key(id);";
		String query4="alter table jdbc_prac add salary int;";
		String query5="update jdbc_prac set salary=2500 where id=1;";
		String query6="alter table jdbc_prac modify id int not null auto_increment;";
		
		//Step2-Load and register drivers
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con= DriverManager.getConnection(url, username, pass);
		Statement st= con.createStatement();
		
		int res= st.executeUpdate(query6);
		System.out.println(res+"->rows affected");
		
		/*long res= st.executeLargeUpdate(query4);
		System.out.println(res+"->updated");*/
		
		/*ResultSet res= st.executeQuery(query1);		
		String userdata="";
		while(res.next())
		{
			userdata= res.getInt("id")+":"+res.getString("fname")+":"+res.getString("lname");
			System.out.println(userdata);
		}*/
		
		
		con.close();
		
	}
}