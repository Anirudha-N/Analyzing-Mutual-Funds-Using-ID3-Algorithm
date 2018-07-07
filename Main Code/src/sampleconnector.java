import java.sql.*;

public class sampleconnector 
{
	private Connection connect = null;
    private Statement statement = null;
    //private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public sampleconnector() throws Exception
    {
    	try 
        {
                // load the MySQL driver.
                Class.forName("com.mysql.jdbc.Driver");
                
                // Setup the connection with the DB
                connect = DriverManager.getConnection("jdbc:mysql://localhost/MF_Schema?"
                                                + "user=root&password=ssa");

                // Statements to issue SQL queries to the database
                statement = connect.createStatement();                
                
        } 
        catch (Exception e) 
        {
        	throw e;
        } 
    }
    
    public void readDataBase() throws Exception 
    {
        try 
        {
                // load the MySQL driver.
                //Class.forName("com.mysql.jdbc.Driver");
                
                // Setup the connection with the DB
                //connect = DriverManager.getConnection("jdbc:mysql://localhost/MF_Schema?"
                //                                + "user=root&password=ssa");

                // Statements to issue SQL queries to the database
                //statement = connect.createStatement();
                
                // Result set to get the result of the SQL query
                resultSet = statement.executeQuery("select * from MF_Schema.MF_Schemes");
                
                writeResultSet(resultSet);
                
        } 
        catch (Exception e) 
        {
        	throw e;
        } 
        finally 
        {
        	close();
        }
    }
    
    private void writeResultSet(ResultSet resultSet) throws SQLException 
    {
        // ResultSet is initially before the first data set
        while (resultSet.next()) 
        {
                int ID = resultSet.getInt("Scheme_Id");
                String SName = resultSet.getString("Scheme_Name");
               
                System.out.println("Scheme_ID: " + Integer.toString(ID));
                System.out.println("Scheme_Name: " + SName);
        }
    }
    
    // close the resultSet
    private void close() {
            try 
            {
                    if (resultSet != null) 
                    {
                            resultSet.close();
                    }

                    if (statement != null) 
                    {
                            statement.close();
                    }

                    if (connect != null) 
                    {
                            connect.close();
                    }
            } 
            catch (Exception e) 
            {

            }
    }

    
        public static void main(String[] args) throws Exception 
        {
        	sampleconnector dao = new sampleconnector();
        	dao.readDataBase();
                   }
}
