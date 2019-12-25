

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Calisanislemleri {
    
    private Connection con=null;
    private Statement statement=null;
    private  PreparedStatement preparedStatement= null;
    
    
    public void calisanSil(int id){
        
        String sorgu="Delete from calisanlar where id= ?";
        
        try {
            preparedStatement=con.prepareStatement(sorgu);
            
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Calisanislemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void calisanGüncelle(int id,String ad,String soyad,String departman,String maas){
        
        
        String sorgu="Update calisanlar set ad=?,soyad=?,departman=?,maas=? where id=?";
        
        try {
            preparedStatement=con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            
            preparedStatement.setInt(5, id);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Calisanislemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
    }
    
    public void calisanEkle(String ad,String soyad,String departman,String maas){
        
        String sorgu="Insert Into calisanlar(ad,soyad,departman,maas) VALUES(?,?,?,?)";
        try {
            preparedStatement =con.prepareStatement(sorgu);
            
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Calisanislemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    public ArrayList<Calisan> calisanGetir(){
        
        ArrayList<Calisan> cikti=new ArrayList<Calisan>();
        
        try {
            statement=con.createStatement();
            
            String sorgu="Select * From calisanlar";
            
            ResultSet rs=statement.executeQuery(sorgu);
            
            while (rs.next()) {                
                
                int id=rs.getInt("id");
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyad");
                String dept=rs.getString("departman");
                String maas=rs.getString("maas");
                
                cikti.add(new Calisan(id, ad, soyad, dept, maas));
                
                
            }
            return cikti;
           
        } catch (SQLException ex) {
            Logger.getLogger(Calisanislemleri.class.getName()).log(Level.SEVERE, null, ex);
            return  null;
        }
        
        
    }

    public boolean  girisYap(String kullanıcı,String parola){
        
        
        
        String sorgu="Select * from adminler where username=? and password=?";
        
        try {
            preparedStatement=con.prepareStatement(sorgu);
            preparedStatement.setString(1, kullanıcı);
            preparedStatement.setString(2, parola);
            
            ResultSet rs=preparedStatement.executeQuery();
            if (rs.next()==false) {
                
                return false;
                
            }else{
                
                return  true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Calisanislemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  false;
        
    }
    
    
    
    public Calisanislemleri(){
        
        String url="jdbc:mysql://"+Database.host+":"+Database.port+"/"+Database.db_ismi+"?useUnicode=true&charecterEncoding=utf8";
        
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
           
            System.out.println("Driver bulunamadı");
        }
        
        try {
            con=(Connection) DriverManager.getConnection(url,Database.kullaniciAd,Database.parola);
          
        } catch (SQLException ex) {
           ex.printStackTrace();
        }  
        
        
        
        
    }

 
 
}
