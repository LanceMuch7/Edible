import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.*;
//import javax.sound.sampled.AudioInputStream;
//import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This file can be used to create very simple animations. Just fill in the
 * definition of drawFrame with the code to draw one frame of the animation, and
 * possibly change a few of the values in the rest of the program as noted
 * below. Note that if you change the name of the class, you must also change
 * the name in the main() routine!
 */
public class Edible extends JPanel implements ActionListener {
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 500;
    public static ArrayList<Poly> List = new ArrayList();
    
    public static boolean[] Directions = new boolean[4];
    private static int x = CANVAS_WIDTH / 2 - 5;
    private static int y = CANVAS_HEIGHT / 8;
    private static int size = 14;
    private static boolean GameOver = false;
    private static int speed = 4;
    private static int rate = 20;
    
    private static Font[] fonts = {
        new Font("Adore64", Font.BOLD, 26),
        new Font("Jedi", Font.PLAIN, 14),
        new Font("Jedi", Font.PLAIN, 16)
    };
    private static Image[] background = {
        //new Image()
    };
    private static File[] SF = {
        //new File()
    };
    

    public Edible() {
        super();
    }

    /*
    private int addPlayerScore(int score) throws FileNotFoundException {
        File Scores = new File("Scores.txt");
        Scanner data = new Scanner(Scores);
        ArrayList<String> D = new ArrayList();

        while (data.hasNext()) {
            D.add(data.nextLine());
        }
        for (int k = 0; k < D.size(); k++) {
            int oldscore = Integer.parseInt(D.get(k).substring(0, D.get(k).indexOf('|')));
            if (score > oldscore) {
                
                D.add(k, );
            }
        }
    }
    */
    
    /**
     * Draws one frame of an animation. This subroutine is called re second and
     * is responsible for redrawing the entire drawing area. The parameter g is
     * used for drawing. The frameNumber starts at zero and increases by 1 each
     * time this subroutine is called. The parameters width and height give the
     * size of the drawing area, in pixels. The sizes and positions of the
     * rectangles that are drawn depend on the frame number, giving the illusion
     * of motion.
     *
     * @param g
     * @param frameNumber
     * @param width
     * @param height
     */
    public void drawFrame(Graphics g, int frameNumber, int width, int height) throws FileNotFoundException {
        if (GameOver) {
            g.setColor(new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random())));
            g.setFont(fonts[0]);
            g.drawString("GAME OVER!", 270, 230);
            g.setFont(fonts[1]);
            g.drawString("YOU GOT EATEN!", 300, 320);
            g.setFont(fonts[2]);
            g.drawString("[Enter to play again]", 300, 336);
        } else if (frameNumber < 100) {
            g.setFont(fonts[2]);
            g.setColor(Color.GRAY);
            g.drawString("Use arrow keys to eat Squares smaller than", 160, 220);
            g.drawString("you, but don't get eated by the bigger ones!", 160, 240);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.setFont(fonts[0]);
            g.drawString("Game timer: " + ((int)(20*frameNumber/10 - 5))/100 + " seconds.", 80, 240);
            
            for (Poly i : List){
                if(frameNumber - i.getAge() > 1.5*1778/speed) {
                    List.remove(i);
                } else {
                    int[] NewPos = {(int)(i.getCoords(0) + 0.45*speed), (int)(i.getCoords(1) + 0.45*i.getDirection(1))};
                    int xDiff = x - i.getCoords(0);
                    int yDiff = y - i.getCoords(1);
                    
                    g.setColor(i.getColor());
                    g.fillRect(NewPos[0], NewPos[1], i.getSize(), i.getSize());
                    i.setCoords(NewPos);
                    if (size > i.getSize()) {
                        if (Math.sqrt(xDiff*xDiff + yDiff*yDiff) < size/2 + 5) {
                            size += 1;
                            List.remove(i);
                        }
                    } else {
                        if (Math.sqrt(xDiff*xDiff + yDiff*yDiff) < i.getSize()/2 + 5) {
                            GameOver = true;
                            size = 0;
                            //addPlayerScore(frameNumber);
                            break;
                        }
                    }
                }
            }
            
            g.setColor(Color.BLACK);
            g.fill3DRect(x, y, size, size, true);

            if (Directions[0]) {
                x -= speed/2;
            } if (Directions[1]) {
                x += speed/2;
            } if (Directions[2]) {
                y -= speed/2;
            } if (Directions[3]) {
                y += speed/2;
            }
            
            double a = Math.random();
            if(a > 0.95 && List.size() < 10){
                int coord[] = {0,(int)(600*Math.random())};
                double direct[] = {speed, 2*Math.random() - 1};
                Poly p = new Poly(1, new Color((int)(255*Math.random()),(int)(255*Math.random()),(int)(255*Math.random())), size+(int)(Math.random()*28 - 18), coord, direct, frameNumber);
                List.add(p);
            }
            speed = 4 + (int)((size-14)/8);
        }
    }
    
    
// -------------------------------------------- MAIN METHOD ------------------------------------
    public static void main(String[] args) {
        /* NOTE:  The string in the following statement goes in the title bar
         * of the window.
         */
        JFrame window = new JFrame("Edibles");

        /*
         * NOTE: If you change the name of this class, you must change
         * the name of the class in the next line to match!
         */
        Edible drawingArea = new Edible();

        JPanel containerPanel = new JPanel();
        containerPanel.add(drawingArea);

        drawingArea.setBackground(Color.WHITE);
        window.setContentPane(containerPanel);

        /* NOTE:  In the next line, the numbers 600 and 450 give the
         * initial width and height of the drawing array.  You can change
         * these numbers to get a different size.
         */
        drawingArea.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        
        window.pack();
        window.setLocation(100, 50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // "this" JFrame fires KeyEvent
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        Directions[0] = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        Directions[1] = true;
                        break;
                    case KeyEvent.VK_UP:
                        Directions[2] = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        Directions[3] = true;
                        break;
                    case KeyEvent.VK_ENTER:
                        size = 10;
                        speed = 4;
                        frameNum = 0;
                        GameOver = false;
                        List = new ArrayList();
                        x = 400;
                        y = 250;
                        break;
                }
            }
            @Override
            public void keyReleased(KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        Directions[0] = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        Directions[1] = false;
                        break;
                    case KeyEvent.VK_UP:
                        Directions[2] = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        Directions[3] = false;
                        break;
                }
            }
        });

        /*
         * Note:  In the following line, you can change true to
         * false.  This will prevent the user from resizing the window,
         * so you can be sure that the size of the drawing area will
         * not change.  It can be easier to draw the frames if you know
         * the size.
         */
        window.setResizable(false);

        /* NOTE:  In the next line, the number gives the time between
         * calls to drawFrame().  The time is given in milliseconds, where
         * one second equals 1000 milliseconds.  You can increase this number
         * to get a slower animation.  You can decrease it somewhat to get a
         * faster animation, but the speed is limited by the time it takes
         * for the computer to draw each frame. 
         */
        Timer frameTimer = new Timer(rate, drawingArea);

        window.setVisible(true);
        frameTimer.start();

    } // end main

    private static int frameNum;
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        frameNum++;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            drawFrame(g, frameNum, getWidth(), getHeight());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Edible.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


