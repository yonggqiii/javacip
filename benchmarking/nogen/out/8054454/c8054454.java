class c8054454 {

    public void sortIndexes() {
        int i, j, count;
        int t;
        count = JavaCIPUnknownScope.m_ItemIndexes.length;
        for (i = 1; i < count; i++) {
            for (j = 0; j < count - i; j++) {
                if (JavaCIPUnknownScope.m_ItemIndexes[j] > JavaCIPUnknownScope.m_ItemIndexes[j + 1]) {
                    t = JavaCIPUnknownScope.m_ItemIndexes[j];
                    JavaCIPUnknownScope.m_ItemIndexes[j] = JavaCIPUnknownScope.m_ItemIndexes[j + 1];
                    JavaCIPUnknownScope.m_ItemIndexes[j + 1] = t;
                }
            }
        }
    }
}
