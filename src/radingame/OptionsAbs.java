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
abstract class OptionsAbs {
    private static int grid;
    private static int playerz;
    private static int mode;

    public static void setGrid(int grid){
        OptionsAbs.grid = grid;
    }
    public static void setPlayer(int player){
        OptionsAbs.playerz = player;
    }
    
    public static void setMode(int mode){
        OptionsAbs.mode = mode;
    }
    
    public static int getGrid(){
      return  (OptionsAbs.grid);
    }
    public static int getPlayer(){
        return OptionsAbs.playerz;
    }
    
    public static int getMode(){
        return OptionsAbs.mode;
    }
    

}
