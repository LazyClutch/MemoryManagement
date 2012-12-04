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
    private JLabel inputLabel = new JLabel("Update Information");
    private JLabel outputLabel = new JLabel("Console:");
    private JLabel LRULabel = new JLabel("Algorithm:LRU");
    private JLabel pageFrameLabel = new JLabel("Page Frame:");
    private JLabel pageSizeLabel = new JLabel("Page Size: ");
    private JLabel totalSizeLabel = new JLabel("Total Size: ");
    
    //JButton
    private JButton runButton = new JButton("Run");
    
    //JTextArea
    private static JTextArea outputJTextArea = new JTextArea();
    
    //JTextField
    private JTextField pageFrame = new JTextField("4");
    private JTextField pageSize = new JTextField("10");
    private JTextField totalSize = new JTextField("320");
    private static JTextField outputTextField = new JTextField();
    
    //JScroolPane
    private JScrollPane outputScrollPane = new JScrollPane(outputJTextArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    //Some API related variables
    private static int pageFrameVolume = 0;
    private static int pageSizeVolume = 0;
    private static int totalSizeVolume = 0;
    
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
        optionPanel.add(totalSizeLabel);
        optionPanel.add(totalSize);
        optionPanel.add(runButton);
        pageFrame.setEditable(false);
        pageSize.setEditable(false);
        totalSize.setEditable(false);
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
        outputJTextArea.setEditable(true);
        outputPanel.add(outputScrollPane,BorderLayout.CENTER);
        add(outputPanel,BorderLayout.CENTER);
        
        BorderLayout inputLayout = new BorderLayout();
        inputPanel.setLayout(inputLayout);
        inputPanel.add(inputLabel,BorderLayout.WEST);
        inputPanel.add(outputTextField,BorderLayout.CENTER);
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
        pageFrameVolume = Integer.parseInt(pageFrame.getText());
        pageSizeVolume = Integer.parseInt(pageSize.getText());
        totalSizeVolume = Integer.parseInt(pageSize.getText());
    }
    
    public static int getPageFrame(){
        return pageFrameVolume;
    }
    
    public static int getPageSize(){
        return pageSizeVolume;
    }
    
    public static int getTotalSize(){
        return totalSizeVolume;
    }
    
    private void printHeadDescription(){
        String space = "                    ";
        outputJTextArea.append("LRU ALGORITHM:\n");
        outputJTextArea.append("Directions" + space + "Frame1" + space + "Frame2" + space + "Frame3" + space + "Frame4" + space + "\n");
    }
    
    private void initial(){
        outputJTextArea.setText(null);
        outputTextField.setText(null);
    }
    
    public void runConsole(){
        initial();
        recordOptionValue();
        printHeadDescription();
        console.PageDemand();
    }
    
     static void printPage(int position,int[] pages,boolean isPageChange){ 
         //  36 69 102 135
         int page = position / 32 + 1;
         String pageToString = String.valueOf(page);
         String newLine = "";
         newLine += "" + position + "[" + pageToString + "]";
         for(int i = 0; i < 4;i++){
             newLine += printSpace(36 + i*33 - newLine.length()) + pages[i];
         }
         newLine += "\n";
         outputJTextArea.append(newLine);
     }
     
     static void printRatio(int changeTimes,float changeRatio){
         String consoleInformation = "";
         consoleInformation += "Times of losing pages:" + changeTimes + "       " + "Ratio of changing pages:" + changeRatio;
         outputTextField.setText(consoleInformation);
     }
     
     private static String printSpace(int number){
         String space = "";
         for(int i = 0;i < number;i++){
             space += " ";
         }
         return space;
     }
     
    public static void main(String[] args) {
        Panel panel = new Panel();
    }
    
}
