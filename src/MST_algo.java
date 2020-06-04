//(1)GUI元件-> (2)設計讀檔 -> (3)取值-> (4)作圖 -> (5)排序法 -> (6)顯示結果 
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//宣告B3，並將其繼承JFrame物件。實作ActionListener，接收操作JFrame的事件。 
public class MST_algo extends JFrame implements ActionListener { 
	
	JButton bt, bt2, bt3, bt4, bt5, bt6;	//按鈕

	private JFileChooser fc;				//開啟檔案
	
	private File f;							//檔案
	
	Graphics2D G;							//以G採用2D圖形庫
	
	JPanel jp1, jp2;						//容器	
	
	JLabel jl1;								//標籤
	
	//GUI 元件顏色控制================
	Color CircleGray = new Color(192, 192, 192);
	
	Color NumberBlack = new Color(0, 0, 0);
	
	Color LineBlue = new Color(0, 0, 255);
	
	Color CostYellow = new Color(255, 200, 0);
	
	//============================
	int i, j, n;
	int p, e; 						//p:點數   |e:邊數
	int[] E1 = new int[50];			//邊起點
	int[] E2 = new int[50];			//邊終點
	int[] cost = new int[50];		//成本
	int[] pointx = new int[50];		//點的x座標
	int[] pointy = new int[50];		//點的y座標
	
	//B3建構子
	public MST_algo(String title){	
		setSize(500,500); 				//視窗大小

		//宣告物件========================
		fc = new JFileChooser();
		bt = new JButton("Open...");	
		bt2 = new JButton("Start");	
		bt3 = new JButton("Clear");	
		bt4 = new JButton("Kruskal");	
		bt5 = new JButton("Prim");	
		bt6 = new JButton("Exit");
				
		//標籤，用來顯示當前使用的演算法=========
		jl1 = new JLabel(" ");			//初始清空
		jl1.setForeground(Color.PINK); 
		jl1.setFont(new Font("標楷體", Font.BOLD, 12));
		
		//jp1(按鈕放底部，標籤放頂部)=========
		jp1 = new JPanel();
		add(jp1,BorderLayout.SOUTH);	//加入JFrame，設定為BorderLayout排版之南方
		jp2 = new JPanel();
		add(jp2,BorderLayout.NORTH);	//加入JFrame，設定為BorderLayout排版之北方

		//將各元件放入容器中=================
		jp1.add(bt);
		jp1.add(bt2);
		jp1.add(bt3);
		jp1.add(bt4);
		jp1.add(bt5);
		jp1.add(bt6);
		jp2.add(jl1);

		//將按鈕元件加上動作=================
		bt.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		bt5.addActionListener(this);
		bt6.addActionListener(this);

		setVisible(true);				//使GUI可見
		G = (Graphics2D)getGraphics();
	}
	
	//主程式
	public static void main(String argv[]){
		MST_algo app = new MST_algo("TEST"); 		//建立B3物件，視窗標題:TEST
	}
	
	//按鈕動作
	public void actionPerformed(ActionEvent e) {
		
		try {
			//OPen...
			if (e.getSource() == bt){
				int status = fc.showOpenDialog(null);

				if (status == JFileChooser.APPROVE_OPTION){		
					f = fc.getSelectedFile();	//取得所選定的檔案
				} 
			}
		
			//Start
			else if (e.getSource() == bt2){
				openFile(f);
				paint();
			}
		
			//Clear
			else if (e.getSource() == bt3){
				jl1.setText(" "); //清空jl2。
				repaint();
			}
		
			//Kruskal's Algorithm
			else if (e.getSource() == bt4){
				jl1.setText("Kruskal's Algorithm"); //標籤內容
				Kruskal();
			}
		
			//Prim's Algorithm
			else if (e.getSource() == bt5){
				jl1.setText("Prim's Algorithm"); //標籤內容
				Prim();
			}
		
			//Exit
			else if (e.getSource() == bt6){
				dispose();
				System.exit(0);	//結束程式
			}
		}catch(IOException ex) {
			System.out.println(ex);
		}	
	}
	
	//開啟檔案
	private void openFile(File f) throws IOException{
		
		FileReader fileReader = new FileReader(f);
		
		//建立BufferedReader 的串流
		BufferedReader br = new BufferedReader(fileReader);
			
		String strs[];
		String line;
		
		while ((line = br.readLine()) != null) { //讀取資料
		
			strs = line.split(","); //以逗號區隔，形成數個陣列資料
			i = 0;
		
			//點
			p = Integer.valueOf(strs[i]);
			++i;
		
			//邊
			e = Integer.valueOf(strs[i]);
			++i;
		
			//距離
			for (j = 0; j < e; ++j){ //有e個邊，共e組資料
			
				//邊的起點
				E1[j] = Integer.valueOf(strs[i]);
				++i;
				
				//邊的終點
				E2[j] = Integer.valueOf(strs[i]);
				++i;
				
				//成本
				cost[j] = Integer.valueOf(strs[i]);
				++i;
			}
		
			//點座標
			for (j = 0; j < p; ++j){ //有p個點，共p組資料
			
				pointx[j] = Integer.valueOf(strs[i]);
				++i;
			
				pointy[j] = Integer.valueOf(strs[i]);
				++i;
			}
		}
		br.close();
	}
	
	//初始畫圖布局(點、線、成本)
	private void paint(){	

		G.setStroke(new BasicStroke(1.0f)); //畫筆的寬度(1.0)
		
		//邊
		for (i = 0; i < e; ++i){
			G.setColor(LineBlue);
			G.drawLine(pointx[E1[i] - 1] + 15, pointy[E1[i] - 1] + 15, pointx[E2[i] - 1] + 15, pointy[E2[i] - 1] + 15);
		}
		
		//Cost
		for (i = 0; i < e; ++i){
			G.setColor(CostYellow);
			G.setFont(new Font("Aharoni",Font.BOLD,25));
			G.drawString(String.valueOf(cost[i]), (pointx[E1[i] - 1] + pointx[E2[i] - 1]) / 2 + 10, (pointy[E1[i] - 1] + pointy[E2[i] - 1]) / 2 + 10);
		}
		
		//點
		for (i = 0; i < p; ++i){
			//實心圓
			G.setColor(CircleGray);
			G.fillOval(pointx[i],pointy[i],30,30);	
			
			//圓上的字
			G.setColor(Color.RED);
			G.setFont(new Font("Gill Sans MT",Font.BOLD,25));
			G.drawString(String.valueOf(i+1), pointx[i] + 10, pointy[i] + 20);
		}	
	}
	
	//Kruskal 演算法
	private void Kruskal(){
		
		//將資料依成本小至大排序====================
		int Tem1, Tem2; //暫存
		for (i = 0; i < e; ++i){
			n = cost[i];
			Tem1 = E1[i];
			Tem2 = E2[i];
			
			for (j = i - 1; j >= 0 && cost[j] > n; --j){
				cost[j+1] = cost[j];
				E1[j+1] = E1[j];
				E2[j+1] = E2[j];
			}
			
			cost[j+1] = n;
			E1[j+1] = Tem1;
			E2[j+1] = Tem2;
		}
		
		//演算法開始============================
		int[] vends = new int[p];	//某點所屬集合
		
		n = 0;
		int Times = 0, flag = 1;	//Times:當前MST完成邊數 |flag:判斷邊是否可以成為MST(1:可以，無迴路)
		
		//定義個點初始屬於不同集合
		for (i = 0; i < p; ++i){
			vends[i] = i;
		}
		
		//進行(總點數p-1)次 = MST的邊數，從排序後成本最小的E1[0]、E2[0]開始
		while (Times < p - 1){
			//如果屬於同意集合會形成迴路，++n跳過===============
			if (vends[E1[n]-1] == vends[E2[n]-1])
				++n;
			else
				flag = 1;
			
			//得到MST的邊，開始畫=========================
			if (flag == 1){
			
				G.setStroke(new BasicStroke(2.0f));
				G.setColor(NumberBlack);
				
				//邊
				G.drawLine(pointx[E1[n]-1] + 15, pointy[E1[n]-1] + 15, pointx[E2[n]-1] + 15, pointy[E2[n]-1] + 15);
				G.setStroke(new BasicStroke(5.0f));
				//點
				G.drawOval(pointx[E1[n]-1],pointy[E1[n]-1],30,30);
				G.drawOval(pointx[E2[n]-1],pointy[E2[n]-1],30,30);
			
				++Times;	//MST新增一條邊
				
				vends[E2[n]-1] = vends[E1[n]-1];	//歸為同一集合
				
				++n;
				
				//暫停0.5秒===========================
				try {
					Thread.sleep(500);
				}catch(Exception e){}
				
				flag = 0;
			}	
		}
	}
	
	//Prim 演算法
	private void Prim(){
		
		int[][] w = new int[e][e];		 //記錄從前[]到後[]的成本，形成權重陣列
		int[] dis = new int[p];			 //紀錄目前的MST到圖上各點的距離
		int[] parent = new int[p];		 //紀錄各個點在MST上的父親
		Boolean[] visit= new Boolean[p]; //紀錄各個點是不是已在MST之中(True:是)
		
		//將權重陣列初始化(-1表示沒資料)
		for (i = 0; i < e; ++i){
			for (j = 0; j < e; ++j){
				w[i][j] = -1;
			}
		}
		
		//將資料匯入權重陣列
		for (i = 0; i < e; ++i){
			w[E1[i]-1][E2[i]-1] = cost[i];
			w[E2[i]-1][E1[i]-1] = cost[i];
		}
		
		//初始設定各點都不在MST，且到MST的距離無限大
		for (i = 0; i < p; ++i) 
			visit[i] = false;
	    for (i = 0; i < p; ++i) 
	    	dis[i] = 999;
	    
	    //將起始點設為1(陣列從0開始)===================
	    dis[0] = 0; 	
	    parent[0] = 0;
	    
	    //演算法開始==============================
	    for (i = 0; i < p; ++i){
	    	
	        int a = -1, b = -1, min = 999;	//a:紀錄MST新找到的邊的終點
	        
	        for (int j = 0; j < p; ++j)
	        	
	        	//從非MST的點尋找距離最近的(且不為初始值-1)
	            if (!visit[j] &&( dis[j] < min && dis[j] != -1)){
	                a = j;
	                min = dis[j];
	            }
	        
	        if (a == -1) 
	        	break;				//MST已找完
	        
	        visit[a] = true;		//該點進入MST
	        dis[a] = -1;			//在MST內的點，將距離重設為-1
	        
	        //找到MST開始作畫(以a及其父親形成的連線)=======
	        G.setStroke(new BasicStroke(2.0f));
			G.setColor(NumberBlack);
			
			//邊
			G.drawLine(pointx[parent[a]] + 15, pointy[parent[a]] + 15, pointx[a] + 15, pointy[a] + 15);
			G.setStroke(new BasicStroke(5.0f));
			//點
			G.drawOval(pointx[parent[a]], pointy[parent[a]], 30, 30);
			G.drawOval(pointx[a], pointy[a], 30, 30);  
			
			//暫停0.5秒===========================
			try {
				Thread.sleep(500);
			}catch(Exception e){}
			
			//更新各點到MST的距離====================
	        for (b = 0 ; b < p ; ++b){
	        	
	            if (!visit[b] && ( w[a][b] < dis[b] && w[a][b] != -1)){
	                dis[b] = w[a][b]; // 離樹最近
	                parent[b] = a;
	            }

	        	if (!visit[b] && ( w[b][a] < dis[b] && w[b][a] != -1)){
	        		dis[b] = w[b][a]; // 離樹最近
	        		parent[b] = a;
	        	}
	        }
	    }
	}
}