package com.surveyvor.pdf;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.surveyvor.model.User;

public class PDFBuilder extends AbstractITextPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		// get data model which is passed by the Spring container
		Map<Long,List<User>> resultat = (Map<Long, List<User>>) model.get("resultat");

		doc.add(new Paragraph("Résultat du sondage"));

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] {4.0f,6.0f });
		table.setSpacingBefore(10); 

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.BLACK);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.ORANGE);
		cell.setPadding(5);

		// write table header
		cell.setPhrase(new Phrase("Groupes", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Etudiants affectés", font));
		table.addCell(cell);

		// write table row data
		//Parcours de la map
		for(Entry<Long, List<User>> entry : resultat.entrySet()) {
			//cle est le groupe
			//valeur est la liste d'User affectée au dit groupe
			Long cle = entry.getKey();
			List<User> valeur = entry.getValue();

			//affiche l'id du groupe dans une cellule
			table.addCell(String.valueOf(entry.getKey()));

			//construit un paragraphe de string étant les noms d'user de la liste affecté a ce groupe
			Paragraph p = new Paragraph();
			for (int j=0;j<valeur.size(); j++) {
				p.add(valeur.get(j).getName()+"\n");
			}
			//ajoute le parapgrahe a la cellule
			table.addCell(p);
		}

		//ajoute la table au document PDF
		doc.add(table);

	}

}