public class hibernate_class_34 {

    public void testUserClassAndUsers() {
        System.out.println("Testing Users and UserClasses...");
        Session newSession = null;
        System.out.println("1 - Creating UserClasses:");
        Transaction t1 = newSession.beginTransaction();
        t1.commit();
        System.out.println("2 - Creating Users:");
        Transaction t2 = newSession.beginTransaction();
        t2.commit();
        System.out.println("3 - Deleting UserClass (\"UserClass 1\"):");
        Transaction t3 = newSession.beginTransaction();
        t3.commit();
        newSession.close();
    }

    public static void main(String[] args) {
        hibernate_class_34 instance = new hibernate_class_34();
        instance.testUserClassAndUsers();
    }
}
