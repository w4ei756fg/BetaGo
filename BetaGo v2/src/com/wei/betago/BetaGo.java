package com.wei.betago;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class BetaGo {
	
	static int x = 15, y = 15, layers = 3;
	static int px, py;

	static Board board = new Board(x, y); // ���� �ʱ�ȭ

	public static void main(String[] args) {
		

		Scanner userInput = new Scanner(System.in);
		show("1. �÷���(���vs���)");
		show("2. �÷���(���vsAI)");
		show("3. AI �н����");
		show("4. �׽�Ʈ���");
		switch(userInput.nextInt()) {
		case 1:
			playHumanvsHuman();
			break;
		case 2:
			playHumanvsAI();
			break;
		case 3:
			learnAI();
			break;
		case 4:
			int[][] sss = new int[2][3];
			show(sss.length);
			show(sss[0].length);
		break;
		}
		
		
		
	}
	
	static void learnAI() {
		String[] rawData = loadCSV("D:/test.csv");
		if (rawData.length > 1) {
			int[][] data = new int[rawData.length/2][2];
			for(int i = 0; i < rawData.length; i++) {
				data[i][0] = Integer.parseInt(rawData[i*2]);
				data[i][1] = Integer.parseInt(rawData[i*2 + 1]);
			}
			Learning learn = new Learning(data, x, y);
		}
		else
			show("E:�����Ͱ� �ùٸ��� �ʽ��ϴ�.");
	}
	
	static String[] loadCSV(String path) {
		BufferedReader br = null;
		String line;
		String[] temp = {""};
		try {
			br = new BufferedReader(new FileReader(path));
			//while((line = br.readLine()) != null) {
			if ((line = br.readLine()) != null)
			{
				temp = line.split(","); // ��ǥ�� ����
					
				
				for(int i=0; i<temp.length; i++) {
					System.out.print((i+1)+": "+temp[i]);
					if(i!=temp.length-1) System.out.print(", ");
					else System.out.println();
				}
				
			}
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	//���vsAI (�ڵ�)
	static void playHumanvsAI() {
		Network AI = new Network(x*y, x*y, layers, x*y); // AI �ʱ�ȭ
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
					if (optimalData[i] > optimalData[max] || (board.getBoard(max / x , max % x) != 0 && optimalData[i] <= optimalData[max])) {
						max = i;
					}
				}
				board.setTurn(max / x , max % x);//����
			}
		}
		board.drawBoard();
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
