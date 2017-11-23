package com.cui.code.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class MenuDemo
{

	private Frame frame;
	private MenuBar menuBar;
	private Menu filemMenu, editmMenu, newFilemMenu;
	private MenuItem menuItem2_1;
	private MenuItem menuItem1_1_1;

	private FileDialog openDialog, saveDialog;
	private MenuItem openMenuItem;
	private MenuItem saveMenuItem;
	private MenuItem closeItem;

	private TextArea textArea;

	private File file;

	public MenuDemo()
	{
		init();
	}

	private void init()
	{
		frame = new Frame("CUI的小小记事本");
		frame.setBounds(300, 300, 500, 400);
		frame.setVisible(true);

		openDialog = new FileDialog(frame, "打开", FileDialog.LOAD);
		saveDialog = new FileDialog(frame, "保存", FileDialog.SAVE);

		menuBar = new MenuBar();
		filemMenu = new Menu("文件");
		editmMenu = new Menu("编辑");

		openMenuItem = new MenuItem("打开");
		saveMenuItem = new MenuItem("保存");

		newFilemMenu = new Menu("新建");
		menuItem1_1_1 = new MenuItem("java");
		closeItem = new MenuItem("关闭");

		menuItem2_1 = new MenuItem("复制");

		newFilemMenu.add(menuItem1_1_1);
		filemMenu.add(newFilemMenu);
		filemMenu.add(openMenuItem);
		filemMenu.add(saveMenuItem);
		filemMenu.add(closeItem);

		editmMenu.add(menuItem2_1);

		menuBar.add(filemMenu);
		menuBar.add(editmMenu);

		frame.setMenuBar(menuBar);

		textArea = new TextArea(20, 40);
		frame.add(textArea);
		myEvent();
	}

	private void myEvent()
	{
		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		openMenuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				openDialog.setVisible(true);

				String dirPath = openDialog.getDirectory();
				String fileName = openDialog.getFile();

				// System.out.println(dirPath + fileName);

				if (dirPath == null || fileName == null)
				{
					return;
				}
				textArea.setText("");
				file = new File(dirPath, fileName);

				try
				{

					BufferedReader bufferedReader = new BufferedReader(
							new FileReader(file));

					String lineString = null;

					while ((lineString = bufferedReader.readLine()) != null)
					{
						textArea.append(lineString + "\r\n");
					}
					bufferedReader.close();
				} catch (IOException ex)
				{
					// TODO: handle exception
					throw new RuntimeException("读取失败！");
				}
			}
		});

		saveMenuItem.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				if (file == null)
				{
					saveDialog.setVisible(true);

					String dirPath = saveDialog.getDirectory();
					String fileName = saveDialog.getFile();

					if (dirPath == null || fileName == null)
					{
						return;
					}
					file = new File(dirPath, fileName);
				}

				try
				{

					BufferedWriter bufferedWriter = new BufferedWriter(
							new FileWriter(file));

					String text = textArea.getText();

					bufferedWriter.write(text);

					bufferedWriter.close();
				} catch (IOException ex)
				{
					// TODO: handle exception
					throw new RuntimeException("保存失败！");
				}
			}
		});

		closeItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
	}

	public static void main(String[] args)
	{
		new MenuDemo();
	}

}
