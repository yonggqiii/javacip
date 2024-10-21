public class hibernate_class_28 implements Serializable {
	private int id;
	private User user;
	private List<hibernate_class_28> comments = new ArrayList<hibernate_class_28>();
	private int articleId;
	private String text;

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Comment [" + "articleId " + articleId + " " + "id " + id + " " + "text " + text + " " + "]";
	}

}
