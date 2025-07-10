import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
import static java.lang.System.exit;
import static java.lang.System.in;
import static java.lang.Thread.sleep;

public class Hotel_Reservation_System {

    private static final String url="jdbc:mysql://localhost:3306/hotel";
    private static final String name="root";
    private static final String password="atif@2005";
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("All Drives Are Successfully Loaded ");
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try(Connection connection = DriverManager.getConnection(url,name,password)){
            Scanner input=new Scanner(System.in);
            int choice;
            while (true){
                System.out.println("*************** ATIF HOTEL *******************");
                System.out.println("""
                         1) New Reservation\s
                         2) Check Reservation\s
                         3) Get Room no\s
                         4) Update Reservation\s
                         5) Delete Reservation\s
                         0) Exit""");
                System.out.print("Your Input :- ");
                choice=input.nextByte();
                switch (choice){
                    case 0:{
                        System.out.print("Existing");
                        int i=5;
                        while(i!=0){
                            System.out.print(".");
                            sleep(450);
                            i--;
                        }
                        input.close();
                        exit(0);
                    }
                    case 1:{
                        newReservation(connection,input);
                        break;
                    }
                    case 2:{
                        checkReservation(connection,input);
                        break;
                    }
                    case 3:{
                        getRoomNo(connection,input);
                        break;
                    }
                    case 4:{
                        Update(connection,input);
                        break;
                    }
                    case 5:{
                        delete(connection,input);
                        break;
                    }
                    default:{
                        System.out.println("Invalid Input");
                        break;
                    }
                }
            }

        }
        catch (Exception e){
            System.out.println("Error :- "+e.getMessage());
        }
    }

    public static  void newReservation(Connection connection,Scanner input){
        try(Statement sta=connection.createStatement()){
            String name,phoneNumber;
            int roomNumber;
            input.nextLine();
            System.out.println("Enter Your Name");
            name=input.nextLine();
            System.out.println("Enter Your Contract Number");
            phoneNumber=input.next();
            System.out.println("Enter Room Number");
            roomNumber=input.nextInt();
            StringBuilder sc=new StringBuilder();
            String query=sc.append("INSERT INTO RESERVATION (GUEST_NAME,ROOM_NUMBER,CONTRACT_NUMBER) VALUES (")
                    .append("\"").append(name).append("\"")
                    .append(",").append(roomNumber)
                    .append(",").append("\"").append(phoneNumber)
                    .append("\"").append(");").toString();
            int row=sta.executeUpdate(query);
            if(row>0)
                System.out.println("Successfull");
            else
                System.out.println("failed");

        }
        catch (Exception e){
            System.out.println(e.getMessage());

        }
    }

    public static void checkReservation(Connection connection,Scanner input){
        try(Statement sta=connection.createStatement()){
            ResultSet rs=sta.executeQuery("SELECT * FROM RESERVATION;");
            System.out.println("+----------+--------------------+---------------+--------------+--------------------+");
            System.out.printf("|%-10s|%-20s|%-15s|%-15s|%-19s|\n","     ID","       Name","  PH-Number ",
                    "  Room Number  ","       Time");
            System.out.println("+----------+--------------------+---------------+--------------+--------------------+");
            while(rs.next()){
                System.out.printf("|%-10s|%-20s|%-15s|%-15s|%15s|\n",rs.getInt("RESERVATION_ID")+""
                        ,rs.getString("GUEST_NAME"),rs.getString("CONTRACT_NUMBER")
                        ,rs.getInt("ROOM_NUMBER"),rs.getString("RESERVATION_DATE"));
            }
            System.out.println("+----------+--------------------+---------------+--------------+--------------------+");
            rs.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void getRoomNo(Connection connection,Scanner input){
        input.nextLine();
        System.out.println("Enter The Name");
        String name= input.nextLine();
        System.out.println("Enter The Id");
        int id=input.nextInt();
        String Query = "SELECT * FROM RESERVATION WHERE RESERVATION_ID=" + id +
                " AND GUEST_NAME='" + name + "';";

        try(Statement sta=connection.createStatement()){
            ResultSet rs=sta.executeQuery(Query);
            System.out.println("****************************************");
            while (rs.next()){
                System.out.printf("RESERVATION_ID :- %d\nGUEST_NAME :- %s\nCONTRACT_NUMBER :- %s\nROOM_NUMBER :- %d\n",rs.getInt("RESERVATION_ID"),
                        rs.getString("GUEST_NAME"),rs.getString("CONTRACT_NUMBER")
                        ,rs.getInt("ROOM_NUMBER"));
            }
            rs.close();
            System.out.println("****************************************");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static  void Update(Connection connection,Scanner input){
        System.out.println("Enter Your RESERVATION_ID");
        int id=input.nextInt();
        input.nextLine();
        if(exists(connection,id)){
            try(Statement sta=connection.createStatement()) {
                System.out.println("Enter Your New Name");
                String name = input.nextLine();
                System.out.println("Enter Your New Contract Number");
                String number = input.nextLine();
                System.out.println("Enter New Room Number");
                int room=input.nextInt();
                String Query = "UPDATE RESERVATION SET GUEST_NAME=\"" + name + "\",CONTRACT_NUMBER=\"" + number + "\"," +
                        "ROOM_NUMBER="+room+" WHERE RESERVATION_ID="+id+";";
                int rs= sta.executeUpdate(Query);
                if(rs>0){
                    System.out.println("Updated Successfully");
                }
                else{
                    System.out.println("Updation failed");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("RESERVATION ID DOES NOT EXISTS ");
        }
    }

    public  static boolean exists(Connection connection,int id){
        try(Statement sta = connection.createStatement()){
            ResultSet rs=sta.executeQuery("SELECT RESERVATION_ID FROM RESERVATION WHERE RESERVATION_ID="+id);
            boolean ans=rs.next();
            rs.close();
            return  ans;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return  false;
    }

    public static  void delete(Connection connection,Scanner input){
        System.out.println("Enter Your RESERVATION_ID");
        int id=input.nextInt();
        input.nextLine();
        if(exists(connection,id)){
            try(Statement sta=connection.createStatement()) {
               String Query ="DELETE FROM RESERVATION WHERE RESERVATION_ID="+id+";";
               int rs= sta.executeUpdate(Query);
               if(rs>0){
                   System.out.println("Successfully Deleted");
               }
               else{
                   System.out.println("Delection failed");
               }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("RESERVATION ID DOES NOT EXISTS ");
        }
    }

}