class c17985016 {

    public void setRandom(boolean random) {
        random = random;
        if (random) {
            JavaCIPUnknownScope.possibleScores = new int[JavaCIPUnknownScope.NUM_SCORES];
            for (int i = 0; i < JavaCIPUnknownScope.NUM_SCORES - 1; i++) {
                getRandomScore: while (true) {
                    int score = (int) (Math.random() * 20) + 1;
                    for (int j = 0; j < i; j++) {
                        if (score == JavaCIPUnknownScope.possibleScores[j]) {
                            continue getRandomScore;
                        }
                    }
                    JavaCIPUnknownScope.possibleScores[i] = score;
                    break;
                }
            }
            JavaCIPUnknownScope.possibleScores[JavaCIPUnknownScope.NUM_SCORES - 1] = 25;
            boolean sorted = false;
            while (!sorted) {
                sorted = true;
                for (int i = 0; i < JavaCIPUnknownScope.NUM_SCORES - 1; i++) {
                    if (JavaCIPUnknownScope.possibleScores[i] > JavaCIPUnknownScope.possibleScores[i + 1]) {
                        int t = JavaCIPUnknownScope.possibleScores[i];
                        JavaCIPUnknownScope.possibleScores[i] = JavaCIPUnknownScope.possibleScores[i + 1];
                        JavaCIPUnknownScope.possibleScores[i + 1] = t;
                        sorted = false;
                    }
                }
            }
            JavaCIPUnknownScope.setPossibleScores(JavaCIPUnknownScope.possibleScores);
        }
    }
}
