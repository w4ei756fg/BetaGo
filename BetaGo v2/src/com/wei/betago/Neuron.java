package com.wei.betago;

class Neuron {
	private double[] weight;
	private double input, output = 0;
	Neuron(int prevLayerCount, double input) {
		this.weight = new double[prevLayerCount];
		this.input = input;
	}
	//�Է�(�Է� ���̾��)
	public void put(double input) {
		this.input = input;
	}
	
	//����
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
	public void process() { // input���̾ ���
		this.output = sigmoid(input);
	}
	
	//���
	public double output() {
		return output;
	}
	
	
	//Ȱ�� �Լ�
	private double sigmoid(double x) {
		return (double)1 / ((double)1 + Math.exp(-x));
	}
}
