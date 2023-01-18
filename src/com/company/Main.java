package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            // Получение фабрики - шаблона для создания объектов
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            // Получение из фабрики билдера, который парсит XML, создает структуру Document в виде иерархического дерева
            DocumentBuilder dbuild = f.newDocumentBuilder();
            // Парсинг XML путем создания структуры Document. Теперь есть доступ ко всем элементам
            Document doc1 = dbuild.parse(new File("laba1.xml"));

            // Формируем запрос, который найдет все записи с атрибутами
           /**/
            Object result = XPathFactory.newInstance().newXPath().compile("/catalog/material[rashod<kolvo][kolvo div rashod >=3]").evaluate(doc1, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;


            /**/Object result2 = XPathFactory.newInstance().newXPath().compile("/catalog/material[not(preceding-sibling::material/rashod <= rashod) " +
                    "and not(following-sibling::material/rashod < rashod)]").evaluate(doc1, XPathConstants.NODESET);
            NodeList nodes2 = (NodeList) result2;


            //создаем новый объект Document для записи выбранных xml данных
            Document doc2 = dbuild.newDocument();
            Element resultLaba1 = doc2.createElement("resultLaba1");
            doc2.appendChild(resultLaba1);
            Element exhausting = doc2.createElement("exhausting");
            resultLaba1.appendChild( exhausting );
            Element leastUsing = doc2.createElement("leastusing");
            resultLaba1.appendChild( leastUsing );

            //Добавляем узлы от первого ко второму объекту
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                doc2.adoptNode(node);
                exhausting.appendChild(node);
            }

            for (int i = 0; i < nodes2.getLength(); i++) {
                Node node = nodes2.item(i);
                doc2.adoptNode(node);
                leastUsing.appendChild(node);
            }



            //создаем объект TransformerFactory для печати
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //записываем данные в новый документ
            DOMSource source = new DOMSource(doc2);
            StreamResult file = new StreamResult(new File("resultLaba1.xml"));
            transformer.transform(source, file);
            System.out.println("Создание XML файла закончено");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}