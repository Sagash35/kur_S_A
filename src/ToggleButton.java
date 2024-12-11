import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToggleButton extends JComponent {
    private boolean toggled = false;
    public ToggleButton() {
        setPreferredSize(new Dimension(80, 55));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toggled = !toggled;
                setToggle(toggled);
                repaint();
                firePropertyChange("toggled", !toggled, toggled);
            }
        });
    }
    public void setToggle(boolean toggled){
        this.toggled=toggled;
    }
    public boolean getToggle(){
        return toggled;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getToggle() ? (new Color(210, 78, 238)) : (new Color(178, 0, 219)));
        Font largeFont = new Font("Times new roman", Font.PLAIN, 15);
        String keyChar;
        g.setFont(largeFont);
        g.fillRoundRect(0, 17, 40, 20, 10, 10); // Округленные черные клавиши

        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 17, 40, 20, 10, 10); // Округленные черные клавиши

        if(getToggle()){

            keyChar = "выкл.";
        }
        else{
            keyChar = "вкл.";
        }
        g.drawString(keyChar, 3, 32);
    }

    public boolean isToggled() {
        return toggled;
    }
}
