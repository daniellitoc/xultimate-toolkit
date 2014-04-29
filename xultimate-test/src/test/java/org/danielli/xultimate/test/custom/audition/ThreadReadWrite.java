package org.danielli.xultimate.test.custom.audition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 设计两个线程，一个读取文件，一个向文件写入。
 */
public class ThreadReadWrite {
	private BufferedReader reader;
	private BufferedWriter writer;
	{
		try {
			File file = new File("/home/toc/Desktop/a");
			reader = new BufferedReader(new FileReader(file));
			writer = new BufferedWriter(new FileWriter(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public String read() throws IOException {
		lock.readLock().lock();
		String content = reader.readLine();
		lock.readLock().unlock();
		return content;
	}

	public void write(String content) throws IOException {
		lock.writeLock().lock();
		writer.append(content);
		writer.flush();
		lock.writeLock().unlock();
	}

	class WriterTask implements Runnable {

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					write(String.valueOf(i) + "\n");
					System.out.println("写入内容：" + String.valueOf(i));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class ReaderTask implements Runnable {

		@Override
		public void run() {

			try {
				for (int i = 0; i < 10; i++) {
					System.out.println("读到内容：" + read());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		ThreadReadWrite readWrite = new ThreadReadWrite();
		new Thread(readWrite.new WriterTask()).start();
		new Thread(readWrite.new ReaderTask()).start();
	}
}
