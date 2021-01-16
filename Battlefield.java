import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;


import java.io.*;
import java.applet.*;

class Flag {
	 int f1=0,f2=0;
	 public Flag(){ }
	 public synchronized void putf1begin() {
	    while (f1==1)  try{ System.out.println("putf1begin wait"); wait(5);  } catch(Exception e){}
	 }
	 public synchronized void putf1end() { 
	    f1=1;	
	    notifyAll();  
	 }
	 public synchronized void getf1begin() {
		 while (f1==0)  try{ System.out.println("getf1begin wait"); wait(5);  } catch(Exception e){}
     }
	 public synchronized void getf1end() { 
		    f1=0;
		    notifyAll();
		}
	 
	 public synchronized void putf2begin() {
		   while (f2==1)  try{ System.out.println("putf2begin wait"); wait(5);  } catch(Exception e){}
		    }
	 
	 public synchronized void putf2end() {	    
		    f2=1;	
		    notifyAll();  
		 }
	 public synchronized void getf2begin() {
		   while (f2==0)  try{ System.out.println("getf2begin wait"); wait(5);  } catch(Exception e){}
		    }
	 
	 public synchronized void getf2end() {	    
		    f2=0;	
		    notifyAll();  
		 }

}
public class Battlefield  extends Frame{
	/**
	 *
	 */
	private static final long serialVersionUID = 7218330541696287662L;
	Image OffScreen1, OffScreen2, O2;
	Graphics2D     drawOffScreen1,drawOffScreen2,g;
    Image        myplane,eplane1,eplane2,bullet,explode,backgroud,a1,a2,a3,gameoverimage,winimage, trophy1image, trophy2image;
    int key;
    Airplane Controlplane;
   	ArrayList<Bullet> bulletsList;
   	ArrayList<Airplane> planeList;
   	ArrayList<Explode> explodeList;
	ArrayList<Accessory> accessoryList;
	ArrayList<Missile> missileList;
    TextField t1,t2,t3,t4 ;     //文本框分别是子弹余量、生命值、油量
    Panel p1,p2;
    Button start,save,load;
    Timer timer,timer2,timer3, timer4, timer5;
    Drawer	d1;
    Displayer d2;
    Backgroudmusic m1;
    Scenemusic m2;
   	int delay=10000;   //每隔10秒补充给养
   	float backy=638;
	boolean fire=false;
	boolean missile = false;
   	boolean goon=true;
   	int gameover=0;
	boolean hasAccessory=false;
	boolean addplane=false;
	//成就数组
	ArrayList<Trophy> trophyList;
	int shutdownCount = 0;
	int totalTime = 0;
	int difficulty = 0;

	Flag flag;
	public Battlefield (int difficulty)
    { 	
		this.difficulty = difficulty;
		OffScreen1     =  new BufferedImage(1000,900,BufferedImage.TYPE_INT_RGB);
		drawOffScreen1 = (Graphics2D)OffScreen1.getGraphics();
		OffScreen2     =  new BufferedImage(1000,900,BufferedImage.TYPE_INT_RGB);
		drawOffScreen2 = (Graphics2D)OffScreen2.getGraphics();
        flag=new Flag();
		myplane = getToolkit().getImage("Airplanes/airplane3.gif"); 
		eplane1 = getToolkit().getImage("Airplanes/airplane4.gif"); 
		eplane2 = getToolkit().getImage("Airplanes/airplane4-1.gif"); 
		a1=getToolkit().getImage("accessory/lives.gif"); 
		a2=getToolkit().getImage("accessory/box1.gif"); 
		a3=getToolkit().getImage("accessory/oil.gif"); 
		Accessory.aimage1=a1;
		Accessory.aimage2=a2;
		Accessory.aimage3=a3;
		Airplane.eplane1=eplane1;
		Airplane.eplane2=eplane2;
		bullet=getToolkit().getImage("Bullets/Bullet2.gif"); 
		explode=getToolkit().getImage("Bullets/explode.gif");
		backgroud=getToolkit().getImage("Backgrounds/sea2.JPG");
		gameoverimage=getToolkit().getImage("accessory/gameover.gif");
		winimage=getToolkit().getImage("accessory/win.gif");
		trophy1image = getToolkit().getImage("trophies/trophy1.gif");
		trophy2image = getToolkit().getImage("trophies/trophy2.gif");
		
       	planeList = new ArrayList<Airplane>(); 
       	bulletsList = new ArrayList<Bullet>(); 
       	explodeList = new ArrayList<Explode>(); 
		accessoryList = new ArrayList<Accessory>(); 
		missileList = new ArrayList<Missile>();
		//新建成就数组
		trophyList = new ArrayList<Trophy>();
    }
	public void gameperpare(){
        Controlplane=new Airplane(500,750,80,66);
        Controlplane.speed=50;  //调整了飞机的移动速度
        p2.addKeyListener(new MyKeyListener());
        m2=new Scenemusic();   //m2是音乐效果
        
        trophyList.add(new Trophy("屠戮者"));
        trophyList.add(new Trophy("闪电歼敌机"));
	}
	
	//开新的一局游戏
	public void gamebegin(){
		TimerTask task=new TimerTask(){
			 public void run(){
				 hasAccessory=true;       //task用来定期补充给养
			     m2.beepclip.loop();
			 }	
			};
		timer = new Timer();
		timer.schedule(task,0,delay);

	    TimerTask task2=new TimerTask(){       //task2是用来定时减少战斗机的油量的
			public void run(){
			    Controlplane.oil-=5 ;    //定时，油量减5       
		        t3.setText(Controlplane.oil+""); 	 //t3文本框是用来显示油量的
			}	
		};
		
		timer2 = new Timer();
		timer2.schedule(task2,3000,3000);  //间隔是3秒

	    TimerTask task3=new TimerTask(){
			public void run(){
                addplane=true;          //task3是用来定时补充敌机的
            }	
		};
		timer3 = new Timer();
		timer3.schedule(task3,5000,10000);    //间隔为40秒，我改成20
		/**
		 * 判断奖杯一是否获得
		 */
		TimerTask task4 = new TimerTask() {
			public void run() {
				if(shutdownCount >= 5) {     //成就获得的条件
					if(trophyList.get(0).hasTrophy() == false) {
						
						trophyList.get(0).showTrophy();     //展示获得成就
						
						trophyList.get(0).setTrophyTrue();  //设置成就为已获得
					}
				}
			}
		};
		timer4 = new Timer();
		timer4.schedule(task4, 0, 10);
			
		TimerTask task5 = new TimerTask() {
			public void run() {
				totalTime += 1;
			}
		};
		timer5 = new Timer();
		timer5.schedule(task5, 1000, 1000);
		
		//设定我方战斗机的初始值
		Controlplane.pX=500;        
		Controlplane.pY=500;   			//学长给的是750，但是太低了在我的电脑上看不见;
		Controlplane.life=200;			//初始生命值改成200
		Controlplane.bulletnum=100;     //子弹数量
		Controlplane.oil=100;   		//初始油量


        g=(Graphics2D)this.p2.getGraphics(); 
       	planeList.clear(); 
       	bulletsList.clear(); 
       	explodeList.clear(); 
		   accessoryList.clear(); 
		   missileList.clear();
        for (int i=1;i<=8;i++){
        	Airplane p1=new Airplane(90*i,50,78,68);
        	planeList.add(p1);
        	p1.intervel=p1.getRandomIntNum(0,6);
            p1.eplane=1;

        }

        p2.requestFocus();
        // m1=new Backgroudmusic();
        // m1.run();
        //d1=new Drawer();
    	d2=new Displayer();
    	//d1.start();
        d2.start();
	}
	
	public void gameContrl(Graphics2D drawOffScreen){
 
    	  drawOffScreen.fillRect(0, 0, 1000, 800);
		  drawOffScreen.drawImage(backgroud,0,0,1000,900,0,(int)backy,360,320+(int)backy,null);
		  backy-=.2;
		
		  if (backy<0) backy=638; 

		   drawOffScreen.drawImage(backgroud,0,0,1000,900,null);  
    	   if (addplane){      //每隔一段时间会置为true
		       if (planeList.size() < 1) planeList.add(new Airplane());    //如果敌机数量小于8，则补充敌机
		   addplane=false;
           }
    	   Iterator<Airplane> pnums = planeList.iterator();
    	   while(pnums.hasNext()) { 
    		     Airplane p = (Airplane)pnums.next(); 
                 p.fly();
                 if (p.eplane==1) drawOffScreen.drawImage(Airplane.eplane1, p.pX, p.pY, null);
                 if (p.eplane==2) drawOffScreen.drawImage(Airplane.eplane2, p.pX, p.pY, null);
                 
                  
     		     if ((p.getRandomIntNum(0, 300))==2)  {
    		    	 Bullet b2=new Bullet(p.pX+p.pWidth/2-3,p.pY+p.pHeight,13,13);
    		    	 b2.speed=-3;
    		    	 bulletsList.add(b2);     
     		     }
    		      
    		      Iterator<Bullet> bnums = bulletsList.iterator();
    	    	   while(bnums.hasNext()) { 
    	  		      Bullet b = (Bullet)bnums.next(); 
    	  		      if (p.hit(b)) {
    	 		    	  b=null;
    	 		    	  bnums.remove();
    	 		    	  m2.hitclip.play();
    	 		          };  		      
    	  	       //�ж��Ƿ�ײ�����Ʒɻ�
    	 		     if (p.hit(Controlplane)) 
 	 		    	  m2.explodeclip.play();
				   } 
				   
				   Iterator<Missile> mi = missileList.iterator();
    	    	   while(mi.hasNext()) { 
    	  		      Missile m = mi.next(); 
    	  		      if (p.hit(m) || m.isDeath()) {
    	 		    	  m=null;
    	 		    	  mi.remove();
    	 		    	  m2.hitclip.play();
    	 		    	};
    	  	       }

    	    	   //�ж��Ƿ�ײ������
     		      Iterator<Accessory> anums =accessoryList.iterator();
      	    	   while(anums.hasNext()) { 
      	    	    	Accessory a = (Accessory)anums.next(); 
      	  		        if (p.hit(a)){
      	 		    	    a=null;
    	 		    	    anums.remove();	
         		    	    m2.beepclip.stop();
         		    	    m2.eatclip.play();
       	  		        };
   	    	        } 
    	    	   if (p.life<0) {
    		    	   explodeList.add(new Explode(p.pX,p.pY));
    		    	   p=null;
    		    	   pnums.remove();
  	 		    	   m2.explodeclip.play();
  	 		    	  
  	 		    	   shutdownCount++; //如果击落一台敌机则记录击落数
  	 		    	   
    		       };  		    
    	   } 
    	   if (hasAccessory){
    		   accessoryList.add(new Accessory());
    		   hasAccessory=false; 

            }
        	   Iterator<Accessory> anums = accessoryList.iterator();
        	   while(anums.hasNext()) { 
        		   Accessory a = (Accessory)anums.next(); 
     		   if (a.aimage==1)   drawOffScreen.drawImage(a1,a.aX,a.aY,null);  
     		   if (a.aimage==2)   drawOffScreen.drawImage(a2,a.aX,a.aY,null);  
     		   if (a.aimage==3)   drawOffScreen.drawImage(a3,a.aX,a.aY,null);  
     		      a.aY+=a.speed; 
     		      if (a.aY>900){
     		    	  a=null;
     		    	  anums.remove();
     		    	  m2.beepclip.stop();
     		    	  continue;
        			  //t2.setText(Controlplane.life+"");    
      		        };  
     		      if (Controlplane.hit(a)){
     		    	  a=null;
     		    	  anums.remove();
     		    	  m2.beepclip.stop();
     		    	  m2.eatclip.play();
     		    	  t2.setText(Controlplane.life+"");  
     		    	  continue;
        			  //t2.setText(Controlplane.life+"");    
      		        };  
      		      //�ж��Ƿ񱻻���?
      		      Iterator<Bullet> bnums = bulletsList.iterator();
      	    	   while(bnums.hasNext()) { 
      	  		      Bullet b = bnums.next(); 
      	  		      if (a.hit(b)) {
      	 		    	  b=null;
      	 		    	  bnums.remove();
      	 		    	 m2.hitclip.play();
      	  		      }; 
      	    	   }
    	    	   if (a.life<0) {
     		    	  explodeList.add(new Explode(a.aX,a.aY));
     		    	  a=null;
     		    	  m2.beepclip.stop();
     		    	  anums.remove();
  	 		    	  m2.explodeclip.play();
     		          };  		 
     	        } 
    	   if (fire){		 //发射子弹
    	   	   bulletsList.add(new Bullet(Controlplane.pX+Controlplane.pWidth/2-3,Controlplane.pY,13,13));
    	   	   fire=false;
   
    	   	   t1.setText(Controlplane.bulletnum+"");  
		   }
		   if(missile){   //发射导弹
			   Missile mis = new Missile(Controlplane.pX+Controlplane.pWidth/2-3,Controlplane.pY,13,13,planeList);
			   missileList.add(mis);
			   missile = false;
		   }
    	   Iterator<Bullet> bnums = bulletsList.iterator();
    	   while(bnums.hasNext()) { 
 		      Bullet b = (Bullet)bnums.next(); 
 		      drawOffScreen.drawImage(bullet,b.bX,b.bY,null);  
 		      b.bY-=b.speed; 
  		      if ((b.bY<0) || (b.bY>900)){
 		    	  b=null;
 		    	  bnums.remove();
 		    	  continue;
  		      }
 		      if ((Controlplane.hit(b))){
 		    	  b=null;
 		    	  bnums.remove();
 		    	  m2.hitclip.play();
    			  t1.setText(Controlplane.bulletnum+"");    
 		    	  t2.setText(Controlplane.life+"");    
 		    	  t3.setText(Controlplane.oil+"");    
  		      };  	
			 } 
			
			for(Missile mis : missileList){
				mis.update();
				drawOffScreen.drawImage(bullet,mis.bX,mis.bY,null);
			}
    	    if (gameover==0) drawOffScreen.drawImage(myplane,Controlplane.pX,Controlplane.pY,null);
    	    if (gameover==-1) {
    	    	if(shutdownCount > 5) {
    	    		drawOffScreen.drawImage(trophy1image, 300, 300, null);
    	    	}
    	    	drawOffScreen.drawImage(gameoverimage,Controlplane.pX,Controlplane.pY,null);  
    	    }
    	    if (gameover==1) {
    	    	if(totalTime < 60) {
    	    		
    	    		drawOffScreen.drawImage(trophy2image, 100, 300, null);
    	    	}
    	    	if(shutdownCount > 5) {
    	    		drawOffScreen.drawImage(trophy1image, 300, 300, null);
    	    	}
    	    	drawOffScreen.drawImage(winimage,Controlplane.pX,Controlplane.pY,null);  
    	    	
    	    }

 		      if ((Controlplane.life<0) || (Controlplane.oil<0)) {     //战斗机失败条件
		    	  explodeList.add(new Explode(Controlplane.pX,Controlplane.pY));
		    	  gameover=-1;
		    	  Controlplane.life=0;
		    	  Controlplane.oil=0;
	 		      m2.explodeclip.play();
		          };
           if (planeList.size()==0) gameover=1;
//		          
	     if ((explodeList.size()==0) && (gameover!=0)) {
	         goon=false;
	     }
		   
		   Iterator<Explode> enums = explodeList.iterator();
 	   	   while(enums.hasNext()) { 
		      Explode e = (Explode)enums.next(); 
		      drawOffScreen.drawImage(explode,e.eX,e.eY,null);  
		      e.life--; 
	
		      if (e.life<0) {
		    	  e=null;
		    	  enums.remove();
		          };  		      
  	        }     
 	    	//g.drawImage(OffScreen1,0,0,this.p2); 
 	}
  class MyKeyListener implements KeyListener{
	 	   public void keyTyped(KeyEvent e)  { }
	 	   public void keyPressed(KeyEvent e)
	 	   {
	 		   key = e.getKeyCode();
	 		   if(key == KeyEvent.VK_RIGHT)    //获取键盘操作
	 	      {
	 			  Controlplane.pX+= Controlplane.speed;
	 	      }
	 	      else if(key == KeyEvent.VK_LEFT)
	 	      {
	 			  Controlplane.pX-= Controlplane.speed;
	 	      }
	 	      else if(key == KeyEvent.VK_UP)
	 	      {
	 			  Controlplane.pY-= Controlplane.speed;
	 	      }
	 	      else if(key == KeyEvent.VK_DOWN)
	 	      {
	 			  Controlplane.pY+= Controlplane.speed;
	 	      }
	 		   
	 		   //以下做了修改，改成：按空格键表示发射子弹，按回车键表示发射导弹
	 	      else if(key == KeyEvent.VK_SPACE)     //KeyEvent.VK_BACK_SPACE
	 	      {
	 	    	if  (Controlplane.bulletnum-->=0)  
	 	    	  fire=true;
 		    	  m2.gunshotclip.play();
			   }
			   else if(key == KeyEvent.VK_BACK_SPACE){
					missile = true;   
			   }
			   
			  
	 	   }
	 	   public void keyReleased(KeyEvent e)
	 	   { }	   
	    }

  public void showcomponent(){
	   MenuBar m_MenuBar = new MenuBar(); 
	   Menu menuFile = new Menu("Plane_Wars_Homework");     
	   m_MenuBar.add(menuFile);                 
	   MenuItem  f1 =  new MenuItem("f1");   
	   MenuItem  f2 = new MenuItem("f2");
	   menuFile.add(f1);
	   menuFile.add(f2);
       setMenuBar(m_MenuBar);  
     //
    	p1 = new Panel();
	    add(p1,"North");
	    p1.setLayout(new GridLayout(1,10));   //使用网格布局
	    
	    Label label1 = new Label("Bullet_num");
	    label1.setAlignment(java.awt.Label.CENTER);   //让标签文字居中
   	    p1.add(label1,0);   
	    t1 = new TextField(2);
	    p1.add(t1,1);
	    
	    Label label2 = new Label("Life");
	    label2.setAlignment(java.awt.Label.CENTER);
   	    p1.add(label2,2);    
	    t2 = new TextField(2);  //在awt中，baiTextField没有设置对齐方式的方法。除非改用JTextField
	    p1.add(t2,3);
	    
	    Label label3 = new Label("Oil");
	    label3.setAlignment(java.awt.Label.CENTER);
   	    p1.add(label3,4);
	    t3 = new TextField(2);
	    p1.add(t3,5);
        start=new Button("Start");	
        p1.add(start,6);
	    start.addActionListener(new Startaction());
        save=new Button("Save");	
        p1.add(save,7);
        save.addActionListener(new Saveaction());
        load=new Button("Load");	
        p1.add(load,8);
        load.addActionListener(new Loadaction());
        	    
   //
	  	p2 = new Panel();

   	    add(p2,"Center");
	/*  	Choice ColorChooser = new Choice();
        ColorChooser.add("Green");
	    ColorChooser.add("Red"); 
	    ColorChooser.add("Blue");
	    p.add(ColorChooser);
	    t1 = new TextField(3);
	    p.add(t1);
	    ColorChooser.addItemListener(new ItemListener(){
 			 public void itemStateChanged(ItemEvent e){
				 String s= e.getItem().toString();
				 t1.setText(s);}
	    });*/
	   }
//public static void main(String args[]){
//	Battlefield f = new Battlefield();
//        f.showcomponent();
//        f.setSize(1000, 900);
//        f.setVisible(true);
//        f.gameperpare();
//        f.gamebegin();    
//	}	
  
  
public static void start(Battlefield f) {
	f.showcomponent();
    f.setSize(1000, 900);
    f.setVisible(true);
    f.gameperpare();
    f.gamebegin(); 
}
  
  
class Drawer extends Thread {
	public void run() {
		 while (goon)
         {
         flag.putf1begin();
         gameContrl(drawOffScreen1);
         flag.putf1end();
        
         flag.putf2begin();
         gameContrl(drawOffScreen2);
         flag.putf2end();

	 }
	}
}
class Displayer extends Thread {
	@SuppressWarnings("deprecation")
	public void run() {
		int a = 0;
		 while (goon)
         {
			gameContrl(drawOffScreen1);
			gameContrl(drawOffScreen2);
	       //flag.getf1begin();
	       	 g.drawImage(OffScreen1,0,0,Battlefield.this.p2);
	       //flag.getf1end();
	       //flag.getf2begin();
	       	 g.drawImage(OffScreen2,0,0,Battlefield.this.p2);   
		   //flag.getf2end();
		   System.out.println(a++);
	 	}
		 System.out.println("Game Over");
		 timer.cancel();
		 timer=null;
		 timer2.cancel();
		 timer2=null;
		 m2.beepclip.stop();
		 m1.clip.stop();
		 m1=null;
		 start.setEnabled(true);
		 
	}
}
class Startaction implements ActionListener{
    public void actionPerformed(ActionEvent event) {                    
    	goon=true;
    	gameover=0;
    	start.setEnabled(false);
    	gamebegin();
    }   
}
class Saveaction implements ActionListener{
    public void actionPerformed(ActionEvent event) {                    
        d1.suspend();
        d2.suspend();
        ObjectOutputStream oos;
		try {
			File f=new File("save/save.dat");
			if (f.exists())  f.delete();

			oos = new ObjectOutputStream(new FileOutputStream("save/save.dat"));
			oos.writeObject(Controlplane);
			oos.writeObject(planeList);
			oos.writeObject(bulletsList);
			oos.writeObject(accessoryList);
			oos.writeObject(explodeList);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        d1.resume();
        d2.resume();
    }   
}
class Loadaction implements ActionListener{
    public void actionPerformed(ActionEvent event) {                    

        ObjectInputStream ios;

			try {
				ios = new ObjectInputStream(new FileInputStream("save/save.dat"));
				Controlplane=(Airplane)ios.readObject();
				planeList=(ArrayList)ios.readObject();
				bulletsList=(ArrayList)ios.readObject();
    			accessoryList=(ArrayList)ios.readObject();
	    		explodeList=(ArrayList)ios.readObject();
		    	ios.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TimerTask task=new TimerTask(){
				 public void run(){
					 hasAccessory=true; 
				     m2.beepclip.loop();
				 }	
				};
			timer = new Timer();
			timer.schedule(task,0,delay);

		     TimerTask task2=new TimerTask(){
				 public void run(){
							 Controlplane.oil-=5 ; 
					         t3.setText(Controlplane.oil+""); 	 
						 }	
						};
			timer2 = new Timer();
			timer2.schedule(task2,3000,3000);
		    TimerTask task3=new TimerTask(){
				 public void run(){
	                 addplane=true;
	                       }	
				};
			timer3 = new Timer();
			timer3.schedule(task3,2000,40000);
			goon=true;
	    	gameover=0;
			p2.requestFocus();

			//d1=new Drawer();
	    	d2=new Displayer();
	    	//d1.start();
	        d2.start();
	        //m1=new Backgroudmusic();
	        //m1.run();

    }   
}
class Backgroudmusic {
	AudioClip clip;
	public void run() {
	File backmusic=new File("music/Joyful Life.mid");
	try{
		clip=Applet.newAudioClip(backmusic.toURL());
		clip.loop();
	}catch (Exception e) {};
   }
 }
class Scenemusic {
	File gunshot,explode,beep,hit,eat;
	AudioClip gunshotclip,explodeclip,beepclip,hitclip,eatclip;
	public  Scenemusic(){
		super();
    		gunshot=new File("music/gunshot.wav");
    		explode=new File("music/explode.wav");
    		beep=new File("music/beep.wav");
    		hit=new File("music/hit.wav");
    		eat=new File("music/eat.wav");
		try{
			gunshotclip=Applet.newAudioClip(gunshot.toURL());
			explodeclip=Applet.newAudioClip(explode.toURL());
			beepclip=Applet.newAudioClip(beep.toURL());
			hitclip=Applet.newAudioClip(hit.toURL());
			eatclip=Applet.newAudioClip(eat.toURL());

		}catch (Exception e) {};
	}
/*	public void run() {
	  while (true) {
		  if (gunshot_voice>0) {gunshotclip.play();gunshot_voice--;};
		  if (explode_voice>0) {explodeclip.play();explode_voice--;};
		  if (accessory_voice>0) {beepclip.play(); accessory_voice--;};
	    }
	  }*/
  }
}
