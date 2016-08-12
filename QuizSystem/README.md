# QuizSystem

How to run this project?

1.Import the database using the sql file "webex21.sql"
create a new table like as followed
```sql
CREATE TABLE `ent_jquiz` (
  `QuizID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `AuthorID` int(11) NOT NULL,
  `GroupID` int(11) NOT NULL,
  `Title` varchar(45) NOT NULL,
  `Description` varchar(255) NOT NULL,
  `Code` mediumblob NOT NULL,
  `MinVar` int(11) NOT NULL,
  `MaxVar` int(11) NOT NULL,
  `AnsType` int(10) unsigned NOT NULL,
  `Privacy` int(10) unsigned NOT NULL,
  `rdfID` varchar(45) NOT NULL,
  `QuesType` int(10) unsigned NOT NULL,
  `timestamp` int(10) unsigned DEFAULT NULL,
  `rdfIdDisplay` varchar(45) DEFAULT NULL,
  `version` int(11) DEFAULT '0',
  `start` int(10) DEFAULT '2',
  `end` int(10) DEFAULT '4',
  PRIMARY KEY (`QuizID`),
  KEY `FK_ent_jquiz_1` (`GroupID`),
  KEY `FK_ent_jquiz_2` (`AuthorID`),
  CONSTRAINT `FK_ent_jquiz_1` FOREIGN KEY (`GroupID`) REFERENCES `ent_group` (`id`),
  CONSTRAINT `FK_ent_jquiz_2` FOREIGN KEY (`AuthorID`) REFERENCES `ent_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=332 DEFAULT CHARSET=utf8;
```
2.Import the whole project into Eclipse.

3.Configure db information in src/Util/DBConnect

4.canel/add the comment in src/servlet/displayQuiz and src/servlet/EvaludateAnswer

That is, in displayQuiz, making sure that you use Session to set attribute.
```java
request.getSession().setAttribute("usr", request.getParameter("usr"));
request.getSession().setAttribute("grp", request.getParameter("grp"));
request.getSession().setAttribute("sid", request.getParameter("sid"));
String usr=(String) request.getSession().getAttribute("usr");
//String usr = getServletContext().getInitParameter("usr");
```

in EvaludateAnswer, making sure that you use Session to get attribute.
```java
//String sid = "test";
//String grp = getServletContext().getInitParameter("grp");
//String usr = getServletContext().getInitParameter("usr");
String sid = (String) request.getSession().getAttribute("sid");
String grp = (String) request.getSession().getAttribute("grp");
String usr = (String) request.getSession().getAttribute("usr");
```

5.This system can be used as QuizPet and QuizJet System, you can configure it in web.xml. The value of paramster can be java or python
```xml
<context-param>
    <param-name>language</param-name>
    <param-value>java</param-value>
</context-param>
```
6.Don't forget to change the value of appDir
```xml
<context-param>
  <param-name>appDir</param-name>
  <param-value>/Users/Qi/Documents/workspace/QuizSystem/WebContent/</param-value>
</context-param>
```

7.If there is no online_compile folder under WebContent, please create it.
