package socialdistancing;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/* 
	Building extends JPanel so that we can override the paint method. The paint method is necessary to use the simple
	drawing tools of the library! 
	Simulator implements an ActionListener which adds the method actionPerformed. This method is invoked by the 
	animation timer every timerValue(16ms).
*/
public class Building extends JPanel implements ActionListener{
	// serial suppresses warning
	private static final long serialVersionUID = 1L;
	
	//simulation control objects/values
	JFrame frame;
	Control control; //
	Timer timer; //Event control	
	int time = 0; //Track time as the simulation runs
	
	/* constructor will setup our main Graphic User Interface - a simple Frame! */
	public Building(Control ctl, String title) {
		// used for Control callback
		this.control = ctl;
		
		//Setup the GUI
		frame = new JFrame(title);
		frame.setSize(ctl.frameX,ctl.frameY); //set the size
		
		//add this so that hitting the x button will actually end the program
		//the program will continue to run behind the scenes and you might end up with 10+ of them
		//without realizing it
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//make it visible
		frame.setVisible(true);
		frame.add(this); //add this class (JPanel) to the JFrame
	}
	
	//activation of Simulator separated from Constructor 
	public void activate() {
		//Timer for animation
		//Argument 1: timerValue is a period in milliseconds to fire event
		//Argument 2:t any class that "implements ActionListener"
		timer = new Timer(control.timerValue, this); //timer constructor
		timer.restart(); //restart or start
		
		// frame becomes visible
		frame.setVisible(true);		
	}
	
	/* This invoked by Timer per period in milliseconds in timerValue  */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Triggers paint call through polymorphism
		repaint();	
	}

	/* paint method for drawing the simulation and animation */
	@Override
	public void paint(Graphics g) {
		
		//tracking total time manually with the time variable
		time += control.timerValue;
				
		//events
		super.paintComponent(g); // a necessary call to the parent paint method, required for proper screen refreshing
		paintWalls(g);
		paintPersons(g); // repaint all objects in simulation
	} 	
	
	public void paintPersons(Graphics g) {
		
		//find the Person in the Model!
		int index = 0;
		for(Person pDot1: control.model) {
			for(Person pDot2: control.model) {
				//for each unique pair invoke the collision detection code
				pDot1.collisionDetector(pDot2);
			}
			control.personToWallCollision(pDot1);
			pDot1.healthManager(); //manage health values of the Person
			pDot1.velocityManager(); //manage social distancing and/or roaming values of the Person
			
			//set the color of the for the person oval based on the health status of person object
			switch(pDot1.state) {
				case candidate:
					g.setColor(Color.LIGHT_GRAY);
					break;
				case infected:
					g.setColor(Color.red);
					break;
				case recovered:
					g.setColor(Color.green);
					break;
				case died:
					g.setColor(Color.black);
					
			}
			
			//draw the person oval in the simulation frame
			g.fillOval(pDot1.x, pDot1.y, control.OvalW, control.OvalH);
			
			// draw the person oval in meter/bar indicator
			g.fillOval((control.frameX-(int)(control.frameX*.02)), (int)(control.frameY-((control.numPeople-index)*control.OvalH)/1.67), control.OvalW, control.OvalH);
			index++;
			
		}
	}
	
	public void paintWalls(Graphics g) {
		for(Wall e: control.ob) { 
			g.drawImage(e.getImage(), e.getX(), e.getY(), control.view);
		}
		//sets text color
		g.setColor(Color.BLACK);
		g.setFont(new Font("Roboto", Font.BOLD, 20));
		
		g.drawString("Sprouts", 610, 50);
		g.drawString("Scripps Medical", 5, 50);
		g.drawString("Board and Brew", 5, 440);
		g.drawString("Mr. M's House", 590, 440);
	}
	
}