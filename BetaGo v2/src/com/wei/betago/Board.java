package com.wei.betago;

class Board {
	private int[][] board = new int[15][15]; // 보드 생성
	private String[] dol = {"○", "┼", "●"};
	private int x, y;

	Board(int x, int y) {
		this.x = x;
		this.y = y;
		
		//판 초기화
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y; yy++)
		{
			if (Math.random() > 0.5)
				board[xx][yy] = 5; // 흑
			else
				board[xx][yy] = -5; // 백
		}
	}
	
	//판 초기화
	void resetBoard() {
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y; yy++)
			board[xx][yy] = 0;
	}
		
	void drawBoard() {
		System.out.println("┌┬┬┬┬┬┬┬┬┬┬┬┬┬┬┬┐");
		for(int yy = 0; yy < 15; yy++) {
			System.out.print("├");
			for(int xx = 0; xx < 15; xx++)
				System.out.print(dol[(board[xx][yy] + 5) / 5]);
			System.out.println("┤");
		}
		System.out.println("└┴┴┴┴┴┴┴┴┴┴┴┴┴┴┴┘");
	}
	
	//판 정보 출력
	int[][] getBoard() {
		return board;
	}
	
	//착수
	int setBoard(int turn, int x, int y) { // 1=흑, 0=백
		if (board[x][y] == 0)
			board[x][y] = -5 + 10*turn;
		else
			return 0; // 착수 자리에 이미 돌 있음.
		
		return -1; // 착수 성공
	}
	
	int xy(int x, int y) {
		return x + y * 15;
	}
}
