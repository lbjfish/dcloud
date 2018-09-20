/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class UpdateByIdsSelectiveElementGenerator extends
        AbstractXmlElementGenerator {

    public UpdateByIdsSelectiveElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update"); //$NON-NLS-1$

        answer
                .addAttribute(new Attribute(
                        "id", introspectedTable.getUpdateByIdsSelectiveStatementId())); //$NON-NLS-1$

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();

        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement dynamicElement = new XmlElement("set"); //$NON-NLS-1$
        answer.addElement(dynamicElement);

        for (IntrospectedColumn introspectedColumn : introspectedTable
                .getNonPrimaryKeyColumns()) {
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append("po."+introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            
            /** customize configuration */
            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn);
            String value;
            if("OPER_TIME".equals(columnName)){
                value = "now()";
            }else{
                value = MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "po.");
            }
            sb.append(columnName);
            sb.append(" = ");
            sb.append(value);
            
//            sb.append(MyBatis3FormattingUtilities
//                    .getEscapedColumnName(introspectedColumn));
//            sb.append(" = "); //$NON-NLS-1$
//            sb.append(MyBatis3FormattingUtilities
//                    .getParameterClause(introspectedColumn));
            sb.append(',');

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
        answer.addElement(new TextElement("where 1=2"));
        
        XmlElement idsElement = new XmlElement("if");
        idsElement.addAttribute(new Attribute("test", "ids != null and ids.size > 0"));
        
        XmlElement innerForEach = new XmlElement("foreach");
        innerForEach
                .addAttribute(new Attribute("collection", "ids"));
        innerForEach.addAttribute(new Attribute("item", "listItem"));
        innerForEach.addAttribute(new Attribute("open", "or id in("));
        innerForEach.addAttribute(new Attribute("close", ")"));
        innerForEach.addAttribute(new Attribute("separator", ","));
        innerForEach.addElement(new TextElement("#{listItem}"));
        
        
        idsElement.addElement(innerForEach);
        answer.addElement(idsElement);

//        boolean and = false;
//        for (IntrospectedColumn introspectedColumn : introspectedTable
//                .getPrimaryKeyColumns()) {
//            sb.setLength(0);
//            if (and) {
//                sb.append("  and "); //$NON-NLS-1$
//            } else {
//                sb.append("where "); //$NON-NLS-1$
//                and = true;
//            }
//
//            sb.append(MyBatis3FormattingUtilities
//                    .getEscapedColumnName(introspectedColumn));
//            sb.append(" = "); //$NON-NLS-1$
//            sb.append(MyBatis3FormattingUtilities
//                    .getParameterClause(introspectedColumn));
//            answer.addElement(new TextElement(sb.toString()));
//        }

        if (context.getPlugins()
                .sqlMapUpdateByPrimaryKeySelectiveElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
