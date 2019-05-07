/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sub_yaka;

/**
 *
 * @author kayfr
 */
public class SUB_YAKA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int a = 1;
        DB database=new DB();
        
        if (a == 1){
            database.connect();
            GUIsubYaka form = new GUIsubYaka();
            form.setVisible(true);
            
        }else{
             System.out.println("Something is wrong!");
        }
       
        
        
        
       
        // TODO code application logic here
    }
    
}
