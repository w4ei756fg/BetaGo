package com.wei.betago;

import java.util.Scanner;

class Learning {
	private int[][] data;
	private int x, y, layers;
	private String path, file;
	private Network AI;
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
			show("I:���� ������ �ʱ�ȭ�Ǿ����ϴ�.");
		}
		else {
			AI.importNetwork(path, file);
		}
		//userInput.close();
	}
	
	void learnData(double rate) {
		for(int i = 0; i < data.length; i++)
			learn(rate, i);
	}
	
	
	void learn(double rate, int turn) {
		int[][] board = simulateBoard(turn);
		double[] input = new double[xy(this.x, this.y) - x],
				output = new double[xy(this.x, this.y) - x], 
				target = new double[xy(this.x, this.y) - x];
		double[][] weight;
		double[] error = new double[target.length], error_next = new double[error.length];
		

		show("I:" + turn + "��° �� �н�����");

		for(int y = 0; y < this.y; y++)
		for(int x = 0; x < this.x; x++) {
			//������ �Է��� ���� ��ȯ
			if (board[x][y] != 0) { input[xy(x, y)] = board[x][y] % 2 * 10 - 5; } else { input[xy(x, y)] = 0; }
			//��ǥ�� �ʱ�ȭ
			target[xy(x, y)] = 0.001;
		}
		//��ǥ�� ����
		target[xy(data[turn][0], data[turn][1])] = 0.999;
		//show("��ǥ[" + data[turn][0] + ", " + data[turn][1] + "]: " + target[xy(data[turn][0], data[turn][1])]);

		//������ ����   0
		AI.input(input); // AI�� �����Ȳ ����
		AI.calculate();
		
		input = null; // ���⼭���� double input[]�� ������ l-1������ Ȱ���Լ� ��°����� �뵵 ����
		//������ �˰���
		int layers = AI.neuronOutput().length;
		for(int l = layers - 1; l > 0; l--){
			weight = AI.getNeuronWeights(l);
			output = AI.output(l);
			input = AI.output(l - 1);
			//n�� ���� ����
			if (l == layers - 1) { // output layer �� ��,   1
				for(int i = 0; i < output.length; i++) {
					error[i] = Math.pow(output[i] - target[i], 2); // output���� �����Լ�
					show("error[" + l + "," + i + "]: " + error[i]);
				}
				error_next = error;
			}
			else { // hidden layer �� ��,(���� ������)   4
				error = new double[error_next.length];

				for(int n = 0; n < weight[0].length; n++) { // left;input
					error[n] = 0;
					for(int j = 0; j < weight.length; j++) { // right;output
						error[n] += error_next[j] * weight[j][n];
						show("error[" + l + "," + n + "]: " + error[n]);
					}
				}
				error_next = error;
			}
			//����ġ ����   3
			for(int j = 0; j < weight.length; j++) // right;output
			for(int i = 0; i < weight[j].length; i++) { // left;input
				weight[j][i] = Math.min(Math.max(weight[j][i] - rate * error[j] * output[j] * (1 - output[j]) * input[i], -1), 1);
				//if (ii == 2 && l == 1)
				//show("����ġ[" + l + ", " + k + ", " + j + "]: " + (error[ll][k] * neuronOutput[ll][k] * (1 - neuronOutput[ll][k]) * neuronOutput[ll - 1][j]));
			}
			//update weights
			AI.setNeuronWeights(l , weight);
		}
		AI.exportNetwork(path, file);
	}
	
	//�����Ϳ��� Ư�� �ϱ��� �����Ų ���� ��ȯ��
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
			show("E:�ùķ��̼� ����(" + e + ")");
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
