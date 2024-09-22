package mitraMario;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverScreen {
    private JFrame frame;
    private Image backgroundImage;

    public GameOverScreen(Game game, String s, Handler handler) {
        frame = new JFrame("Game Over");
        frame.setSize(500, 500);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        backgroundImage = new ImageIcon(getClass().getResource("/gameover_background.jpg")).getImage();
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); 
        JLabel label = new JLabel("Game Over", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 40));
        label.setForeground(Color.RED);
        label.setBounds(150, 30, 200, 50); 
        JLabel message = new JLabel(s, SwingConstants.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 20));
        message.setForeground(Color.black);
        message.setBounds(50, 90, 400, 30); 

        JLabel coins = new JLabel("Coins Collected: " + handler.totalCoins, SwingConstants.CENTER);
        coins.setFont(new Font("Serif", Font.BOLD, 20));
        coins.setForeground(Color.black);
        coins.setBounds(50, 140, 400, 30); 

        JLabel mushrooms = new JLabel("Mushrooms Collected: " + handler.totalMushrooms, SwingConstants.CENTER);
        mushrooms.setFont(new Font("Serif", Font.BOLD, 20));
        mushrooms.setForeground(Color.black);
        mushrooms.setBounds(50, 190, 400, 30); 

        JLabel gombas = new JLabel("Gombas Defeated: " + handler.totalGombas, SwingConstants.CENTER);
        gombas.setFont(new Font("Serif", Font.BOLD, 20));
        gombas.setForeground(Color.black);
        gombas.setBounds(50, 240, 400, 30); 

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.BOLD, 20));
        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBounds(100, 350, 100, 50); 

        JButton replayButton = new JButton("Replay");
        replayButton.setFont(new Font("Serif", Font.BOLD, 20));
        replayButton.setBackground(Color.BLUE);
        replayButton.setForeground(Color.WHITE);
        replayButton.setBounds(300, 350, 100, 50); 
        
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        replayButton.setBorderPainted(false);
        replayButton.setFocusPainted(false);
        
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        replayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                game.restart();
            }
        });

       
        panel.add(label);
        panel.add(message);
        panel.add(coins);
        panel.add(mushrooms);
        panel.add(gombas);
        panel.add(exitButton);
        panel.add(replayButton);

        
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
