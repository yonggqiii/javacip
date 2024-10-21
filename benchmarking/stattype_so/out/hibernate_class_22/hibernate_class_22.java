public class hibernate_class_22 {

    public JSONObject check() {
        // creates a new session needed to add elements to a database
        Session session = null;
        // holds the result of the check in the database
        JSONObject check = new JSONObject();
        // try {
        // creates a new session needed to add elements to a database
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        String username = null;
        boolean justusername = false;
        if (justusername) {
            // query created to select a username from user table
            String hquery = "Select username from User user Where username = ? ";
            // query created
            Query query = session.createQuery(hquery);
            // sets the username of the query the values JSONObject contents
            query.setString(0, username);
            // executes query and adds username string variable
            String user = (String) query.uniqueResult();
            // checks to see if result is found (null if not found)
            if (user == null) {
                // adds false to Jobject if not found
                check.put("indatabase", "false");
            } else {
                check.put("indatabase", "true");
            }
            // adds check to Jobject to say just to check username
            check.put("justusername", true);
        } else {
            // query created to select a username and password from user table
            String hquery = "Select username from User user Where username = :user and password = :pass ";
            Query query = session.createQuery(hquery);
            query.setString("user", username);
            String password = null;
            query.setString("pass", password);
            String user = (String) query.uniqueResult();
            if (user == null) {
                check.put("indatabase", false);
            } else {
                check.put("indatabase", true);
            }
            check.put("justusername", false);
        }
        // } catch (Exception e) {
        System.out.println(JavaCIPUnknownScope.e.getMessage());
        // logg.log(Level.WARNING, " Exception", e.getMessage());
        // } finally {
        // Actual contact insertion will happen at this step
        session.flush();
        session.close();
        // }
        // returns Jobject
        return check;
    }
}
