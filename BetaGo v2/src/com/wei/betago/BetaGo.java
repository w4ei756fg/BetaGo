package com.wei.betago;

import java.util.Scanner;

public class BetaGo {
	
	static int x = 15, y = 15, layers = 3;
	static int px, py;

	public static void main(String[] args) {
		Board board = new Board(x, y); // 보드 초기화
		Network AI = new Network(x*y, x*y, layers, x*y); // AI 초기화
		
		AI.input(board.getBoard()); // AI에 현재상황 전달
		board.resetBoard();
		show("A:Game Start");
		
		//플레이어 차례
		Scanner userInput = new Scanner(System.in);
		board.drawBoard();
		show(board.whoIsWinner());

		while(board.whoIsWinner() == -1)
		{
			show("I:현재 차례(" + board.nowTurn() + ")");
			show("Q:돌을 둘 X좌표값?");
			px = userInput.nextInt();
			show("Q:돌을 둘 Y좌표값?");
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
