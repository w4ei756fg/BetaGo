package com.wei.betago;

import java.util.Scanner;

class Learning {
	private int[][] data;
	private int x, y, layers;
	private String path, file;
	private Network AI;
	Learning (int[][] data, int x, int y, int layers, String path, String file) {
		this.data = data;
		this.x = x;
		this.y = y;
		this.layers = layers;
		this.path = path;
		this.file = file;
		Scanner userInput = new Scanner(System.in);
		//int [][] dd = simulateBoard(userInput.nextInt());
		AI = new Network(x*y, x*y, layers, x*y);
		show("뉴런 정보를 초기화 후 학습? 1=yes");
		//if (userInput.nextInt() != 1) {
		if(true) {
			AI.importNetwork(path, file);
		}
		else {
			AI.randomNeuron();
			show("I:뉴런 정보가 초기화되었습니다.");
		}
		//userInput.close();
	}
	
	void learn(double rate) {
		double[][][] weight; // weight[layer][j][i]
		double[][] error = new double[AI.neuronOutput().length][AI.neuronOutput()[1].length], neuronOutput; // 오차 역전파
		double[] output, target;
		int[][] board;
		double[][] targetBoard = new double[this.x][this.y], inputBoard = new double[this.x][this.y];
		
		for(int ii = 1; ii < data.length; ii++) {
			show("I:" + ii + "번째 수 학습시작");
			board = simulateBoard(ii);
			//targetBoard = simulateBoard(ii + 1);
			for(int x = 0; x < this.x; x++)
			for(int y = 0; y < this.y; y++) {
				//입력을 위해 변환
				if (board[x][y] == 0)
					inputBoard[x][y] = 0.1;
				else
					inputBoard[x][y] = board[x][y] % 2 * 10 - 5;
				//목표값 설정
				if (data[ii][0] == x && data[ii][1] == y)
					targetBoard[x][y] = 1;
				else
					targetBoard[x][y] = -1;
				//show("입력[" + x + ", " + y + "]: " + targetBoard[x][y]);
			}
			weight = AI.getNeuronWeights();
			//error backpropagation
			AI.input(targetBoard); // AI에 목표상황 전달
			AI.calculate(); // 연산
			target = AI.output(); // 목표값
			
			AI.input(board); // AI에 현재상황 전달
			AI.calculate(); // 연산
			output = AI.output(); // 실제값
			for(int j = 0; j < target.length; j++)
				error[error.length - 1][j] = Math.pow(target[j] - output[j], 2); // output계층 오차함수
			
			
			for(int l = layers - 1; l > 0; l--)
			for(int n = 0; n < weight[l].length; n++) { // left
				error[l - 1][n] = 0;
				for(int j = 0; j < weight[l].length; j++) { // right
					error[l - 1][n] += error[l][j] * weight[l][j][n];
				}
			}
			
			//weight backpropagation
			neuronOutput = AI.neuronOutput();
			for(int l = layers - 1; l > 0; l--)
			for(int k = 0; k < weight[l].length; k++) // right
			for(int j = 0; j < weight[l][k].length; j++) { // left
				weight[l][k][j] += rate * error[l][k] * neuronOutput[l][k] * (1 - neuronOutput[l][k]) * neuronOutput[l - 1][j];
				//if (ii == 2 && l == 1)
				//show("가중치[" + l + ", " + k + ", " + j + "]: " + (error[l][k] * neuronOutput[l][k] * (1 - neuronOutput[l][k]) * neuronOutput[l - 1][j]));
			}
			
			//update weights
			AI.setNeuronWeights(weight);
		}
		AI.exportNetwork(path, file);
	}
	
	//데이터에서 특정 턴까지 진행시킨 판을 반환함
	int[][] simulateBoard(int turn) {
		try {
			Board board = new Board(x, y);
			board.resetGame();
			for(int i = 0; i < turn; i++) {
				board.setTurn(data[i][0], data[i][1]);
			}
			//board.drawBoard(); // for debug
			return board.getBoard();
		}
		catch(Exception e) {
			show("E:시뮬레이션 실패(" + e + ")");
			return new int[0][0];
		}
	}
	
	private void show(String str) {
		System.out.println(str);
	}
}
