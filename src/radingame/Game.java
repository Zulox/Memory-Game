/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package radingame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.lang.Object;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Zulox
 */
public class Game extends javax.swing.JFrame implements ActionListener{

    
    
    private  int maxPair = (OptionsAbs.getGrid() *OptionsAbs.getGrid() ) / 2;
    private int maxCard = maxPair * 2;
    private JButton[] btn = new JButton[maxCard];
    private Color[] c={Color.red, Color.blue, Color.green, Color.magenta};
   
    private TextBased[]card = new TextBased[maxCard];
    private ImgBased[]Imgcard = new ImgBased[maxCard];
    
    private int temp1 = -1;
    private int temp2 = -2;
    private int cardStatus = -1;
    private int tempCardChoosen1;
    private int tempCardChoosen2;
    
    private int maxPlayer = OptionsAbs.getPlayer(); // yolo
    private JLabel[] lblPlayer = new JLabel[maxPlayer];
    private JLabel[] lblScore = new JLabel[maxPlayer];
    private Player [] playa = new Player[maxPlayer];
    private int PlayerTurn = 1;
    private int GameMode = OptionsAbs.getMode();
    
    private int RemainingPair = maxPair;
    
    
    
    public Game() {
   
        initComponents();
        DeclareClass();
        pnl_Button.setLayout(new java.awt.GridLayout( OptionsAbs.getGrid() , OptionsAbs.getGrid()));
        addbutton();
        addPlayer();
        assignValue();  
        
    }
    
    
    
    
    public void setMaxPair(int pair){
        this.maxPair = (pair * pair)/2;
    }
    
    public void setGameMode(int mode){
        this.GameMode = mode;
    }
    
    public void setMaxPlayer(int playerz){
        this.maxPlayer = playerz;
    }
    
    private void DeclareClass(){
        
        for(int i = 0; i < maxPlayer ; i++){
            playa[i] = new Player();
        }
        
        for(int i = 0; i < maxCard ; i++){
           card[i] = new TextBased();
           Imgcard[i] = new ImgBased();
        }
        
         
    
    }
    
    //Dynamic add Button
    private void addbutton(){
    
        for(int j = 0; j < maxCard ; j++){
                btn[j] = new JButton();
                pnl_Button.add(btn[j]);
		btn[j].addActionListener((ActionListener) this);
                btn[j].setFont(new java.awt.Font("Tahoma", 1, 20));
	}
    }
    
    //Add Player in Sidemenu
    private void addPlayer(){
        String stuff;
        pnl_player.setLayout(new java.awt.GridLayout(maxPlayer, 2));
        for(int j = 0; j < maxPlayer ; j++){
                
                stuff = "Player " + (j+1);
                lblPlayer[j] = new JLabel(stuff);
                lblPlayer[j].setFont(new java.awt.Font("Tahoma", 1, 20));
                lblPlayer[j].setForeground(c[j]);
                lblPlayer[j].setHorizontalAlignment(JLabel.CENTER);
                
                
                lblScore[j] = new JLabel("0");
                lblScore[j].setFont(new java.awt.Font("Tahoma", 1, 20));
                lblScore[j].setHorizontalAlignment(JLabel.CENTER);
                
                pnl_player.add(lblPlayer[j]);
		pnl_player.add(lblScore[j]);
	}
        
    }
    
    //Dynamic Button action
    public void actionPerformed(ActionEvent evt){
     for(int j = 0; j <  maxCard; j++){
        if(evt.getSource() == btn[j]){
               
            if(GameMode == 1){
                TextGame(j);
            }
            else if(GameMode == 2){
                ImgGame(j);
            }
        }
    } 
    }
       
    //GameMode = 1 Run Function Text Based game sahaja
    private void TextGame(int identifier){
        System.out.println( ""  );
        JLabel myLabel = new JLabel();
        
  
         if( cardStatus == -1 )
        {
            temp1 = card[identifier].getCardID();
             btn[identifier].setText("" +temp1);
            tempCardChoosen1 = identifier;
            System.out.println( temp1 + "  ,  " + temp2  + "   "  +cardStatus);
            btn[tempCardChoosen1].setEnabled(false);
            cardStatus = -2;
        }
        else if(  cardStatus == -2 )
        {
            temp2 = card[identifier].getCardID();
            btn[identifier].setText("" +temp2);
            tempCardChoosen2 = identifier;
            btn[tempCardChoosen2].setEnabled(false);
            System.out.println( temp1 + "  ,  " + temp2  + "   "  +cardStatus);
            cardStatus = -3;
        }
        
        
        if( cardStatus == -3){
            
           if(temp1 != temp2){ 
             Timer timer = new Timer(0, (ActionEvent evt) -> {
             btn[tempCardChoosen1].setText("");
             btn[tempCardChoosen1].setEnabled(true);
             
             btn[tempCardChoosen2].setText("");
             btn[tempCardChoosen2].setEnabled(true);
             System.out.println( temp1 + "  ,  " + temp2  + "   "  +cardStatus);
             
             temp1 = -1;
             temp2 = -2;
             cardStatus = -1; 
             
             
            if(PlayerTurn <= maxPlayer){ 
                PlayerTurn++;
            }
            
            if(PlayerTurn > maxPlayer ){
                PlayerTurn = 1;
            }
            
            CPlayer.setText("Player " + PlayerTurn);
            CPlayer.setForeground(c[PlayerTurn-1]);
            
     
             
             
             });
                  timer.setInitialDelay(1200); //wait one second
                  timer.setRepeats(false); //only once
                  timer.start();

     
           }
           
           if(temp1 == temp2){
                temp1 = -1;
                temp2 = -2;
                cardStatus = -1; 
                
                int score = playa[PlayerTurn-1].getPlayerScore() + 1;
                playa[PlayerTurn-1].setPlayerScore(score);
           
               for(int j = 0; j < maxPlayer ; j++){
                    lblScore[j].setText("" +playa[j].getPlayerScore());
               }
             
                
                
                RemainingPair--;
                
                if(RemainingPair == 0){
                    EndGame();
                }
                
           }
        }
        
       
    }
    
    //GameMode = 1 Run Function Images Based game sahaja
    private void ImgGame(int identifier){
    
        System.out.println( ""  );
        int picnum = Imgcard[identifier].getImgNum();
        
        try{
        String domoarigato =  "/radingame/Image/"+picnum+".jpg";
        System.out.println(domoarigato);
        Image img = ImageIO.read(getClass().getResource(domoarigato));
        
        int btnwidth  = btn[identifier].getWidth()  - 20;
        int btnheight = btn[identifier].getHeight() - 20;
        Image newimg = img.getScaledInstance( btnwidth, btnheight,  java.awt.Image.SCALE_SMOOTH ) ;  
        
        ImageIcon Gambar = new ImageIcon(newimg);
        
        
        
         if( cardStatus == -1 )
        {
            temp1 = Imgcard[identifier].getImgNum();
            btn[identifier].setDisabledIcon(Gambar);
            btn[identifier].setIcon(Gambar);
            tempCardChoosen1 = identifier;
            btn[tempCardChoosen1].setEnabled(false);
            System.out.println( temp1 + "  ,  " + temp2  + "   "  +cardStatus);
            
            //unclickable
            
            cardStatus = -2;
        }
        else if(  cardStatus == -2 )
        {
            temp2 = Imgcard[identifier].getImgNum();
            btn[identifier].setDisabledIcon(Gambar);
            btn[identifier].setIcon(Gambar);
            tempCardChoosen2 = identifier;
            btn[tempCardChoosen1].setEnabled(false);
            System.out.println( temp1 + "  ,  " + temp2  + "   "  +cardStatus);
            cardStatus = -3;
        }
        
        
        if( cardStatus == -3){
            
           if(temp1 != temp2){ 
             Timer timer = new Timer(0, (ActionEvent evt) -> {
             btn[tempCardChoosen1].setIcon(null);
             btn[tempCardChoosen1].setEnabled(true);
             
             btn[tempCardChoosen2].setIcon(null);
             btn[tempCardChoosen2].setEnabled(true);
             System.out.println( temp1 + "  ,  " + temp2  + "   "  +cardStatus);
             
             temp1 = -1;
             temp2 = -2;
             cardStatus = -1; 
             
             
            if(PlayerTurn <= maxPlayer){ 
                PlayerTurn++;
            }
            
            if(PlayerTurn > maxPlayer ){
                PlayerTurn = 1;
            }
            
            CPlayer.setText("Player " + PlayerTurn);
            CPlayer.setForeground(c[PlayerTurn-1]);

     
             
             
             });
                  timer.setInitialDelay(1200); //wait one second
                  timer.setRepeats(false); //only once
                  timer.start();

     
           }
           
           if(temp1 == temp2){
                temp1 = -1;
                temp2 = -2;
                cardStatus = -1; 
                
                int score = playa[PlayerTurn-1].getPlayerScore() + 1;
                playa[PlayerTurn-1].setPlayerScore(score);
           
               for(int j = 0; j < maxPlayer ; j++){
                    lblScore[j].setText("" +playa[j].getPlayerScore());
               }
             
                
                
                RemainingPair--;
                
                if(RemainingPair == 0){
                    EndGame();
                }

                
           }
        }
        
         } catch (IOException ex) {};    
    }
    
    private void assignValue(){
        Random rand = new Random();
        int min = 1;
       
        
        int[] limitNum = new int[maxPair];      
        Arrays.fill(limitNum, 2);
        

        boolean proceed = false;
         
        int tempnum;
        int z = 0;
        int remainingCard = maxCard;
         do{
             tempnum = rand.nextInt((maxPair - min) + 1) + min;
             
             if(limitNum[tempnum-1] != 0){
                 
                    card[z].setCardId(tempnum);
                    Imgcard[z].setImgNum(tempnum);
                 
                     
                 
                 
                 limitNum[tempnum-1] -= 1;
                 z++;
                 remainingCard--;
             }
             if(remainingCard == 0){
                 proceed = true;
             }
     
                 
         
         }while(proceed == false);
         
        for(int i = 0 ; i < maxCard ; i++){
          System.out.print("    " + card[i].getCardID());
        }
        
        shuffleArray(card);
         System.out.println("    ");
        for(int i = 0 ; i < maxCard ; i++){
          System.out.print("    " + card[i].getCardID());
        }
    }
    
    // show ranking stuff
    private void EndGame(){
        
        int[][] multi = new int[maxPlayer][2];
        
        for( int j = 0; j < maxPlayer ; j++){
            
            multi[j][0] = j;
            multi[j][1] =  playa[j].getPlayerScore();
        
        }
        
        for (int i = 0; i < multi.length-1; i++){

          for(int j = 0; j < multi.length-1; j++){

              if( multi[j][1] < multi[j+1][1]){

                int [] temp = multi[j];
                multi[j] = multi[j+1];
                multi[j+1] = temp;                  
              }
          }
        }
        System.out.println("__________________________________");
        for( int j = 0; j < maxPlayer ; j++){          
           System.out.println(multi[j][0] +  "     " + multi[j][1] );

        }
        
        
        String stuff;
       
        JLabel[] lblPlayer2 = new JLabel[maxPlayer];
        JLabel[] lblScore2 = new JLabel[maxPlayer];
        pnl_result.setLayout(new java.awt.GridLayout(maxPlayer, 2));
        for(int j = 0; j < maxPlayer ; j++){
                
                stuff = "Player " + (multi[j][0]+1);
                lblPlayer2[j] = new JLabel(stuff);
                lblPlayer2[j].setFont(new java.awt.Font("Tahoma", 1, 20));
                lblPlayer2[j].setForeground(c[multi[j][0]]);
                lblPlayer2[j].setHorizontalAlignment(JLabel.CENTER);
                
                
                lblScore2[j] = new JLabel(""+ multi[j][1]);
                lblScore2[j].setFont(new java.awt.Font("Tahoma", 1, 20));
                lblScore2[j].setHorizontalAlignment(JLabel.CENTER);
                
                pnl_result.add(lblPlayer2[j]);
		pnl_result.add(lblScore2[j]);
                
                
                
	}
        
        JOptionPane.showMessageDialog(null, pnl_ranking , "Game Over" , JOptionPane.PLAIN_MESSAGE  );
        
        
            
    
    }
    
    private void shuffleArray(TextBased[] ar)
  {
    Random rnd = new Random();
    for (int i = ar.length - 1; i > 0; i--)
    {
      int index = rnd.nextInt(i + 1);
      // Simple swap
      TextBased a = ar[index];
      ar[index] = ar[i];
      ar[i] = a;
    }
  }

    /**
     * !!!!!!!!!!!DONT GO HERE, This is auto generated, it doesnt concern you, especially you radin because you stupid crazy people!!!!!!!
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_ranking = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pnl_result = new javax.swing.JPanel();
        pnl_Info = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        CPlayer = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        pnl_player = new javax.swing.JPanel();
        pnl_Button = new javax.swing.JPanel();

        pnl_ranking.setName(""); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("RESULT");

        pnl_result.setLayout(new java.awt.GridLayout(4, 2));

        javax.swing.GroupLayout pnl_rankingLayout = new javax.swing.GroupLayout(pnl_ranking);
        pnl_ranking.setLayout(pnl_rankingLayout);
        pnl_rankingLayout.setHorizontalGroup(
            pnl_rankingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_rankingLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(pnl_rankingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_result, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_rankingLayout.setVerticalGroup(
            pnl_rankingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_rankingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl_result, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        pnl_Info.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel1.setText("Turn : ");

        CPlayer.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        CPlayer.setForeground(new java.awt.Color(255, 0, 0));
        CPlayer.setText("Player 1");

        jButton1.setBackground(new java.awt.Color(153, 255, 153));
        jButton1.setText("RESTART");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("NEW GAME");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        pnl_player.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(0, 0, 0)));
        pnl_player.setAlignmentY(1.5F);
        pnl_player.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnl_player.setLayout(new java.awt.GridLayout(4, 2, 0, 10));

        javax.swing.GroupLayout pnl_InfoLayout = new javax.swing.GroupLayout(pnl_Info);
        pnl_Info.setLayout(pnl_InfoLayout);
        pnl_InfoLayout.setHorizontalGroup(
            pnl_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_InfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_InfoLayout.createSequentialGroup()
                        .addGap(0, 28, Short.MAX_VALUE)
                        .addGroup(pnl_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_InfoLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(CPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_InfoLayout.createSequentialGroup()
                                .addGroup(pnl_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton2)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(41, 41, 41))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_InfoLayout.createSequentialGroup()
                        .addComponent(pnl_player, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pnl_InfoLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        pnl_InfoLayout.setVerticalGroup(
            pnl_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(pnl_InfoLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnl_InfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CPlayer)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_player, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(410, Short.MAX_VALUE))
        );

        pnl_InfoLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        pnl_Button.setBorder(new javax.swing.border.MatteBorder(null));
        pnl_Button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnl_Button.setLayout(new java.awt.GridLayout(4, 2));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnl_Info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_Info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        this.dispose();
      
        new Game().setVisible(true);
      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
       new Option().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
       java.awt.EventQueue.invokeLater(new Runnable() {
           public void run() {
             new Game().setVisible(true);
               
            }
       });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CPlayer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel pnl_Button;
    private javax.swing.JPanel pnl_Info;
    private javax.swing.JPanel pnl_player;
    private javax.swing.JPanel pnl_ranking;
    private javax.swing.JPanel pnl_result;
    // End of variables declaration//GEN-END:variables
}
