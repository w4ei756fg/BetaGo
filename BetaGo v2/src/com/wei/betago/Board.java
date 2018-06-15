package com.wei.betago;

class Board {
	private int[][] board = new int[15][15]; // ���� ����
	private String[] dol = {"��", "��", "��"};
	private int x, y, turn = 1;

	Board(int x, int y) {
		this.x = x;
		this.y = y;
		
		//�� �ʱ�ȭ
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y; yy++)
		{
			if (Math.random() > 0.5)
				board[xx][yy] = 1; // ��
			else
				board[xx][yy] = 2; // ��
		}
	}
	
	//���� ����
	int setTurn(int x, int y) {
		int error = setBoard(turn, x, y);
		if (error == -1)
		{
			turn++;
			System.out.print("A:[");
			System.out.print(x);
			System.out.print(", ");
			System.out.print(y);
			System.out.println("]��ġ�� �����Ͽ����ϴ�.(���� ��" + turn + ")");
			return -1;
		}
		else
		{
			System.out.print("E:[");
			System.out.print(x);
			System.out.print(", ");
			System.out.print(y);
			System.out.println("]��ġ�� ���� �̹� �ֽ��ϴ�.");
			return 0;
		}
	}
	
	//�� ����
	private int setBoard(int turn, int x, int y) { // 1=��, 0=��
		if (board[x][y] == 0)
			board[x][y] = turn;
		else
			return 0; // ���� �ڸ��� �̹� �� ����.
		
		return -1; // ���� ����
	}
	
	//���� ��
	int nowTurn() {
		return turn;
	}
	
	//���� �ʱ�ȭ
	void resetGame() {
		resetBoard();
		turn = 1;
	}
	
	//�� �ʱ�ȭ
	void resetBoard() {
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y; yy++)
			board[xx][yy] = 0;
	}
	
	//�� �ð�ȭ ���
	void drawBoard() {
		System.out.println("����������������������������������");
		for(int yy = 0; yy < 15; yy++) {
			System.out.print("��");
			for(int xx = 0; xx < 15; xx++)
				if (board[xx][yy] != 0)
					System.out.print(dol[board[xx][yy] % 2 + 1]);
				else
					System.out.print(dol[0]);
			System.out.println("��");
		}
		System.out.println("����������������������������������");
	}
	
	//�� ���� ���
	int[][] getBoard() {
		return board;
	}
	
	int xy(int x, int y) {
		return x + y * 15;
	}
	
	int whoIsWinner() {
		//����
		for(int xx = 0; xx < x - 4; xx++)
		for(int yy = 0; yy < y; yy++)
		{
			if (board[xx][yy] == 5 && 
				board[xx + 1][yy] == 5 && 
				board[xx + 2][yy] == 5 && 
				board[xx + 3][yy] == 5 && 
				board[xx + 4][yy] == 5)
				return 1;
			if (board[xx][yy] == -5 && 
				board[xx + 1][yy] == -5 && 
				board[xx + 2][yy] == -5 && 
				board[xx + 3][yy] == -5 && 
				board[xx + 4][yy] == -5)
				return 0;
		}
		//����
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y - 4; yy++)
		{
			if (board[xx][yy] == 5 && 
				board[xx][yy + 1] == 5 && 
				board[xx][yy + 2] == 5 && 
				board[xx][yy + 3] == 5 && 
				board[xx][yy + 4] == 5)
				return 1;
			if (board[xx][yy] == -5 && 
				board[xx][yy + 1] == -5 && 
				board[xx][yy + 2] == -5 && 
				board[xx][yy + 3] == -5 && 
				board[xx][yy + 4] == -5)
				return 0;
		}
		// \�밢��
		for(int xx = 0; xx < x - 4; xx++)
		for(int yy = 0; yy < y - 4; yy++)
		{
			if (board[xx][yy] == 5 && 
				board[xx + 1][yy + 1] == 5 && 
				board[xx + 2][yy + 2] == 5 && 
				board[xx + 3][yy + 3] == 5 && 
				board[xx + 4][yy + 4] == 5)
				return 1;
			if (board[xx][yy] == -5 && 
				board[xx + 1][yy + 1] == -5 && 
				board[xx + 2][yy + 2] == -5 && 
				board[xx + 3][yy + 3] == -5 && 
				board[xx + 4][yy + 4] == -5)
				return 0;
		}
		// /�밢��
		for(int xx = 0; xx < x - 4; xx++)
		for(int yy = 0; yy < y - 4; yy++)
		{
			if (board[xx + 4][yy] == 5 && 
				board[xx + 3][yy + 1] == 5 && 
				board[xx + 2][yy + 2] == 5 && 
				board[xx + 1][yy + 3] == 5 && 
				board[xx][yy + 4] == 5)
				return 1;
			if (board[xx + 4][yy] == -5 && 
				board[xx + 3][yy + 1] == -5 && 
				board[xx + 2][yy + 2] == -5 && 
				board[xx + 1][yy + 3] == -5 && 
				board[xx][yy + 4] == -5)
				return 0;
		}
		return -1;
	}
}
