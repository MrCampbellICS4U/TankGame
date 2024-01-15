//Create frame/panel/drawing panel
//collect input
//draw everything
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Main {
	static boolean w,a,s,d;
	public static void main(String[] args){
	    this.addKeyListener(new KeyAdapter() {
	    	public void keyPressed(KeyEvent e) {
	    		int keyCode = e.getKeyCode();
	    	    if(keyCode == 'W' || keyCode == 38) w = true;
	    	    if(keyCode == 'A' || keyCode == 37) a = true;
	    	    if(keyCode == 'S' || keyCode == 40) s = true;
	    	    if(keyCode == 'D' || keyCode == 39) d = true; 
				if(keyCode == KeyEvent.VK_ESCAPE) //PAUSE MENU;
	    	}
	       	public void keyReleased(KeyEvent e) {
	       		int keyCode = e.getKeyCode();
	       		if(keyCode == 'W' || keyCode == 38) w = false;
	       		if(keyCode == 'A' || keyCode == 37) a = false;
	       		if(keyCode == 'S' || keyCode == 40) s = false;
	       		if(keyCode == 'D' || keyCode == 39) d = false;          
	    	}
	    });
  }
}

/*
frame.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) { 
        if(e.getButton() == MouseEvent.BUTTON1) {
            shoot()
        }
        if(e.getButton() == MouseEvent.BUTTON3) {
            bomb()
        }
    }
});
*/