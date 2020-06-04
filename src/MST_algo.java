//(1)GUI����-> (2)�]�pŪ�� -> (3)����-> (4)�@�� -> (5)�ƧǪk -> (6)��ܵ��G 
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

//�ŧiB3�A�ñN���~��JFrame����C��@ActionListener�A�����ާ@JFrame���ƥ�C 
public class MST_algo extends JFrame implements ActionListener { 
	
	JButton bt, bt2, bt3, bt4, bt5, bt6;	//���s

	private JFileChooser fc;				//�}���ɮ�
	
	private File f;							//�ɮ�
	
	Graphics2D G;							//�HG�ĥ�2D�ϧήw
	
	JPanel jp1, jp2;						//�e��	
	
	JLabel jl1;								//����
	
	//GUI �����C�ⱱ��================
	Color CircleGray = new Color(192, 192, 192);
	
	Color NumberBlack = new Color(0, 0, 0);
	
	Color LineBlue = new Color(0, 0, 255);
	
	Color CostYellow = new Color(255, 200, 0);
	
	//============================
	int i, j, n;
	int p, e; 						//p:�I��   |e:���
	int[] E1 = new int[50];			//��_�I
	int[] E2 = new int[50];			//����I
	int[] cost = new int[50];		//����
	int[] pointx = new int[50];		//�I��x�y��
	int[] pointy = new int[50];		//�I��y�y��
	
	//B3�غc�l
	public MST_algo(String title){	
		setSize(500,500); 				//�����j�p

		//�ŧi����========================
		fc = new JFileChooser();
		bt = new JButton("Open...");	
		bt2 = new JButton("Start");	
		bt3 = new JButton("Clear");	
		bt4 = new JButton("Kruskal");	
		bt5 = new JButton("Prim");	
		bt6 = new JButton("Exit");
				
		//���ҡA�Ψ���ܷ�e�ϥΪ��t��k=========
		jl1 = new JLabel(" ");			//��l�M��
		jl1.setForeground(Color.PINK); 
		jl1.setFont(new Font("�з���", Font.BOLD, 12));
		
		//jp1(���s�񩳳��A���ҩ񳻳�)=========
		jp1 = new JPanel();
		add(jp1,BorderLayout.SOUTH);	//�[�JJFrame�A�]�w��BorderLayout�ƪ����n��
		jp2 = new JPanel();
		add(jp2,BorderLayout.NORTH);	//�[�JJFrame�A�]�w��BorderLayout�ƪ����_��

		//�N�U�����J�e����=================
		jp1.add(bt);
		jp1.add(bt2);
		jp1.add(bt3);
		jp1.add(bt4);
		jp1.add(bt5);
		jp1.add(bt6);
		jp2.add(jl1);

		//�N���s����[�W�ʧ@=================
		bt.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		bt5.addActionListener(this);
		bt6.addActionListener(this);

		setVisible(true);				//��GUI�i��
		G = (Graphics2D)getGraphics();
	}
	
	//�D�{��
	public static void main(String argv[]){
		MST_algo app = new MST_algo("TEST"); 		//�إ�B3����A�������D:TEST
	}
	
	//���s�ʧ@
	public void actionPerformed(ActionEvent e) {
		
		try {
			//OPen...
			if (e.getSource() == bt){
				int status = fc.showOpenDialog(null);

				if (status == JFileChooser.APPROVE_OPTION){		
					f = fc.getSelectedFile();	//���o�ҿ�w���ɮ�
				} 
			}
		
			//Start
			else if (e.getSource() == bt2){
				openFile(f);
				paint();
			}
		
			//Clear
			else if (e.getSource() == bt3){
				jl1.setText(" "); //�M��jl2�C
				repaint();
			}
		
			//Kruskal's Algorithm
			else if (e.getSource() == bt4){
				jl1.setText("Kruskal's Algorithm"); //���Ҥ��e
				Kruskal();
			}
		
			//Prim's Algorithm
			else if (e.getSource() == bt5){
				jl1.setText("Prim's Algorithm"); //���Ҥ��e
				Prim();
			}
		
			//Exit
			else if (e.getSource() == bt6){
				dispose();
				System.exit(0);	//�����{��
			}
		}catch(IOException ex) {
			System.out.println(ex);
		}	
	}
	
	//�}���ɮ�
	private void openFile(File f) throws IOException{
		
		FileReader fileReader = new FileReader(f);
		
		//�إ�BufferedReader ����y
		BufferedReader br = new BufferedReader(fileReader);
			
		String strs[];
		String line;
		
		while ((line = br.readLine()) != null) { //Ū�����
		
			strs = line.split(","); //�H�r���Ϲj�A�Φ��ƭӰ}�C���
			i = 0;
		
			//�I
			p = Integer.valueOf(strs[i]);
			++i;
		
			//��
			e = Integer.valueOf(strs[i]);
			++i;
		
			//�Z��
			for (j = 0; j < e; ++j){ //��e����A�@e�ո��
			
				//�䪺�_�I
				E1[j] = Integer.valueOf(strs[i]);
				++i;
				
				//�䪺���I
				E2[j] = Integer.valueOf(strs[i]);
				++i;
				
				//����
				cost[j] = Integer.valueOf(strs[i]);
				++i;
			}
		
			//�I�y��
			for (j = 0; j < p; ++j){ //��p���I�A�@p�ո��
			
				pointx[j] = Integer.valueOf(strs[i]);
				++i;
			
				pointy[j] = Integer.valueOf(strs[i]);
				++i;
			}
		}
		br.close();
	}
	
	//��l�e�ϥ���(�I�B�u�B����)
	private void paint(){	

		G.setStroke(new BasicStroke(1.0f)); //�e�����e��(1.0)
		
		//��
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
		
		//�I
		for (i = 0; i < p; ++i){
			//��߶�
			G.setColor(CircleGray);
			G.fillOval(pointx[i],pointy[i],30,30);	
			
			//��W���r
			G.setColor(Color.RED);
			G.setFont(new Font("Gill Sans MT",Font.BOLD,25));
			G.drawString(String.valueOf(i+1), pointx[i] + 10, pointy[i] + 20);
		}	
	}
	
	//Kruskal �t��k
	private void Kruskal(){
		
		//�N��ƨ̦����p�ܤj�Ƨ�====================
		int Tem1, Tem2; //�Ȧs
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
		
		//�t��k�}�l============================
		int[] vends = new int[p];	//�Y�I���ݶ��X
		
		n = 0;
		int Times = 0, flag = 1;	//Times:��eMST������� |flag:�P�_��O�_�i�H����MST(1:�i�H�A�L�j��)
		
		//�w�q���I��l�ݩ󤣦P���X
		for (i = 0; i < p; ++i){
			vends[i] = i;
		}
		
		//�i��(�`�I��p-1)�� = MST����ơA�q�Ƨǫᦨ���̤p��E1[0]�BE2[0]�}�l
		while (Times < p - 1){
			//�p�G�ݩ�P�N���X�|�Φ��j���A++n���L===============
			if (vends[E1[n]-1] == vends[E2[n]-1])
				++n;
			else
				flag = 1;
			
			//�o��MST����A�}�l�e=========================
			if (flag == 1){
			
				G.setStroke(new BasicStroke(2.0f));
				G.setColor(NumberBlack);
				
				//��
				G.drawLine(pointx[E1[n]-1] + 15, pointy[E1[n]-1] + 15, pointx[E2[n]-1] + 15, pointy[E2[n]-1] + 15);
				G.setStroke(new BasicStroke(5.0f));
				//�I
				G.drawOval(pointx[E1[n]-1],pointy[E1[n]-1],30,30);
				G.drawOval(pointx[E2[n]-1],pointy[E2[n]-1],30,30);
			
				++Times;	//MST�s�W�@����
				
				vends[E2[n]-1] = vends[E1[n]-1];	//�k���P�@���X
				
				++n;
				
				//�Ȱ�0.5��===========================
				try {
					Thread.sleep(500);
				}catch(Exception e){}
				
				flag = 0;
			}	
		}
	}
	
	//Prim �t��k
	private void Prim(){
		
		int[][] w = new int[e][e];		 //�O���q�e[]���[]�������A�Φ��v���}�C
		int[] dis = new int[p];			 //�����ثe��MST��ϤW�U�I���Z��
		int[] parent = new int[p];		 //�����U���I�bMST�W������
		Boolean[] visit= new Boolean[p]; //�����U���I�O���O�w�bMST����(True:�O)
		
		//�N�v���}�C��l��(-1��ܨS���)
		for (i = 0; i < e; ++i){
			for (j = 0; j < e; ++j){
				w[i][j] = -1;
			}
		}
		
		//�N��ƶפJ�v���}�C
		for (i = 0; i < e; ++i){
			w[E1[i]-1][E2[i]-1] = cost[i];
			w[E2[i]-1][E1[i]-1] = cost[i];
		}
		
		//��l�]�w�U�I�����bMST�A�B��MST���Z���L���j
		for (i = 0; i < p; ++i) 
			visit[i] = false;
	    for (i = 0; i < p; ++i) 
	    	dis[i] = 999;
	    
	    //�N�_�l�I�]��1(�}�C�q0�}�l)===================
	    dis[0] = 0; 	
	    parent[0] = 0;
	    
	    //�t��k�}�l==============================
	    for (i = 0; i < p; ++i){
	    	
	        int a = -1, b = -1, min = 999;	//a:����MST�s��쪺�䪺���I
	        
	        for (int j = 0; j < p; ++j)
	        	
	        	//�q�DMST���I�M��Z���̪�(�B������l��-1)
	            if (!visit[j] &&( dis[j] < min && dis[j] != -1)){
	                a = j;
	                min = dis[j];
	            }
	        
	        if (a == -1) 
	        	break;				//MST�w�䧹
	        
	        visit[a] = true;		//���I�i�JMST
	        dis[a] = -1;			//�bMST�����I�A�N�Z�����]��-1
	        
	        //���MST�}�l�@�e(�Ha�Ψ���˧Φ����s�u)=======
	        G.setStroke(new BasicStroke(2.0f));
			G.setColor(NumberBlack);
			
			//��
			G.drawLine(pointx[parent[a]] + 15, pointy[parent[a]] + 15, pointx[a] + 15, pointy[a] + 15);
			G.setStroke(new BasicStroke(5.0f));
			//�I
			G.drawOval(pointx[parent[a]], pointy[parent[a]], 30, 30);
			G.drawOval(pointx[a], pointy[a], 30, 30);  
			
			//�Ȱ�0.5��===========================
			try {
				Thread.sleep(500);
			}catch(Exception e){}
			
			//��s�U�I��MST���Z��====================
	        for (b = 0 ; b < p ; ++b){
	        	
	            if (!visit[b] && ( w[a][b] < dis[b] && w[a][b] != -1)){
	                dis[b] = w[a][b]; // ����̪�
	                parent[b] = a;
	            }

	        	if (!visit[b] && ( w[b][a] < dis[b] && w[b][a] != -1)){
	        		dis[b] = w[b][a]; // ����̪�
	        		parent[b] = a;
	        	}
	        }
	    }
	}
}