package com.bankle.common.asis.component;//package kr.co.anbu.component;
//
//import lombok.extern.slf4j.Slf4j;
////import org.w3c.dom.Document;
////import org.w3c.dom.Element;
////import org.w3c.dom.Node;
////
////import javax.xml.parsers.DocumentBuilder;
////import javax.xml.parsers.DocumentBuilderFactory;
////import javax.xml.parsers.ParserConfigurationException;
////import javax.xml.transform.OutputKeys;
////import javax.xml.transform.Transformer;
////import javax.xml.transform.TransformerException;
////import javax.xml.transform.TransformerFactory;
////import javax.xml.transform.dom.DOMSource;
////import javax.xml.transform.stream.StreamResult;
////import java.io.File;
////import java.util.HashMap;
//
//@Slf4j
//public class FileXMLWriter {
//
////    private Document document;
////
////    public FileXMLWriter(Document document) throws ParserConfigurationException {
////
////        log.info("========================== createXMLDocument START ==========================");
////
////        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
////        DocumentBuilder documentBuilder;
////
////        documentBuilder = documentBuilderFactory.newDocumentBuilder();
////
////        document = documentBuilder.newDocument();
////    }
////
////    public void createXMLDocument(HashMap paramMap, String fileName){
////
////        try{
////
////            Element rootElement = document.createElement("inf");
////            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
////            rootElement.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
////            document.appendChild(rootElement);
////
////            rootElement.appendChild(getCommonInfo(paramMap));
////            rootElement.appendChild(getImageFileInfo(paramMap));
////
////            Transformer transformer = TransformerFactory.newInstance().newTransformer();
////            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
////
////            DOMSource domSource = new DOMSource(document);
////
////            StreamResult console = new StreamResult(System.out);
////            transformer.transform(domSource, console);
////
////            StreamResult file = new StreamResult(new File(fileName));
////            transformer.transform(domSource, file);
////
////            log.info("========================== createXMLDocument END ==========================");
////
////
////        }catch(TransformerException e){
////            e.printStackTrace();
////        }
////    }
////
////    private Node getCommonInfo(HashMap paramMap) {
////
////        log.info("ImgKeyNo "+ paramMap.get("ImgKeyNo"));
////
////        Element element = document.createElement("CommonInfo");
////        appendChild(element, "OpCode", "2");
////        appendChild(element, "ImgKeyNo", (String) paramMap.get("ImgKeyNo"));
////        appendChild(element, "BprAplDscd", "01");
////        appendChild(element, "RoutingCode", (String) paramMap.get("RoutingCode"));
////        appendChild(element, "ScanEmpNo", "APP99999");
////        appendChild(element, "ScanBrCd", (String) paramMap.get("ScanBrCd"));
////        appendChild(element, "ChrpeEmpNo", "APP99999");
////        appendChild(element, "ChrpeBrCd", (String) paramMap.get("ChrpeBrCd"));
////        appendChild(element, "NonBpm", "N");
////        appendChild(element, "BfPrcDscd", "Y");
////
////        return element;
////
////    }
////
////    private Node getImageFileInfo(HashMap paramMap) {
////
////        Element element = document.createElement("ImageFileInfo");
////        appendChild(element, "TableName","WBPRHL0010TM");
////        element.appendChild(getFile(paramMap));
////
////        return element;
////    }
////
////    private Node getFile(HashMap paramMap) {
////
////        /*  문서코드 :
////			소유권이전서류 4040524057
////			당행상환영수증 4040524058
////			타행상환영수증 4040524059
////			(근담보)근저당권설정계약서 3030301023
////		*/
////        Element element = document.createElement("File");
////        appendChild(element, "OpType","N");
////        appendChild(element, "FileName", (String) paramMap.get("imgFileName"));
////        appendChild(element, "ImgFormNo", (String) paramMap.get("ImgFormNo"));
////        appendChild(element, "ImgSrno", (String) paramMap.get("ImgSrno"));
////        appendChild(element, "ImgPageCn", (String) paramMap.get("ImgPageCn"));
////        appendChild(element, "ImgSizCn", (String) paramMap.get("ImgSizCn"));
////        appendChild(element, "BprCreChnlDscd","1");
////        appendChild(element, "ImgExtNm","tif");
////
////        return element;
////    }
////
////    private void appendChild(Element element, String name, String value){
////        element.appendChild(getElements(name, value));
////    }
////
////    private Node getElements(String name, String value) {
////        Element node = document.createElement(name);
////        node.appendChild(document.createTextNode(value));
////
////        return node;
////    }
//}
