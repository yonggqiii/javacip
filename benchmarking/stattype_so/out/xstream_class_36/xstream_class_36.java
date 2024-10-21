import java.util.Map;
import java.util.HashMap;

public class xstream_class_36 {

    public static void main(String[] arg) {
        MapTest services = null;
        services.setServicesHealth("ID01", "Jack");
        services.setServicesHealth("ID02", "Neil");
        XStream stream = new XStream(new StaxDriver());
        stream.alias("MapTest", MapTest.class);
        stream.addImplicitMap(MapTest.class, "map", "id", String.class, "name");
        String xmlStr = stream.toXML(services);
        System.out.println(xmlStr);
    }
}

class MapTest {

    private Map<String, String> mapList;

    public MapTest() {
        mapList = new HashMap<String, String>();
    }

    public void setServicesHealth(String id, String name) {
        mapList.put(id, name);
    }
}
