package com.wei.betago;

public class BetaGo {
	
	static int x = 15, y = 15, layers = 3;

	public static void main(String[] args) {
		Board board = new Board(x, y); // 보드 초기화
		Network AI = new Network(x*y, x*y, layers, x*y); // AI 초기화
		
		AI.input(board.getBoard()); // AI에 현재상황 전달
		board.resetBoard();
		board.setBoard(1, 7, 7);
		board.setBoard(0, 5, 7);
		board.drawBoard();
	}
}
