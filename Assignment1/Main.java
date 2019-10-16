package assignment1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// 메인함수 = 인풋 입력을 받아서 
// dfs, bfs, dfid 함수의 결과를 파일에 써 주는 클래스.
public class Main {
	public static void main(String[] args) {
		// 입력 받기.
		int n = Integer.parseInt(args[0]);
		String path = args[1];
		
		// 저장 경로 저장 및 파일 writer 선언.
		File file = new File(args[1]+"/result" + Integer.toString(n) + ".txt");
		FileWriter fw = null;
		
		try {
			// 파일 writer 열기, Search를 위한 트리 선언.
			fw = new FileWriter(file, false);
			Tree tree = new Tree(n);
			
			// DFS 서치 결과 만들기.
			fw.write(">DFS\n");
			// resultdfs에 정답과 함께 걸린 시간이 넘어옴.
			Result resultdfs = tree.dfs();
			// 정답이 없는 경우.
			if (resultdfs == null) {
				fw.write("No Solution\n");
				fw.write("Time : 0.0");
			}
			// 정답이 있는 경우 출력하기
			else {
				fw.write("Location :");
				for (int temp : resultdfs.queens) {
					fw.write(" "+temp);
				}
				fw.write("\n");
				fw.write("Time : "+ Double.toString(resultdfs.time) + "\n");
			}
			fw.write("\n");
			
			// BFS 서치 결과 만들기.
			fw.write(">BFS\n");
			// resultbfs에 정답과 함께 걸린 시간이 넘어옴.
			Result resultbfs = tree.bfs();
			// 정답이 없는 경우.
			if (resultbfs == null) {
				fw.write("No Solution\n");
				fw.write("Time : 0.0");
			}
			// 정답이 있는 경우 출력하기
			else {
				fw.write("Location :");
				for (int temp : resultbfs.queens) {
					fw.write(" "+temp);
				}
				fw.write("\n");
				fw.write("Time : "+ Double.toString(resultbfs.time) + "\n");
			}
			fw.write("\n");
			
			// DFID 서치 결과 만들기.
			fw.write(">DFID\n");
			// resultdfid에 정답과 함께 걸린 시간이 넘어옴.
			Result resultdfid = tree.dfid();
			// 정답이 없는 경우.
			if (resultdfid == null) {
				fw.write("No Solution\n");
				fw.write("Time : 0.0");
			}
			// 정답이 있는 경우 출력하기
			else {
				fw.write("Location :");
				for (int temp : resultdfid.queens) {
					fw.write(" "+temp);
				}
				fw.write("\n");
				fw.write("Time : "+ Double.toString(resultdfid.time) + "\n");
			}
			
			// 파일에 쓰기.
			fw.flush();
			
			// 예외처리
		} catch(IOException e) {
			e.printStackTrace();
		}	
	}
}