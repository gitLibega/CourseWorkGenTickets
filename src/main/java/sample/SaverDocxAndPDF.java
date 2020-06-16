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
            fileChooser.setTitle("Open Document...");//Заголовок диалога
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
                        XWPFParagraph paragraph4 = document.createParagraph();
                        XWPFRun run = paragraph4.createRun();
                        run.setText(ticketsArray.get(i));

                        XWPFParagraph paragraph2 = document.createParagraph();
                        paragraph2.setAlignment(ParagraphAlignment.CENTER);

                        run = paragraph2.createRun();
                        run.setText(titleTicket+" Имя:________________ Фамилия:____________");

                        XWPFParagraph paragraph3 = document.createParagraph();
                        paragraph3.setAlignment(ParagraphAlignment.CENTER);
                         run = paragraph3.createRun();
                        run.setText("Билет "+count);

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
            BaseFont baseFont = loadBaseFont("/TimesNewRoman.ttf");
            Font font= new Font(baseFont, 14);
            FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
            fileChooser.setTitle("Save Document...");//Заголовок диалога
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

                        Paragraph p3 = new Paragraph();
                        p3.setFont(font);
                        p3.add(ticketsArray.get(i));
                        document.add(p3);

                        Paragraph p=new Paragraph();
                        p.setFont(font);
                        p.setAlignment(1);
                        p.add(titleTicket+" Имя:________________ Фамилия:____________");
                        document.add(p);

                        Paragraph p1=new Paragraph();
                        p1.setFont(font);
                        p1.setAlignment(1);
                        p1.add(("Билет "+count));
                        document.add(p1);
                    }
else{
                    Paragraph p = new Paragraph();
                    p.setFont(font);
                    p.add(ticketsArray.get(i));
                    document.add(p);
                }
                }
                document.close();
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Generate exam tickets, please");
        }
    }

    /**
     * Метод создания нового шрифта из файла ресурсов, позволяет сохранять документ на любом языке
     *
     * @param fontName - Имя фала ресурсов со шрифтом
     * @return - Экземпляр класса BaseFont с созданным шрифтом
     */
    private static BaseFont loadBaseFont(String fontName) {
        BaseFont baseFont= null;
        try {
            baseFont = BaseFont.createFont(fontName, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return baseFont;
    }




}
