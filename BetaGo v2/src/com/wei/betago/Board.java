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
		for(int yy = 0; yy < y; yy++) {
			System.out.print("��");
			for(int xx = 0; xx < x; xx++)
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
	//Ư�� ��ǥ ���
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
				//����
				int d = 0;
				for(int i = 0; i < 5; i++)
					if (xx + i < x && board[xx + i][yy] != 0 && (board[xx + i][yy] % 2) == t)
						d++;
				if (d == 5) { return t; }
				
				//����
				d = 0;
				for(int i = 0; i < 5; i++)
					if (yy + i < y && board[xx][yy + i] != 0 && (board[xx][yy + i] % 2) == t)
						d++;
				if (d == 5) { return t; }
				
				// \�밢��
				d = 0;
				for(int i = 0; i < 5; i++)
					if (xx + i < x && yy + i < y && board[xx + i][yy + i] != 0 && (board[xx + i][yy + i] % 2) == t)
						d++;
				if (d == 5) { return t; }
				
				// /�밢��
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
