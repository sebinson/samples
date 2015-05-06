package net.sebinson.sample.demos.nio.channel.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

    public static void main(String[] args) throws IOException {

        RandomAccessFile raf = new RandomAccessFile("data/nio-data.txt", "rw");

        FileChannel channel = raf.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(100);

        int bytesRead = channel.read(buffer);
        
        while (bytesRead != -1) {

            System.out.println("Read " + bytesRead);
            
            buffer.flip();

            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }

            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        raf.close();
    }
}
