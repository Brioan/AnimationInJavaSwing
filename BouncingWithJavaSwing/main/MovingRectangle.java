/*
 *  ===============================================================================
 *  Brian Lin
 *  MovingRectangle.java : The class of all rectangles.
 *  ===============================================================================
 */
import java.awt.*;

public class MovingRectangle extends MovingShape {

	
	/** constructors for a rectangle
	 */
	public MovingRectangle() {
		super();
	}

	public MovingRectangle(int x, int y, int mw, int mh, Color border, Color fill, int pathType, int w, int h) {
		super(x, y, mw,  mh,  border,  fill,  pathType, w, h);
	}

	/** draws the rectangle
	 */
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(fillColor);
		g2d.fillRect(x, y, width, height);		
		g2d.setPaint(borderColor);
		g2d.drawRect(x, y, width, height);
		drawHandles(g);
	}

	/** Checks if in shape
	 */
	public boolean contains(Point point) {
		return (x <= point.x && point.x <= (x + width + 1)	&& y <= point.y && point.y <= (y + height + 1));
	}
	
	public double area(){return width*height;}
}