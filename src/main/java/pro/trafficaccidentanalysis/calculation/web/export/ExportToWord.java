package pro.trafficaccidentanalysis.calculation.web.export;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import pro.trafficaccidentanalysis.calculation.web.calculation.StoppingDistance;

public class ExportToWord {

	static File stylesheet = new File(
			"/src/main/java/pro/trafficaccidentanalysis/calculation/web/export/MML2OMML.XSL");
//	".\\src\\main\\java\\pro\\trafficaccidentanalysis\\calculation\\web\\export\\MML2OMML.XSL"
	static TransformerFactory tFactory = TransformerFactory.newInstance();
	static StreamSource stylesource = new StreamSource(stylesheet);

	private static void setTitle(String titleText, XWPFDocument document) {
		XWPFParagraph title = document.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titleRun = title.createRun();
		titleRun.setText(titleText);
		titleRun.setBold(true);
		titleRun.setFontFamily("Courier");
		titleRun.setFontSize(20);
	}

	private static void setResult(String result, XWPFDocument document) {
		XWPFParagraph para1 = document.createParagraph();
		para1.setAlignment(ParagraphAlignment.BOTH);
		XWPFRun para1Run = para1.createRun();
		para1Run.setText(result);
	}

	static CTOMath getOMML(String mathML) throws Exception {
		Transformer transformer = tFactory.newTransformer(stylesource);

		StringReader stringreader = new StringReader(mathML);
		StreamSource source = new StreamSource(stringreader);

		StringWriter stringwriter = new StringWriter();
		StreamResult result = new StreamResult(stringwriter);
		transformer.transform(source, result);

		String ooML = stringwriter.toString();
		stringwriter.close();

		CTOMathPara ctOMathPara = CTOMathPara.Factory.parse(ooML);
		CTOMath ctOMath = ctOMathPara.getOMathArray(0);

		// for making this to work with Office 2007 Word also, special font settings are
		// necessary
		XmlCursor xmlcursor = ctOMath.newCursor();
		while (xmlcursor.hasNextToken()) {
			XmlCursor.TokenType tokentype = xmlcursor.toNextToken();
			if (tokentype.isStart()) {
				if (xmlcursor.getObject() instanceof CTR) {
					CTR cTR = (CTR) xmlcursor.getObject();
					cTR.addNewRPr().addNewRFonts().setAscii("Cambria Math");
					cTR.getRPr().getRFonts().setHAnsi("Cambria Math");
				}
			}
		}

		return ctOMath;
	}

	public static void createWord(String documentTitle, String titleText, String sectionTitle, StoppingDistance stoppingDistance, HttpServletResponse response)
			throws Exception {
		XWPFDocument document = new XWPFDocument();
		setTitle(titleText, document);

		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun run = paragraph.createRun();
		run.setText(sectionTitle);
		run.addBreak();

		String mathML = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">" + 
							"<mrow>" +
								"<msub><mi>S</mi><mi>z</mi></msub>" +
								"<mo>=</mo>" +
								"<mn>" + stoppingDistance.getReactionTime() + "</mn>" +
								"<mo>&#x22C5;</mo>" + 								
								"<mfrac>" +
									"<mrow>" +
										"<mi>" + stoppingDistance.getSpeed() + "</mi>" +
									"</mrow>" +
									"<mrow>" + 
										"<mi>3.6</mi>" +
									"</mrow>" +
								"</mfrac>" + 								
								"<mo>+</mo>" + 
									"<mfrac>" +
										"<mrow>" +
											"<mo stretchy=\"true\" form=\"prefix\">(</mo>" +
												"<mfrac>" +
													"<mrow>" +
														"<mi>" + stoppingDistance.getSpeed() + "</mi>" +
													"</mrow>" +
													"<mrow>" +
														"<mi>3,6</mi>" +
													"</mrow>" +
												"</mfrac>" +
												"<mo>-</mo>" +
												"<mfrac>" + 
													"<mrow>" + 
														"<mi>" + stoppingDistance.getDeceleration() + "</mi><mo>&#x22C5;</mo><mi>" + stoppingDistance.getBrakingForceIncrease() + "</mi>" +
													"</mrow>" +
													"<mrow>" +
														"<mi>2</mi>" +
													"</mrow>" +
												"</mfrac>" +
											"<msup><mo stretchy=\"true\" form=\"postfix\">)</mo><mn>2</mn></msup>" +
										"</mrow>" +										
										"<mrow>" +
											"<mi>2</mi>" +
											"<mo>&#x22C5;</mo>" +
											"<mi>" + stoppingDistance.getDeceleration() + "</mi>" +
										"</mrow>" +
									"</mfrac>" +
							"</mrow>" +
						"</math>";

		CTOMath ctOMath = getOMML(mathML);

		CTP ctp = paragraph.getCTP();
		ctp.setOMathArray(new CTOMath[] { ctOMath });

		paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		run = paragraph.createRun();

		mathML = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">" + "<mrow>"
				+ "<msub><mi>S</mi><mn>z</mn></msub><mo>=</mo><mi>" + stoppingDistance.getStoppingDistance() + "</mi>" + "</mrow>" + "</math>";

		ctOMath = getOMML(mathML);

		ctp = paragraph.getCTP();
		ctp.setOMathArray(new CTOMath[] { ctOMath });

		OutputStream output = null;
		try {
			response.reset();
			String fileName = documentTitle;
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
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

//	public static void createWord(String documentTitle, String titleText, String result, HttpServletResponse response) {
//		XWPFDocument document = new XWPFDocument();
//		setTitle(titleText, document);
//		setResult(result, document);
//
//		OutputStream output = null;
//		try {
//			response.reset();
//			String fileName = URLEncoder.encode(documentTitle + ".docx", "UTF-8");
//			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
//			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//			response.setHeader("Pragma", "public");
//			response.setHeader("Cache-control", "no-transform");
//
//			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//			document.write(byteArrayOutputStream);
//
//			byte[] buffer = byteArrayOutputStream.toByteArray();
//
//			output = response.getOutputStream();
//			output.write(buffer);
//			output.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
