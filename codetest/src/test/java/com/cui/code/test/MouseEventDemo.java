package com.cui.code.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseEventDemo {

	private Frame frame;
	private Button button;
	private TextField tf;

	public MouseEventDemo() {
		init();
	}

	private void init() {

		frame = new JFrame("事件监听");
		frame.setLocation(300, 200);
		frame.setSize(300, 400);
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);

		button = new Button("按我……");
		tf= new TextField(30);
		
		frame.add(button);
		frame.add(tf);

		myEvent();

	}

	private void myEvent() {
		// button.addActionListener(new ActionListener() {
		//
		// public void actionPerformed(ActionEvent e) {
		// System.out.println(e.getActionCommand());
		// }
		// });

		tf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (!(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9)) {
					System.out.println("必须是数字……");
					e.consume();
				}

			}
		});

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switch (e.getClickCount()) {
				case 1:
					System.out.println("你点了一下！");
					break;
				case 2:
					System.out.println("你点了两下！");
					break;
				case 3:
					System.out.println("你点了三下！");
					break;
				case 4:
					System.out.println("你点了四下！");
					break;
				default:
					System.out.println("你疯了吧！点那么多干嘛！鼠标坏不鸟了啊");
				}
			}
		});

		button.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println(KeyEvent.getKeyText(e.getKeyCode()) + "..."
						+ e.getKeyCode());
			}
		});

	}

	public static void main(String[] args) {
		new MouseEventDemo();
	}

}
