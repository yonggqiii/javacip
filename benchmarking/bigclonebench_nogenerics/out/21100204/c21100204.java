class c21100204 {

    public void greatestIncrease(int maxIterations) {
        double[] increase = new double[JavaCIPUnknownScope.numModels];
        int[] id = new int[JavaCIPUnknownScope.numModels];
        Model md = new Model();
        double oldPerf = 1;
        for (int i = 0; i < JavaCIPUnknownScope.numModels; i++) {
            md.addModel(JavaCIPUnknownScope.models[i], false);
            increase[i] = oldPerf - md.getLoss();
            id[i] = i;
            oldPerf = md.getLoss();
        }
        for (int i = 0; i < JavaCIPUnknownScope.numModels; i++) {
            for (int j = 0; j < JavaCIPUnknownScope.numModels - 1 - i; j++) {
                if (increase[j] < increase[j + 1]) {
                    double increasetemp = increase[j];
                    int temp = id[j];
                    increase[j] = increase[j + 1];
                    id[j] = id[j + 1];
                    increase[j + 1] = increasetemp;
                    id[j + 1] = temp;
                }
            }
        }
        for (int i = 0; i < maxIterations; i++) {
            JavaCIPUnknownScope.addToEnsemble(JavaCIPUnknownScope.models[id[i]]);
            if (JavaCIPUnknownScope.report)
                JavaCIPUnknownScope.ensemble.report(JavaCIPUnknownScope.models[id[i]].getName(), JavaCIPUnknownScope.allSets);
            JavaCIPUnknownScope.updateBestModel();
        }
    }
}
