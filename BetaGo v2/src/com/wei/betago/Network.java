package com.wei.betago;

class Network {
	Neuron[][] neuron;
	int inputNode, outputNode;
	Network (int inputNode, int hiddenNode, int hiddenLayer, int outputNode){
		this.inputNode = inputNode;
		this.outputNode = outputNode;
		//init neuron
		this.neuron = new Neuron[hiddenLayer + 2][hiddenNode];
		for(int l = 0; l < hiddenLayer + 2; l++)
		for(int n = 0; n < hiddenNode; n++)
		{
			if (l == 0)
				this.neuron[l][n] = new Neuron(0, 0);
			else if (l == 1)
				this.neuron[l][n] = new Neuron(inputNode, 0);
			else
				this.neuron[l][n] = new Neuron(hiddenNode, 0);
		}
	}
	//입력
	void input(double input[]) {
		for(int i = 0; i < neuron[0].length; i++) {
			if (i < input.length)
				neuron[0][i].put(input[i]);
			else
				neuron[0][i].put(0);
			neuron[0][i].process();
			System.out.println("입력[" + i + "]: " + neuron[0][i].output());
		}
	}
	//for gomoku
	void input(int input[][]) {
		for(int x = 0; x < 15; x++)
		for(int y = 0; y < 15; y++)
		{
			neuron[0][xy(x, y)].put(input[x][y]);
			neuron[0][xy(x, y)].process();
			System.out.println("입력[" + x + ", " + y + "]: " + neuron[0][xy(x, y)].output());
		}
	}
	void calculate() {
		
	}
	
	private int xy(int x, int y) {
		return x + y * 15;
	}
	
}
