class c6972162 {

    public void removeDownload() {
        int rowCount = JavaCIPUnknownScope.mDownloadTable.getSelectedRowCount();
        if (rowCount <= 0)
            return;
        int[] rows = JavaCIPUnknownScope.mDownloadTable.getSelectedRows();
        int[] orderedRows = new int[rows.length];
        Vector downloadFilesToRemove = new Vector();
        for (int i = 0; i < rowCount; i++) {
            int row = rows[i];
            if (row >= JavaCIPUnknownScope.mDownloadMgr.getDownloadCount())
                return;
            orderedRows[i] = JavaCIPUnknownScope.mDownloadSorter.indexes[row];
        }
        rowCount = JavaCIPUnknownScope.mDownloadTable.getRowCount();
        JavaCIPUnknownScope.mDownloadTable.removeRowSelectionInterval(0, rowCount - 1);
        for (int i = orderedRows.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (orderedRows[j] > orderedRows[j + 1]) {
                    int tmp = orderedRows[j];
                    orderedRows[j] = orderedRows[j + 1];
                    orderedRows[j + 1] = tmp;
                }
            }
        }
        for (int i = orderedRows.length - 1; i >= 0; i--) {
            JavaCIPUnknownScope.mDownloadMgr.removeDownload(orderedRows[i]);
        }
        JavaCIPUnknownScope.mainFrame.refreshAllActions();
    }
}
