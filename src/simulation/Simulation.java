package simulation;

import agent.*;
import core.*;
import general_metanorms_game.*;
import network.*;
import random.*;
import genetic_algorithm.*;

public class Simulation {
	public static void start(int seed_num,int n,int n0,int edge) {
		//Sfmt rnd= new Sfmt(seed_num);
		MakeRnd rnd=new MakeRnd(seed_num);
		int times=2000;	//シミュレーション回数
		int world=30;
		Agent[] agents=new Agent[n];
		
		Agent[][] agents_d=new Agent[world][n];
		
		//Create Agents
		for(int i=0;i<n;i++) {
			agents[i]=new Agent(i);
			agents[i].createGene(rnd);
			//agents[i].showBits(i);
		}
		
		for(int i=0;i<world;i++) {
			for(int j=0;j<n;j++) {
				agents_d[i][j]=new Agent(i);
				agents_d[i][j].createGene(rnd);
				//agents_d[i][j].showBits(j);
			}
		}
		
		BA ba=new BA(agents,agents_d,n,n0,edge,world,rnd);
		ba.BAmodel();
		
		System.out.println("Making network completed.");
		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
		
		CalculationBVL.SetHeader();
		//MetaPunishment punishment =new MetaPunishment(n,3.0,-1.0,-9.0,-2.0,-9.0,-2.0); 
		dMRG reward =new dMRG(n,-3.0,1.0,-2.0,9.0,-2.0,9.0); 
		dMRG_d[] reward_d;
		reward_d=new dMRG_d[world];
		MakeRnd[] r;
		r=new MakeRnd[world];
		Sync sync = new Sync();
		
		double[] sumB=new double[n];
		double[] sumL=new double[n];
		
		double tmpB=0;
		double tmpL=0;
		
		for(int i=0;i<n;i++) {
			sumB[i]=0;
			sumL[i]=0;
		}
		
		for(int i=0;i<times;i++) {
			
			//punishment.game(i,agents,rnd);
			reward.game(i,agents,rnd);
			
			for(int j=0;j<world;j++) {
				r[j]=new MakeRnd(j+10);
				reward_d[j]=new dMRG_d(sync,n,-3.0,1.0,-2.0,9.0,-2.0,9.0,agents_d[j],r[j],i,j);
				reward_d[j].start();
			}
			
			sync.waitSync();
			
			PrintWorldScore ps=new PrintWorldScore(i,world);
			ps.makeFile(times,n);
			ps.writeScore(times, n, agents_d);
			ps.closeFile();
			
			UniformCrossover uc=new UniformCrossover(n,world,agents,agents_d);
			//UniformCrossoverReward ucr=new UniformCrossoverReward(n);
			uc.ga(i,rnd);
			//ucr.ga(i,agents,rnd);
			
			
			CalculationBVL.Export(agents,n,i);

			for(int j=0;j<n;j++) {
				agents[j].resetScore();
				//agents[j].printBL();
				for(int k=0;k<world;k++) {
					agents_d[k][j].resetScore();
				}
			}
			//各エージェントの平均を出力するように書き換え
			for(int j=0;j<n;j++) {
				double tmpb=0.0;
				double tmpl=0.0;
				tmpb+=agents[j].getAgentValueBL(0)/7;
				tmpl+=agents[j].getAgentValueBL(1)/7;
				for(int w=0;w<world;w++) {
					tmpb+=agents_d[w][j].getAgentValueBL(0)/7;
					tmpl+=agents_d[w][j].getAgentValueBL(1)/7;
				}
				tmpb/=(world+1);
				tmpl/=(world+1);
				PrintGean.Export(tmpb,tmpl, n, i,j);
			}
			//PrintGean.Export(agents, n, i);
			
			if(i>=1500) {
				for(int j=0;j<n;j++) {
					tmpB=0;
					tmpL=0;
					tmpB+=agents[j].getAgentValueBL(0)/7;
					tmpL+=agents[j].getAgentValueBL(1)/7;
					for(int w=0;w<world;w++) {
						tmpB+=agents_d[w][j].getAgentValueBL(0)/7;
						tmpL+=agents_d[w][j].getAgentValueBL(1)/7;
					}
					tmpB/=(world+1);
					tmpL/=(world+1);
					sumB[j]+=tmpB;
					sumL[j]+=tmpL;
				}
			}
			
			System.out.println("finish step"+i);
		}
		PrintGean.ExportAverage(agents, n, sumB, sumL, times);
		System.out.println("Finished 2000gen");
		
		
		
	}
	
}
