package game;

import java.awt.*;
import javax.swing.*;
import java.math.*;
import java.util.ArrayList;

public class Game {
	public static final int BOARDX = 31;
	public static final int BOARDY = 31;
	
	public static final int BLANK = 0;
	public static final int WALL = 1;
	public static final int SCORE = 2;
	public static final int SNAKE = 3;
	public static final int HEAD = 4;
	
	public static final int UP = 10;
	public static final int DOWN = 11;
	public static final int LEFT = 12;
	public static final int RIGHT = 13;
	
	
	public static int i, j;
	
	public static int direction;
	public static int length;
	public static int move_cnt;
	public static int speed;
	
	public static Point scoreloc = new Point();
	
	public static int board[][] = new int[BOARDX][BOARDY];
	public static int temp[][] = new int[BOARDX][BOARDY];
	
	public static Point headloc = new Point();
	public static Point[] snakeloc = new Point[BOARDX * BOARDY];
	
	public static int score;
	public static int prev_score;
	public static int highest_score;
	
	public static boolean need_score;
	public static boolean keyPressed;
	
//	TODO List	
//O void direct(); // ���� ����
//O void move(); // �̵�
//O void init(); // �ʱ�ȭ

	
//	void print_intboard();
//	void print_score();
//	void print_instruction();
//O void makescore(); // ���� ����
//O void record_move();
//O void run(); // ����
//	void game_over(); // ���� ����

	
//	void print_board_old(); // ���
//	void print_board(); // ȿ���� ���
//	void print_intboard_old(); // ���� ���
//	int input(); // �Է�
	
	// �ʱ�ȭ
	public static void init() {
		
		move_cnt = 0;
		score = 0;
		need_score = true;
		
		direction = RIGHT;
		keyPressed = false;
		speed = 100;
		length = 5;
		
		headloc.x = (int)(BOARDX / 2);
		headloc.y = (int)(BOARDY / 2);
		
		for (i = 0; i < BOARDY; i++) { // �׵θ��� ���� �ִ� �⺻ �� ����
			for (j = 0; j < BOARDX; j++) {
				if (i == 0 || i == BOARDY - 1 || j == 0 || j == BOARDX - 1) // �׵θ��� �� ����
					board[i][j] = WALL;
				else
					board[i][j] = BLANK; // �׵θ��� �ƴϸ� ��ĭ���� ä��
			}
			
		}
		for (i = 0; i < snakeloc.length; i++) {
			snakeloc[i] = new Point(BLANK, BLANK);
		}

		for (i = 0; i < BOARDY; i++) { 
			for (j = 0; j < BOARDX; j++) {
				temp[i][j] = board[i][j]; // ������ ���纻�� ����
			}
		}
		
	}
	// �����̱�
	public static void move() { 
		switch (direction) {
		case UP: headloc.x--; break;
		case DOWN: headloc.x++; break;
		case LEFT: headloc.y--; break;
		case RIGHT: headloc.y++; break;
		}

		try {
			if (board[headloc.y][headloc.x] == SCORE) { // ���� ȹ�� �� �̺�Ʈ
				need_score = true;
				score++; // ���ھ� 1 ����
				length++; // ���� 1 ����
			}
			else if (board[headloc.y][headloc.x] == WALL || board[headloc.y][headloc.x] == SNAKE) { // �浹 �� �̺�Ʈ
				JOptionPane.showMessageDialog(null, "���");
				init(); // �ٽ� ����
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			init(); // �ٽ� ����
		}
		
		
		// �� ��ġ�� �Ӹ� ����
		board[headloc.y][headloc.x] = HEAD;
		move_cnt++; // ������ Ƚ�� ����

		record_move(); // �� ��ġ ����ϱ�
	}
	// �� ��ġ ����ϴ� �迭�� ��ĭ�� �ڷ� �б�
	public static void push() {
		for (int i = length; i > 0; i--) {
			snakeloc[i].x = snakeloc[i - 1].x;
			snakeloc[i].y = snakeloc[i - 1].y;
			if (board[snakeloc[i].y][snakeloc[i].x] == HEAD)
				board[snakeloc[i].y][snakeloc[i].x] = SNAKE; // �Ӹ� �ƴ� ���� �������� ��ȯ
		}
	}
	// �� ��ġ ����ϱ�
	public static void record_move() {
		push();
		snakeloc[0].x = headloc.x;
		snakeloc[0].y = headloc.y;
		board[snakeloc[0].y][snakeloc[0].x] = HEAD; // �밡��
		if (board[snakeloc[length].y][snakeloc[length].x] != WALL)
			board[snakeloc[length].y][snakeloc[length].x] = BLANK;
	}
	// ���� ����
	public static void makescore() {
		

		scoreloc.x = (int)(Math.random() * BOARDX);
		scoreloc.y = (int)(Math.random() * BOARDY);

		if (board[scoreloc.y][scoreloc.x] == BLANK) {
			board[scoreloc.y][scoreloc.x] = SCORE;
			need_score = false;
		}
		else
			makescore();
		
		print_info();
	}
	
	public static void print_board() {
		for (i = 0; i < BOARDY; i++) {
			for (j = 0; j < BOARDX; j++) {
				switch (board[j][i]) {
				case BLANK: GUI.panels[i][j].setBackground(Color.black); break;
				case WALL: GUI.panels[i][j].setBackground(Color.red); break;
				case SCORE: GUI.panels[i][j].setBackground(Color.blue); break;
				case SNAKE: GUI.panels[i][j].setBackground(Color.white); break;
				case HEAD: GUI.panels[i][j].setBackground(Color.yellow); break;
				}
			}
		}
	}
	
	public static void print_info() {
		
		GUI.Title.setText("<html> ������ ĭ �� : " + Game.move_cnt + " ���� : " + Game.score + "<br> X : " + headloc.x + " Y : " + headloc.y  + "���� : " + Game.length + "</html>"); 
	}
	
	public static void run() throws InterruptedException {
		init();
		new GUI();
		
		while (true) {
			if (keyPressed)
				keyPressed = false;
			else
				move();
			if (need_score)
				makescore();
			print_board();
			print_info();
			
			Thread.sleep(speed);
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		run();
	}
}
