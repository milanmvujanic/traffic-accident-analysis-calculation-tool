package pro.trafficaccidentanalysis.calculation.web.export;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigInteger;
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
import org.apache.poi.xwpf.usermodel.XWPFStyle;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathPara;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyle;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTStyles;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STOnOff;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STPageOrientation;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STStyleType;

import pro.trafficaccidentanalysis.calculation.web.calculation.StoppingDistance;

public class ExportToWord {

	static File stylesheet = new File(
//			"./src/main/java/pro/trafficaccidentanalysis/calculation/web/export/MML2OMML.XSL");
	".\\src\\main\\java\\pro\\trafficaccidentanalysis\\calculation\\web\\export\\MML2OMML.XSL");
	static TransformerFactory tFactory = TransformerFactory.newInstance();
	static StreamSource stylesource = new StreamSource(stylesheet);

	private static void setPage(XWPFDocument document) { 
		CTDocument1 doc = document.getDocument();
		CTBody body = doc.getBody();
		
		if (!body.isSetSectPr()) {
		     body.addNewSectPr();
		}
		CTSectPr section = body.getSectPr();
		
		if(!section.isSetPgSz()) {
		    section.addNewPgSz();
		}
		
		CTPageSz pageSize = section.getPgSz();
		pageSize.setW(BigInteger.valueOf(11900));
		pageSize.setH(BigInteger.valueOf(16840));
		
		pageSize.setOrient(STPageOrientation.PORTRAIT);
	}
	
	private static void setTitle(String titleText, XWPFDocument document) {
		XWPFParagraph title = document.createParagraph();
		title.setAlignment(ParagraphAlignment.CENTER);

		XWPFRun titleRun = title.createRun();
		titleRun.setText(titleText);
		titleRun.setBold(true);
		titleRun.setFontFamily("Arial");
		titleRun.setFontSize(13);
		titleRun.addBreak();
	}
	
	private static XWPFRun createRun(XWPFParagraph paragraph, ParagraphAlignment alignment, String text, String fontFamily, int fontSize, boolean bold) {
//		paragraph.setAlignment(alignment);
		XWPFRun run = paragraph.createRun();
		run.setText(text);
		run.setFontFamily(fontFamily);
		run.setFontSize(fontSize);
		run.setBold(bold);
		
		return run;
	}
	
	private static XWPFStyle createNamedStyle(XWPFStyles styles, STStyleType.Enum styleType, String styleId) {
		if (styles == null || styleId == null) return null;
		XWPFStyle style = styles.getStyle(styleId);
		if (style == null) {
		   CTStyle ctStyle = CTStyle.Factory.newInstance();
		   ctStyle.addNewName().setVal(styleId);
		   ctStyle.setCustomStyle(STOnOff.TRUE);
		   style = new XWPFStyle(ctStyle, styles);
		   style.setType(styleType);
		   style.setStyleId(styleId);
		   styles.addStyle(style);
		}
		return style;
	}
	
	private static void applyJustification(XWPFStyle style, ParagraphAlignment value) throws Exception {
		if (style == null || value == null) return;

		Field _ctStyles = XWPFStyles.class.getDeclaredField("ctStyles");
		_ctStyles.setAccessible(true);
		CTStyles ctStyles = (CTStyles)_ctStyles.get(style.getStyles());

		for (CTStyle ctStyle : ctStyles.getStyleList()) {
			if (ctStyle.getStyleId().equals(style.getStyleId())) {
				CTPPr ppr = ctStyle.getPPr(); if (ppr == null) ppr = ctStyle.addNewPPr();
				CTJc jc = ppr.getJc(); if (jc == null) jc = ppr.addNewJc();
		    if (value == ParagraphAlignment.BOTH) {
		    	jc.setVal(STJc.BOTH);
		    } else if (value == ParagraphAlignment.CENTER) {
		    	jc.setVal(STJc.CENTER);
		    } else if (value == ParagraphAlignment.DISTRIBUTE) {
		    	jc.setVal(STJc.DISTRIBUTE);
		    } else if (value == ParagraphAlignment.HIGH_KASHIDA) {
		    	jc.setVal(STJc.HIGH_KASHIDA);
		    } else if (value == ParagraphAlignment.LEFT) {
		    	jc.setVal(STJc.LEFT);
		    } else if (value == ParagraphAlignment.LOW_KASHIDA) {
		    	jc.setVal(STJc.LOW_KASHIDA);
		    } else if (value == ParagraphAlignment.MEDIUM_KASHIDA) {
		    	jc.setVal(STJc.MEDIUM_KASHIDA);
		    } else if (value == ParagraphAlignment.NUM_TAB) {
		    	jc.setVal(STJc.NUM_TAB);
		    } else if (value == ParagraphAlignment.RIGHT) {
		    	jc.setVal(STJc.RIGHT);
		    } else if (value == ParagraphAlignment.THAI_DISTRIBUTE) {
		    	jc.setVal(STJc.THAI_DISTRIBUTE);
		    }
		    style.setStyle(ctStyle);
		   }
		}
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
		setPage(document);
		setTitle(titleText, document);
		
		XWPFStyles styles = document.createStyles();
		XWPFStyle style = createNamedStyle(styles, STStyleType.PARAGRAPH, "TextJustificationLEFT");
		applyJustification(style, ParagraphAlignment.LEFT);

		XWPFParagraph paragraph = document.createParagraph();
		XWPFRun run = createRun(paragraph, ParagraphAlignment.LEFT, sectionTitle, "Arial", 12, false);
		
		paragraph = document.createParagraph();
		paragraph.setStyle(style.getStyleId());
		run = createRun(paragraph, ParagraphAlignment.LEFT, "", "Arial", 12, false);

		String mathML = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\" display=\"block\">" + 
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
		run = createRun(paragraph, ParagraphAlignment.LEFT, "", "Arial", 12, false);
		
		mathML = "<math xmlns=\"http://www.w3.org/1998/Math/MathML\">" + "<mrow>"
				+ "<msub><mi>S</mi><mn>z</mn></msub><mo>=</mo><mi>" + stoppingDistance.getStoppingDistance() + "</mi>" + "</mrow>" + "</math>";

		ctOMath = getOMML(mathML);

		ctp = paragraph.getCTP();		
		ctp.setOMathArray(new CTOMath[] { ctOMath });

		OutputStream output = null;
		try {
			response.reset();
			documentTitle = URLEncoder.encode(documentTitle + ".docx", "UTF-8");
			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			response.setHeader("Content-disposition", "attachment;filename=" + documentTitle);
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
