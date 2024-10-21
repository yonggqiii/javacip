public class gwt_class_35 extends Animation {
	// public class gwt_class_35 extends Animation {

		private FlowPanel scroller;
		private final Element e;

		public gwt_class_35() {
			scroller = new FlowPanel();
			e = scroller.getElement();
		}

		public void scrollTo(int position, int milliseconds) {

			int scrollStart = e.getOffsetLeft();
			int scrollStop = position;

			GWT.log("ScrollStart: " + scrollStart + " scrollStop: " + scrollStop);
			run(milliseconds);
		}

		@Override
		protected void onUpdate(double progress) {
			int scrollStart = 0;
			int scrollStop = 0;
			double position = scrollStart + (progress * (scrollStop - scrollStart));
			Unit u = null;
			e.getStyle().setLeft(position, u);
		}
	}
// }
