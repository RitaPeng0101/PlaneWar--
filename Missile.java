import java.awt.*;
import java.io.*;
import java.util.ArrayList;
public class Missile implements Serializable {
	/**
	 *导弹类
	 */
	private static final long serialVersionUID = 7564578705984248647L;
	int bX, bY;
	int bWidth,bHeight;
	int speed=2;
	Airplane target;
	double real_x,real_y;
	int life = 0;
	transient Image bimage;
	public Missile(int x,int y,int w,int h,ArrayList<Airplane> plist){
		super();
		  bX=x;
		  bY=y;
		  real_x = x;
		  real_y = y;
		  bWidth=w;
		  bHeight=h;
		  int t = (int)(Math.random() * plist.size());
		  if(t==plist.size()) t--;
		  target = plist.get(t);
	}

	public void update(){
		life++;
		double x = target.pX;
		double y = target.pY;
		double dist = Math.sqrt((double)((x-real_x)*(x-real_x)+(y-real_y)*(y-real_y)));
		double dx = (x - bX) * (double) speed / dist;
		double dy = (y - bY) * (double) speed / dist;
		real_x += dx;
		real_y += dy;
		bX = (int)Math.round(real_x);
		bY = (int)Math.round(real_y);
	}

	public boolean isDeath(){
		return life>=500;
	}

	public void hit(){
		
	}
	public void moveToTop(){
		
	}
	public void moveToBottom(){
		
	}
	public void moveToleft(){
		
	}
	public void moveToRihgt(){
		
	}
}
