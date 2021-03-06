package com.wei.betago;

class Board {
	private int[][] board = new int[15][15]; // 보드 생성
	private String[] dol = {"┼", "○", "●"};
	private int x, y, turn = 1;

	Board(int x, int y) {
		this.x = x;
		this.y = y;
		
		//판 초기화
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y; yy++)
		{
			if (Math.random() > 0.5)
				board[xx][yy] = 1; // 흑
			else
				board[xx][yy] = 2; // 백
		}
	}
	
	//차례 진행
	int setTurn(int x, int y) {
		int error = setBoard(turn, x, y);
		if (error == -1)
		{
			turn++;
			/*
			System.out.print("A:[");
			System.out.print(x);
			System.out.print(", ");
			System.out.print(y);
			System.out.println("]위치에 착수하였습니다.(현재 턴" + turn + ")");*/
			return -1;
		}
		else
		{
			System.out.print("E:[");
			System.out.print(x);
			System.out.print(", ");
			System.out.print(y);
			System.out.println("]위치에 돌이 이미 있습니다.");
			return 0;
		}
	}
	
	//돌 놓기
	private int setBoard(int turn, int x, int y) { // 1=흑, 0=백
		if (board[x][y] == 0)
			board[x][y] = turn;
		else
			return 0; // 착수 자리에 이미 돌 있음.
		
		return -1; // 착수 성공
	}
	
	//현재 턴
	int nowTurn() {
		return turn;
	}
	
	//게임 초기화
	void resetGame() {
		resetBoard();
		turn = 1;
	}
	
	//판 초기화
	void resetBoard() {
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y; yy++)
			board[xx][yy] = 0;
	}
	
	//판 시각화 출력
	void drawBoard() {
		System.out.print("┌");
		for(int xx = 0; xx < x; xx++)
			System.out.print("┬");
		System.out.println("┐");
		for(int yy = 0; yy < y; yy++) {
			System.out.print("├");
			for(int xx = 0; xx < x; xx++)
				if (board[xx][yy] != 0)
					System.out.print(dol[board[xx][yy] % 2 + 1]);
				else
					System.out.print(dol[0]);
			System.out.println("┤");
		}
		System.out.print("└");
		for(int xx = 0; xx < x; xx++)
			System.out.print("┴");
		System.out.println("┘");
	}
	
	//판 정보 출력
	int[][] getBoard() {
		return board;
	}
	//특정 좌표 출력
	int getBoard(int x, int y) {
		return board[x][y];
	}
	
	int xy(int x, int y) {
		return x + y * this.x;
	}
	
	int whoIsWinner() {
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y; yy++)
		{
			for(int t = 0; t < 2; t++)
			{
				//가로
				int d = 0;
				for(int i = 0; i < 5; i++)
					if (xx + i < x && board[xx + i][yy] != 0 && (board[xx + i][yy] % 2) == t)
						d++;
				if (d == 5) { return t; }
				
				//세로
				d = 0;
				for(int i = 0; i < 5; i++)
					if (yy + i < y && board[xx][yy + i] != 0 && (board[xx][yy + i] % 2) == t)
						d++;
				if (d == 5) { return t; }
				
				// \대각선
				d = 0;
				for(int i = 0; i < 5; i++)
					if (xx + i < x && yy + i < y && board[xx + i][yy + i] != 0 && (board[xx + i][yy + i] % 2) == t)
						d++;
				if (d == 5) { return t; }
				
				// /대각선
				d = 0;
				for(int i = 0; i < 5; i++)
					if (xx + 4 - i < x && yy + i < y && board[xx + 4 - i][yy + i] != 0 && (board[xx + 4 - i][yy + i] % 2) == t)
						d++;
				if (d == 5) { return t; }
			}
		}
		return -1;
	}
}
