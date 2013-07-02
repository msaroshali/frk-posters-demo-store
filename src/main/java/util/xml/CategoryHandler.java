package util.xml;

import java.util.ArrayList;
import java.util.List;

import models.SubCategory;
import models.TopCategory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


import com.avaje.ebean.Ebean;

public class CategoryHandler extends DefaultHandler {

	private TopCategory category;

	private SubCategory subCategory;

	private List<SubCategory> subCategoryList;

	private String currentValue;

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		if (localName.equals("category")) {
			category = new TopCategory();
			subCategoryList = new ArrayList<SubCategory>();
		}

		if (localName.equals("subCategory")) {
			subCategory = new SubCategory();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) {
		if (localName.equals("nameCategory")) {
			category.setName(currentValue);
			String url = currentValue.replaceAll(" ", "");
			category.setUrl(url);
		}

		if (localName.equals("category")) {
			category.setSubCategories(subCategoryList);
			Ebean.save(category);
		}

		if (localName.equals("nameSubCategory")) {
			subCategory.setName(currentValue);
			String url = currentValue.replaceAll(" ", "");
			subCategory.setUrl(url);
		}

		if (localName.equals("subCategory")) {
			subCategoryList.add(subCategory);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) {
		currentValue = new String(ch, start, length);
	}
}
