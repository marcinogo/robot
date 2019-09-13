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
                sh 'mvn package -Dmaven.test.skip=true'
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
                sh 'mvn gatling:test -Dgatling.useOldJenkinsJUnitSupport=true'
                gatlingArchive()
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
    post {
            always {
                echo 'This will always run'
                junit 'build/reports/**/*.xml'
            }
            success {
                echo 'This will run only if successful'
            }
            failure {
                echo 'This will run only if failed'
            }
            unstable {
                echo 'This will run only if the run was marked as unstable'
            }
            changed {
                echo 'This will run only if the state of the Pipeline has changed'
                echo 'For example, if the Pipeline was previously failing but is now successful'
            }
        }
}