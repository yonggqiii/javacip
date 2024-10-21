public class xstream_class_9 {
}

class ServerIP {

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

class ServerHandler {

    private String fileName = "servers.xml";

    private String path = "J:\\workspace\\LOG730\\src\\Q3\\";

    private XStream xstream = new XStream(new DomDriver());

    public void readFromXML() {
        // try {
        FileInputStream fis = new FileInputStream(path + fileName);
        ServerIP server = (ServerIP) xstream.fromXML(fis, new ServerIP());
        System.out.println("Host: " + server.getIp());
        // } catch (FileNotFoundException e) {
        // e.printStackTrace();
        // }
    }
}
