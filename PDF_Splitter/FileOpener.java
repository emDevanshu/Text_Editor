package PDF_Splitter;
/* Java program to open any file from PC and display it */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileOpener {
    public static void main(String[] args) {
        System.out.println(openfile());
    }
    public static boolean openfile()
    {
        try {
            JFrame frame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
            fileChooser.setFileFilter(filter);

            fileChooser.setCurrentDirectory(new File("/Users/devanshusharma/Desktop"));

            int result = fileChooser.showOpenDialog(frame);
            System.out.println("Result : " + result );

            if(result == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println("FilePath : "+ selectedFile);

                if(!Desktop.isDesktopSupported()) {
                    System.out.println("Not supported");
                    return false;
                }
                else {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(selectedFile);
                    return true;
                }
            }
            else if(result == JFileChooser.CANCEL_OPTION)
            {
                System.out.println("Cancelled");
                return false;
            }
            fileChooser.setVisible(false);
            frame.dispose();
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
        return false;
    }
}
