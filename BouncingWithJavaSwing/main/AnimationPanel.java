/*
 *  ======================================================================
 *  Brian Lin
 *  AnimationPanel.java : 
 *  Moves shapes around on the screen according to different paths.
 *  The main drawing area where shapes are added and manipulated.
 *  Contains a popup menu to clear all shapes.
 *  ======================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class AnimationPanel extends JComponent implements Runnable {
    private Thread animationThread = null;	// the thread for animation
    private ArrayList<MovingShape> shapes;	// the ArrayList which stores a list of shapes
    private int currentXPos=10, currentYPos=20,
        currentShapeType=0,		// the current shape type
        currentPath=0,			// the current path type
        currentWidth = 30,		// the current width type
        currentHeight = 50;		// the current height type
    private MovingShape s;		// MovingShape object
    private Color currentBorderColor = Color.black;  // the current border colour of a shape
    private Color currentFillColor = Color.white; 	 // the current fill colour of a shape
    private int delay = 30;		// the current animation speed
    JPopupMenu popup;			// popup menu

     /** Constructor of the AnimationPanel
        */
    public AnimationPanel() {
        shapes = new ArrayList<MovingShape>(); //create the ArrayList to store shapes
        popup = new JPopupMenu(); //create the popup menu
        makePopupMenu();
        // add the mouse event to handle popup menu
        addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            public void mouseClicked( MouseEvent e ) {
                if (animationThread != null)   // if the animation has started, then
                    for (MovingShape currentShape: shapes)
                        if ( currentShape.contains( e.getPoint()) )  // if the mousepoint is within a shape, then set the shape to be selected/deselected
                            currentShape.setSelected( ! currentShape.isSelected() );
            }
        });
    }

    /** create a new shape
     */
    protected void createNewShape() {
        // get the margin of the frame
        Insets insets = getInsets();
        int marginWidth = getWidth() - insets.left - insets.right;
        int marginHeight = getHeight() - insets.top - insets.bottom;
        // create a new shape dependent on all current properties and the mouse position
        switch (currentShapeType) {
            case 0: {
            	//create a new rectangle 
				shapes.add(new MovingRectangle(currentXPos, currentYPos, marginWidth, marginHeight, currentBorderColor, currentFillColor, currentPath, currentWidth, currentHeight));
                break;
            }
            case 1: {
            	//create a new square
				shapes.add(new MovingSquare(currentXPos, currentYPos, marginWidth, marginHeight, currentBorderColor, currentFillColor, currentPath, currentWidth, currentWidth));
                break;
            }
        }
    }
    
	/** get the current width/height
	 * @return currentWidth/currentHeight
	 */
	public int getCurrentWidth() {return currentWidth;}

	public int getCurrentHeight() {return currentHeight;}
	
	/** set current width/height for all selected shapes
	 * @param w/h the new width/height
	 */
	public void setCurrentWidth(int w) {
		currentWidth = w;
		for (MovingShape s: shapes) {
			if (s.isSelected()) {s.setWidth(currentWidth);}}
	}
	public void setCurrentHeight(int h) {
		currentHeight = h;
		for (MovingShape s: shapes) {
			if (s.isSelected()) {s.setHeight(currentHeight);}}
	}
	

    /** set the current shape type
     * @param s    the new shape type
     */
    public void setCurrentShapeType(int s) {
        currentShapeType = s;
    }

    /** reset the margin size of all shapes in the ArrayList
     */
    public void resetMarginSize() {
        Insets insets = getInsets();
        int marginWidth = getWidth() - insets.left - insets.right;
        int marginHeight = getHeight() - insets.top - insets.bottom ;
        //complete this
		for (MovingShape s: shapes) {s.setMarginSize(marginWidth, marginHeight);}
    }

    /** set the current path type and the path type for all currently selected shapes
     * @param t    the new path type
     */
    public void setCurrentPathType(int t) {
        currentPath = t;
		for (MovingShape s: shapes) {
			if (s.isSelected()) {s.setPath(currentPath);}}
    }

    /** set the current x and the x for all currently selected shapes
     * @param s    the new x value
     */
    public void setCurrentXPos(int x) {
        currentXPos = x;	
		for (MovingShape s: shapes) {
			if (s.isSelected()) {s.setX(currentXPos);}}
    }

    /** set the current y and the y for all currently selected shapes
     * @param y    the new y value
     */
    public void setCurrentYPos(int y) {
        currentYPos = y;
		for (MovingShape s: shapes) {if (s.isSelected()) {s.setY(currentYPos);}}
    }

    /** set the current border colour and the border colour for all currently selected shapes
     * @param bc    the new border colour value
     */
    public void setCurrentBorderColor(Color bc) {
        currentBorderColor = bc;
		for (MovingShape s: shapes) {if (s.isSelected()) {s.setBorderColor(currentBorderColor);}}
    }

    /** set the current fill colour and the border colour for all currently selected shapes
     * @param bc    the new fill colour value
     */
    public void setCurrentFillColor(Color fc) {
        currentFillColor = fc;
		for (MovingShape s: shapes) {
			if (s.isSelected()) {s.setFillColor(currentFillColor);}}
    }

    /** get the current x position in the top left corner
     * @return x - the x value
     */
    public int getCurrentXPos() {
        return currentXPos;
    }

    /** get the current y position in the top left corner
     * @return y - the y value
     */
    public int getCurrentYPos() {
        return currentYPos;
    }

    /** get the current fill colour
     * @return currentFillColor - the fill colour value
     */
    public Color getCurrentFillColor() {
        return currentFillColor;
    }

    /** get the current border colour
     * @return currentBorderColor - the border colour value
     */
    public Color getCurrentBorderColor() {
        return currentBorderColor;
    }

   // you don't need to make any changes after this line ______________

    /** remove all shapes from the ArrayList
     */
    public void clearAllShapes() {
        shapes.clear();
    }

    /**    update the painting area
     * @param g    the graphics control
     */
    public void update(Graphics g){
        paint(g);
    }

    /**    move and paint all shapes within the animation area
     * @param g    the Graphics control
     */
    public void paintComponent(Graphics g) {
        for (MovingShape currentShape: shapes) {
            currentShape.move();
            currentShape.draw(g);
        }
    }

    /** create the popup menu for our animation program
     */
    protected void makePopupMenu() {
        JMenuItem menuItem;
     // clear all
        menuItem = new JMenuItem("Clear All");
        menuItem.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearAllShapes();
            }
        });
        popup.add(menuItem);
     }

    /** change the speed of the animation
     * @param newValue     the speed of the animation in ms
     */
    public void adjustSpeed(int newValue) {
        if (animationThread != null) {
            stop();
            delay = newValue;
            start();
        }
    }

    /**    When the "start" button is pressed, start the thread
     */
    public void start() {
        animationThread = new Thread(this);
        animationThread.start();
    }

    /**    When the "stop" button is pressed, stop the thread
     */
    public void stop() {
        if (animationThread != null) {
            animationThread = null;
        }
    }

    /** run the animation
     */
    public void run() {
        Thread myThread = Thread.currentThread();
        while(animationThread==myThread) {
            repaint();
            pause(delay);
        }
    }

    /** Sleep for the specified amount of time
     */
    private void pause(int milliseconds) {
        try {
            Thread.sleep((long)milliseconds);
        } catch(InterruptedException ie) {}
    }

    /** Calculate total area of all shapes
     */ 
	public void calculateArea() {
		float totalArea = 0;
		for (MovingShape s: shapes) {
			totalArea += s.area();
		}
		System.out.println("Area: " + totalArea);
		
	}
}
