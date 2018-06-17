package com.wei.betago;

class Neuron {
	private double[] weight;
	private double input, output = 0;
	Neuron(int prevLayerCount, double input) {
		this.weight = new double[prevLayerCount];
		this.input = input;
		
		//초기화
		randomWeights();
	}
	
	//가중치 랜덤 초기화
	void randomWeights() {
		for(int i = 0; i < weight.length; i++) {
			weight[i] = (Math.random() - 0.5) * (1D / Math.sqrt(weight.length)) * 2;
		}
	}
	
	//입력 (input레이어에 사용)
	void put(double input) { this.input = input; }
	void process() { this.output = sigmoid(input); }
	
	//연산
	void process(Neuron[] inputNeuron) {
		double[] input = new double[inputNeuron.length];
		for(int i = 0; i < input.length; i++) {
			input[i] = inputNeuron[i].output();
		}
		double result = 0;
		for(int i = 0; i < weight.length; i++)
			result += weight[i] * input[i];
		this.output = sigmoid(result);
	}
	void process(double[] input) {
		double result = 0;
		for(int i = 0; i < weight.length; i++)
			result += weight[i] * input[i];//행렬곱
		this.output = sigmoid(result);
	}
	
	//출력
	double output() { return output; }

	//가중치값 입력
	void setWeights(double[] weight) { this.weight = weight.clone(); }
	void setWeight(int i, double weight) { this.weight[i] = weight; }
	//가중치값 출력
	double[] getWeights() { return weight; }
	double getWeight(int i) { return weight[i]; }
	
	//활성 함수
	private double sigmoid(double x) { return (double)1 / ((double)1 + Math.exp(-x)); }
}
