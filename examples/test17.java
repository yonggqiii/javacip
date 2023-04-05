class Main {
    public static void main(String[] args) {
        List<Integer> ls = new List<>();
        List<String> ls2 = new List<>();
        List<Dog> ls3 = new List<>();
        ls = ls.addAll(ls);
        ls2 = ls2.addAll(ls2);
        ls3 = ls3.addAll(ls3);
        Integer i = ls.get(4);
        String s = ls2.get(2);
        Dog d = ls3.get(1);
    }
}