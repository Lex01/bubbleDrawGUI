import javax.swing.JFrame;

public class BubbleDraw extends JFrame {

	public static void main(String[] args) {
		// set up the frame for BubbleDraw app
		JFrame frame = new JFrame("BubbleDraw GUI App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().add(new BubblePanel());
		
		frame.pack();
		frame.setVisible(true);
		

	}

}
