package sample;

/*
 * SaverDocxAndPDF Класс печати документа с билетами
 *
 * Version 1.0
 *
 * Author:Libega Ilya
 */

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.stage.FileChooser;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SaverDocxAndPDF extends Controller {
    /** saveDocxFile-сохранение билетов в docx
     *
     * @param ticketsArray- списочный массив билетов
     * @param titleTicket - загаловок билета
     * @throws IOException
     */
    public static void saveInDocxFile( ArrayList <String> ticketsArray, String titleTicket ) throws IOException {

        if(ticketsArray.size()>0) {
            XWPFDocument document = new XWPFDocument();

            FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
            fileChooser.setTitle("Save Document");//Заголовок диалога
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("docx files (*.docx)", "*.docx");//Расширение
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(null);
            FileOutputStream out = new FileOutputStream(new File(file.getPath()));
            if (file != null) {

                int count=0;

                for (int i = 0; i <ticketsArray.size() ; i++) {
                    if(ticketsArray.get(i)=="________________________________________________") {
                        count++;




                        XWPFParagraph paragraph2 = document.createParagraph();
                        paragraph2.setAlignment(ParagraphAlignment.CENTER);
                        XWPFRun run = paragraph2.createRun();
                        run = paragraph2.createRun();

                        run.setText(titleTicket+" "+count);

                    }
                    XWPFParagraph paragraph = document.createParagraph();

                    XWPFRun run = paragraph.createRun();
                    run.setText(ticketsArray.get(i));


                }
                document.write(out);
                out.close();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Generate exam tickets please");
        }
    }

    /** saveInPdFile- сохранение с расширение pdf
     *
     * @param ticketsArray-массив билетов
     * @param titleTicket- название билета
     * @throws IOException
     * @throws DocumentException
     */
    public static void saveInPdfFile( ArrayList <String> ticketsArray, String titleTicket) throws IOException, DocumentException {
        if(ticketsArray.size()>0) {
            Document document = new Document();
            BaseFont bf = BaseFont.createFont("/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED); //подключаем файл шрифта, который поддерживает кириллицу
            Font font = new Font(bf);
            FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
            fileChooser.setTitle("Save Document");//Заголовок диалога
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");//Расширение
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                int count=0;//счетчик билетов
                String File=file.getPath();//ссылка на сохраняемый файл
                PdfWriter.getInstance(document,new FileOutputStream(File));
                document.open();


                for (int i = 0; i <ticketsArray.size() ; i++) {
                    if(ticketsArray.get(i)=="________________________________________________") {
                        count++;
                        Paragraph p=new Paragraph();
                        p.setFont(font);
                        p.setAlignment(1);
                        p.add((titleTicket+" "+count));
                        document.add(p);
                    }

                    Paragraph p = new Paragraph();
                    p.setFont(font);
                    p.add(ticketsArray.get(i));
                    document.add(p);
                }
                document.close();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Generate exam tickets, please");
        }
    }

}
