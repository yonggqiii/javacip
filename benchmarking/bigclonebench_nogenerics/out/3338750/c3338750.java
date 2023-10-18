class c3338750 {

    public void sortPlayersTurn() {
        Token tempT = new Token();
        Player tempP = new Player("test name", tempT);
        int tempN = 0;
        boolean exchangeMade = true;
        for (int i = 0; i < JavaCIPUnknownScope.playerNum - 1 && exchangeMade; i++) {
            exchangeMade = false;
            for (int j = 0; j < JavaCIPUnknownScope.playerNum - 1 - i; j++) {
                if (JavaCIPUnknownScope.diceSum[j] < JavaCIPUnknownScope.diceSum[j + 1]) {
                    tempP = JavaCIPUnknownScope.players[j];
                    tempN = JavaCIPUnknownScope.diceSum[j];
                    JavaCIPUnknownScope.players[j] = JavaCIPUnknownScope.players[j + 1];
                    JavaCIPUnknownScope.diceSum[j] = JavaCIPUnknownScope.diceSum[j + 1];
                    JavaCIPUnknownScope.players[j + 1] = tempP;
                    JavaCIPUnknownScope.diceSum[j + 1] = tempN;
                    exchangeMade = true;
                }
            }
        }
    }
}
