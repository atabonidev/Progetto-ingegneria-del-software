package main_package.view;

import java.io.PrintWriter;

public class OutputUtils {
    private PrintWriter writer;

    public OutputUtils(PrintWriter writer){
        this.writer = writer;
    }
    public void print(String testo) {
        writer.print(testo);
        writer.flush();
    }

    public void println(String testo) {
        writer.println(testo);
        writer.flush();
    }

    public void close(){
        writer.close();
    }
}
