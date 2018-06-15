package com.wei.betago;

public class BetaGo {
	
	static int x = 15, y = 15, layers = 3;

	public static void main(String[] args) {
		Board board = new Board(x, y); // ���� �ʱ�ȭ
		Network AI = new Network(x*y, x*y, layers, x*y); // AI �ʱ�ȭ
		
		AI.input(board.getBoard()); // AI�� �����Ȳ ����
		board.resetBoard();
		board.setBoard(1, 7, 7);
		board.setBoard(0, 5, 7);
		board.drawBoard();
	}
}
