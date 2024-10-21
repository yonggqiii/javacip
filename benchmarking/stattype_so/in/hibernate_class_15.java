/**
 * @author dudekta
 */
public class hibernate_class_15 {
	private Long id;
	private Date creationTimeStamp;
	private String name;
	private String type;
	private String description;
	private Long length;
	private byte[] content;

	/** Don't invoke this. Used by Hibernate only. */
	public void setContentBlob(Blob imageBlob) throws SQLException, IOException {
		this.content = this.toByteArray(imageBlob);
	}

	/** Don't invoke this. Used by Hibernate only. */
	public Blob getContentBlob() {
		return Hibernate.createBlob(this.content);
	}

	private byte[] toByteArray(Blob fromBlob) throws SQLException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// try {
			return toByteArrayImpl(fromBlob, baos);
		// } catch (SQLException e) {
		// 	throw new RuntimeException(e);
		// } catch (IOException e) {
		// 	throw new RuntimeException(e);
		// } finally {
			// if (baos != null) {
				// try {
					// baos.close();
				// } catch (IOException ex) {
				// }
			// }
		// }
	}

	private byte[] toByteArrayImpl(Blob fromBlob, ByteArrayOutputStream baos) throws SQLException, IOException {
		byte[] buf = new byte[4000];
		InputStream is = fromBlob.getBinaryStream();
		// try {
			for (;;) {
				int dataSize = is.read(buf);

				if (dataSize == -1)
					break;
				baos.write(buf, 0, dataSize);
			}
		// } finally {
			if (is != null) {
				// try {
					is.close();
				// } catch (IOException ex) {
				// }
			}
		// }
		return baos.toByteArray();
	}

	/**
	 * @return Returns the creationTimeStamp.
	 */
	public Date getCreationTimeStamp() {
		return creationTimeStamp;
	}

	/**
	 * @param creationTimeStamp
	 *            The creationTimeStamp to set.
	 */
	public void setCreationTimeStamp(Date creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the length.
	 */
	public Long getLength() {
		return length;
	}

	/**
	 * @param length
	 *            The length to set.
	 */
	public void setLength(Long length) {
		this.length = length;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the content.
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * @param content
	 *            The content to set.
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
}
