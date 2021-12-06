import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class TextEditor extends JFrame implements ActionListener{ // extend the jframe and implement the action listener interface
    
	
	// Declare all the elements required to create project
	JTextArea textArea;
	JScrollPane scrollPane;
	JLabel fontLabel;
	JSpinner fontSizeSpinner;
	JButton fontColorButton;
	JComboBox fontBox;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	
	TextEditor(){ // constructor for text editor class 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to close the frame when we want 
		this.setTitle("Ak Text Editor");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());  // set the layout as flow layout as it's convenient for project
		this.setLocationRelativeTo(null);  // to appear the output in the middle of the screen
		
		textArea = new JTextArea();  // Initialize text area
		textArea.setLineWrap(true);  // to manage the ending and next line generation
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN,20));
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450,450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		fontLabel = new JLabel("Font: ");
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontSizeSpinner.getValue()));
				
			}
			
		});
		
		fontColorButton = new JButton("color");  // Initialize the color button with a text of color
		fontColorButton.addActionListener(this);  // Add action listener so that it does something when we click on it
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
		// -------menubar--------
		
		
		menuBar = new JMenuBar();// initialize menu bar and other required for editor to save,open,etc 
		fileMenu = new JMenu("File");  
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		openItem.addActionListener(this);// add action listener to all the items in menu bar to give functionality to corresponding buttons
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		fileMenu.add(openItem);  // add all the items in menu
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
	    menuBar.add(fileMenu);
		
		// -------/menubar--------
	    
	    this.setJMenuBar(menuBar);  // add everything in frame required
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(fontColorButton);
		this.add(fontBox);
		this.add(scrollPane);
		this.setVisible(true);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {  // declare the action performed nethod
		// TODO Auto-generated method stub
		if(e.getSource()==fontColorButton) {  // using if condition to choose the color
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = colorChooser.showDialog(null, "Choose A Color", Color.black);  // to give option for color choice to user
			
			textArea.setForeground(color);  // change the text fg color as per the choice
		}
		
		if(e.getSource()==fontBox) {  // condition to choose the type of font user selects and apply it in editor
			textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
		}
		
		if(e.getSource()==openItem) {  // condition to open the item
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
			fileChooser.setFileFilter(filter);
			
			int response = fileChooser.showOpenDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new  File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n";
							textArea.append(line);
						}
					}
				}catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
		}
        if(e.getSource()==saveItem) { // contion to save item
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response = fileChooser.showSaveDialog(null);
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				
				}catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
		}
        
        if(e.getSource()==exitItem) { // condition to exit
        	
        	
			System.exit(0);
		}
		
		
	}

}
