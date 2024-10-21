public class xstream_class_44 {

    public static int COUNT = 0;

    public static int MAX_DEPTH = 800;

    /**
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) {
        TestObject object = getNewTestObject();
        File persistFile = new File("C:\\ABC.xml");
        FileOutputStream outStream = new FileOutputStream(persistFile);
        XStream x = new XStream();
        x.alias("testObject", TestObject.class);
        x.toXML(object, outStream);
        outStream.close();
    }

    public static TestObject getNewTestObject() {
        TestObject orderRecord = null;
        orderRecord.setName("OBJECT_" + COUNT);
        while (COUNT++ < MAX_DEPTH) {
            orderRecord.setParentObject(getNewTestObject());
        }
        return orderRecord;
    }
}

class TestObject {

    private TestObject parentObject;

    private String name;

    /**
     * @return the parentObject
     */
    public TestObject getParentObject() {
        return parentObject;
    }

    /**
     * @param parentObject the parentObject to set
     */
    public void setParentObject(TestObject parentObject) {
        this.parentObject = parentObject;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
