import java.util.ArrayList;
public class Class_7 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("mm-dd-yyyy");
		ArrayList<Person> people;
		people = new ArrayList<Person>();
		// try {
			people.add(new Person("Joe", 92422, df.parse("12-12-2010")));
			people.add(new Person("Joef", 24122, df.parse("1-12-2010")));
			people.add(new Person("Joee", 24922, df.parse("12-2-2010")));
		// } catch (ParseException e) {
		// 	// TODO Auto-generated catch block
		// 	e.printStackTrace();
		// }

		Collections.sort(people, new CompId());
		System.out.println("BY ID");
		for (Person p : people) {
			System.out.println(p.toString());
		}

		Collections.sort(people, new CompDate(false));
		System.out.println("BY Date asc");
		for (Person p : people) {
			System.out.println(p.toString());
		}
		Collections.sort(people, new CompDate(true));
		System.out.println("BY Date desc");
		for (Person p : people) {
			System.out.println(p.toString());
		}

	}

}

class Person {
  public String name;
  public int id;
  public Date hireDate;

  public Person(String iname, int iid, Date ihireDate) {
    name = iname;
    id = iid;
    hireDate = ihireDate;
  }

  public String toString() {
    return name + " " + id + " " + hireDate.toString();
  }

}

  // Comparator
class CompId implements Comparator<Person> {
  @Override
  public int compare(Person arg0, Person arg1) {
    return arg0.id - arg1.id;
  }
}

class CompDate implements Comparator<Person> {
  private int mod = 1;

  public CompDate(boolean desc) {
    if (desc)
      mod = -1;
  }

  @Override
  public int compare(Person arg0, Person arg1) {
    return mod * arg0.hireDate.compareTo(arg1.hireDate);
  }
}
