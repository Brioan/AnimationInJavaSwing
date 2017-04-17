/*
 *  ===============================================================================
 *  Brian Lin
 *  MovingSquare.java : The class of all squares.
 *  ===============================================================================
 */
import java.awt.*;

public class MovingSquare extends MovingShape {

	
	/** constructors for a rectangle
	 */
	public MovingSquare() {
		super();
	}

	public MovingSquare(int x, int y, int mw, int mh, Color border, Color fill, int pathType, int w, int h) {
		super(x, y, mw,  mh,  border,  fill,  pathType, w, h);
	}

	/** draws the square
	 */
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(fillColor);
		g2d.fillRect(x, y, width, width);		
		g2d.setPaint(borderColor);
		g2d.drawRect(x, y, width, width);
		drawHandles(g);
		
		
	}

	/** Checks if in shape
	 */
	public boolean contains(Point point) {
		return (x <= point.x && point.x <= (x + width + 1)	&& y <= point.y && point.y <= (y + width + 1));
	}
	

	
	public double area(){return width*width;}
}