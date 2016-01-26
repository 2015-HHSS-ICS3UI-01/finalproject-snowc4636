package GameExample;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 *
 * @author snowc4636
 */
public class Game extends JComponent implements KeyListener, MouseMotionListener, MouseListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    // sets the framerate and delay for our game
    // you just need to select an approproate framerate
    long desiredFPS = 60;
    long desiredTime = (1000) / desiredFPS;
    int levels = 1;
    int frameCount = 0;
    int RandomX = 0;
    int RandomY = 0;
    int move = -1;
    int score = 0;
    int number = 0;
    int Life = 3;
    // player position variables
    int x = 100;
    int y = 500;
    //mouse variables
    int mouseX = 0;
    int mouseY = 0;
    //number of ducks
    int numberOfDucks = 0;
    int speed = 0;
    boolean buttonPressed = false;
    int screen = 0;
    //font
    Font myFont = new Font("Arial", Font.BOLD, 50);
    // arrays
    ArrayList<Duck> ducks = new ArrayList<Duck>();
    ArrayList<Duck> deadDucks = new ArrayList<Duck>();
    //keyboard variables
    boolean up = false;
    boolean down = false;
    boolean right = false;
    boolean left = false;
    boolean jump = false;
    boolean prevJump = false;
    boolean  Gameover  = false;
    // putting the images into the file
    BufferedImage menu = loadImage("5267876982_9cb999ec64.png");
    BufferedImage background = loadImage("images.png");
    BufferedImage gun = loadImage("diamondback_fs9_nine_for_sale-8.png");
    BufferedImage crosshair = loadImage("Untitled-1.png");
    BufferedImage duck = loadImage("Untitled-1888.png");
    BufferedImage scores = loadImage("Untitled-1_edited-2.png");
    BufferedImage level = loadImage("Untitled-12111.png");
    BufferedImage life = loadImage("aefbsdhubG_edited-1.png");
    BufferedImage GameOver = loadImage("Game_Over_Screen.png");
    //inputing the images

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (Exception e) {
            System.out.println("Error loading" + filename);
        }
        return img;
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!

        //draw the menu screen
        g.clearRect(0, 0, WIDTH, HEIGHT);
        if (screen == 0) {
            g.drawImage(menu, 0, 0, WIDTH, HEIGHT, null);

        }
        
        //if the screen equals 1 draw this
        if (screen == 1) {
            // draw these images
            g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
            g.drawImage(gun, x - 85, 450, 200, 200, null);
            g.drawImage(level, 250, 525, 100, 50, null);
            g.drawImage(life, 500, 525, 100, 50, null);
            // change the font for the next drawings
            g.setFont(myFont);
            // draw in the scores and input numbers on the screen
            g.drawImage(scores, 5, 525, 100, 50, null);
            g.drawString("" + score, 120, 565);
            g.drawString("" + number, 370, 565);
            g.drawString("" + Life, 650, 565);
            for (Rectangle block : ducks) {
                g.drawImage(duck, block.x, block.y, 50, 50, null);
            }
            if (screen == 4) {
                g.drawImage(GameOver, 0, 0, WIDTH, HEIGHT, null);

            }

            // GAME DRAWING GOES HERE 
            // set the color to black
            g.setColor(Color.BLACK);





            // draw the crosshair
            g.drawImage(crosshair, x, y, 50, 50, null);
            // GAME DRAWING ENDS HERE
        }

if( Gameover && screen == 4){
            g.drawImage(GameOver, 0, 0, WIDTH, HEIGHT, null);

        }

    }
    // The main game loop
    // In here is where all the logic for my game will go

    public void run() {
        // initial things to do before game starts
        //add ducks
        // if the creen equals 1
        if (screen == 1) {
            // the number of ducks = the level times 4
            numberOfDucks = number * 3;
            // input random duck at random x and y and different speeds
            for (int i = 0; i < numberOfDucks; i++) {
                RandomX = (int) (Math.random() * (-100 - -1000 + 1)) + -1000;
                RandomY = (int) (Math.random() * (450 - 100 + 1)) + 100;
                speed = (int) (Math.random() * (17 - 8 + 1)) + 8;
                ducks.add(new Duck(RandomX, RandomY, 100, 50, speed));
            }
        }





        // END INITIAL THINGS TO DO


        // Used to keep track of time used to draw and update the game
        // This is used to limit the framerate later on
        long startTime = 0;
        long deltaTime;

        // the main game loop section
        // game will end if you set done = false;
        boolean done = false;
        while (!done) {
            // determines when we started so we can keep a framerate
            startTime = System.currentTimeMillis();

            // all your game rules and move is done in here
            // GAME LOGIC STARTS HERE 
            //where the x position is
            if (screen == 1) {
                x = mouseX - 20;

                y = mouseY - 20;
                // levels = 0
                if (levels > 0) {
                    // framecount
                    frameCount++;
                    if (frameCount > 1) {
                        //ducks x + the speed
                        for (Duck d : ducks) {
                            if (d.x < 800) {
                                d.x = d.x + d.speed;
                            }
                        }
                        frameCount = 0;
                    }



                }
                // if the life = 0 do this
                if (Life > 0) {
                    //if the button is pressed where x is the duck will be removed
                    Iterator<Duck> it = ducks.iterator();
                    while (it.hasNext()) {
                        Duck d = it.next();
                        if (buttonPressed) {
                            if (d.contains(mouseX, mouseY)) {
                                deadDucks.add(d);
                                it.remove();
                                // each duck is worth 10 points
                                score = score + 10;
                                System.out.println(score);
                            }

                        }
                    }
                    // if no ducks are left. Level goes up 1 and number of ducks times 3
                    if (ducks.size() == 0) {
                        number = number + 1;
                        numberOfDucks = number * 3;
                        // again input random ducks at random x and y and speed
                        for (int i = 0; i < numberOfDucks; i++) {
                            RandomX = (int) (Math.random() * (-100 - -1000 + 1)) + -1000;
                            RandomY = (int) (Math.random() * (450 - 100 + 1)) + 100;
                            speed = (int) (Math.random() * (17 - 8 + 1)) + 8;
                            ducks.add(new Duck(RandomX, RandomY, 100, 50, speed));
                        }
                    }


                }
            } else if (screen == 0) {
            }
            // if duck reaches x = 800 life will be subtracted by 1
            Iterator<Duck> it = ducks.iterator();
            while (it.hasNext()) {
                Rectangle removeDuck = it.next();
                if (removeDuck.x >= 800) {
                    Life = Life - 1;
                    it.remove();
                
                }
            }
                if (Life == 0) {
                        // if 3 ducks reach x = 800, game is over
                        Gameover = true;
                        screen = 4;
                    }













            // GAME LOGIC ENDS HERE 

            // update the drawing (calls paintComponent)
            repaint();



            // SLOWS DOWN THE GAME BASED ON THE FRAMERATE ABOVE
            // USING SOME SIMPLE MATH
            deltaTime = System.currentTimeMillis() - startTime;
            if (deltaTime > desiredTime) {
                //took too much time, don't wait
            } else {
                try {
                    Thread.sleep(desiredTime - deltaTime);
                } catch (Exception e) {
                };
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates a windows to show my game
        JFrame frame = new JFrame("My Game");

        // creates an instance of my game
        Game game = new Game();
        // sets the size of my game
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(game);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        //add the listeners
        frame.addKeyListener(game); //keyboard
        game.addMouseListener(game); // mouse
        game.addMouseMotionListener(game); // mouse

        // starts my game loop
        game.run();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (screen == 0) {
        }
        // press enter to switch to screen 1
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            screen = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            left = false;
        } else if (key == KeyEvent.VK_RIGHT) {
            right = false;
        } else if (key == KeyEvent.VK_SPACE) {
            jump = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // mouse value
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // if holding down the left button
        if (e.getButton() == MouseEvent.BUTTON1) {
            buttonPressed = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // if released a button
        if (e.getButton() == MouseEvent.BUTTON1) {
            buttonPressed = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private ArrayList<Duck> ducks(int RandomX, int i, int i0, int i1, Object object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Font newFont(String arial, int BOLD, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}