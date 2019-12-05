
import java.awt.event.*;
import java.awt.*;
 
import javax.swing.*;
/*
 五子棋主框架類，菜单显示 Backgammon main frame class, menu display
 */
public class StartChessJFrame extends JFrame {
  private ChessBoard chessBoard;
  private JPanel toolbar;
  private JButton startButton,backButton,exitButton;
  
  private JMenuBar menuBar;
  private JMenu sysMenu;
  private JMenuItem startMenuItem,exitMenuItem,backMenuItem;
  private JLabel label;
  
  public StartChessJFrame(){
	  setTitle("FiveChess");//设置标题 Set title
	  chessBoard=new ChessBoard();
	
	  
	  Container contentPane=getContentPane();
	  contentPane.add(chessBoard);
	  chessBoard.setOpaque(true);
	  
	  
	  //创建和添加菜单 Create and add menus
	  menuBar =new JMenuBar();//初始化菜单栏Initialize the menu bar
	  sysMenu=new JMenu("menu");//初始化菜单Initialization menu
	  //初始化菜单项 Initialize menu items
	  startMenuItem=new JMenuItem("Restart");
	  exitMenuItem =new JMenuItem("Exit");
	  backMenuItem =new JMenuItem("Regret");
	  //将三个菜单项添加到页面上 Add three menu items to the page
	  sysMenu.add(startMenuItem);
	  sysMenu.add(exitMenuItem);
	  sysMenu.add(backMenuItem);
	
	  MyItemListener lis=new MyItemListener();
	  //将三个菜单注册到事件监听器上 Register three menus to the event listener
	  this.startMenuItem.addActionListener(lis);
	  backMenuItem.addActionListener(lis);
	  exitMenuItem.addActionListener(lis);
	  menuBar.add(sysMenu);//将系统菜单添加到菜单栏上 Add the system menu to the menu bar
	  setJMenuBar(menuBar);//将menuBar设置为菜单栏 Set menuBar as the menu bar
	  
	  toolbar=new JPanel();//工具面板实例化 Tool panel instantiation
      
	  startButton=new JButton("Restart");
	  exitButton=new JButton("Exit");
	  backButton=new JButton("Regret");
	 // 将工具面板按钮用FlowLayout布局 Layout tool panel buttons with FlowLayout
	  toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
	  //将三个按钮添加到工具面板 Add three buttons to the tools panel
	  toolbar.add(startButton);
	  toolbar.add(exitButton);
	  toolbar.add(backButton);
	  //将三个按钮注册监听事件 Register three buttons to listen for events
	  startButton.addActionListener(lis);
	  exitButton.addActionListener(lis);
	  backButton.addActionListener(lis);
	  //将工具面板布局到界面”南方“也就是下方  Put the tool panel layout below
	  getContentPane().add(toolbar,BorderLayout.SOUTH);
	  label = new JLabel(" ");
	  toolbar.add(label);
	
	  getContentPane().add(chessBoard);//将面板对象添加到窗体上 Adding panel objects to a form
	  //设置界面关闭事件 Set interface close event
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  //setSize(800,800);
	  pack();//自适应大小
	  
  }
 
  private class MyItemListener implements ActionListener{
	  public void actionPerformed(ActionEvent e){
		  Object obj=e.getSource();//获得事件源 Get event source
		  if(obj==StartChessJFrame.this.startMenuItem||obj==startButton){
			  //重新开始 Restart
			  //JFiveFrame.this内部类引用外部类
			  label.setText("Regret...");
			  //System.out.println("Restart");
			  chessBoard.restartGame();
		  }
		  else if (obj==exitMenuItem||obj==exitButton)
			  System.exit(0);
		  else if (obj==backMenuItem||obj==backButton){
			  label.setText("Regret...");
			  //System.out.println("Regret...");
			  chessBoard.goback();
		  }
	  }
  }
  
  
  
  public static void main(String[] args){
	  StartChessJFrame f=new StartChessJFrame();//创建主框架Create the main frame
	  f.setVisible(true);//显示主框架 Show main frame
	  
  }
}