package com.wei.betago;

import java.util.Scanner;

class Learning {
	private int[][] data;
	private int x, y, layers;
	private String path, file;
	Network AI;
	Learning (int[][] data, int x, int y, int layers, int reset,String path, String file) {
		this.data = data;
		this.x = x;
		this.y = y;
		this.layers = layers;
		this.path = path;
		this.file = file;
		Scanner userInput = new Scanner(System.in);
		//int [][] dd = simulateBoard(userInput.nextInt());
		AI = new Network(x*y, x*y, layers, x*y);
		if(reset == 1) {
			AI.randomNeuron();
			show("I:뉴런 정보가 초기화되었습니다.");
		}
		else {
			AI.importNetwork(path, file);
		}
		//userInput.close();
	}
	
	double learnData(double rate) {
		double error_sum = 0;
		for(int i = 0; i < data.length; i++)
			error_sum += learn(rate, i);
		return error_sum / data.length;
	}
	
	
	double learn(double rate, int turn) {
		int[][] board = simulateBoard(turn);
		double[] input = new double[xy(this.x, this.y) - x],
				output = new double[xy(this.x, this.y) - x], 
				target = new double[xy(this.x, this.y) - x];
		double[][] weight;
		double[] error = new double[target.length], error_next = new double[error.length];
		
		double error_target = 0;
		

		//show("I:" + turn + "번째 수 학습시작");

		for(int y = 0; y < this.y; y++)
		for(int x = 0; x < this.x; x++) {
			//뉴런에 입력을 위해 변환
			if (board[x][y] != 0) { input[xy(x, y)] = board[x][y] % 2 * 10 - 5; } else { input[xy(x, y)] = 0; }
			//목표값 초기화
			target[xy(x, y)] = 0.001;
		}
		//목표값 설정
		target[xy(data[turn][0], data[turn][1])] = 0.999;
		//show("목표[" + data[turn][0] + ", " + data[turn][1] + "]: " + target[xy(data[turn][0], data[turn][1])]);

		//순전파 시행   0
		AI.input(input); // AI에 현재상황 전달
		AI.calculate();
		
		input = null; // 여기서부터 double input[]의 역할은 l-1계층의 활성함수 출력값으로 용도 변경
		//역전파 알고리즘
		int layers = AI.neuronOutput().length;
		for(int l = layers - 1; l > 0; l--){
			weight = AI.getNeuronWeights(l);
			output = AI.output(l);
			input = AI.output(l - 1);
			//n층 오차 구함
			if (l == layers - 1) { // output layer 일 때,   1
				for(int i = 0; i < output.length; i++) {
					// output계층 오차함수
					//error[i] = -target[i] * Math.log10(output[i]) - (1 - target[i]) * Math.log10(1 - output[i]); //로지스틱 회귀 함수
					error[i] = output[i] - target[i];
					//show("error[" + l + "," + i + "]: " + error[i]); // 출력 계층 오차 표시
				}
				error_next = error;
			}
			else { // hidden layer 일 때,(오차 역전파)   4
				error = new double[error_next.length];

				for(int n = 0; n < weight[0].length; n++) { // left;input
					error[n] = 0;
					for(int j = 0; j < weight.length; j++) { // right;output
						error[n] += error_next[j] * weight[j][n];
						//show("error[" + l + "," + n + "]: " + error[n]);
					}
				}
				error_next = error;
			}
			//가중치 조정   3
			for(int j = 0; j < weight.length; j++) // right;output
			for(int i = 0; i < weight[j].length; i++) { // left;input
				weight[j][i] = weight[j][i] - rate * error[j] * output[j] * (1 - output[j]) * input[i];
				//weight[j][i] = Math.min(Math.max(weight[j][i] - rate * error[j] * output[j] * (1 - output[j]) * input[i] -1), 1);
				//if (ii == 2 && l == 1)
				//show("가중치[" + l + ", " + k + ", " + j + "]: " + (error[ll][k] * neuronOutput[ll][k] * (1 - neuronOutput[ll][k]) * neuronOutput[ll - 1][j]));
			}
			//update weights
			AI.setNeuronWeights(l , weight);
		}
		//AI.exportNetwork(path, file); // 오버헤드 방지
		
		//오차율 반환
		error_target = AI.output(layers - 1)[xy(data[turn][0], data[turn][1])] - target[xy(data[turn][0], data[turn][1])];
		return error_target;
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
	private int xy(int x, int y) {
		return x + y * this.x;
	}
}
