package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI extends JFrame {
	public static Container c;
	public static JLabel Title;
	public static JButton Back;
	public static JPanel gameboard; // �� �г�
	public static JPanel[][] panels = new JPanel[Game.BOARDX][Game.BOARDY]; // ������ �ǵ�
	
	public static String dir;
	public static int key;
	
	public static int WIDTH = 605;
	public static int HEIGHT = 600;
	
	public GUI() {
		setTitle("SnakeGame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		setBackground(new Color(238,238,238));
		
		c = getContentPane();
		c.setLayout(null);
		
		
		gameboard = new JPanel();
		gameboard.setLayout(null);
		gameboard.setSize(HEIGHT - 10, HEIGHT);
		gameboard.setLocation(0, 0);
		gameboard.setBackground(new Color(154,211,188));
		
		for (int i = 0; i < Game.BOARDX; i++) {
			for (int j = 0; j < Game.BOARDY; j++) {
				
				panels[i][j] = new JPanel();
				
//				if (i % 2 == 0 && j % 2 == 0 || i % 2 != 0 && j % 2 != 0) // ��ġ�� ���� �� ����
//					panels[i][j].setBackground(new Color(71,137,120));
//				else
//					panels[i][j].setBackground(new Color(243,234,194));
				
				panels[i][j].setBackground(new Color(71,137,120));
				
				panels[i][j].setSize(gameboard.getHeight() / Game.BOARDX, gameboard.getHeight() / Game.BOARDY);
				panels[i][j].setLocation((j * panels[i][j].getHeight()), (i * panels[i][j].getHeight()));
				
				gameboard.add(panels[i][j]);
			}
		}
		c.add(gameboard);
		
		
		
		
		
		
		Title = new JLabel();
		Title.setHorizontalAlignment(JLabel.TRAILING);
		Title.setBounds(300, 300, 100, 50);
		c.add(Title);
		
		
		
		
		
		
		
		
		
		
		
		
		c.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				key = e.getKeyCode();
				switch (key) { // �Է� �� ���� ���� ��ȯ
				case 87: // �� ����Ű
				case KeyEvent.VK_UP:
					if (Game.direction != Game.DOWN) {// �ݴ� �������� ��ȯ ���ϰ� ����
						Game.direction = Game.UP;
						dir = "UP";
					}
					break;
				case 83: // �Ʒ� ����Ű
				case KeyEvent.VK_DOWN: 
					if (Game.direction != Game.UP) {
						Game.direction = Game.DOWN;
						dir = "DOWN";
					}
					break;
				case 65: // ���� ����Ű
				case KeyEvent.VK_LEFT:
					if (Game.direction != Game.RIGHT) {
						Game.direction = Game.LEFT;
						dir = "LEFT";
					}
					break;
				case 68: // ������ ����Ű
				case KeyEvent.VK_RIGHT:
					if (Game.direction != Game.LEFT) {
						Game.direction = Game.RIGHT;
						dir = "RIGHT";
					}
					break;

				case KeyEvent.VK_R: // �ӵ� ������
					if (Game.speed >= 10) {
						Game.speed -= 10; 
					}
					break;
				case KeyEvent.VK_F: // �ӵ� ������
					Game.speed += 10; 
					break;
				case KeyEvent.VK_Q: // �������
					System.exit(1); break;
				}
				
				
				
				Game.move();
				Game.keyPressed = true;
				Game.print_info();
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		c.setFocusable(true);
		c.requestFocus();
		
		setSize(WIDTH, HEIGHT + 28);
		setLocationRelativeTo(null);
		setVisible(true);
	
	}
}
