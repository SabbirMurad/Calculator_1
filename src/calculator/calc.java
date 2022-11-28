package calculator;
import java.util.Stack;
import java.util.Vector;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.MatteBorder;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Frame;

public class calc {

	private JFrame frmCalculator;
	private JTextField screen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					calc window = new calc();
					window.frmCalculator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public calc() {
		initialize();
	}
	//changing the string to postfix
	  public static Stack<String> postfix(String str){
		    Stack<String> exp_holder=new Stack<>();
		    Stack<Character> comp_holder=new Stack<>();
		    str+=")";
		    comp_holder.push('(');
		    String temp="";
		    for(int i=0;i<str.length();i++){
				if(str.charAt(i)=='+'||str.charAt(i)=='-'){
			        if(comp_holder.peek()=='+' || comp_holder.peek()=='-'||comp_holder.peek()=='*' || comp_holder.peek()=='/'){
			        	String s=String.valueOf(comp_holder.pop());
			            exp_holder.push(s);
			            comp_holder.push(str.charAt(i));
			        }
			        else{
			        	comp_holder.push(str.charAt(i));
			        }
				}
				else if(str.charAt(i)=='*'||str.charAt(i)=='/'){
					if(comp_holder.peek()=='*' || comp_holder.peek()=='/'){
						String s=String.valueOf(comp_holder.pop());
						exp_holder.push(s);
						comp_holder.push(str.charAt(i));
					}
			        else{
			        	comp_holder.push(str.charAt(i));
			        }
				}
				else if(str.charAt(i)=='^'){
					if(comp_holder.peek()=='^'){
						String s=String.valueOf(comp_holder.pop());
						exp_holder.push(s);
						comp_holder.push(str.charAt(i));
			        }
			        else{
			        	comp_holder.push(str.charAt(i));
			        }
				}
				else if(str.charAt(i)=='('){
					if(i>0 && Character.isDigit(str.charAt(i-1))){
						if(comp_holder.peek()=='*' || comp_holder.peek()=='/'){
							String s=String.valueOf(comp_holder.pop());
							exp_holder.push(s);
							comp_holder.push('*');
						}
						else{
							comp_holder.push('*');
						}
					}
					comp_holder.push(str.charAt(i));
				}
				else if(str.charAt(i)==')'){
					while (comp_holder.peek()!='('){
						String s="";
						s+=comp_holder.pop();
						exp_holder.push(s);
						if(comp_holder.size()==0){
							break;
						}
					}
					comp_holder.pop();
				}
				else{
					temp+=str.charAt(i);
					if(str.charAt(i+1)=='+'||str.charAt(i+1)=='-'||str.charAt(i+1)=='*'||str.charAt(i+1)=='/'||str.charAt(i+1)==')'||str.charAt(i+1)=='('||str.charAt(i+1)=='^'){
						exp_holder.push(temp);
						temp="";
					}
				}
			}
			return exp_holder;
		}
	  //calculating value from postfix expression
	  public static double calcNum(String str){
		    Vector<String> post = postfix(str);
		    Stack<Double>holder =new Stack<>();
		    
		    for(int i=0;i<post.size();i++){
		        String s=post.get(i);
		        char ch;
		        ch=post.get(i).charAt(0);
		        if(ch=='+'||ch=='-'||ch=='*'||ch=='/'||ch=='^'){
		            double a=holder.pop();
		            double b;
		            if(holder.size()==0) {
		            	b=0;
		            }
		            else {
		            	b=holder.pop();
		            }
		            if(ch=='+'){
		                holder.push(b+a);
		            }
		            else if(ch=='-'){
		                holder.push(b-a);
		            }
		            else if(ch=='*'){
		                holder.push(b*a);
		            }
		            else if(ch=='/'){
		                holder.push(b/a);
		            }
		            else if(ch=='^'){
		                holder.push((Math.pow(b,a)));
		            }
		        }
		        else{
		            holder.push(Double.parseDouble(s));
		        }
		    }
		    return holder.peek();
		}
	//getting the result
	public static String getResult(String str) {
		String ss;
		try {
			ss=String.valueOf(calcNum(str));
		}
		catch(Exception ex) {
			ss="Error";
		}
		return ss;
	}
	//getting the value to insert
	boolean valueSolved=false;
	private JTextField history_screen;
	
	public String addText(String s) {
		if(s=="c") {
			return "";
		}
		else if(s=="<") {
			String value=screen.getText().toString();
			value=value.substring(0,value.length()-1);
			return value;
		}
		else if(s=="¹/x") {
			String value=screen.getText().toString();
			value="1/("+value+")";
			return value;
		}
		else if(s=="=") {
			valueSolved=true;
			String value=screen.getText().toString();
			history_screen.setText(value);
			screen.setForeground(new Color(240,180,16));
			return getResult(value);
		}
		else {
			String value=screen.getText().toString();
			if(valueSolved) {
				history_screen.setText(value);
				value = "";
				valueSolved=false;
				screen.setForeground(Color.WHITE);
			}
			value+=s;
			return value;
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCalculator = new JFrame();
		frmCalculator.setBackground(new Color(36, 37, 48));
		frmCalculator.setTitle("Calculator");
		frmCalculator.setForeground(Color.GRAY);
		
		frmCalculator.getContentPane().setBackground(new Color(36, 37, 48));
		frmCalculator.setBounds(100, 100, 302, 525);
		frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculator.getContentPane().setLayout(null);
		
		screen = new JTextField();
		screen.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		screen.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(60, 61, 73)));
		
		screen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		screen.setFont(new Font("Tahoma", Font.PLAIN, 24));
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setForeground(Color.WHITE);
		screen.setBackground(new Color(36, 37, 48));
		screen.setBounds(10, 48, 266, 49);
		frmCalculator.getContentPane().add(screen);
		screen.setColumns(10);
		
		//------------------------------------------
		JButton btn_00 = new JButton("00");
		btn_00.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_00.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("00"));
			}
		});
		btn_00.setForeground(Color.WHITE);
		btn_00.setFont(new Font("Arial", Font.BOLD, 16));
		btn_00.setFocusPainted(false);
		btn_00.setBorderPainted(false);
		btn_00.setBackground(new Color(46, 47, 62));
		btn_00.setBounds(79, 405, 59, 48);
		frmCalculator.getContentPane().add(btn_00);
		btn_00.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_00.setBackground(new Color(240, 180, 16));
			}
		});
		btn_00.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_00.setBackground(new Color(46, 47, 62));
			}
		});
		btn_00.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_0 = new JButton("0");
		btn_0.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("0"));
			}
		});
		btn_0.setFont(new Font("Arial", Font.BOLD, 16));
		btn_0.setFocusPainted(false);
		btn_0.setBorderPainted(false);
		btn_0.setForeground(Color.WHITE);
		btn_0.setBackground(new Color(46, 47, 62));
		btn_0.setBounds(10, 405, 59, 48);
		frmCalculator.getContentPane().add(btn_0);
		btn_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_0.setBackground(new Color(240, 180, 16));
			}
		});
		btn_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_0.setBackground(new Color(46, 47, 62));
			}
		});
		btn_0.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_1 = new JButton("1");
		btn_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("1"));
			}
		});
		btn_1.setFont(new Font("Arial", Font.BOLD, 16));
		btn_1.setFocusPainted(false);
		btn_1.setBorderPainted(false);
		btn_1.setForeground(Color.WHITE);
		btn_1.setBackground(new Color(46, 47, 62));
		btn_1.setBounds(10, 346, 59, 48);
		frmCalculator.getContentPane().add(btn_1);
		btn_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_1.setBackground(new Color(240, 180, 16));
			}
		});
		btn_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_1.setBackground(new Color(46, 47, 62));
			}
		});
		btn_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_2 = new JButton("2");
		btn_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("2"));
			}
		});
		btn_2.setFont(new Font("Arial", Font.BOLD, 16));
		btn_2.setFocusPainted(false);
		btn_2.setBorderPainted(false);
		btn_2.setForeground(Color.WHITE);
		btn_2.setBackground(new Color(46, 47, 62));
		btn_2.setBounds(79, 346, 59, 48);
		frmCalculator.getContentPane().add(btn_2);
		btn_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_2.setBackground(new Color(240, 180, 16));
			}
		});
		btn_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_2.setBackground(new Color(46, 47, 62));
			}
		});
		btn_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_3 = new JButton("3");
		btn_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("3"));
			}
		});
		btn_3.setFont(new Font("Arial", Font.BOLD, 16));
		btn_3.setFocusPainted(false);
		btn_3.setBorderPainted(false);
		btn_3.setForeground(Color.WHITE);
		btn_3.setBackground(new Color(46, 47, 62));
		btn_3.setBounds(148, 346, 59, 48);
		frmCalculator.getContentPane().add(btn_3);
		btn_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_3.setBackground(new Color(240, 180, 16));
			}
		});
		btn_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_3.setBackground(new Color(46, 47, 62));
			}
		});
		btn_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_4 = new JButton("4");
		btn_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("4"));
			}
		});
		btn_4.setFont(new Font("Arial", Font.BOLD, 16));
		btn_4.setFocusPainted(false);
		btn_4.setBorderPainted(false);
		btn_4.setForeground(Color.WHITE);
		btn_4.setBackground(new Color(46, 47, 62));
		btn_4.setBounds(10, 288, 59, 48);
		frmCalculator.getContentPane().add(btn_4);
		btn_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_4.setBackground(new Color(240, 180, 16));
			}
		});
		btn_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_4.setBackground(new Color(46, 47, 62));
			}
		});
		btn_4.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_5 = new JButton("5");
		btn_5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("5"));
			}
		});
		btn_5.setFont(new Font("Arial", Font.BOLD, 16));
		btn_5.setFocusPainted(false);
		btn_5.setBorderPainted(false);
		btn_5.setForeground(Color.WHITE);
		btn_5.setBackground(new Color(46, 47, 62));
		btn_5.setBounds(79, 288, 59, 48);
		frmCalculator.getContentPane().add(btn_5);
		btn_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_5.setBackground(new Color(240, 180, 16));
			}
		});
		btn_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_5.setBackground(new Color(46, 47, 62));
			}
		});
		btn_5.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_6 = new JButton("6");
		btn_6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("6"));
			}
		});
		btn_6.setFont(new Font("Arial", Font.BOLD, 16));
		btn_6.setFocusPainted(false);
		btn_6.setBorderPainted(false);
		btn_6.setForeground(Color.WHITE);
		btn_6.setBackground(new Color(46, 47, 62));
		btn_6.setBounds(148, 288, 59, 48);
		frmCalculator.getContentPane().add(btn_6);
		btn_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_6.setBackground(new Color(240, 180, 16));
			}
		});
		btn_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_6.setBackground(new Color(46, 47, 62));
			}
		});
		btn_6.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_7 = new JButton("7");
		btn_7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("7"));
			}
		});
		btn_7.setFont(new Font("Arial", Font.BOLD, 16));
		btn_7.setFocusPainted(false);
		btn_7.setBorderPainted(false);
		btn_7.setForeground(Color.WHITE);
		btn_7.setBackground(new Color(46, 47, 62));
		btn_7.setBounds(10, 229, 59, 48);
		frmCalculator.getContentPane().add(btn_7);
		btn_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_7.setBackground(new Color(240, 180, 16));
			}
		});
		btn_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_7.setBackground(new Color(46, 47, 62));
			}
		});
		btn_7.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_8 = new JButton("8");
		btn_8.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("8"));
			}
		});
		btn_8.setFont(new Font("Arial", Font.BOLD, 16));
		btn_8.setFocusPainted(false);
		btn_8.setBorderPainted(false);
		btn_8.setForeground(Color.WHITE);
		btn_8.setBackground(new Color(46, 47, 62));
		btn_8.setBounds(79, 229, 59, 48);
		frmCalculator.getContentPane().add(btn_8);
		btn_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_8.setBackground(new Color(240, 180, 16));
			}
		});
		btn_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_8.setBackground(new Color(46, 47, 62));
			}
		});
		btn_8.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_9 = new JButton("9");
		btn_9.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("9"));
			}
		});
		btn_9.setFont(new Font("Arial", Font.BOLD, 16));
		btn_9.setFocusPainted(false);
		btn_9.setBorderPainted(false);
		btn_9.setForeground(Color.WHITE);
		btn_9.setBackground(new Color(46, 47, 62));
		btn_9.setBounds(148, 229, 59, 48);
		frmCalculator.getContentPane().add(btn_9);
		btn_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_9.setBackground(new Color(240, 180, 16));
			}
		});
		btn_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_9.setBackground(new Color(46, 47, 62));
			}
		});
		btn_9.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_dot = new JButton(".");
		btn_dot.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_dot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("."));
			}
		});
		btn_dot.setFont(new Font("Arial", Font.BOLD, 16));
		btn_dot.setFocusPainted(false);
		btn_dot.setBorderPainted(false);
		btn_dot.setForeground(Color.WHITE);
		btn_dot.setBackground(new Color(46, 47, 62));
		btn_dot.setBounds(148, 405, 59, 48);
		frmCalculator.getContentPane().add(btn_dot);
		btn_dot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_dot.setBackground(new Color(240, 180, 16));
			}
		});
		btn_dot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_dot.setBackground(new Color(46, 47, 62));
			}
		});
		btn_dot.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_c = new JButton("C");
		btn_c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("c"));
			}
		});
		btn_c.setFont(new Font("Arial", Font.BOLD, 16));
		btn_c.setFocusPainted(false);
		btn_c.setBorderPainted(false);
		btn_c.setForeground(Color.WHITE);
		btn_c.setBackground(new Color(60, 61, 73));
		btn_c.setBounds(148, 108, 59, 48);
		frmCalculator.getContentPane().add(btn_c);
		btn_c.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_c.setBackground(new Color(240, 180, 16));
			}
		});
		btn_c.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_c.setBackground(new Color(60, 61, 73));
			}
		});
		btn_c.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_back = new JButton("<");
		btn_back.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("<"));
			}
		});
		btn_back.setFont(new Font("Arial", Font.BOLD, 16));
		btn_back.setFocusPainted(false);
		btn_back.setBorderPainted(false);
		btn_back.setForeground(Color.WHITE);
		btn_back.setBackground(new Color(60, 61, 73));
		btn_back.setBounds(217, 108, 59, 48);
		frmCalculator.getContentPane().add(btn_back);
		btn_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_back.setBackground(new Color(240, 180, 16));
			}
		});
		btn_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_back.setBackground(new Color(60, 61, 73));
			}
		});
		btn_back.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_plus = new JButton("+");
		btn_plus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("+"));
			}
		});
		btn_plus.setFont(new Font("Arial", Font.BOLD, 18));
		btn_plus.setFocusPainted(false);
		btn_plus.setBorderPainted(false);
		btn_plus.setForeground(new Color(240, 180, 16));
		btn_plus.setBackground(new Color(60, 61, 73));
		btn_plus.setBounds(217, 346, 59, 48);
		frmCalculator.getContentPane().add(btn_plus);
		btn_plus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_plus.setBackground(new Color(240, 180, 16));
				btn_plus.setForeground(new Color(255, 255, 255));
			}
		});
		btn_plus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_plus.setBackground(new Color(60, 61, 73));
				btn_plus.setForeground(new Color(240, 180, 16));
			}
		});
		btn_plus.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_minus = new JButton("-");
		btn_minus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("-"));
			}
		});
		btn_minus.setFont(new Font("Arial", Font.BOLD, 18));
		btn_minus.setFocusPainted(false);
		btn_minus.setBorderPainted(false);
		btn_minus.setForeground(new Color(240, 180, 16));
		btn_minus.setBackground(new Color(60, 61, 73));
		btn_minus.setBounds(217, 288, 59, 48);
		frmCalculator.getContentPane().add(btn_minus);
		btn_minus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_minus.setBackground(new Color(240, 180, 16));
				btn_minus.setForeground(new Color(255, 255, 255));
			}
		});
		btn_minus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_minus.setBackground(new Color(60, 61, 73));
				btn_minus.setForeground(new Color(240, 180, 16));
			}
		});
		btn_minus.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_multiplication = new JButton("*");
		btn_multiplication.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_multiplication.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("*"));
			}
		});
		btn_multiplication.setFont(new Font("Arial", Font.BOLD, 18));
		btn_multiplication.setFocusPainted(false);
		btn_multiplication.setBorderPainted(false);
		btn_multiplication.setForeground(new Color(240, 180, 16));
		btn_multiplication.setBackground(new Color(60, 61, 73));
		btn_multiplication.setBounds(217, 229, 59, 48);
		btn_multiplication.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_multiplication.setBackground(new Color(240, 180, 16));
				btn_multiplication.setForeground(new Color(255, 255, 255));
			}
		});
		btn_multiplication.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_multiplication.setBackground(new Color(60, 61, 73));
				btn_multiplication.setForeground(new Color(240, 180, 16));
			}
		});
		btn_multiplication.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		frmCalculator.getContentPane().add(btn_multiplication);
		//------------------------------------------
		JButton btn_division = new JButton("/");
		btn_division.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_division.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("/"));
			}
		});
		btn_division.setFont(new Font("Arial", Font.BOLD, 18));
		btn_division.setFocusPainted(false);
		btn_division.setBorderPainted(false);
		btn_division.setForeground(new Color(240, 180, 16));
		btn_division.setBackground(new Color(60, 61, 73));
		btn_division.setBounds(217, 167, 59, 47);
		frmCalculator.getContentPane().add(btn_division);
		btn_division.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_division.setBackground(new Color(240, 180, 16));
				btn_division.setForeground(new Color(255, 255, 255));
			}
		});
		btn_division.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_division.setBackground(new Color(60, 61, 73));
				btn_division.setForeground(new Color(240, 180, 16));
			}
		});
		btn_division.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_bracket_open = new JButton("(");
		btn_bracket_open.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_bracket_open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("("));
			}
		});
		btn_bracket_open.setFont(new Font("Arial", Font.BOLD, 16));
		btn_bracket_open.setForeground(Color.WHITE);
		btn_bracket_open.setFocusPainted(false);
		btn_bracket_open.setBorderPainted(false);
		btn_bracket_open.setBackground(new Color(60, 61, 73));
		btn_bracket_open.setBounds(10, 108, 59, 48);
		frmCalculator.getContentPane().add(btn_bracket_open);
		btn_bracket_open.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_bracket_open.setBackground(new Color(240, 180, 16));
			}
		});
		btn_bracket_open.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_bracket_open.setBackground(new Color(60, 61, 73));
			}
		});
		btn_bracket_open.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_bracket_close = new JButton(")");
		btn_bracket_close.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_bracket_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText(")"));
			}
		});
		btn_bracket_close.setFont(new Font("Arial", Font.BOLD, 16));
		btn_bracket_close.setFocusPainted(false);
		btn_bracket_close.setBorderPainted(false);
		btn_bracket_close.setForeground(Color.WHITE);
		btn_bracket_close.setBackground(new Color(60, 61, 73));
		btn_bracket_close.setBounds(79, 108, 59, 48);
		frmCalculator.getContentPane().add(btn_bracket_close);
		btn_bracket_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_bracket_close.setBackground(new Color(240, 180, 16));
			}
		});
		btn_bracket_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_bracket_close.setBackground(new Color(60, 61, 73));
			}
		});
		btn_bracket_close.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_equal = new JButton("=");
		btn_equal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_equal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("="));
			}
		});
		btn_equal.setFont(new Font("Arial", Font.BOLD, 16));
		btn_equal.setFocusPainted(false);
		btn_equal.setBorderPainted(false);
		btn_equal.setForeground(Color.WHITE);
		btn_equal.setBackground(new Color(240, 180, 16));
		btn_equal.setBounds(217, 405, 59, 48);
		frmCalculator.getContentPane().add(btn_equal);
		btn_equal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_squre = new JButton("x²");
		btn_squre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_squre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("^2"));
			}
		});
		btn_squre.setForeground(Color.WHITE);
		btn_squre.setFont(new Font("Arial", Font.BOLD, 16));
		btn_squre.setFocusPainted(false);
		btn_squre.setBorderPainted(false);
		btn_squre.setBackground(new Color(60, 61, 73));
		btn_squre.setBounds(148, 167, 59, 48);
		frmCalculator.getContentPane().add(btn_squre);
		btn_squre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_squre.setBackground(new Color(240, 180, 16));
			}
		});
		btn_squre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_squre.setBackground(new Color(60, 61, 73));
			}
		});
		btn_squre.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_power = new JButton("^");
		btn_power.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_power.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("^"));
			}
		});
		btn_power.setForeground(Color.WHITE);
		btn_power.setFont(new Font("Arial", Font.BOLD, 16));
		btn_power.setFocusPainted(false);
		btn_power.setBorderPainted(false);
		btn_power.setBackground(new Color(60, 61, 73));
		btn_power.setBounds(79, 167, 59, 48);
		frmCalculator.getContentPane().add(btn_power);
		btn_power.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_power.setBackground(new Color(240, 180, 16));
			}
		});
		btn_power.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_power.setBackground(new Color(60, 61, 73));
			}
		});
		btn_power.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		JButton btn_resiprocal = new JButton("¹/x");
		btn_resiprocal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn_resiprocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				screen.setText(addText("¹/x"));
			}
		});
		btn_resiprocal.setForeground(Color.WHITE);
		btn_resiprocal.setFont(new Font("Arial", Font.BOLD, 16));
		btn_resiprocal.setFocusPainted(false);
		btn_resiprocal.setBorderPainted(false);
		btn_resiprocal.setBackground(new Color(60, 61, 73));
		btn_resiprocal.setBounds(10, 166, 59, 48);
		frmCalculator.getContentPane().add(btn_resiprocal);
		btn_resiprocal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn_resiprocal.setBackground(new Color(240, 180, 16));
			}
		});
		btn_resiprocal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				btn_resiprocal.setBackground(new Color(60, 61, 73));
			}
		});
		btn_resiprocal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(240, 180, 16)));
		//------------------------------------------
		history_screen = new JTextField();
		history_screen.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		history_screen.setEditable(false);
		history_screen.setFont(new Font("Tahoma", Font.PLAIN, 12));
		history_screen.setForeground(Color.WHITE);
		history_screen.setHorizontalAlignment(SwingConstants.RIGHT);
		history_screen.setBorder(null);
		history_screen.setBackground(new Color(36, 37, 48));
		history_screen.setBounds(10, 11, 266, 37);
		frmCalculator.getContentPane().add(history_screen);
		history_screen.setColumns(10);
		//------------------------------------------
	}
}
