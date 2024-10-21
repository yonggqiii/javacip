public final class xstream_class_17 {
	public static void main(String[] args) {
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		xstream.registerConverter(new PositionConverter());

		final Position position = new Position();
		position.setTitle("The Title");
		position.setStartDate("The Start Date");
		position.setEndDate("The End Date");

		final String xml = xstream.toXML(position);
		System.out.println("Generated XML:");
		System.out.println(xml);

		final Position genPosition = (Position) xstream.fromXML(xml);
		System.out.println("Generated Position:");
		System.out.println("\tTitle: " + genPosition.getTitle());
		System.out.println("\tStart Date: " + genPosition.getStartDate());
		System.out.println("\tEnd Date: " + genPosition.getEndDate());
	}


}

class PositionConverter implements Converter {
  public boolean canConvert(Class clazz) {
    return Position.class == clazz;
  }

  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    Position position = (Position) value;
    writer.startNode("PositionBorder");

    writer.startNode("Title");
    writer.setValue(position.getTitle());
    writer.endNode();

    writer.startNode("StartDate");
    writer.setValue(position.getStartDate());
    writer.endNode();

    writer.startNode("EndDate");
    writer.setValue(position.getEndDate());
    writer.endNode();

    writer.endNode();
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    Position position = new Position();
    // move it to <PositionBorder> tag.
    reader.moveDown();
    // now move it to <Title> tag.
    reader.moveDown();
    String title = reader.getValue();
    position.setTitle(title);
    reader.moveUp(); // moves back to <PositionBorder>

    reader.moveDown(); // should move down to <StartDate> tag
    String startDate = reader.getValue();
    position.setStartDate(startDate);
    reader.moveUp(); // move back to <PositionBorder>

    reader.moveDown(); // should move down to <EndDate> tag
    String endDate = reader.getValue();
    position.setEndDate(endDate);
    reader.moveUp(); // move back to <PositionBorder>

    return position;
  }
}
class Position {
  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String title;
  private String startDate;
  private String endDate;
}
