# quizjet
How to run it?

(1)Import the database using the sql file "webex21.sql" in the "db file" folder(If the database doesn't exist.).

(2)Import the whole project into Eclipse. 

(3) Configure the database name string "DBNAME" in edu.pitt.pawslab.quizpet.instance.Setting. For example,
"webex21".


(4)Configure db information in the /WEB-INF/web.xml. Configure the url of db.webexURL AND db.user and db.passwd.

(5)Configure the db information in /WEB-INF/spring/database.xml, such as webex, um2, aggregate.

(6)Save all the configuration. Run it locally in eclipse to test the project.

(7)Export it as an War file and deploy it on tomcat.




How to develop the project?

(1) The UI is implemented using React framework.
The files are in the folder "QuizjetAuthoring-UI-React".
There are two modes: Development mode and Production mode, which you can configure in /json/ApiUrl.js.

If you want to update the React js code and test the result, you need to assign SERVER to TEST_SERVER; when you 
want to deploy the project, you need to assign SERVER TO PRODUCTION_SERVER.

Here are some useful commands using React.

sudo npm install webpack-dev-server -g

npm install --save-dev jsx-loader

npm install --save react react-dom

npm install brace

sudo npm install react-ace

webpack-dev-server --host 0.0.0.0 --port 9090

(2)
Before you start, try to have a basic idea of React and Webpack. When you develop locally, you can edit the react project directly. The webpack host the react project and will automatically generate a bundle js file on the path /assets/bundle.js in the react project. 

When you finish the React (UI) part and the backend part (using Eclipse), you can deploy the project.

First, you need to edit the /json/ApiUrl.js file, assign SERVER to PRODUCTION_SERVER like this "var SERVER = PRODUCTION_SERVER;"

Then you can use a web browser such Chrome and visit "http://localhost:9090/assets/bundle.js". If everything is ok, you will see the compressed js file. You can copy the content and paste it to resources/js/bundle.min.js file in the Eclipse project. 

Fianlly you can export your whole eclipse project to a war file and deploy it. 



Check the following lines in home.jsp to understand how js file is imported.

<c:choose>

		<c:when test="${domain == 'localhost'}">

	<script src="http://localhost:9090/webpack-dev-server.js"></script>

    <script type="text/javascript" src="http://localhost:9090/assets/bundle.js"></script>

    	</c:when>

    	<c:otherwise>

    <script type="text/javascript" src="${baseURL}/resources/js/bundle.min.js"></script>

    	</c:otherwise>

	</c:choose>
	




	

