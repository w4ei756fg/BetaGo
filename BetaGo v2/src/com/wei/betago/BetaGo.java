package com.wei.betago;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class BetaGo {
	
	static int x = 15, y = 15, layers = 3;
	static int px, py;

	static Board board = new Board(x, y); // 보드 초기화

	public static void main(String[] args) {
		

		Scanner userInput = new Scanner(System.in);
		show("1. 플레이(사람vs사람)");
		show("2. 플레이(사람vsAI)");
		show("3. AI 학습모드");
		show("4. 테스트모드");
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
			show("E:데이터가 올바르지 않습니다.");
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
				temp = line.split(","); // 쉼표로 구분
					
				
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
	
	//사람vsAI (자동)
	static void playHumanvsAI() {
		Network AI = new Network(x*y, x*y, layers, x*y); // AI 초기화
		board.resetBoard();
		show("A:Game Start");
		Scanner userInput = new Scanner(System.in);
		show("플레이어 색 선택 1 = 흑, 0 = 백");
		int turn = userInput.nextInt();
		while(board.whoIsWinner() == -1)
		{
			board.drawBoard();
			show(board.whoIsWinner());
			if (board.nowTurn() % 2 == turn)
			{
				//플레이어 차례
				show("I:현재 차례(" + board.nowTurn() + ")");
				show("Q:돌을 둘 좌표값?");
				System.out.print("x:");
				px = userInput.nextInt();
				System.out.print("y:");
				py = userInput.nextInt();
				board.setTurn(px, py);
				show(board.whoIsWinner());
			}
			else
			{
				//AI 차례
				AI.input(board.getBoard()); // AI에 현재상황 전달
				AI.calculate(); // 연산
				double[] optimalData = AI.output();
				int max = 0;
				//최적값 최댓값 구하기
				for(int i = 0; i < optimalData.length; i++) {
					if (optimalData[i] > optimalData[max] || (board.getBoard(max / x , max % x) != 0 && optimalData[i] <= optimalData[max])) {
						max = i;
					}
				}
				board.setTurn(max / x , max % x);//착수
			}
		}
		board.drawBoard();
	}
	
	//사람vs사람 (수동)
	static void playHumanvsHuman() {
		board.resetBoard();
		show("A:Game Start");
		
		//플레이어 차례
		Scanner userInput = new Scanner(System.in);
		board.drawBoard();
		show(board.whoIsWinner());

		while(board.whoIsWinner() == -1)
		{
			show("I:현재 차례(" + board.nowTurn() + ")");
			show("Q:돌을 둘 X좌표값?");
			px = userInput.nextInt();
			show("Q:돌을 둘 Y좌표값?");
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
