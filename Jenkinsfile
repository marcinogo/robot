pipeline {
    agent any

    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning environment...'
                sh 'mvn clean'
            }
        }
        stage('Compile') {
            steps {
                echo 'Compiling..'
                sh 'mvn compile'
            }
        }
        stage('Unit testing') {
            steps {
                echo 'Unit testing..'
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                echo 'Packaging...'
                sh 'mvn package'
            }
        }
       stage('Integration testing') {
           steps {
               echo 'Integration testing...'
               retry(3) {
                    sh 'mvn verify'
               }
           }
       }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}