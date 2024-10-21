public class JodaTime10 {

	// ...
	private Object writeReplace() {
		return new Stub(null);
	}

}

final class Stub implements Serializable {
  private static final long serialVersionUID = -6212696554273812441L;

  private transient DateTimeZone iZone;

  Stub(DateTimeZone zone) {
    iZone = zone;
  }

  private Object readResolve() {
    return ISOChronology.getInstance(iZone);
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeObject(iZone);
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    iZone = (DateTimeZone) in.readObject();
  }
}
