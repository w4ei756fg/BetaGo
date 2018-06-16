package com.wei.betago;

class Neuron {
	private double[] weight;
	private double input, output = 0;
	Neuron(int prevLayerCount, double input) {
		this.weight = new double[prevLayerCount];
		this.input = input;
	}
	//입력(입력 레이어용)
	public void put(double input) {
		this.input = input;
	}
	
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
			result += weight[i] * input[i];
		this.output = sigmoid(result);
	}
	void process() { // input레이어에 사용
		this.output = sigmoid(input);
	}
	
	//출력
	double output() {
		return output;
	}
	
	//가중치값 출력
	double[] getWeights() {
		return weight;
	}
	double getWeight(int i) {
		return weight[i];
	}
	
	//가중치값 입력
	void setWeights(double[] weight) {
		this.weight = weight.clone();
	}
	void setWeight(int i, double weight) {
		this.weight[i] = weight;
	}
	
	
	//활성 함수
	private double sigmoid(double x) {
		return (double)1 / ((double)1 + Math.exp(-x));
	}
}
