/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package memorymanagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import sun.util.logging.resources.logging;

/**
 *
 * @author lenovo
 */
public class Panel extends JFrame implements ActionListener{
    
    MemoryManagement console = new MemoryManagement();
    
    //JPanel
    private JPanel inputPanel = new JPanel();
    private JPanel outputPanel = new JPanel();
    private JPanel optionPanel = new JPanel();
    
    //JLabel
    private JLabel inputLabel = new JLabel("Input a String of numbers:");
    private JLabel outputLabel = new JLabel("Console:");
    private JLabel LRULabel = new JLabel("Algorithm:LRU");
    private JLabel pageFrameLabel = new JLabel("Page Frame:");
    private JLabel pageSizeLabel = new JLabel("Page Size: ");
    private JLabel totalSizeLabel = new JLabel("Total Size: ");
    
    //JButton
    private JButton runButton = new JButton("Run");
    
    //JTextArea
    private JTextArea outputJTextArea = new JTextArea();
    
    //JTextField
    private JTextField pageFrame = new JTextField("3");
    private JTextField pageSize = new JTextField("1");
    private JTextField totalSize = new JTextField("160");
    private JTextField inputTextField = new JTextField("3,4,1,3,2,5,1,4,2,5,3,1,2");
    
    //JScroolPane
    private JScrollPane outputScrollPane = new JScrollPane(outputJTextArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    //Some API related variables
    private static int pageFrameVolume = 0;
    private static int pageSizeVolume = 0;
    private static int[] inputArray;
    
    public Panel() throws HeadlessException {
        super("Demand Paging");
        GeneralSetting();
        LayoutSetting();
    }
    
    private void setLookAndFeel(){
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exc) {
        // ignore error
        }
    }
    
    private void GeneralSetting(){
        setLookAndFeel();
        setSize(800,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private void LayoutSetting(){
        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        
        FlowLayout pageOptionLayout = new FlowLayout();
        optionPanel.setLayout(pageOptionLayout);
        optionPanel.add(LRULabel);
        optionPanel.add(pageFrameLabel);
        optionPanel.add(pageFrame);
        optionPanel.add(pageSizeLabel);
        optionPanel.add(pageSize);
        optionPanel.add(runButton);
        runButton.addActionListener(this);
//        optionPanel.add(totalSizeLabel);
//        optionPanel.add(totalSize);
        add(optionPanel,BorderLayout.NORTH);
        
        BorderLayout outputLayout = new BorderLayout();
        outputPanel.setLayout(outputLayout);
        outputPanel.add(outputLabel,BorderLayout.WEST);
        //outputPanel.add(outputJTextArea, BorderLayout.CENTER);
        outputJTextArea.setLineWrap(true);
        outputJTextArea.setWrapStyleWord(true);
        outputJTextArea.setEditable(false);
        outputPanel.add(outputScrollPane,BorderLayout.CENTER);
        add(outputPanel,BorderLayout.CENTER);
        
        BorderLayout inputLayout = new BorderLayout();
        inputPanel.setLayout(inputLayout);
        inputPanel.add(inputLabel,BorderLayout.WEST);
        inputPanel.add(inputTextField,BorderLayout.CENTER);
        add(inputPanel,BorderLayout.SOUTH);
    }
    
    @Override
    public void actionPerformed(ActionEvent event){
        String actionCommand = event.getActionCommand();
        if(actionCommand.equals("Run")){
            runConsole();
        }
    }
    
    private void recordOptionValue(){
        String[] inputArrayFromString = inputTextField.getText().split(",");
        inputArray = new int[inputArrayFromString.length];
        pageFrameVolume = Integer.parseInt(pageFrame.getText());
        pageSizeVolume = Integer.parseInt(pageSize.getText());
        for(int i = 0;i < inputArrayFromString.length;i++){
            inputArray[i] = Integer.parseInt(inputArrayFromString[i]);
        }
    }
    
    public static int getPageFrame(){
        return pageFrameVolume;
    }
    
    public static int getPageSize(){
        return pageSizeVolume;
    }
    
    public static int[] getInputValue(){
        return inputArray;
    }
    
     public void runConsole(){
        recordOptionValue();
    }
    
    public static void main(String[] args) {
        Panel panel = new Panel();
    }
    
}
