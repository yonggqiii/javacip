import java.io.Serializable;
public class hibernate_class_27 {

	public static List<ProjectAssignment> getAllResources() {
		List<ProjectAssignment> projectMasters;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("select distinct aid, pid, userName from ProjectAssignment");
		projectMasters = (List<ProjectAssignment>) query.list();
		session.close();

		return projectMasters;
	}

}

class ProjectAssignment implements Serializable {

  private short aid;
  private String pid;
  private String userName;

  public ProjectAssignment() {
  }

  public ProjectAssignment(short aid) {
    this.aid = aid;
  }

  public ProjectAssignment(short aid, String pid, String userName) {
    this.aid = aid;
    this.pid = pid;
    this.userName = userName;
  }

  public short getAid() {
    return this.aid;
  }

  public void setAid(short aid) {
    this.aid = aid;
  }

  public String getPid() {
    return this.pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
