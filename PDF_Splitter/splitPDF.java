package PDF_Splitter;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class splitPDF {
    static String start="", end="";
    static boolean flag = true;
    public static void main(String[] args) throws IOException
    {

        JOptionPane.showMessageDialog(null, "Select a file");


        // GET THE FILE FROM THE USER
        String path = getfile(); //this stores the path of the selected file
        if(path.equals("-1")) {
            System.out.println("No File Selected");
            return;
        }


        // TAKE INPUT FROM THE USER, start to end range
        takeInput();
        if(flag == false) return;



        int slashIdx = path.lastIndexOf('/');
        int dotIdx = path.lastIndexOf('.');
        String original_filename = path.substring(slashIdx+1,dotIdx);

        // LOADING THE FILE TO BE SPLIT
        File oldfile = new File(path);
        PDDocument doc = PDDocument.load(oldfile);




        //HERE WE ARE DEFINING WHERE THE NEW FILE WILL GET STORED
        File newFileDest = new File("/Users/devanshusharma/Desktop");
//        newfileDest.mkdirs(); // if location doesn't exist it will create a new directory


        // THIS OBJECT SPLITS THE FILE
        Splitter splitter = new Splitter();
        splitter.setStartPage(Integer.parseInt(start));
        splitter.setEndPage(Integer.parseInt(end));


        List<PDDocument> splitpages = splitter.split(doc);


        //This for loop will merge all the pages accordingly
        PDDocument newfile = new PDDocument();
        for(PDDocument mydoc : splitpages)
        {
            newfile.addPage(mydoc.getPage(0));
        }

        newfile.save(newFileDest+"/"+original_filename+"_"+start+"_to_"+end+".pdf");
        newfile.close();


    }
    public static String getfile()
    {
        try {
            JFrame frame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();

            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
            fileChooser.setFileFilter(filter);

            fileChooser.setCurrentDirectory(new File("/Users/devanshusharma/Desktop"));

            int result = fileChooser.showOpenDialog(frame);


            if(result == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
                frame.dispose();
                return selectedFile.toString();
            }
            else if(result == JFileChooser.CANCEL_OPTION)
            {
                JOptionPane.showMessageDialog(null, "No File Selected!!");
            }

            frame.dispose();
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return "-1";
    }
    private static void takeInput() {
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.setPreferredSize(new Dimension(250, 70));

        panel.add(new JLabel("From page : "));
        JTextField nameTextField = new JTextField();
        panel.add(nameTextField);
        panel.add(new JLabel("To page : "));
        JTextField ageTextField = new JTextField();
        panel.add(ageTextField);


        int result = JOptionPane.showConfirmDialog(null, panel, "Enter the range", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            start = nameTextField.getText();
            end = ageTextField.getText();
        }
        else if(result == JOptionPane.CANCEL_OPTION) {
            flag = false;
            return;
        }

        System.out.println(start);
        System.out.println(end);
    }
}