package org.jbossoutreach.certifier;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import javax.swing.text.html.StyleSheet;
import java.io.*;

public class Application {

    // destination paths
    public static final String DEST = "./output/new1.pdf";
    public static final String SOURCE_HTML = "templates/basic.html";

    public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new Application().createPdf(DEST);
    }

    public void createPdf(String file) throws IOException, DocumentException {
        // step 1
        Document document = new Document(PageSize.A4.rotate());
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                getClass().getClassLoader().getResourceAsStream(SOURCE_HTML));
        // step 5
        document.close();
    }

}
