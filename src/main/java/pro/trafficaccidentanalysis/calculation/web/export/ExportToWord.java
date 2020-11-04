package pro.trafficaccidentanalysis.calculation.web.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ExportToWord {
	
	private static void setTitle(String titleText, XWPFDocument document) {
		XWPFParagraph title = document.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);
		
		XWPFRun titleRun = title.createRun();
		titleRun.setText(titleText);
		titleRun.setBold(true);
		titleRun.setFontFamily("Courier");
		titleRun.setFontSize(20);
	}
	
	private static void setResult(double result, XWPFDocument document) {
		XWPFParagraph para1 = document.createParagraph();
		para1.setAlignment(ParagraphAlignment.BOTH);
		String string1 = result + "";
		XWPFRun para1Run = para1.createRun();
		para1Run.setText(string1);
	}
	
	public static void createWord(String documentTitle, String titleText, double result, HttpServletResponse response) {
		XWPFDocument document = new XWPFDocument();
		setTitle(titleText, document);
		setResult(result, document);
		
		OutputStream output = null;
		try {
			response.reset();
			String fileName = URLEncoder.encode(documentTitle + ".docx", "UTF-8");
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			response.setHeader("Content-disposition", "attachment;filename="+fileName);
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-control", "no-transform");
						
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			document.write(byteArrayOutputStream);
						
			byte[] buffer = byteArrayOutputStream.toByteArray();

            output = response.getOutputStream();
            output.write(buffer);
            output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
