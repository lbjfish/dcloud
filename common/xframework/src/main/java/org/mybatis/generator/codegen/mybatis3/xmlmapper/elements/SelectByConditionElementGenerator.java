/** customize configuration */
// add class

package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 
 * @author Frank Zheng
 * @version 1.0.0
 * @date 2015年1月7日 
 */
public class SelectByConditionElementGenerator extends
        AbstractXmlElementGenerator {

//    private boolean isSimple;
    
    public SelectByConditionElementGenerator(boolean isSimple) {
        super();
//        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getListStatementId()));
        answer.addAttribute(new Attribute("parameterType",
                introspectedTable.getBaseRecordType()));
        answer.addAttribute(new Attribute("resultMap",
                introspectedTable.getBaseResultMapId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
//        sb.append("select ");
//        Iterator<IntrospectedColumn> iter;
//        if (isSimple) {
//            iter = introspectedTable.getNonPrimaryKeyColumns().iterator();
//        } else {
//            iter = introspectedTable.getAllColumns().iterator();
//        }
//        while (iter.hasNext()) {
//            
//            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(iter.next());
//            sb.append(columnName);
//
//            if (iter.hasNext()) {
//                sb.append(", ");
//            }
//            if (sb.length() > 80) {
//                answer.addElement(new TextElement(sb.toString()));
//                sb.setLength(0);
//                OutputUtilities.xmlIndent(sb, 1);
//            }
//        }
//        if(sb.length() > 0){
//            answer.addElement(new TextElement(sb.toString()));
//            sb.setLength(0);
//            OutputUtilities.xmlIndent(sb, 2);
//        }
        
        answer.addElement(new TextElement("select "));
        answer.addElement(getBaseColumnListElement());
        
        sb.append("from ");
//        answer.addElement(new TextElement(sb.toString()));
//        sb.setLength(0);
//        OutputUtilities.xmlIndent(sb, 1);
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
//        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));
        // include
        answer.addElement(getCustomizeIncludeElement());
        // sort
        XmlElement orderFieldElement = new XmlElement("if");
        orderFieldElement.addAttribute(new Attribute("test", "orderField != null and orderField != ''"));
        orderFieldElement.addElement(new TextElement("ORDER BY ${orderField}"));
        
        XmlElement orderStringElement = new XmlElement("if");
        orderStringElement.addAttribute(new Attribute("test", "orderString != null and orderString != ''"));
        orderStringElement.addElement(new TextElement(" ${orderString}"));
        
        orderFieldElement.addElement(orderStringElement);
        answer.addElement(orderFieldElement);
        XmlElement nullSort = new XmlElement("if");
        nullSort.addAttribute(new Attribute("test", "sort == null"));
        //nullSort.addElement(new TextElement("ORDER BY ${@needModify} ${order}"));
        nullSort.addElement(new TextElement("ORDER BY oper_time DESC"));
//        answer.addElement(nullSort);
        

        if (context.getPlugins()
                .sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
