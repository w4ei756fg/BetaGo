package com.wei.betago;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BetaGo {
	
	static int x = 3, y = 3, layers = 3;
	static int px, py;
	static String path = "D:\\JAVA Projects", file = "neuron", dataFile = "D:/test.csv";

	static Board board = new Board(x, y); // ���� �ʱ�ȭ

	public static void main(String[] args) throws IOException {
		int mode = 0;
		Scanner userInput = new Scanner(System.in);
		Scanner rateInput = new Scanner(System.in);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
		if (args.length == 3) {
			path = args[0];
			file = args[1];
			dataFile = args[2];
		}
		else if (args.length == 1 && args[0] == "manual") {
			show("Q:Path?");
			path = br2.readLine();
			show("Q:File?");
			file = br2.readLine();
			show("Q:Datafile?");
			dataFile = br2.readLine();
		}
		else {
			show("I:�⺻�� ����");
			path = ".";
			file = "neuron";
			dataFile = ".\\test.csv";
		}
		do {
			show("Q:�۾� ����");
			show("1. �÷���(���vs���)");
			show("2. �÷���(���vsAI)");
			show("3. AI �н����");
			show("4. �� ���� ���� ����");
			show("5. ����");
			mode = userInput.nextInt();
			//mode = 3;
			switch(mode) {
			case 1:
				playHumanvsHuman();
				break;
			case 2:
				playHumanvsAI();
				break;
			case 3:
				show("Q:��ȭ��?");
				Double rate = rateInput.nextDouble();
				show("Q:�ݺ� Ƚ��?");
				int c = userInput.nextInt();
				show("Q:���� ������ �ʱ�ȭ �� �н�? 1=yes 0=no");
				int reset = userInput.nextInt();
				learnAI(c, rate, reset);
				show("A:�н� ����");
				break;
			case 4:
				Network AI = new Network(x*y, x*y, layers, x*y);
				show("I:���� ������..");
				AI.randomNeuron();
				show("I:���� �����Ϸ�");
				AI.exportNetwork(path, file);
				show("I:���ο� ���� ���� ���� ���� �Ϸ�");
			break;
			}
		} while(mode != 5);
		
		
		userInput.close();
		rateInput.close();
	}
	
	static void learnAI(int count, double rate, int reset) {
		String[][] rawData = loadCSV(dataFile);
		double error_rate = 0;
		int cut = 0;
		if (rawData != null) {
			show("I:�н� ������ �ҷ����� ����");
			int[][] data = new int[rawData.length][2];
			for(int i = 0; i < rawData.length; i++)
			for(int ii = 0; ii < 2; ii++)
				data[i][ii] = Integer.parseInt(rawData[i][ii]);
			show("I:�н� �� ����");
			Learning learn = new Learning(data, x, y, layers, reset, path, file);
			show("I:�н� �� ������(bot:" + learn + ")");
			show("I:�н�����");
			for(int i = 0; Math.pow(10, i) < count; i++)
				cut = i - 1;
			error_rate = 0;
			for(int i = 0; i < count; i++) {
				error_rate += learn.learnData(rate);
				if (i % Math.pow(10, cut) == 0) {
					show("I:" + i + "��° �н� �Ϸ�(������:" + Math.abs(error_rate / Math.pow(10, cut)) + ")");
					error_rate = 0;
				}
			}
			show("A:" + count + "ȸ �н��Ϸ�");
			show("I:���� ���� ���� ��");
			learn.AI.exportNetwork(path, file);
			show("I:���� ���� ���� �Ϸ�");
		}
		else
			show("E:�����Ͱ� �ùٸ��� �ʽ��ϴ�.");
	}
	
	static String[][] loadCSV(String path) {
		BufferedReader br = null;
		String line;
		List<String[]> temp = new ArrayList<String[]>();
		String[][] data = null;
		try {
			br = new BufferedReader(new FileReader(path));
			while((line = br.readLine()) != null) {
				String[] tt = line.split(",");
				temp.add(tt); // ��ǥ�� ����
			}
			br.close();
			
			//show("size: " + temp.size());
			data = new String[temp.size()][2];
			for(int i = 0; i < temp.size(); i++) {
				data[i][0] = temp.get(i)[0];
				data[i][1] = temp.get(i)[1];
				//show(i + ",x: " + temp.get(i)[0]);
				//show(i + ",y: " + temp.get(i)[1]);
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	//���vsAI (�ڵ�)
	static void playHumanvsAI() {
		Network AI = new Network(x*y, x*y, layers, x*y); // AI �ʱ�ȭ
		AI.importNetwork(path, file);
		board.resetBoard();
		show("A:Game Start");
		Scanner userInput = new Scanner(System.in);
		show("�÷��̾� �� ���� 1 = ��, 0 = ��");
		int turn = userInput.nextInt();
		while(board.whoIsWinner() == -1)
		{
			board.drawBoard();
			show(board.whoIsWinner());
			if (board.nowTurn() % 2 == turn)
			{
				//�÷��̾� ����
				show("I:���� ����(" + board.nowTurn() + ")");
				show("Q:���� �� ��ǥ��?");
				System.out.print("x:");
				px = userInput.nextInt();
				System.out.print("y:");
				py = userInput.nextInt();
				board.setTurn(px, py);
				show(board.whoIsWinner());
			}
			else
			{
				//AI ����
				AI.input(board.getBoard()); // AI�� �����Ȳ ����
				AI.calculate(); // ����
				double[] optimalData = AI.output();
				int max = 0;
				//������ �ִ� ���ϱ�
				for(int i = 0; i < optimalData.length; i++) {
					show("[" + i % x + "," + i / x + "]" + optimalData[i]);
					if ((board.getBoard(i % x , i / x) == 0 && optimalData[i] > optimalData[max]) || 
						(board.getBoard(max % x , max / x) != 0 && optimalData[i] <= optimalData[max])) {
						max = i;
					}
				}
				board.setTurn(max % x, max / x);//����
			}
		}
		board.drawBoard();
		userInput.close();
	}
	
	//���vs��� (����)
	static void playHumanvsHuman() {
		board.resetBoard();
		show("A:Game Start");
		
		//�÷��̾� ����
		Scanner userInput = new Scanner(System.in);
		board.drawBoard();
		show(board.whoIsWinner());

		while(board.whoIsWinner() == -1)
		{
			show("I:���� ����(" + board.nowTurn() + ")");
			show("Q:���� �� X��ǥ��?");
			px = userInput.nextInt();
			show("Q:���� �� Y��ǥ��?");
			py = userInput.nextInt();
			board.setTurn(px, py);
			board.drawBoard();
			show(board.whoIsWinner());
		}
		userInput.close();
	}
	
	
	
	static void show(String str) {
		System.out.println(str);
	}
	static void show(int str) {
		System.out.println(str);
	}
	static void show(double str) {
		System.out.println(str);
	}
}
