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
//O void direct(); // 방향 변경
//O void move(); // 이동
//O void init(); // 초기화

	
//	void print_intboard();
//	void print_score();
//	void print_instruction();
//O void makescore(); // 먹이 생성
//O void record_move();
//O void run(); // 실행
//	void game_over(); // 게임 오버

	
//	void print_board_old(); // 출력
//	void print_board(); // 효율적 출력
//	void print_intboard_old(); // 정수 출력
//	int input(); // 입력
	
	// 초기화
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
		
		for (i = 0; i < BOARDY; i++) { // 테두리에 벽이 있는 기본 판 생성
			for (j = 0; j < BOARDX; j++) {
				if (i == 0 || i == BOARDY - 1 || j == 0 || j == BOARDX - 1) // 테두리에 벽 생성
					board[i][j] = WALL;
				else
					board[i][j] = BLANK; // 테두리가 아니면 빈칸으로 채움
			}
			
		}
		for (i = 0; i < snakeloc.length; i++) {
			snakeloc[i] = new Point(BLANK, BLANK);
		}

		for (i = 0; i < BOARDY; i++) { 
			for (j = 0; j < BOARDX; j++) {
				temp[i][j] = board[i][j]; // 게임판 복사본에 전달
			}
		}
		
	}
	// 움직이기
	public static void move() { 
		switch (direction) {
		case UP: headloc.x--; break;
		case DOWN: headloc.x++; break;
		case LEFT: headloc.y--; break;
		case RIGHT: headloc.y++; break;
		}

		try {
			if (board[headloc.y][headloc.x] == SCORE) { // 점수 획득 시 이벤트
				need_score = true;
				score++; // 스코어 1 증가
				length++; // 길이 1 증가
			}
			else if (board[headloc.y][headloc.x] == WALL || board[headloc.y][headloc.x] == SNAKE) { // 충돌 시 이벤트
				JOptionPane.showMessageDialog(null, "사망");
				init(); // 다시 시작
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			init(); // 다시 시작
		}
		
		
		// 새 위치에 머리 생성
		board[headloc.y][headloc.x] = HEAD;
		move_cnt++; // 움직인 횟수 증가

		record_move(); // 뱀 위치 기록하기
	}
	// 뱀 위치 기록하는 배열을 한칸씩 뒤로 밀기
	public static void push() {
		for (int i = length; i > 0; i--) {
			snakeloc[i].x = snakeloc[i - 1].x;
			snakeloc[i].y = snakeloc[i - 1].y;
			if (board[snakeloc[i].y][snakeloc[i].x] == HEAD)
				board[snakeloc[i].y][snakeloc[i].x] = SNAKE; // 머리 아닌 곳은 몸통으로 변환
		}
	}
	// 뱀 위치 기억하기
	public static void record_move() {
		push();
		snakeloc[0].x = headloc.x;
		snakeloc[0].y = headloc.y;
		board[snakeloc[0].y][snakeloc[0].x] = HEAD; // 대가리
		if (board[snakeloc[length].y][snakeloc[length].x] != WALL)
			board[snakeloc[length].y][snakeloc[length].x] = BLANK;
	}
	// 점수 생성
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
		
		GUI.Title.setText("<html> 움직인 칸 수 : " + Game.move_cnt + " 점수 : " + Game.score + "<br> X : " + headloc.x + " Y : " + headloc.y  + "길이 : " + Game.length + "</html>"); 
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
