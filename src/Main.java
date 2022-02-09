public class Main {
    public static void main(String[] args){
        Database DB = new Database();
        UserConsole user = new UserConsole(DB);
        try{
            DB.connectToDB();
        }
        catch(Exception e){
            System.out.println("could not connect to DB");
            System.exit(1);
        }
        user.showConsole();
    }
}
