package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame {
    Location currentLocation;
    Panel gamePanel;
    String command;
    CommandLine commandInput;
    JTextArea displayOutput;
    public Game(){
        setTitle("Alone");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        commandInput = new CommandLine();
        commandInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                command = commandInput.getText();
                commandInput.setText("");
                displayOutput.append(command+"\n");
            }
        });
        displayOutput = new JTextArea();

        displayOutput.setEditable(false);
        displayOutput.setLineWrap(true);

        setBackground(Color.white);
        setForeground(Color.black);
        //gamePanel = new Panel();
        add(displayOutput, BorderLayout.CENTER);
        add(commandInput,BorderLayout.SOUTH);

        setSize(200,100);

        Location l1 = new Location("Room one","description for room one");
        Location l2 = new Location("Room two","description for room two");
        l1.addExit(new Exit(Exit.Direction.NORTH, l2));
        l2.addExit(new Exit(Exit.Direction.SOUTH,l1 ));

        currentLocation = l1;
        showLocation();
        pack();
        setVisible(true);
    }

    private  void showLocation(){
        displayOutput.append("\n" + currentLocation.getLocationTitle() +"\n");
        displayOutput.append("\n" + currentLocation.getLocationDescription()+ "\n");
        displayOutput.repaint();

    }

    public static void main(String[] args) {
	new Game();
    }
}
