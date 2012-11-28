/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package memorymanagement;

/**
 *
 * @author lenovo
 */
public class MemoryManagement {

    //System property
    int pageSize;
    int pageFrame;
    int []inputValue;
    
    MemoryManagement(){
       
    }
    
    public void PageDemand(){
        getValuesFromScreen();
        calculateTheResult();
    }
    
    private void getValuesFromScreen(){
        pageSize = Panel.getPageSize();
        pageFrame = Panel.getPageFrame();
        inputValue = Panel.getInputValue();
    }
    
    private void calculateTheResult(){
        // TODO:calculate the result
    }
}
