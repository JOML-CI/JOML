node ("default-java || heavy-java") {
    stage('Checkout') {
        echo "Going to check out the things !"
        checkout scm
        sh 'chmod +x mvnw'
    }
     stage('Build') {
        sh './mvnw clean test'
        sh sed 's;<artifactId>joml</artifactId>;<artifactId>joml-gwt</artifactId>;'  pom.xml > pom-gwt.xml  && ./mvnw clean package -Dmaven.javadoc.skip=false -Dgwt  -f pom-gwt.xml
        sh sed 's;<artifactId>joml</artifactId>;<artifactId>joml-jdk3</artifactId>;' pom.xml > pom-jdk3.xml && ./mvnw clean package -Dmaven.javadoc.skip=false -Djdk3 -f pom-jdk3.xml
        sh sed 's;<artifactId>joml</artifactId>;<artifactId>joml-jdk8</artifactId>;' pom.xml > pom-jdk8.xml && ./mvnw clean package -Dmaven.javadoc.skip=false -Djdk8 -f pom-jdk8.xml
        sh sed 's;<artifactId>joml</artifactId>;<artifactId>joml-graal</artifactId>;' pom.xml > pom-graal.xml && ./mvnw clean package -Dmaven.javadoc.skip=false -Dgraal -f pom-graal.xml
    }
    stage('Publish') {
        if (env.BRANCH_NAME.equals("main")) {
             withCredentials([usernamePassword(credentialsId: 'artifactory-gooey', usernameVariable: 'artifactoryUser', passwordVariable: 'artifactoryPass')]) {
                sh ./mvnw clean deploy --settings .travis/settings.xml -Ddeployment -Dmaven.test.skip=true
                sh ./mvnw clean deploy --settings .travis/settings.xml -Ddeployment -Dgwt   -Dmaven.test.skip=true -f pom-gwt.xml
                sh ./mvnw clean deploy --settings .travis/settings.xml -Ddeployment -Djdk3  -Dmaven.test.skip=true -f pom-jdk3.xml
                sh ./mvnw clean deploy --settings .travis/settings.xml -Ddeployment -Djdk8  -Dmaven.test.skip=true -f pom-jdk8.xml
                sh ./mvnw clean deploy --settings .travis/settings.xml -Ddeployment -Dgraal -Dmaven.test.skip=true -f pom-graal.xml
            }
        }
    }

}
