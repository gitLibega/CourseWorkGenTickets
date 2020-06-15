package sample;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.chart.ScatterChart;
import org.apache.log4j.helpers.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;


import javax.print.PrintService;
import javax.swing.*;
import javax.swing.text.StyleConstants;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/*
 * PrintTickets Класс печати документа с билетами
 *
 * Version 1.0
 *
 * Author:Libega Ilya
 */
public class PrintTickets extends Controller {


/**Метод создания документа и занесения в него данных с последующей печатью
 * @param ticketsArray - лист строк, где каждая строка формирует билеты и заголовок к ним
 * @param titleTicket-заголовок билета
 */
    public static void printDocumentWithTickets(ArrayList<String> ticketsArray, String titleTicket)
                                                throws IOException, PrinterException, DocumentException,
                                                URISyntaxException {
        if (ticketsArray.size()>0) {

            int count = 0; //Счетчик билетов
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream("printFile.pdf"));
            document.open();

          Font font= new Font(Font.FontFamily.TIMES_ROMAN);

            for (int i = 0; i < ticketsArray.size(); i++) {
                if (ticketsArray.get(i) == "________________________________________________") {
                    count++;
                    Paragraph p = new Paragraph();
                    p.setAlignment(1);
                    p.add(titleTicket+" " + count);
                    document.add(p);
                }

                Paragraph p = new Paragraph();
                p.setFont(font);
                p.add(ticketsArray.get(i));
                document.add(p);
            }
            document.close();
try {
    File file = new File("printFile.pdf");
   print(file);
    file.deleteOnExit();
}
catch (Exception e)
{
    JOptionPane.showMessageDialog(null,e);
}
        }
        else{
            JOptionPane.showMessageDialog(null, "Generate exam tickets please");
        }

    }


    /** print
     *
     * @param file-файл идущий на печать
     * @param <DDocument>
     * @throws PrinterException
     * @throws IOException
     */
    private static <DDocument> void print(File file) throws PrinterException, IOException {

        PDDocument doc = PDDocument.load(file);
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(doc));
        job.print();

    }
}
