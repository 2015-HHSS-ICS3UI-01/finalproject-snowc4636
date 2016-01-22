/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameExample;

import java.awt.Rectangle;

/**
 *
 * @author snowc4636
 */
public class Duck extends Rectangle {

    int speed = 0;

    public Duck(int x, int y, int width, int height, int s) {
        this(x, y, width, height);
        speed = s;
    }
    
    public Duck(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }
}
