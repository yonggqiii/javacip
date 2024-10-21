public class Android36 {
	private static final String KEY_NAME = "My Key";
	private static final String VALUE = "My Value";
	private static final String DATABASE_TABLE = "DB";

	private long insertRow(int rowParameter, String rowValue, SQLiteDatabase db) {
		long res = -1;
		ContentValues settingsParameterValues = new ContentValues();
		settingsParameterValues.put(KEY_NAME, rowParameter);
		settingsParameterValues.put(VALUE, rowValue);
		if (db != null) {
			res = db.insert(DATABASE_TABLE, null, settingsParameterValues);
		}
		return res;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
