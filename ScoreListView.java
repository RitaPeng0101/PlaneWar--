import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Logins.FinalInterface;
import Logins.ImpleUserService;
import Logins.Tookit;
import Logins.User;
import Logins.UserService;

public class ScoreListView extends JFrame implements MouseListener{
	private static final long serialVersionUID = 1L;
	private List<User> list;
	private JPanel jp;
	private User login = null; // 用来判断是游戏结束跳转到该页还是登录前跳转到该页
	
	public ScoreListView(User login) {
		this.login = login;
		this.init();
	}

	private void init() {
		// TODO Auto-generated method stub
		JPanel buttom = new JPanel(new BorderLayout());
		buttom.add(this.ScoreListPanel());
		this.add(buttom);
		this.setSize(FinalInterface.WIDTH, FinalInterface.HEIGHT);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("image/hero0.png").getImage());
		this.setTitle("飞机大战");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 处理排行榜面板
	 * 
	 * @return
	 */
	public JPanel ScoreListPanel() {
		JPanel jPanel = new JPanel(new BorderLayout());

//		ScoreListPanel listPanel = new ScoreListPanel();
		Logins.ScoreListPanel  listPanel = new Logins.ScoreListPanel();
		listPanel.setLayout(null);

		JLabel title = new JLabel("本周飞机大战排行榜", JLabel.CENTER);
		title.setBounds(42, 120, 380, 40);
		title.setFont(new Font("黑体", Font.BOLD, 40));
		listPanel.add(title);

		// 加一个面板 可以存入多个jlabel
		jp = new JPanel();
		jp.setOpaque(false);
		jp.setBounds(0, 0, 500, 800);
		listPanel.add(jp);

		JLabel nick = new JLabel("玩    家", JLabel.CENTER);
		nick.setFont(Tookit.getFont5());
		nick.setBounds(80, 180, 80, 30);
		listPanel.add(nick);

		JLabel score = new JLabel("分    数", JLabel.CENTER);
		score.setFont(Tookit.getFont5());
		score.setBounds(304, 180, 80, 30);
		listPanel.add(score);

		ImpleUserService service = new UserService();
		list = service.selectAll();
		Collections.sort(list);
		
		// 删除一周之外的得分纪录
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCreateShoreRecordTime().getTime() < System
					.currentTimeMillis() - 7 * 24 * 3600000) {
                list.remove(i);
			}
		}
		
		// 遍历前十条记录
		for (int i = 0; i < list.size(); i++) {
			if (i < 10) {
				JLabel nickName = new JLabel(list.get(i).getNickName(),
							JLabel.CENTER);
				nickName.setFont(Tookit.getFont4());
				nickName.setBounds(80, 180 + (i + 1) * 40, 80, 30);
				listPanel.add(nickName);

				JLabel userScore = new JLabel(list.get(i).getScore()
							.toString(), JLabel.CENTER);
				userScore.setFont(Tookit.getFont4());
				userScore.setBounds(304, 180 + (i + 1) * 40, 80, 30);
				listPanel.add(userScore);
			}
		}

		/**
		 * 当玩家未登录进入该页时，该钮为返回按钮，点击返回登录页
		 * 当玩家登录后结束游戏进入该页时，该钮为退出按钮，点击退出程序
		 */
		final JButton reverseExit = new JButton(login == null ? "返回" : "退出");
		reverseExit.setBounds(192, 630, 80, 30);
		listPanel.add(reverseExit);
		reverseExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String context = reverseExit.getText(); // 获取按钮中的文字内容
				if (login == null && context.equals("返回")) {
					new Login().setVisible(true);
					ScoreListView.this.dispose();
				}
				if (login != null && context.equals("退出")) {
					JOptionPane.showMessageDialog(ScoreListView.this, login.getNickName() + "已成功退出！");
					System.exit(0);
				}
			}
		});
				
		jPanel.add(listPanel);
		return jPanel;
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
