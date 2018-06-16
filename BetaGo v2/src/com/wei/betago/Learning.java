package com.wei.betago;

import java.util.Scanner;
import org.ini4j.Ini;

class Learning {
	private int[][] data;
	private int x, y, layers;
	Learning (int[][] data, int x, int y, int layers) {
		this.data = data;
		this.x = x;
		this.y = y;
		this.layers = layers;
		Scanner userInput = new Scanner(System.in);
		simulateBoard(userInput.nextInt());
		Network AI = new Network(x*y, x*y, layers, x*y);
		AI.exportNetwork("D:\\JAVA Projects", "neuron");
	}
	
	
	//데이터에서 특정 턴까지 진행시킨 판을 반환함
	int[][] simulateBoard(int turn) {
		Board board = new Board(x, y);
		board.resetGame();
		for(int i = 0; i < turn; i++) {
			board.setTurn(data[i][0], data[i][1]);
		}
		board.drawBoard();
		return board.getBoard();
	}
}
