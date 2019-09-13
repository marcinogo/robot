pipeline {
    agent any
    environment {
        def scannerHome = tool name: 'SonarQubeScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
    }
    stages {
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
               retry(3) {
                  echo 'Integration testing...'
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
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('Sonar') {
//                 TODO: Pass sonar-jenkins.properties in other way
                    sh "${scannerHome}/bin/sonar-scanner -Dproject.settings=/opt/sonarqube/conf/sonar-jenkins.properties"
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
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
            echo 'Publish unit tests reports'
            junit 'target/surefire-reports/*.xml'
            echo 'Publish integration tests reports'
            junit 'target/failsafe-reports/*.xml'
//                 Fixme: can not find this report
//                 echo 'Publish performance test report'
//                 junit 'target/gatling/assertions-*.xml'
            echo 'Cleaning workspace'
            deleteDir()
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