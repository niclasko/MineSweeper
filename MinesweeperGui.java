/*
 * Minesweeper GUI
 * by Niclas Kjall-Ohlsson copyright 2005
 */

public class MinesweeperGui implements java.awt.event.MouseListener {
   
    // Images
    private final int COVERED = 0;
    private final int UNCOVERED = 1;
    private final int MINE = 2;
    private final int ONE = 3;
    private final int TWO = 4;
    private final int THREE = 5;
    private final int FOUR = 6;
    private final int FIVE = 7;
    private final int SIX = 8;
    private final int SEVEN = 9;
    private final int EIGHT = 10;
    private final int COVERED_RECOMMENDED = 11;
    private final int TERMINAL = 12;
    
    // GUI elements
    private javax.swing.JFrame frame = new javax.swing.JFrame();
    private MyPanel panel;
    private java.awt.image.BufferedImage images[] = new java.awt.image.BufferedImage[13];
    private javax.swing.JLabel lblUc;
    private javax.swing.JLabel lblUm;
    private javax.swing.JButton btnReset;
    private javax.swing.JTextField txtNumMines;
    
       
    // Thread of execution
    
    private Minesweeper sweeper;
    
    public static void main(String args[]) {
        new MinesweeperGui();
    }
    
    public void loadImages() {
        String sIname = "";
        for(int i=0;i<images.length;i++) {
            switch(i) {
                case COVERED:
                   sIname = "covered";
                   break;
                case UNCOVERED:
                   sIname = "uncovered";
                   break;
                case MINE:
                   sIname = "mine";
                   break;
                case ONE:
                   sIname = "1";
                   break;
                case TWO:
                   sIname = "2";
                   break;
                case THREE:
                   sIname = "3";
                   break;
                case FOUR:
                   sIname = "4";
                   break;
                case FIVE:
                   sIname = "5";
                   break;
                case SIX:
                   sIname = "6";
                   break;
                case SEVEN:
                   sIname = "7";
                   break;
                case EIGHT:
                   sIname = "8";
                   break;
                case COVERED_RECOMMENDED:
                   sIname = "covered_recommended";
                   break;
                case TERMINAL:
                   sIname = "terminal";
                   break;
            }
			
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(sIname+".gif");

            java.awt.Image image = icon.getImage();
            java.awt.image.BufferedImage buffImage = 
              new java.awt.image.BufferedImage(
                  icon.getIconWidth(), 
                  icon.getIconHeight(), 
                  java.awt.image.BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics g = buffImage.getGraphics();
            g.drawImage(image, 0, 0, null);
            images[i] = buffImage;
        }
    }
   
    class MyPanel extends javax.swing.JPanel {
        public MyPanel(boolean b) {
            super(b);
        }
        
        public void paintComponent(java.awt.Graphics pg) {
            for(int i=1; i<sweeper.getRows()+1;i++) {
                for(int j=1; j<sweeper.getCols()+1; j++) {
                    if(sweeper.cc_tab[i][j]) { pg.drawImage(images[COVERED],(i-1)*16,(j-1)*16,null); }
                    else if(!sweeper.cc_tab[i][j]) {
                        switch(sweeper.ml_tab[i][j]) {
                        case -1:
                           pg.drawImage(images[MINE],(i-1)*16,(j-1)*16,null);
                           break;
                        case 0:
                           pg.drawImage(images[UNCOVERED],(i-1)*16,(j-1)*16,null); 
                           break;
                        case 1:
                           pg.drawImage(images[ONE],(i-1)*16,(j-1)*16,null); 
                           break;
                        case 2:
                           pg.drawImage(images[TWO],(i-1)*16,(j-1)*16,null); 
                           break;
                        case 3:
                           pg.drawImage(images[THREE],(i-1)*16,(j-1)*16,null); 
                           break;
                        case 4:
                           pg.drawImage(images[FOUR],(i-1)*16,(j-1)*16,null); 
                           break;
                        case 5:
                           pg.drawImage(images[FIVE],(i-1)*16,(j-1)*16,null); 
                           break;
                        case 6:
                           pg.drawImage(images[SIX],(i-1)*16,(j-1)*16,null); 
                           break;
                        case 7:
                           pg.drawImage(images[SEVEN],(i-1)*16,(j-1)*16,null); 
                           break;
                        case 8:
                           pg.drawImage(images[EIGHT],(i-1)*16,(j-1)*16,null); 
                           break;
                        }
                    }
                }
            }
            
            if(sweeper.gameOver()) {
                pg.drawImage(images[TERMINAL], 1, 55, null);
                return;
            }
            
        }
    }
    
    public void refreshGui() {
        lblUc.setText(""+sweeper.getNumUncoveredCells());
        lblUm.setText(""+sweeper.getNumUncoveredMines());

        panel.repaint();
    }
    
    public void mouseClicked(java.awt.event.MouseEvent e) {
        sweeper.probeCell((int)(e.getX()/16)+1,(int)(e.getY()/16)+1);
        refreshGui();
    }
    
    public void mouseEntered(java.awt.event.MouseEvent e) {
    }
    
    public void mouseExited(java.awt.event.MouseEvent e) {
    }
    
    public void mousePressed(java.awt.event.MouseEvent e) {
    }
    
    public void mouseReleased(java.awt.event.MouseEvent e) {
    }
    
    public MinesweeperGui() {
        
        sweeper = new Minesweeper(); 
        sweeper.initialize();
        loadImages();
        
        java.awt.Container c = frame.getContentPane();
        c.setLayout(null);
        c.setSize(sweeper.getRows()*16+10,sweeper.getCols()*16+10);
        
        frame.setTitle("MINESWEEPER");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.setSize(sweeper.getRows()*16+200,sweeper.getCols()*16+200);
        
		javax.swing.JLabel lblUcTitle = new javax.swing.JLabel("Uncovered Cells:");
		lblUcTitle.setBounds(15, 25, 120, 20);
		c.add(lblUcTitle);
		
        lblUc = new javax.swing.JLabel("0");
        lblUc.setBounds(140,25,30,20);
        c.add(lblUc);
        
		javax.swing.JLabel lblUmTitle = new javax.swing.JLabel("Uncovered Mines:");
		lblUmTitle.setBounds(15, 50, 120, 20);
		c.add(lblUmTitle);

        lblUm = new javax.swing.JLabel("0");
        lblUm.setBounds(140,50,20,20);
        c.add(lblUm);
        
		javax.swing.JLabel lblNumMinesTitle = new javax.swing.JLabel("Mines (change and click reset):");
		lblNumMinesTitle.setBounds(100, 1, 200, 20);
		c.add(lblNumMinesTitle);

        txtNumMines = new javax.swing.JTextField(""+sweeper.getNumMines());
        txtNumMines.setBounds(305,1,40,20);
        c.add(txtNumMines);
        
        btnReset = new javax.swing.JButton("Reset");
        btnReset.setBounds(15,1,80,20);
        c.add(btnReset);

        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                sweeper.setNumMines(Integer.parseInt(txtNumMines.getText()));
                sweeper.initialize();
                panel.repaint();
            }
        });
        
        panel = new MyPanel(true);
        panel.setBounds(15,80,sweeper.getRows()*16,sweeper.getCols()*16);
        c.add(panel);
        panel.addMouseListener(this);

        frame.setVisible(true);
        
    }    
    
}