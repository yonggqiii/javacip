public class xstream_class_30 implements Converter {

    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        // implement logic for marshalling to xml
    }

    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Items items = new Items();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            final String currentAttribute = hierarchicalStreamReader.getAttribute("name");
            if ("id".equals(currentAttribute)) {
                items.setId(hierarchicalStreamReader.getValue());
            } else if ("first-name".equals(currentAttribute)) {
                items.setFirstName(hierarchicalStreamReader.getValue());
            } else if ("last-name".equals(currentAttribute)) {
                items.setLastName(hierarchicalStreamReader.getValue());
            } else if ("address".equals(currentAttribute)) {
                items.setAddress(hierarchicalStreamReader.getValue());
            } else if ("zip".equals(currentAttribute)) {
                items.setZip(hierarchicalStreamReader.getValue());
            }
            hierarchicalStreamReader.moveUp();
        }
        return items;
    }

    public boolean canConvert(Class aClass) {
        return aClass == Items.class;
    }
}

class Items {

    private String id;

    private String firstName;

    private String lastName;

    private String address;

    private String zip;

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
