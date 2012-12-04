/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package memorymanagement;

import java.util.Random;
import javax.swing.text.Position;


/**
 *
 * @author lenovo
 */
class Page{
    //const
    public final int DIRECTION_SIZE = 320;
    public final int PAGE_SIZE = 4;
    
    //member variables
    public int[] directions = new int[DIRECTION_SIZE];
    public int[] pages = new int[PAGE_SIZE];
    public int[] pageUsedTime = new int[PAGE_SIZE];

    public Page() {
        for(int i = 0;i < DIRECTION_SIZE;i++){
            directions[i] = i;
        }
        for(int i = 0;i < PAGE_SIZE;i++){
            pages[i] = -1;
            pageUsedTime[i] = 0;
        }
    }
    
    public void initial(){
        for(int i = 0;i < DIRECTION_SIZE;i++){
            directions[i] = i;
        }
        for(int i = 0;i < PAGE_SIZE;i++){
            pages[i] = -1;
            pageUsedTime[i] = 0;
        }
    }
    
}

public class MemoryManagement {
    
    //const
    public final int DIRECTION_SIZE = 320;
    public final int PAGE_SIZE = 4;
    
    //System property
    private int pageSize;
    private int pageFrame;
    private int totalSize;
    
    //Page 
    private Page page = new Page();
    private static int timesOfPageChange = 0;
    private static int allChanges = 0;
    
    MemoryManagement(){
       
    }
    
    public void PageDemand(){
        initial();
        getValuesFromScreen();
        calculateTheResult();
        calculateTheRatio();
    }
    
    public void initial(){
        page.initial();
        timesOfPageChange = 0;
        allChanges = 0;
    }
    
    private void getValuesFromScreen(){
        pageSize = Panel.getPageSize();
        pageFrame = Panel.getPageFrame();
        totalSize = Panel.getTotalSize();
    }
    
    private void randomNumber(){
        int count = directionNotChange();
        while(count < DIRECTION_SIZE / 2){
            for(int i = 0;i < DIRECTION_SIZE / 4 - count/2;i++){
                Random random = new Random();
                int randomNumber1 = random.nextInt(DIRECTION_SIZE);
                int randomNumber2 = random.nextInt(DIRECTION_SIZE);
                if(randomNumber1 != randomNumber2){
                    int temp = page.directions[randomNumber1];
                    page.directions[randomNumber1] = page.directions[randomNumber2];
                    page.directions[randomNumber2] = temp; 
                }                    
            }
            count = directionNotChange();
        }
        
    }
    
    private int directionNotChange(){
        int count = 0;
        for(int i = 0;i < DIRECTION_SIZE;i++){
            if(page.directions[i] != i){
                count++;
            }
        }
        return count;
    }
    
    private void updatePageUsedTime(int pageNumber){
        for(int i = 0;i < PAGE_SIZE;i++){
            if(pageNumber == i ){
                page.pageUsedTime[i] = 0;
            }else if(page.pages[i] != -1){
                page.pageUsedTime[i]++;
            }
        }
    }
    
    private void pageSelect(int position){
        int isAllPageInUse = isAllPageUsed(); 
        int equalValue = hasAnEqualValue(position / 32 + 1);
        if (equalValue >= 0) {
            updatePageUsedTime(pageSize);
            Panel.printPage(position,page.pages,false);
        }else{
            if(isAllPageInUse >= 0){
                page.pages[isAllPageInUse] = position / 32 + 1;
                updatePageUsedTime(isAllPageInUse);
                Panel.printPage(position,page.pages,false);
            }else{       
                int mostUsedPage = searchForLeastUsedPage();
                page.pages[mostUsedPage] = position / 32 + 1;
                updatePageUsedTime(mostUsedPage);
                timesOfPageChange++;
                Panel.printPage(position, page.pages, true);
            }
        }     
    }
    
    private int hasAnEqualValue(int position){
        for(int i = 0;i < PAGE_SIZE;i++){
            if(page.pages[i] == position) {
                return i;
            }
        }
        return -1;
    }
    
    private int isAllPageUsed(){
        for(int i = 0;i < PAGE_SIZE;i++){
            if(page.pages[i] == -1){
                return i;
            }
        }
        return -1;
    }
    
    private int searchForLeastUsedPage(){
        int leastUsedPageNumber = -1;
        int leastUsedTime = -1;
        for(int i = 0;i < PAGE_SIZE;i++){
            if (page.pageUsedTime[i] > leastUsedTime) {
                leastUsedPageNumber = i;
                leastUsedTime = page.pageUsedTime[i];
            }
        }
        return leastUsedPageNumber;
    }

    
    private void calculateTheResult(){
        randomNumber();
        for(int i: page.directions){
            pageSelect(i);
        }
    }
    private void calculateTheRatio(){
        float pageRatio = (float)timesOfPageChange / DIRECTION_SIZE;
        Panel.printRatio(timesOfPageChange,pageRatio);
    }
}
