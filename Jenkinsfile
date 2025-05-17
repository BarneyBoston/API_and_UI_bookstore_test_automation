pipeline {
    agent {
        label 'WindowsAgent'
    }

    environment {
        ENV = 'jenkins'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        timeout(time: 6, unit: 'HOURS')
    }

    stages {
        stage('Start Docker Compose') {
            steps {
                sh 'docker-compose up -d'

                sh 'sleep 20'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -DENV=${ENV}'
            }
        }
    }

    post {
        success {
            emailext to: 'sampleEmail@gmail.com',
                     mimeType: 'text/html',
                     subject: 'Test Results',
                     body: '${FILE,path="target/surefire-reports/email-report.html"}'

            allure([
                includeProperties: false,
                jdk              : '',
                properties       : [],
                reportBuildPolicy: 'ALWAYS',
                results          : [[path: 'target/allure-results']]
            ])
        }
        always {
            sh 'docker-compose down'
        }
    }
}