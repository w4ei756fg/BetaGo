package com.wei.betago;

import java.util.Scanner;

public class BetaGo {
	
	static int x = 15, y = 15, layers = 3;
	static int px, py;

	public static void main(String[] args) {
		Board board = new Board(x, y); // ���� �ʱ�ȭ
		Network AI = new Network(x*y, x*y, layers, x*y); // AI �ʱ�ȭ
		
		AI.input(board.getBoard()); // AI�� �����Ȳ ����
		board.resetBoard();
		show("A:Game Start");
		
		//�÷��̾� ����
		Scanner userInput = new Scanner(System.in);
		board.drawBoard();
		show(board.whoIsWinner());

		while(board.whoIsWinner() == -1)
		{
			show("I:���� ����(" + board.nowTurn() + ")");
			show("Q:���� �� X��ǥ��?");
			px = userInput.nextInt();
			show("Q:���� �� Y��ǥ��?");
			py = userInput.nextInt();
			board.setTurn(px, py);
			board.drawBoard();
			show(board.whoIsWinner());
		}
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
