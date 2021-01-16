import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import Logins.MyLoginPanel;
import Logins.Tookit;

public class DifficultyChoose extends JFrame implements MouseListener {
	private static final long serialVersionUID = 1L;
	private JPanel jp;
	private int ChosenDifficulty = -1;
	
    public DifficultyChoose() {
		UIManager.put("TextField.font", Tookit.getFont1()) ;
		UIManager.put("Label.font", Tookit.getFont1()) ;
		UIManager.put("Button.font", Tookit.getFont1()) ;
		this.init();
	}
    
	private void init() {
		JPanel buttom = new JPanel(new BorderLayout()) ;
		buttom.add(this.ChoosePanel()) ;
		this.add(buttom) ;
		this.setSize(340, 320) ;
		this.setLocationRelativeTo(null) ;
		this.setResizable(false) ;
		this.setIconImage(new ImageIcon("Airplanes/airplane1.gif").getImage()) ;  // 注意位置1
		this.setTitle("选择难度") ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JPanel ChoosePanel(){
		JPanel jPanel = new JPanel(new BorderLayout()) ;
		//上面
		JLabel jChooseDifficulty = new JLabel(
				new ImageIcon("image/choosedifficulty.png"), JLabel.CENTER);
		jPanel.add(jChooseDifficulty, BorderLayout.NORTH) ;//加一个jlable
		//下面
		ChooseDifficultyPanel myPanel = new ChooseDifficultyPanel();
		myPanel.setLayout(null);
		
		jp = new JPanel() ;
		jp.setOpaque(false) ;
		jp.setBounds(130, 100, 140, 200);
		myPanel.add(jp);


		JButton EasyButton = new JButton("Easy");
		EasyButton.setBounds(100, 40, 140, 40) ;
		myPanel.add(EasyButton);
		EasyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Battlefield f=new Battlefield(-1);
				f.start(f);
				DifficultyChoose.this.dispose(); 
			}
		});
		
		JButton MediumButton = new JButton("Medium");
		MediumButton.setBounds(100, 100, 140, 40) ;
		myPanel.add(MediumButton);
		MediumButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Battlefield f=new Battlefield(0);
				f.start(f);
				DifficultyChoose.this.dispose(); 
			}
		});
		
		JButton HardButton = new JButton("Hard");
		HardButton.setBounds(100, 160, 140, 40) ;
		myPanel.add(HardButton);
		HardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Battlefield f=new Battlefield(1);
				f.start(f);
				DifficultyChoose.this.dispose(); 
			}
		});
		
		
		jPanel.add(myPanel) ;
		return jPanel;
	}
	
	public int getChoosnDifficulty() {
		return this.ChosenDifficulty;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
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
	
//	public static void main(String[] args) {
//			DifficultyChoose dc = new DifficultyChoose();
//			dc.setVisible(true);
//			int difficulty = dc.getChoosnDifficulty();
//			if (difficulty < 0) {
//				System.out.println("Easy");
//			}else if (difficulty == 0) {
//				System.out.println("Medium");
//			}else {
//				System.out.println("Hard");
//			}
//			
//		}

}

	
	
class ChooseDifficultyPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		//获得资源 
		ImageIcon iIcon = new ImageIcon("image/bg0104.jpg") ;
		//画一个背景图
		g.drawImage(iIcon.getImage(), 0, 0, null) ;
	}
}
