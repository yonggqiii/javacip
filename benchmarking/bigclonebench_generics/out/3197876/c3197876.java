class c3197876 {

    public ArrayList<String> showTopLetters() {
        int[] tempArray = new int[JavaCIPUnknownScope.engCountLetters.length];
        char[] tempArrayLetters = new char[JavaCIPUnknownScope.abcEng.length];
        ArrayList<String> resultTopFiveLetters = new ArrayList<String>();
        tempArray = JavaCIPUnknownScope.engCountLetters.clone();
        tempArrayLetters = JavaCIPUnknownScope.abcEng.clone();
        int tempCount;
        char tempLetters;
        for (int j = 0; j < (JavaCIPUnknownScope.abcEng.length * JavaCIPUnknownScope.abcEng.length); j++) {
            for (int i = 0; i < JavaCIPUnknownScope.abcEng.length - 1; i++) {
                if (tempArray[i] > tempArray[i + 1]) {
                    tempCount = tempArray[i];
                    tempLetters = tempArrayLetters[i];
                    tempArray[i] = tempArray[i + 1];
                    tempArrayLetters[i] = tempArrayLetters[i + 1];
                    tempArray[i + 1] = tempCount;
                    tempArrayLetters[i + 1] = tempLetters;
                }
            }
        }
        for (int i = tempArrayLetters.length - 1; i > tempArrayLetters.length - 6; i--) {
            resultTopFiveLetters.add(tempArrayLetters[i] + ":" + tempArray[i]);
        }
        return resultTopFiveLetters;
    }
}
