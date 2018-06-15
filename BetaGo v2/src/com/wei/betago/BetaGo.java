package com.wei.betago;

public class BetaGo {
	
	static int x = 15, y = 15, layers = 3;

	public static void main(String[] args) {
		Board board = new Board(x, y); // ���� �ʱ�ȭ
		Network AI = new Network(x*y, x*y, layers, x*y); // AI �ʱ�ȭ
		
		AI.input(board.getBoard()); // AI�� �����Ȳ ����
		board.resetBoard();
		show("���� ��: " + board.nowTurn());
		board.setTurn(7, 7);
		show("���� ��: " + board.nowTurn());
		board.setTurn(5, 7);
		show("���� ��: " + board.nowTurn());
		board.setTurn(5, 7);
		board.drawBoard();
		show(board.whoIsWinner());
	}
	
	
	
	static void show(String str) {
		System.out.println(str);
	}
	static void show(int str) {
		System.out.println(str);
	}
	static void show(double str) {
		System.out.println(str);
	}
}
