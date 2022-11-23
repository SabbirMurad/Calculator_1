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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
		        if(str.charAt(i)=='+'||str.charAt(i)=='-'||str.charAt(i)=='*'||str.charAt(i)=='/'||str.charAt(i)=='('){
		            if(str.charAt(i)=='+'||str.charAt(i)=='-'||str.charAt(i)=='*'||str.charAt(i)=='/'){
		                if(comp_holder.peek()=='*' || comp_holder.peek()=='/'){
		                    String s=String.valueOf(comp_holder.peek());
		                    comp_holder.pop();
		                    exp_holder.push(s);
		                    comp_holder.push(str.charAt(i));
		                }
		                else{
		                    comp_holder.push(str.charAt(i));
		                }
		            }
		            else{
		                comp_holder.push(str.charAt(i));
		            }
		        }
		        else if(str.charAt(i)==')'){
		            while (comp_holder.peek()!='('){
		                String s="";
		                s+=comp_holder.peek();
		                comp_holder.pop();
		                exp_holder.push(s);
		                if(comp_holder.size()==0){
		                    break;
		                }
		            }
		            comp_holder.pop();
		        }
		        else{
		            temp+=str.charAt(i);
		            if(str.charAt(i+1)=='+'||str.charAt(i+1)=='-'||str.charAt(i+1)=='*'||str.charAt(i+1)=='/'||str.charAt(i+1)==')'){
		                exp_holder.push(temp);
		                temp="";
		            }
		        }
		    }
		    return exp_holder;
		}
	  //calculating value from postfix expression
		public static float calcNum(String str){
		    Vector<String> post = postfix(str);
		    Stack<Float>holder =new Stack<>();
		    
		    for(int i=0;i<post.size();i++){
		        String s=post.get(i);
		        char ch;
		        ch=post.get(i).charAt(0);
		        if(ch=='+'||ch=='-'||ch=='*'||ch=='/'){
		            float a=holder.peek();
		            holder.pop();
		            float b=holder.peek();
		            holder.pop();
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
		        }
		        else{
		            holder.push(Float.parseFloat(s));
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
	public String addText(String s) {
		if(s=="c") {
			return "";
		}
		else if(s=="<") {
			String value=screen.getText().toString();
			value=value.substring(0,value.length()-1);
			return value;
		}
		else if(s=="=") {
			String value=screen.getText().toString();
			return getResult(value);
		}
		else {
			String value=screen.getText().toString();
			value+=s;
			return value;
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCalculator = new JFrame();
		frmCalculator.setTitle("Calculator");
		frmCalculator.setForeground(Color.GRAY);
		
		frmCalculator.getContentPane().setBackground(Color.GRAY);
		frmCalculator.setBounds(100, 100, 302, 410);
		frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculator.getContentPane().setLayout(null);
		
		screen = new JTextField();
		screen.setFont(new Font("Tahoma", Font.PLAIN, 18));
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setForeground(Color.WHITE);
		screen.setBackground(Color.GRAY);
		screen.setBounds(10, 10, 266, 38);
		frmCalculator.getContentPane().add(screen);
		screen.setColumns(10);
		
		JButton btn_0 = new JButton("0");
		btn_0.setFont(new Font("Arial", Font.BOLD, 16));
		btn_0.setFocusPainted(false);
		btn_0.setBorderPainted(false);
		btn_0.setForeground(Color.WHITE);
		btn_0.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("0"));
			}
		});
		btn_0.setBackground(Color.DARK_GRAY);
		btn_0.setBounds(10, 297, 59, 48);
		frmCalculator.getContentPane().add(btn_0);
		//------------------------------------------
		JButton btn_1 = new JButton("1");
		btn_1.setFont(new Font("Arial", Font.BOLD, 16));
		btn_1.setFocusPainted(false);
		btn_1.setBorderPainted(false);
		btn_1.setForeground(Color.WHITE);
		btn_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("1"));
			}
		});
		btn_1.setBackground(Color.DARK_GRAY);
		btn_1.setBounds(10, 238, 59, 48);
		frmCalculator.getContentPane().add(btn_1);
		//------------------------------------------
		JButton btn_2 = new JButton("2");
		btn_2.setFont(new Font("Arial", Font.BOLD, 16));
		btn_2.setFocusPainted(false);
		btn_2.setBorderPainted(false);
		btn_2.setForeground(Color.WHITE);
		btn_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("2"));
			}
		});
		btn_2.setBackground(Color.DARK_GRAY);
		btn_2.setBounds(79, 238, 59, 48);
		frmCalculator.getContentPane().add(btn_2);
		//------------------------------------------
		JButton btn_3 = new JButton("3");
		btn_3.setFont(new Font("Arial", Font.BOLD, 16));
		btn_3.setFocusPainted(false);
		btn_3.setBorderPainted(false);
		btn_3.setForeground(Color.WHITE);
		btn_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("3"));
			}
		});
		btn_3.setBackground(Color.DARK_GRAY);
		btn_3.setBounds(148, 238, 59, 48);
		frmCalculator.getContentPane().add(btn_3);
		//------------------------------------------
		JButton btn_4 = new JButton("4");
		btn_4.setFont(new Font("Arial", Font.BOLD, 16));
		btn_4.setFocusPainted(false);
		btn_4.setBorderPainted(false);
		btn_4.setForeground(Color.WHITE);
		btn_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("4"));
			}
		});
		btn_4.setBackground(Color.DARK_GRAY);
		btn_4.setBounds(10, 180, 59, 48);
		frmCalculator.getContentPane().add(btn_4);
		//------------------------------------------
		JButton btn_5 = new JButton("5");
		btn_5.setFont(new Font("Arial", Font.BOLD, 16));
		btn_5.setFocusPainted(false);
		btn_5.setBorderPainted(false);
		btn_5.setForeground(Color.WHITE);
		btn_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("5"));
			}
		});
		btn_5.setBackground(Color.DARK_GRAY);
		btn_5.setBounds(79, 180, 59, 48);
		frmCalculator.getContentPane().add(btn_5);
		//------------------------------------------
		JButton btn_6 = new JButton("6");
		btn_6.setFont(new Font("Arial", Font.BOLD, 16));
		btn_6.setFocusPainted(false);
		btn_6.setBorderPainted(false);
		btn_6.setForeground(Color.WHITE);
		btn_6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("6"));
			}
		});
		btn_6.setBackground(Color.DARK_GRAY);
		btn_6.setBounds(148, 180, 59, 48);
		frmCalculator.getContentPane().add(btn_6);
		//------------------------------------------
		JButton btn_7 = new JButton("7");
		btn_7.setFont(new Font("Arial", Font.BOLD, 16));
		btn_7.setFocusPainted(false);
		btn_7.setBorderPainted(false);
		btn_7.setForeground(Color.WHITE);
		btn_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("7"));
			}
		});
		btn_7.setBackground(Color.DARK_GRAY);
		btn_7.setBounds(10, 121, 59, 48);
		frmCalculator.getContentPane().add(btn_7);
		//------------------------------------------
		JButton btn_8 = new JButton("8");
		btn_8.setFont(new Font("Arial", Font.BOLD, 16));
		btn_8.setFocusPainted(false);
		btn_8.setBorderPainted(false);
		btn_8.setForeground(Color.WHITE);
		btn_8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("8"));
			}
		});
		btn_8.setBackground(Color.DARK_GRAY);
		btn_8.setBounds(79, 121, 59, 48);
		frmCalculator.getContentPane().add(btn_8);
		//------------------------------------------
		JButton btn_9 = new JButton("9");
		btn_9.setFont(new Font("Arial", Font.BOLD, 16));
		btn_9.setFocusPainted(false);
		btn_9.setBorderPainted(false);
		btn_9.setForeground(Color.WHITE);
		btn_9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("9"));
			}
		});
		btn_9.setBackground(Color.DARK_GRAY);
		btn_9.setBounds(148, 121, 59, 48);
		frmCalculator.getContentPane().add(btn_9);
		//------------------------------------------
		JButton btn_dot = new JButton(".");
		btn_dot.setFont(new Font("Arial", Font.BOLD, 16));
		btn_dot.setFocusPainted(false);
		btn_dot.setBorderPainted(false);
		btn_dot.setForeground(Color.WHITE);
		btn_dot.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("."));
			}
		});
		btn_dot.setBackground(Color.DARK_GRAY);
		btn_dot.setBounds(79, 297, 59, 48);
		frmCalculator.getContentPane().add(btn_dot);
		//------------------------------------------
		JButton btn_c = new JButton("c");
		btn_c.setFont(new Font("Arial", Font.BOLD, 16));
		btn_c.setFocusPainted(false);
		btn_c.setBorderPainted(false);
		btn_c.setForeground(Color.WHITE);
		btn_c.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("c"));
			}
		});
		btn_c.setBackground(Color.DARK_GRAY);
		btn_c.setBounds(148, 59, 59, 48);
		frmCalculator.getContentPane().add(btn_c);
		//------------------------------------------
		JButton btn_back = new JButton("<=");
		btn_back.setFont(new Font("Arial", Font.BOLD, 16));
		btn_back.setFocusPainted(false);
		btn_back.setBorderPainted(false);
		btn_back.setForeground(Color.WHITE);
		btn_back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("<"));
			}
		});
		btn_back.setBackground(Color.DARK_GRAY);
		btn_back.setBounds(217, 59, 59, 48);
		frmCalculator.getContentPane().add(btn_back);
		//------------------------------------------
		JButton btn_plus = new JButton("+");
		btn_plus.setFont(new Font("Arial", Font.BOLD, 16));
		btn_plus.setFocusPainted(false);
		btn_plus.setBorderPainted(false);
		btn_plus.setForeground(Color.WHITE);
		btn_plus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("+"));
			}
		});
		btn_plus.setBackground(Color.DARK_GRAY);
		btn_plus.setBounds(217, 297, 59, 48);
		frmCalculator.getContentPane().add(btn_plus);
		//------------------------------------------
		JButton btn_minus = new JButton("-");
		btn_minus.setFont(new Font("Arial", Font.BOLD, 16));
		btn_minus.setFocusPainted(false);
		btn_minus.setBorderPainted(false);
		btn_minus.setForeground(Color.WHITE);
		btn_minus.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("-"));
			}
		});
		btn_minus.setBackground(Color.DARK_GRAY);
		btn_minus.setBounds(217, 238, 59, 48);
		frmCalculator.getContentPane().add(btn_minus);
		//------------------------------------------
		JButton btn_mul = new JButton("*");
		btn_mul.setFont(new Font("Arial", Font.BOLD, 16));
		btn_mul.setFocusPainted(false);
		btn_mul.setBorderPainted(false);
		btn_mul.setForeground(Color.WHITE);
		btn_mul.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("*"));
			}
		});
		btn_mul.setBackground(Color.DARK_GRAY);
		btn_mul.setBounds(217, 180, 59, 48);
		frmCalculator.getContentPane().add(btn_mul);
		//------------------------------------------
		JButton btn_div = new JButton("/");
		btn_div.setFont(new Font("Arial", Font.BOLD, 16));
		btn_div.setFocusPainted(false);
		btn_div.setBorderPainted(false);
		btn_div.setForeground(Color.WHITE);
		btn_div.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("/"));
			}
		});
		btn_div.setBackground(Color.DARK_GRAY);
		btn_div.setBounds(217, 122, 59, 47);
		frmCalculator.getContentPane().add(btn_div);
		//------------------------------------------
		JButton btn_open = new JButton("(");
		btn_open.setFont(new Font("Arial", Font.BOLD, 16));
		btn_open.setForeground(Color.WHITE);
		btn_open.setFocusPainted(false);
		btn_open.setBorderPainted(false);
		btn_open.setBackground(Color.DARK_GRAY);
		btn_open.setBounds(10, 59, 59, 48);
		frmCalculator.getContentPane().add(btn_open);
		btn_open.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("("));
			}
		});
		//------------------------------------------
		JButton btn_close = new JButton(")");
		btn_close.setFont(new Font("Arial", Font.BOLD, 16));
		btn_close.setFocusPainted(false);
		btn_close.setBorderPainted(false);
		btn_close.setForeground(Color.WHITE);
		btn_close.setBackground(Color.DARK_GRAY);
		btn_close.setBounds(79, 59, 59, 48);
		frmCalculator.getContentPane().add(btn_close);
		btn_close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText(")"));
			}
		});
		//------------------------------------------
		JButton btn_equal = new JButton("=");
		btn_equal.setFont(new Font("Arial", Font.BOLD, 16));
		btn_equal.setFocusPainted(false);
		btn_equal.setBorderPainted(false);
		btn_equal.setForeground(Color.WHITE);
		btn_equal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				screen.setText(addText("="));
			}
		});
		btn_equal.setBackground(Color.DARK_GRAY);
		btn_equal.setBounds(148, 297, 59, 48);
		frmCalculator.getContentPane().add(btn_equal);
	}
}
