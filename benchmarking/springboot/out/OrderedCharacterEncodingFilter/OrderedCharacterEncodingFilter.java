public class OrderedCharacterEncodingFilter extends CharacterEncodingFilter implements OrderedFilter {

    private int order = Ordered.HIGHEST_PRECEDENCE;

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
