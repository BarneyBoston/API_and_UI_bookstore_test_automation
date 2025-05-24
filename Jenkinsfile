pipeline {
    agent {
        label 'windows-agent'
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
                bat 'docker-compose up -d'
            }
        }

        stage('Compile') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml -DENV=%ENV%'
            }
        }
    }

    post {
        always {
            stage('Prepare Allure Environment') {
                steps {
                    bat """
                    if not exist target\\allure-results mkdir target\\allure-results
                    echo ENV=%ENV% > target\\allure-results\\environment.properties
                    """
                }
            }
            allure([
                commandline: 'allure',
                includeProperties: false,
                jdk              : '',
                properties       : [],
                reportBuildPolicy: 'ALWAYS',
                results          : [[path: 'target/allure-results']]
            ])
            bat 'docker-compose down'
        }
    }
}
