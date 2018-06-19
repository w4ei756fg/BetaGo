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

	static Board board = new Board(x, y); // 보드 초기화

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
			show("I:기본값 세팅");
			path = ".";
			file = "neuron";
			dataFile = ".\\test.csv";
		}
		do {
			show("Q:작업 선택");
			show("1. 플레이(사람vs사람)");
			show("2. 플레이(사람vsAI)");
			show("3. AI 학습모드");
			show("4. 새 뉴런 정보 저장");
			show("5. 종료");
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
				show("Q:변화율?");
				Double rate = rateInput.nextDouble();
				show("Q:반복 횟수?");
				int c = userInput.nextInt();
				show("Q:뉴런 정보를 초기화 후 학습? 1=yes 0=no");
				int reset = userInput.nextInt();
				learnAI(c, rate, reset);
				show("A:학습 종료");
				break;
			case 4:
				Network AI = new Network(x*y, x*y, layers, x*y);
				show("I:랜덤 생성중..");
				AI.randomNeuron();
				show("I:랜덤 생성완료");
				AI.exportNetwork(path, file);
				show("I:새로운 랜덤 뉴런 정보 저장 완료");
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
			show("I:학습 데이터 불러오기 성공");
			int[][] data = new int[rawData.length][2];
			for(int i = 0; i < rawData.length; i++)
			for(int ii = 0; ii < 2; ii++)
				data[i][ii] = Integer.parseInt(rawData[i][ii]);
			show("I:학습 봇 생성");
			Learning learn = new Learning(data, x, y, layers, reset, path, file);
			show("I:학습 봇 생성됨(bot:" + learn + ")");
			show("I:학습시작");
			for(int i = 0; Math.pow(10, i) < count; i++)
				cut = i - 1;
			error_rate = 0;
			for(int i = 0; i < count; i++) {
				error_rate += learn.learnData(rate);
				if (i % Math.pow(10, cut) == 0) {
					show("I:" + i + "번째 학습 완료(에러율:" + Math.abs(error_rate / Math.pow(10, cut)) + ")");
					error_rate = 0;
				}
			}
			show("A:" + count + "회 학습완료");
			show("I:뉴런 정보 저장 중");
			learn.AI.exportNetwork(path, file);
			show("I:뉴런 정보 저장 완료");
		}
		else
			show("E:데이터가 올바르지 않습니다.");
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
				temp.add(tt); // 쉼표로 구분
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
	
	//사람vsAI (자동)
	static void playHumanvsAI() {
		Network AI = new Network(x*y, x*y, layers, x*y); // AI 초기화
		AI.importNetwork(path, file);
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
					show("[" + i % x + "," + i / x + "]" + optimalData[i]);
					if ((board.getBoard(i % x , i / x) == 0 && optimalData[i] > optimalData[max]) || 
						(board.getBoard(max % x , max / x) != 0 && optimalData[i] <= optimalData[max])) {
						max = i;
					}
				}
				board.setTurn(max % x, max / x);//착수
			}
		}
		board.drawBoard();
		userInput.close();
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
