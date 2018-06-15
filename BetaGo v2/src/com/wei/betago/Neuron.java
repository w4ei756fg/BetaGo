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
	public void process(Neuron[] inputNeuron) {
		double[] input = new double[inputNeuron.length];
		for(int i = 0; i < input.length; i++) {
			input[i] = inputNeuron[i].output();
		}
		double result = 0;
		for(int i = 0; i < weight.length; i++)
			result += weight[i] * input[i];
		this.output = sigmoid(result);
	}
	public void process(double[] input) {
		double result = 0;
		for(int i = 0; i < weight.length; i++)
			result += weight[i] * input[i];
		this.output = sigmoid(result);
	}
	public void process() { // input레이어에 사용
		this.output = sigmoid(input);
	}
	
	//출력
	public double output() {
		return output;
	}
	
	
	//활성 함수
	private double sigmoid(double x) {
		return (double)1 / ((double)1 + Math.exp(-x));
	}
}
