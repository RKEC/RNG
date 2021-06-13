package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    public static void connectDatabase(){
        String url = "jdbc:mysql://localhost/";
        String dbName = "rng";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = "";
        try
        {
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("\nConnected to the database.");

            Statement statement = conn.createStatement();

            conn.close();
        }
        catch (SQLException ex)
        {
            System.out.println("SQL Failed - check MySQL is running and that you are using the correct database details");
            ex.printStackTrace();
        }
    }

    public Connection getConnection()
    {

        Connection con = null;
        String url = "jdbc:mysql://localhost/rng";
        String driver = "com.mysql.cj.jdbc.Driver";
        String username = "root";
        String password = "";

        try
        {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException ex1)
        {
            System.out.println("Failed to find driver class " + ex1.getMessage());
            System.exit(1);
        }
        catch (SQLException ex2)
        {
            System.out.println("Connection failed " + ex2.getMessage());
            System.exit(2);
        }
        return con;
    }

    public int getNewId() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Users> users = new ArrayList<>();

        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM USERS";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int score = rs.getInt("score");
                String difficulty = rs.getString("diff");

                users.add(new Users(id, name, score, difficulty));
            }
        } catch (SQLException e)
        {
            throw new Exception("findAllUsers() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new Exception("findAllUsers() " + e.getMessage());
            }
        }
        int num = users.size() + 1;
        return num;     // may be empty
    }

    public boolean registerScore(int id, String name, int s, int diff) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;
        String difficulty;

        if(diff == 20){
            difficulty = "Easy";
        }else if(diff == 50){
            difficulty = "Medium";
        }else{
            difficulty = "Hard";
        }

        try
        {
            con = this.getConnection();

            String query = "INSERT INTO USERS VALUES (?,?,?,?)";
            ps = con.prepareStatement(query);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, s);
            ps.setString(4, difficulty);

            success = (ps.executeUpdate() == 1);

        } catch (SQLException e)
        {
            throw new Exception("insertUsers() " + e.getMessage());
        }finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new Exception("insertStudent() " + e.getMessage());
            }
        }
        return success;
    }

    public void leaderboards() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 1;

        List<Users> users = new ArrayList<>();

        try
        {
            con = this.getConnection();

            String query = "SELECT * FROM USERS ORDER BY USERS.SCORE DESC";
            ps = con.prepareStatement(query);

            rs = ps.executeQuery();
            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int score = rs.getInt("score");
                String difficulty = rs.getString("diff");

                Users u = new Users(id, name, score, difficulty);
                users.add(u);
                System.out.println(Colours.PURPLE + count +". " + u + Colours.RESET);
                count++;
            }

        } catch (SQLException e)
        {
            throw new Exception("findAllUsers() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new Exception("findAllUsers() " + e.getMessage());
            }
        }

    }

    public void freeConnection(Connection con)
    {
        try
        {
            if (con != null)
            {
                con.close();
                con = null;
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }

}

