import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.Timer;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class BubblePanel extends JPanel {
	
	private ArrayList<Bubble> bubbleList;
	private int size = 30;
	private Timer timer;
	private final int DELAY = 33; //Milliseconds of delay for 30 fps
	private JTextField txtSize;
	private JTextField txtSpeed;
	private JCheckBox chkGroup;
	private JCheckBox chkPause;
	
	public BubblePanel() {
		
		bubbleList = new ArrayList<Bubble>();
		
		addMouseListener( new BubbleListener() );
		addMouseMotionListener( new BubbleListener() );
		addMouseWheelListener( new BubbleListener() );
		
		timer = new Timer(DELAY, new BubbleListener());
		
		setBackground(Color.black);
		setPreferredSize(new Dimension(720,400));
		
		JPanel panel = new JPanel();
		add(panel);
		
		JLabel lblNewLabel = new JLabel("Dot Size: ");
		panel.add(lblNewLabel);
		
		txtSize = new JTextField();
		txtSize.setHorizontalAlignment(SwingConstants.CENTER);
		txtSize.setText("30");
		panel.add(txtSize);
		txtSize.setColumns(3);
		
		JLabel lblAnimationSpeedfps = new JLabel("Animation Speed (fps): ");
		panel.add(lblAnimationSpeedfps);
		
		txtSpeed = new JTextField();
		txtSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		txtSpeed.setText("30");
		panel.add(txtSpeed);
		txtSpeed.setColumns(2);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// get the bubble size
				int newSize = Integer.parseInt(txtSize.getText());
				// get the animation speed
				int newSpeed = Integer.parseInt(txtSpeed.getText());
				// set the bubble size
				size = newSize;
				// set the animation speed
				timer.setDelay(1000/newSpeed);
				repaint();
			}
		});
		panel.add(btnUpdate);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bubbleList = new ArrayList<Bubble>();
			}
		});
		
		chkGroup = new JCheckBox("Group Bubbles"); 
		panel.add(chkGroup);
		
		chkPause = new JCheckBox("Pause");
		chkPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chkPause.isSelected())
					timer.stop();
				else
					timer.start();	
			}
		});

		panel.add(chkPause);
		panel.add(btnClear);
		
		timer.start();
		
	}
	
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		
		// draw bubbles from bubbleList
		for (Bubble bubble:bubbleList){
			page.setColor(bubble.color);
			page.fillOval(bubble.x - bubble.size/2,
					bubble.y - bubble.size/2, bubble.size, bubble.size);
		}
		
		// write the number of bubbles onscreen
		page.setColor(Color.GREEN);
		page.drawString("Count: " + bubbleList.size(), 5, 30);
	}
	
	private class BubbleListener implements MouseListener,
											MouseMotionListener,
											MouseWheelListener,
											ActionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// add to the bubbleList the mouse location
			//timer.stop();
			bubbleList.add(new Bubble(e.getX(),e.getY(),size));
			repaint();
			
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//timer.start();
			
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// add to the bubbleList the mouse location
			//timer.stop();
			bubbleList.add(new Bubble(arg0.getX(),arg0.getY(),size));
			// check if Group checkbox is selected
			if (chkGroup.isSelected()) {
				// set the xspeed and yspeed of bubble to the previous bubble speed
				bubbleList.get(bubbleList.size() - 1).xspeed = bubbleList.get(bubbleList.size() - 2).xspeed;
				bubbleList.get(bubbleList.size() - 1).yspeed = bubbleList.get(bubbleList.size() - 2).yspeed;
			}
			repaint();
			//timer.start();
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent arg0) {
			// change the bubble size
			size -= arg0.getWheelRotation();
			
			txtSize.setText("" + size);			
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// update the location of each bubble for the animation
			for (Bubble bubble:bubbleList) {
				bubble.update();
			}
			// repaint the screen
			repaint();
		}
		
	}
	
	private class Bubble {
		/** Each bubble needs an x,y location and size */
		public int x;
		public int y;
		public int size;
		public Color color;
		public int xspeed;
		public int yspeed;
		private final int MAX_SPEED = 5;
		
		//Constructor
		public Bubble(int newX, int newY, int newSize){
			x = newX;
			y = newY;
			size = newSize;
			color = new Color( (float)Math.random(), 
					(float)Math.random(), 
					(float)Math.random(),
					(float)Math.random() );
			xspeed = (int)(Math.random()* MAX_SPEED * 2 - MAX_SPEED);
			yspeed = (int)(Math.random()* MAX_SPEED * 2 - MAX_SPEED);
		}

		public void update() {
			if (xspeed == 0 && yspeed == 0) {
				xspeed = 3;
				yspeed = 3;
			}
			
			x += xspeed;
			y += yspeed;
			
			//collision detection for edges of panel
			if (x <= size/2 || (x + size/2) >= getWidth() )
				xspeed = xspeed * -1;
			if (y <= size/2 || (y + size/2) >= getHeight() )
				yspeed = yspeed * -1;
			
		}
	}

}
