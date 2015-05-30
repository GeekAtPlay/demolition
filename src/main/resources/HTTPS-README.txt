HTTPS requires a signed certificate certificate and a certificate password which we provide using property values. To do so, we can use the JDK’s keytool like this:

$ keytool -genkey -alias bookmarks -keyalg RSA -keystore src/main/resources/tomcat.keystore
Enter keystore password: password
Re-enter new password: password
What is your first and last name?
  [Unknown]:  Your Name
What is the name of your organizational unit?
  [Unknown]:  Your Team
What is the name of your organization?
  [Unknown]:  Your Company
What is the name of your City or Locality?
  [Unknown]:  City
What is the name of your State or Province?
  [Unknown]:  State
What is the two-letter country code for this unit?
  [Unknown]:  US
Is CN=Josh Long, OU=Spring Team, O=Pivotal, L=IoT, ST=Earth, C=US correct?
  [no]:  yes

Enter key password for <learningspringboot>
	(RETURN if same as keystore password): <RETURN>

NOTE: keystore files MUST be on the file system for embedded Tomcat to read them. They can NOT be embedded in JAR files. (And frankly, that type of security item should NOT be part of your deliverable anyway.)
