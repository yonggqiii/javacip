public class OrderedCharacterEncodingFilter extends CharacterEncodingFilter implements OrderedFilter {

	private int order = Ordered.HIGHEST_PRECEDENCE;

	@Override
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
