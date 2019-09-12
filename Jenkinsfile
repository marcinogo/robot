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
        stage('Unit test') {
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
        stage('Integration test') {
           steps {
               echo 'Integration testing...'
               retry(3) {
                    sh 'mvn failsafe:integration-test'
               }
           }
        }
        stage('Performance test') {
            steps {
                echo 'Performance testing...'
                sh 'gatling:test'
                gatlingArchive()
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}