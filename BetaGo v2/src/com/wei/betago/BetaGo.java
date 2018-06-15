package com.wei.betago;

public class BetaGo {
	
	static int x = 15, y = 15, layers = 3;

	public static void main(String[] args) {
		Board board = new Board(x, y); // 보드 초기화
		Network AI = new Network(x*y, x*y, layers, x*y); // AI 초기화
		
		AI.input(board.getBoard()); // AI에 현재상황 전달
		board.resetBoard();
		show("현재 턴: " + board.nowTurn());
		board.setTurn(7, 7);
		show("현재 턴: " + board.nowTurn());
		board.setTurn(5, 7);
		show("현재 턴: " + board.nowTurn());
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
