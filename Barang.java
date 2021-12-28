
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.*;
import com.mysql.cj.protocol.Resultset;
import java.io.BufferedReader;
import java.io.FileReader;



public class Barang 
{
    
    public static String Barang;
    public static String User;
    public static Integer harga;
    public static Integer nofaktur;
    public static Integer jumlah;
    public static Integer totalharga;

   
    static Scanner input = new Scanner(System.in);

    static Connection conn;
    public static void main(String[] args) throws Exception 
    {

        String url = "jdbc:mysql://localhost:3306/tp14";
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, "root", "");
        
        System.out.println("--------------");
        System.out.println("  Toko Baju");
        System.out.println("--------------");
        System.out.println("1. Tampilkan Data ");
	System.out.println("2. Input Data ");
	System.out.println("3. Update Data ");
	System.out.println("4. Hapus Data ");
	System.out.println("5. Cari Data ");
	System.out.print("\nPilihan anda : ");
	User = input.next();
				
	switch (User) 
        {
                case "1":
                        lihatdata();
                break;
                case "2":
                        tambahdata();
                break;
                case "3":
                        ubahdata();
                break;
                case "4":
                        hapusdata();
                break;
                case "5":
                        caridata();
                break;
                default:
                        System.err.println("\nInput anda tidak ditemukan\nSilakan pilih [1-5]");
	}			

        
        
    }
        
        private static void caridata() throws SQLException
        {
                System.out.println("-------------");
		System.out.println("  Cari Data");
                System.out.println("-------------");
		
		Scanner input = new Scanner (System.in);
				
                System.out.print("Masukkan Nama Barang : ");
        
		String keyword = input.nextLine();
                Statement statement = conn.createStatement();
                String sql = "SELECT * FROM data_transaksi WHERE nama LIKE '%"+keyword+"%'";
                ResultSet result = statement.executeQuery(sql); 
                
                while(result.next())
                {
                        System.out.print("No. Faktur  : ");
                        System.out.println(result.getInt("nofaktur"));
                        System.out.print("Nama        : ");
                        System.out.println(result.getString("nama"));
                        System.out.print("Harga       : ");
                        System.out.println(result.getInt("harga"));
                        System.out.print("Jumlah      : ");
                        System.out.println(result.getInt("jumlah"));
                        System.out.println();
                }
                        
        }
        
        private static void hapusdata() throws SQLException
        {
                System.out.println("--------------");
		System.out.println("  Hapus Data");
                System.out.println("--------------");
		
		Scanner input = new Scanner (System.in);
                try{
                        lihatdata();
                        System.out.print("No.Faktur : ");
                        Integer nofaktur= Integer.parseInt(input.nextLine());
                        
                        String sql = "DELETE FROM data_transaksi WHERE nofaktur = "+ nofaktur;
                        Statement statement = conn.createStatement();
                        //ResultSet result = statement.executeQuery(sql);
                        
                        if(statement.executeUpdate(sql) > 0)
                        {
                            System.out.println("Data Terhapus(No.Faktur "+nofaktur+")");
                        }
                   }
                   catch(SQLException e)
                   {
                        System.out.println("Terjadi kesalahan dalam menghapus data barang");
                   }
        }
        
        private static void ubahdata() throws SQLException
        {
                System.out.println("-------------");
		System.out.println(" Update Data");
                System.out.println("-------------");
                Scanner input = new Scanner (System.in);
		
	try {
            lihatdata();
            System.out.print("Data Terbaru (No.Faktur) : ");
            Integer nofaktur = Integer.parseInt(input.nextLine());
            
            String sql = "SELECT * FROM data_transaksi WHERE nofaktur = " +nofaktur;
            
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            if(result.next())
            {
                
                System.out.print("Nama Barang ["+result.getString("nama")+"]\t: ");
                String Barang = input.nextLine();
                
                System.out.print("Harga Barang["+result.getInt("harga")+"]\t: ");
                Integer harga = input.nextInt();

                System.out.print("Jumlah Barang["+result.getInt("jumlah")+"]\t: ");
                Integer jumlah = input.nextInt();

                sql = "UPDATE data_transaksi SET nama='"+Barang+"',harga= '"+harga+"',jumlah= '"+jumlah+"' WHERE nofaktur='"+nofaktur+"'";
                

                if(statement.executeUpdate(sql) > 0)
                {
                    System.out.println("Data Diperbarui (No.Faktur "+nofaktur+")");
                }
            }
            statement.close();        
        } 
        catch (SQLException e) 
        {
            System.err.println("Terjadi kesalahan dalam mengedit data");
            System.err.println(e.getMessage());
        }  
        }

        private static void tambahdata()throws SQLException 
        {
                System.out.println("--------------");
		System.out.println("  Input Data");
                System.out.println("--------------");
                
        try 
        {
                System.out.print("Nomor Faktur   : ");
                nofaktur = input.nextInt();
                System.out.print("Nama Barang    : ");
                Barang = input.next();
                System.out.print("Harga Barang   : ");
                harga = input.nextInt();
                System.out.print("Jumlah Barang  : ");
                jumlah = input.nextInt();
                System.out.println();
               

                String sql = "INSERT INTO data_transaksi (nofaktur, nama, harga, jumlah) VALUES ('"+nofaktur+"','"+Barang+"','"+harga+"','"+jumlah+"')";
                        
                Statement word = conn.createStatement();
                word.execute(sql);
                System.out.println("Data Ditambahkan");
                       
                
        }
        catch (SQLException e) 
        {
	        
	} 
        catch (InputMismatchException e) 
        {
	    	
	}
        }
        
        private static void lihatdata() throws SQLException 
        {
                
		System.out.println("--------------");
		System.out.println("Data Transaksi");
                System.out.println("--------------");
										
		String sql ="SELECT * FROM data_transaksi";
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);

                while(result.next())
                {       
                        System.out.println();
			System.out.print("No.Faktur   : ");
                        System.out.println(result.getInt("nofaktur"));
                        System.out.print("Nama        : ");
                        System.out.println(result.getString("nama"));
                        System.out.print("Harga       : ");
                        System.out.println(result.getInt("harga"));
                        System.out.print("Jumlah      : ");
                        System.out.println(result.getInt("jumlah"));
                        System.out.println();
                        
                }
        }
 
}


   
