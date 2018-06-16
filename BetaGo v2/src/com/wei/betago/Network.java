package com.wei.betago;

import java.io.File;
import java.io.FileWriter;

import org.ini4j.Ini;
import org.ini4j.Wini;

class Network {
	Neuron[][] neuron;
	int inputNode, outputNode, hiddenNode;
	Network (int inputNode, int hiddenNode, int hiddenLayer, int outputNode){
		this.inputNode = inputNode;
		this.outputNode = outputNode;
		this.hiddenNode = hiddenNode;
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
	
	//출력
	double[] output() {
		double[] output = new double[neuron[neuron.length - 1].length];
		for(int i = 0; i < output.length; i++) {
			output[i] = neuron[neuron.length - 1][i].output();
		}
		return output;
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
		for(int l = 1; l < neuron.length; l++)
		for(int n = 0; n < neuron[l].length; n++)
			neuron[l][n].process(neuron[l - 1]);
	}
	
	//뉴런 가중치 정보 ini 저장
	void exportNetwork(String path, String name) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(path + File.separator + name + ".ini");
            FileWriter fw = new FileWriter(file);
            fw.close();
           
            Wini iniFile = new Wini(file);
            iniFile.put("Info", "inputNode", inputNode);
            iniFile.put("Info", "outputNode", outputNode);
            iniFile.put("Info", "hiddenNode", hiddenNode);
            iniFile.put("Info", "Layers", neuron.length);
            iniFile.store();
            double[] weight;
            show("" + neuron.length);
    		for(int l = 1; l < neuron.length; l++)
        	for(int n = 0; n < neuron[l].length; n++)
        	{
        		weight = neuron[l][n].getWeights();
        		for(int w = 0; w < weight.length; w++) 
        		{
        			iniFile.put("Neuron" + l + "," + n, Integer.toString(w), weight[w]);
        			show("I:[" + l + ", " + n + ", " + w + "]=" + weight[w] + "저장됨.");
        		}
        	}
            
            iniFile.store();
			show("I:저장 성공");
			show("저장경로: " + path + File.separator + name + ".ini");
           
        } catch(Exception e) {
			show("E:저장중 에러");
			show("저장경로: " + path + File.separator + name + ".ini");
            //Log.w(TAG, e.toString());
        }
    }
	
	//뉴런 가중치 정보 출력
	double[][][] getNeuronWeights() {
		double[][][] neuron = new double[this.neuron.length][this.hiddenNode][];
		for(int l = 0; l < this.neuron.length; l++)
		for(int n = 0; n < this.neuron[l].length; n++) {
			neuron[l][n] = this.neuron[l][n].getWeights();
		}
		return neuron;
	}
	double getNeuronWeight(int l, int n, int w) {
		return neuron[l][n].getWeight(w);
	}
	//뉴런 가중치 정보 설정
	void setNeuronWeights(double[][][] newNeuron) {
		for(int l = 0; l < neuron.length; l++)
		for(int n = 0; n < neuron[l].length; n++) {
			neuron[l][n].setWeights(newNeuron[l][n]);
		}
	}
	void setNeuronWeight(int l, int n, int w, double newWeight) {
		neuron[l][n].setWeight(w, newWeight);
	}
	
	private int xy(int x, int y) {
		return x + y * 15;
	}
	private void show(String str) {
		System.out.println(str);
	}
	
}
