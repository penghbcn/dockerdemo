FROM tomcat
RUN rm -rf /usr/local/tomcat/webapps/*
ADD eurekaservice.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080