package com.wei.betago;

class Learning {
	private int[][] data;
	private int x, y;
	Learning (int[][] data, int x, int y) {
		this.data = data;
		this.x = x;
		this.y = y;
	}
	
	//�����Ϳ��� Ư�� �ϱ��� �����Ų ���� ��ȯ��
	int[][] simulateBoard(int turn) {
		Board board = new Board(x, y);
		board.resetGame();
		for(int i = 0; i < turn; i++) {
			board.setTurn(data[i][0], data[i][1]);
		}
		
		return board.getBoard();
	}
}
