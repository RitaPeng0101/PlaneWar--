import java.awt.*;
import java.io.*;
import java.util.*;

public class Accessory implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7702171553492997947L;
	int aX, aY;
	int aWidth,aHeight;
	int speed=1;
	int typeint;
	int life=50;
	int Xoffset=0;
	int intervel;
	int count=0;
	int aimage;
	static Image aimage1,aimage2,aimage3; 
	public Accessory(){
		  aX=getRandomIntNum(50,950);
		  aY=30;
		  aWidth=32;
		  aHeight=32;
		  typeint=getRandomIntNum(0,3);
		  if (typeint==1) aimage=1;
		  if (typeint==2) aimage=2;
		  if (typeint==3) aimage=3;
		  
	}
	public boolean hit(Bullet b){
		if ((aX<b.bX) && (b.bX<aX+aWidth) && (aY<b.bY) && (b.bY<aY+aHeight)){
			life-=20;
			return true;
		} else return false;
		
	}
	public boolean hit(Airplane p){
		if ((aX-+aWidth<p.pX) && (p.pX<aX+aWidth) && (aY<p.pY) && (p.pY<aY+aHeight)){
			life-=60;
			p.life-=60;
			return true;
		} else return false;
		
	}	
	public int getRandomIntNum(int a, int b)
	{
	  Random random = new Random();
	  int c = random.nextInt();
	  if(c<0)
	  {
	    c = -c ;
	  }
	  int d = ((c %(b-a)) + a + 1);
	return d;

	}
}
