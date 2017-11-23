package com.cui.code.test;

import java.io.File;
import java.util.Scanner;

public class DirectorySizeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.print("Enter a directory or a file: ");
		Scanner input = new Scanner(System.in);
		String directory = input.nextLine();

		long fileLength = getSize(new File(directory));
		System.out.println("The size is: " + fileLength + "bytes");
		System.out.println("The size is: " + fileLength/1024 + "KB");
		System.out.println("The size is: " + fileLength/1024/1024 + "MB");

	}

	private static long getSize(File file) {
		long size = 0;

		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++)
				size += getSize(files[i]);
		}

		else
			size += file.length();
		return size;
	}
}
