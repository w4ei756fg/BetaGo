package com.wei.betago;

class Board {
	private int[][] board = new int[15][15]; // ���� ����
	private String[] dol = {"��", "��", "��"};
	private int x, y;

	Board(int x, int y) {
		this.x = x;
		this.y = y;
		
		//�� �ʱ�ȭ
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y; yy++)
		{
			if (Math.random() > 0.5)
				board[xx][yy] = 5; // ��
			else
				board[xx][yy] = -5; // ��
		}
	}
	
	//�� �ʱ�ȭ
	void resetBoard() {
		for(int xx = 0; xx < x; xx++)
		for(int yy = 0; yy < y; yy++)
			board[xx][yy] = 0;
	}
		
	void drawBoard() {
		System.out.println("����������������������������������");
		for(int yy = 0; yy < 15; yy++) {
			System.out.print("��");
			for(int xx = 0; xx < 15; xx++)
				System.out.print(dol[(board[xx][yy] + 5) / 5]);
			System.out.println("��");
		}
		System.out.println("����������������������������������");
	}
	
	//�� ���� ���
	int[][] getBoard() {
		return board;
	}
	
	//����
	int setBoard(int turn, int x, int y) { // 1=��, 0=��
		if (board[x][y] == 0)
			board[x][y] = -5 + 10*turn;
		else
			return 0; // ���� �ڸ��� �̹� �� ����.
		
		return -1; // ���� ����
	}
	
	int xy(int x, int y) {
		return x + y * 15;
	}
}
