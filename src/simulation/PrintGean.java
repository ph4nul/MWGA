package simulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import agent.*;

public class PrintGean {
	
	public static void Export(Agent[] agents,int n,int times) {
		try {
			//出力先を作成する
			FileWriter fw = new FileWriter("D:\\work\\lab\\gean\\gean-"+times+".csv", false);  //※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
        
			//ヘッダー書き込み
			pw.print("");
			pw.print(",");
			pw.print("B");
			pw.print(",");
			pw.print("L");
			pw.println();
        
			
			
			for(int i=0;i<n;i++) {
				pw.print(i);
				pw.print(",");
				pw.print(agents[i].getAgentValueBL(0)/7);
				pw.print(",");
				pw.print(agents[i].getAgentValueBL(1)/7);
				pw.println();
			}

			//ファイルに書き出す
			pw.close();

			//終了メッセージを画面に出力する
			//System.out.println(name+"_"+times+"_"+steps+"("+num+"ターン目)の出力が完了しました。");

		} catch (IOException ex) {
			//例外時処理
			ex.printStackTrace();
		}
	}
	
	public static void ExportAll(Agent[][] agents,int n,int world,int times) {
		try {
			//出力先を作成する
			FileWriter fw = new FileWriter("D:\\work\\lab\\gean\\gean-"+times+"-all.csv", false);  //※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
        
			//ヘッダー書き込み
			pw.print("world");
			pw.print(",");
			for(int i=0;i<n;i++) {
				pw.print("Agent"+i);
				pw.print(",");
			}
			pw.println();
			
			for(int i=0;i<world;i++) {
				pw.print(i);
				pw.println();
				
				pw.print("B");
				pw.print(",");
				for(int j=0;j<n;j++) {
					pw.print(agents[i][j].getAgentValueBL(0)/7);
					pw.print(",");
				}
				pw.println();
				
				pw.print("L");
				pw.print(",");
				for(int j=0;j<n;j++) {
					pw.print(agents[i][j].getAgentValueBL(1)/7);
					pw.print(",");
				}
				pw.println();
				
			}

			//ファイルに書き出す
			pw.close();

			//終了メッセージを画面に出力する
			//System.out.println(name+"_"+times+"_"+steps+"("+num+"ターン目)の出力が完了しました。");

		} catch (IOException ex) {
			//例外時処理
			ex.printStackTrace();
		}
	}
	
	public static void ExportAverage(Agent[] agents,int n,double[] b,double[] l,int times) {
		try {
			//出力先を作成する
			FileWriter fw = new FileWriter("D:\\work\\lab\\gean\\gean-average.csv", false);  //※１
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
        
			//ヘッダー書き込み
			pw.print("");
			pw.print(",");
			pw.print("B");
			pw.print(",");
			pw.print("L");
			pw.println();
        
			
			
			for(int i=0;i<n;i++) {
				pw.print(i);
				pw.print(",");
				pw.print(b[i]/500);
				pw.print(",");
				pw.print(l[i]/500);
				pw.println();
			}

			//ファイルに書き出す
			pw.close();

			//終了メッセージを画面に出力する
			//System.out.println(name+"_"+times+"_"+steps+"("+num+"ターン目)の出力が完了しました。");

		} catch (IOException ex) {
			//例外時処理
			ex.printStackTrace();
		}
	}
	
}
