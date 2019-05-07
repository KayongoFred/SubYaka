/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sub_yaka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
/**
 *
 * @author Kayfr
 */
public class DB {
     /**
     * Connect to a sample database
     */
    public static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
            
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS tennants " +
            "(name TEXT NOT NULL, " +
            " phone TEXT NOT NULL, " +
            " meter_no TEXT NOT NULL, " +
            " room_no TEXT NOT NULL)";
            stmt.executeUpdate(sql);
            
            stmt = conn.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS payments " +
            "(meter_no TEXT NOT NULL, " +
            " ammount_paid TEXT NOT NULL)";
            stmt.executeUpdate(sql);
            
            stmt = conn.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS prices " +
            "(id int AUTO_INCREMENT NOT NULL, "+
            "unites TEXT NOT NULL, " +
            " unite_price TEXT NOT NULL, "+
            "PRIMARY KEY(id))";
            stmt.executeUpdate(sql);
            
            stmt.close();
            conn.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        }
    
    //insert payment
    public static String addPayment(double meter_no, double ammount_paid){
        String rtn="";
        try {
            conn = DriverManager.getConnection(url, "root", "");
            
            stmt = conn.createStatement();
            String sql = "INSERT INTO payments (meter_no, ammount_paid)" +
            " VALUES('"+meter_no+"', '"+ammount_paid+"')";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            rtn="true";
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return rtn;
    }
    
    public static String addprices(String unite, String uniPrice){
        String rtn="";
        try {
            conn = DriverManager.getConnection(url, "root", "");
            
            stmt = conn.createStatement();
            String sql = "INSERT INTO prices (unites, unite_price)" +
            " VALUES('"+unite+"', '"+uniPrice+"')";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            rtn="true";
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        GUIsubYaka.jList2.setModel(DB.myUnites());
        return rtn;
    }
    
    // adding tennants
    public static String addTennant(String name, String phone, String meter_no, String room_no){
        String rtn="";
        try {
            conn = DriverManager.getConnection(url, "root", "");
            
            stmt = conn.createStatement();
            String sql = "INSERT INTO tennants (name, phone, meter_no, room_no)" +
            " VALUES('"+name+"', '"+phone+"', '"+meter_no+"', '"+room_no+"')";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            rtn="true";
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        GUIsubYaka.jList1.setModel(myUsers());
        return rtn;
    }
    
    //fetch list data
    public static DefaultListModel myUsers(){
        DefaultListModel listModel = new DefaultListModel();
        String name="";
        String phone;
        try {
            conn = DriverManager.getConnection(url, "root", "");
            
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM tennants;" );
            while ( rs.next() ) {
                name = rs.getString("name");
                listModel.addElement(name);
                
                phone = rs.getString("phone");
                listModel.addElement(phone);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return listModel;
    }
    
    // Display unites
    public static DefaultListModel myUnites(){
        DefaultListModel listModel = new DefaultListModel();
        String unit="";
        String price;
        try {
            conn = DriverManager.getConnection(url, "root", "");
            
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM prices;" );
            while ( rs.next() ) {
                unit = rs.getString("unite_price");
                price = "Units: "+rs.getString("unites")+", Price: "+unit;
                listModel.addElement(price);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return listModel;
    }
    
    public static String[] GetmyUnit(int index){
        String unit="";
        String price="";
        String id="";
        String[] rtn=new String[]{};
        
        try {
            conn = DriverManager.getConnection(url, "root", "");
            
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM prices LIMIT "+index+";" );
            while ( rs.next() ) {
                unit = rs.getString("unites");
                price = rs.getString("unite_price");
                id = rs.getString("id");
            }
            rtn = new String[]{unit, price, id};
            rs.close();
            stmt.close();
            conn.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return rtn;
    }
    
    public static boolean UpdatePrices(String unit, String price){
        boolean rtn=false;
        try {
            conn = DriverManager.getConnection(url, "root", "");
            
            stmt = conn.createStatement();
            stmt.executeUpdate( "UPDATE prices SET unites='"+unit+"', unite_price='"+price+"' WHERE unites='"+unit+"';" );
            stmt.close();
            conn.close();
            rtn=true;
            GUIsubYaka.jList2.setModel(DB.myUnites());
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return rtn;
    }
    
    public static boolean CheckUnitExistance(String unit){
        boolean rtn=false;
        try {
            conn = DriverManager.getConnection(url, "root", "");
            
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * fROM prices WHERE unites='"+unit+"';" );
            while ( rs.next() ) {
                rtn=true;
            }
            stmt.close();
            conn.close();
            GUIsubYaka.jList2.setModel(DB.myUnites());
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return rtn;
    }
    
    public static String[] addToArr(String[] originalArray, String newItem){
        int currentSize = originalArray.length;
        int newSize = currentSize + 1;
        String[] tempArray = new String[ newSize ];
        for (int i=0; i < currentSize; i++)
        {
            tempArray[i] = originalArray [i];
        }
        tempArray[newSize- 1] = newItem;
        return tempArray;
    }
    
    public static String getDate(){
    Date date=new Date();
    String rtn=String.format("%tB %<te, %<tY", date);
    return rtn;
    }
    
    private static Connection conn = null;
    private static String url = "jdbc:mysql://localhost:3306/umeme";
    private static Statement stmt = null;
}