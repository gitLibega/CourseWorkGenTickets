package sample;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * PrintTickets Класс печати документа с билетами
 *
 * Version 1.0
 *
 * Author:Libega Ilya
 */
public class PrintTickets {


/**Метод создания документа и занесения в него данных с последующей печатью
 * @param ticketsArray - лист строк, где каждая строка формирует билеты и заголовок к ним
 * @param titleTicket-заголовок билета
 */
    public static void printDocumentWithTickets(ArrayList<String> ticketsArray, String titleTicket) throws IOException, PrinterException, DocumentException {
        if (ticketsArray.size()>0) {
            int count = 0; //Счетчик билетов
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("../printFile.pdf"));
            document.open();

            BaseFont bf = BaseFont.createFont("resources/arial.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED); //подключаем файл шрифта, который поддерживает кириллицу
            Font font = new Font(bf);

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
            printPDF("../printFile.pdf");
            File file=new File("../printFile.pdf");
            file.delete();
        }
        else{
            JOptionPane.showMessageDialog(null, "Generate exam tickets please");
        }

    }

    /**Метод печати выбранного файла
     * @param fileName - имя файла, идущиего в печать
     */


    public static void printPDF(String fileName)
            throws IOException, PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        PDDocument doc = PDDocument.load(fileName);
        doc.print(job);
    }
}
