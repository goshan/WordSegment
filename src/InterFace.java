import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.*;





public class InterFace implements MouseListener, WindowListener{
	JFrame frame;
	JPanel buttonPanel;
	JScrollPane inputScroll;
	JScrollPane outputScroll;
	JTextArea input;
	JTextArea output;
	JButton segment;
	Process pro;
	BufferedReader in;
	BufferedWriter out;
	
	InterFace (){
		this.frame = new JFrame("CWord Segmentation System");
		this.frame.setLayout(new BorderLayout());
		this.buttonPanel = new JPanel();
		this.segment = new JButton("segment");
		this.buttonPanel.add(this.segment);
		
		this.input = new JTextArea(10, 0);
		this.output = new JTextArea(10, 0);
		this.inputScroll = new JScrollPane(input);
		this.outputScroll = new JScrollPane(output);
		
		this.frame.add(this.inputScroll, "North");
		this.frame.add(this.buttonPanel, "Center");
		this.frame.add(this.outputScroll, "South");
		this.frame.setSize(400, 440);
		this.frame.setVisible(true);
		
		Runtime runtime = Runtime.getRuntime();
		try {
			this.pro = runtime.exec("Segment_ICTCLAS.exe");
			this.in = new BufferedReader(new InputStreamReader(this.pro.getInputStream()));
			this.out = new BufferedWriter (new OutputStreamWriter(this.pro.getOutputStream()));
			String msg = in.readLine();
			if (!msg.equals("init ITCCLAS...OK")){
				this.in.close();
				this.out.close();
				this.pro.destroy();
				System.exit(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.segment.addMouseListener(this);
		this.frame.addWindowListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == this.segment){
			String outputText = "in-start\n"+this.input.getText()+"\nin-end\n";
			String inputText = new String();
			try {
				this.out.write(outputText);
				this.out.flush();
				String line = this.in.readLine();
				if (line.equals("out-start")){
					while (!(line=this.in.readLine()).equals("out-end")){
						inputText += line+"\n";
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.output.setText(inputText);
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void windowActivated(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

	public void windowClosed(WindowEvent e) {
		try {
			this.in.close();
			this.out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.pro.destroy();
		System.exit(0);
	}

	public void windowClosing(WindowEvent e) {
		try {
			this.in.close();
			this.out.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.pro.destroy();
		System.exit(0);
	}
}