class c12065181 {

    private void sortMasters() {
        JavaCIPUnknownScope.masterCounter = 0;
        for (int i = 0; i < JavaCIPUnknownScope.maxID; i++) {
            if (JavaCIPUnknownScope.users[i].getMasterPoints() > 0) {
                JavaCIPUnknownScope.masterHandleList[JavaCIPUnknownScope.masterCounter] = JavaCIPUnknownScope.users[i].getHandle();
                JavaCIPUnknownScope.masterPointsList[JavaCIPUnknownScope.masterCounter] = JavaCIPUnknownScope.users[i].getMasterPoints();
                JavaCIPUnknownScope.masterCounter = JavaCIPUnknownScope.masterCounter + 1;
            }
        }
        for (int i = JavaCIPUnknownScope.masterCounter; --i >= 0; ) {
            for (int j = 0; j < i; j++) {
                if (JavaCIPUnknownScope.masterPointsList[j] > JavaCIPUnknownScope.masterPointsList[j + 1]) {
                    int tempp = JavaCIPUnknownScope.masterPointsList[j];
                    String temppstring = JavaCIPUnknownScope.masterHandleList[j];
                    JavaCIPUnknownScope.masterPointsList[j] = JavaCIPUnknownScope.masterPointsList[j + 1];
                    JavaCIPUnknownScope.masterHandleList[j] = JavaCIPUnknownScope.masterHandleList[j + 1];
                    JavaCIPUnknownScope.masterPointsList[j + 1] = tempp;
                    JavaCIPUnknownScope.masterHandleList[j + 1] = temppstring;
                }
            }
        }
    }
}
