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
    public boolean[] directionsIsUsed = new boolean[DIRECTION_SIZE];
    public int[] pages = new int[PAGE_SIZE];
    public int[] pageUsedTime = new int[PAGE_SIZE];

    public Page() {
        for(int i = 0;i < DIRECTION_SIZE;i++){
            directions[i] = i;
            directionsIsUsed[i] = false;
        }
        for(int i = 0;i < PAGE_SIZE;i++){
            pages[i] = -1;
            pageUsedTime[i] = 0;
        }
    }
    
    public void initial(){
        for(int i = 0;i < DIRECTION_SIZE;i++){
            directions[i] = i;
            directionsIsUsed[i] = false;
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
    public final int DIRECTION_NEXT = 0;
    public final int DIRECTION_FORTH = 1;
    public final int DIRECTION_BACK = 2;
    
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
    
    private boolean isRandomNumberUsed(int randomNumber){
        return page.directionsIsUsed[randomNumber];
    }
    
    private int randomNumber(int position,int directionOfRandom){
        Random random = new Random();
        int randomNumber = random.nextInt(320);
        if(directionOfRandom == 0 || position == 0){
            while(isRandomNumberUsed(randomNumber)){
                randomNumber = random.nextInt(320);
            }
        }
        else if(directionOfRandom == 1){
            while (isRandomNumberUsed(randomNumber) || randomNumber < position) {                
                randomNumber = random.nextInt(320);
            }
        }
        else{
            while (isRandomNumberUsed(randomNumber) || randomNumber > position) {                
                randomNumber = random.nextInt(320);
            }
        }
        return randomNumber;
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
       if(isAllPageInUse > 0){
           page.pages[isAllPageInUse] = position / 32 + 1;
           updatePageUsedTime(isAllPageInUse);
           Panel.printPage(position,page.pages,false);
       }else{
           int mostUsedPage = searchForLeastUsedPage();
           if (page.pages[mostUsedPage] == (position / 32 + 1)) {
               updatePageUsedTime(mostUsedPage);
               Panel.printPage(position, page.pages, false);
           }
           else{
               page.pages[mostUsedPage] = position / 32 + 1;
               updatePageUsedTime(mostUsedPage);
               timesOfPageChange++;
               Panel.printPage(position, page.pages, true);
           }
       }
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
        for(int i = 0;i < PAGE_SIZE;i++){
            if (page.pageUsedTime[i] > leastUsedPageNumber) {
                leastUsedPageNumber = i;
            }
        }
        return leastUsedPageNumber;
    }
    
    private int setNextPage(int position){
        for(int i = position + 1;i < DIRECTION_SIZE;i++){
            if(page.directionsIsUsed[i] == false){
                return i;
            }
        }
        for(int i = position - 1;i > 0;i++){
            if(page.directionsIsUsed[i] == false){
                return i;
            }
        }
        return -1;
    }
    
    private void calculateTheResult(){
        int position = 0;
        while (true) {            
            position = randomNumber(position,DIRECTION_NEXT);
            if(allChanges == 320){
                break;
            }
            pageSelect(position);
            allChanges++;
            position = setNextPage(position);
            if(allChanges == 320){
                break;
            }
            pageSelect(position);
            allChanges++;
            position = randomNumber(position, DIRECTION_BACK);
            if(allChanges == 320){
                break;
            }
            pageSelect(position);
            allChanges++;
            position = setNextPage(position);
            if(allChanges == 320){
                break;
            }
            pageSelect(position);
            allChanges++;
            position = randomNumber(position, DIRECTION_FORTH);
            if(allChanges == 320){
                break;
            }
            pageSelect(position);
            allChanges++;
        }
    }
    private void calculateTheRatio(){
        float pageRatio = (float)timesOfPageChange / DIRECTION_SIZE;
        Panel.printRatio(timesOfPageChange,pageRatio);
    }
}
