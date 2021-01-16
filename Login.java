import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Logins.ImpleUserService;
import Logins.MyLoginPanel;
import Logins.Tookit;
import Logins.User;
import Logins.UserService;

public class Login extends JFrame implements MouseListener {
	private static final long serialVersionUID = 1L;
	private JTextField nickNameField = null;
	private JPasswordField passwordField = null ;
    private JPanel jp;
    private User login = null;
    
    public static void main(String[] args) {
    	
		new Login().setVisible(true);
	}
    
    public Login() {
		UIManager.put("TextField.font", Tookit.getFont1()) ;
		UIManager.put("Label.font", Tookit.getFont1()) ;
		UIManager.put("Button.font", Tookit.getFont1()) ;
		this.init();
	}
    
    private void init() {
		JPanel buttom = new JPanel(new BorderLayout()) ;
		buttom.add(this.loginPanel()) ;
		this.add(buttom) ;
		this.setSize(340, 320) ;
		this.setLocationRelativeTo(null) ;
		this.setResizable(false) ;
		this.setIconImage(new ImageIcon("Airplanes/airplane1.gif").getImage()) ;  // 注意位置1
		this.setTitle("飞机大战") ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    public JPanel loginPanel(){
		JPanel jPanel = new JPanel(new BorderLayout()) ;
		//上面
		JLabel jLogin = new JLabel(
				new ImageIcon("image/login.png"), JLabel.CENTER);
		jPanel.add(jLogin, BorderLayout.NORTH) ;//加一个jlable
		//下面
		MyLoginPanel myPanel = new MyLoginPanel();
		myPanel.setLayout(null);
		final JLabel nickName = new JLabel("username", JLabel.CENTER);
		nickName.setBounds(60, 60, 65, 15);
		nickName.setFont(Tookit.getFont1()) ;
		myPanel.add(nickName) ;
		
		jp = new JPanel() ;
		jp.setOpaque(false) ;
		jp.setBounds(130, 100, 140, 200);
		myPanel.add(jp);
		nickNameField = new JTextField(12) ;
		nickNameField.setBounds(130, 60, 140, 21) ;
		myPanel.add(nickNameField);
		
		JLabel password = new JLabel("password", JLabel.CENTER) ;
		password.setFont(Tookit.getFont1()) ;
		password.setBounds(60, 120, 65, 15) ;
		passwordField = new JPasswordField(12) ;
		passwordField.setBounds(130, 120, 140, 21) ;
		myPanel.add(password) ;
		myPanel.add(passwordField);

		JButton loginButton = new JButton("login");
		loginButton.setBounds(33, 160, 81, 30) ;
		myPanel.add(loginButton) ;
		loginButton.addActionListener(new ActionListener() {
			
			// 登录逻辑处理
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nickName = nickNameField.getText().trim();
				
				String password = String.valueOf(passwordField.getPassword()).trim();
				
				if (nickName.equals("")) {
					JOptionPane.showMessageDialog(Login.this, "please enter username");
				    return;
				}
				if (password.equals("")) {
					JOptionPane.showMessageDialog(Login.this, "please enter password");
				    return;
				}
				
				// 进行登录验证逻辑判断
				ImpleUserService service = new UserService();
				login = service.selectUser(nickName, password);
				if (login != null) { //验证成功
					JOptionPane.showMessageDialog(Login.this, "welcome" + login.getNickName() + "playing game");
					//
					
					
					
//					new ShootGameView(login).setVisible(true);
					
					Battlefield f=new Battlefield();
					f.start(f);
					
					
					
					Login.this.dispose();  // 关闭当前窗口
				} else {  
					JOptionPane.showMessageDialog(Login.this, "zhanghaohuomimashurucuowu") ;
				    return;
				}
				
			}
		});
		loginButton.setPreferredSize(new Dimension(80, 30));
		loginButton.setBackground(new Color(0x71B8EC));
		
		JButton regist = new JButton("register");
		regist.setBounds(125, 160, 81, 30);
		myPanel.add(regist);
		regist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new RegistView().setVisible(true);
				Login.this.dispose();  
			}
		});
		regist.setPreferredSize(new Dimension(80, 30));
		regist.setBackground(new Color(46, 116, 188));
		
		JButton updatePass = new JButton("forget password?");
		updatePass.setBounds(217, 160, 90, 30);
		myPanel.add(updatePass);
		updatePass.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new UpdatePassView().setVisible(true);
				Login.this.dispose();  
			}
		});
		updatePass.setPreferredSize(new Dimension(80, 30));
		updatePass.setBackground(new Color(90, 177, 234));
		
		JButton scores = new JButton("PaiHangBang");
		scores.setBounds(110, 200, 120, 30) ;
		myPanel.add(scores);
		scores.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ScoreListView(login).setVisible(true);
				Login.this.dispose();  
			}
		});
		scores.setPreferredSize(new Dimension(80, 30));
		scores.setBackground(new Color(127, 197, 245));
		
		jPanel.add(myPanel) ;
		return jPanel;
	}

    @Override
	public void mouseClicked(MouseEvent e) {
		nickNameField.setText(((JLabel)e.getSource()).getText()) ;
		
		jp.removeAll() ;
		this.remove(jp) ;
		this.repaint() ;
		this.validate() ;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel jLabel = (JLabel)e.getSource() ;
		jLabel.setForeground(Tookit.getColor()) ;
		jLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)) ;
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel jLabel = (JLabel)e.getSource() ;
		jLabel.setForeground(Color.black) ;
		jLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)) ;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
