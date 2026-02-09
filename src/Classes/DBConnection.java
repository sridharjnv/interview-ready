package Classes;

public class DBConnection {

    // Eager initialization
//    private static DBConnection object = new DBConnection();
//
//    private DBConnection() {}
//
//    public static DBConnection getInstance() {
//        return object;
//    }


    // Lazy initialization
//    private static DBConnection object;
//
//    private DBConnection(){}
//
//    public static DBConnection getInstance() {
//
//        if(object == null) {
//            object = new DBConnection();
//        }
//        return object;
//    }

    // Synchronized method
//    private static DBConnection object;
//
//    private DBConnection() {}
//
//    synchronized public static DBConnection getInstance() {
//        if(object == null){
//            object = new DBConnection();
//        }
//        return object;
//    }

    //Double check locking
//    private static volatile DBConnection object;
//
//    private DBConnection(){}
//
//    public static DBConnection getInstance() {
//        if(object == null) {
//            synchronized (DBConnection.class) {
//                if(object == null) {
//                    object = new DBConnection();
//                }
//            }
//        }
//        return object;
//    }

    // Bill pugh solution
    private DBConnection() {}

    private static class DBConnectionHolder {
        private static final DBConnection INSTANCE_OBJECT = new DBConnection();

        public static DBConnection getInstance() {
            return DBConnectionHolder.INSTANCE_OBJECT;
        }
    }



}
