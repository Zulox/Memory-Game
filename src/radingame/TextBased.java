/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radingame;

/**
 *
 * @author Zulox
 */

public class TextBased extends  Card{
    
    private int ShownNumber;
    
    public void setShownNumber( int num){
        this.ShownNumber = num;
    }
    
    public int getShownNumber(){
        return this.ShownNumber;
    }
}
