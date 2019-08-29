cd ..
echo 'KLONUJE REPOZYTORIUM'
echo '--------------------'
git clone git@github.com:marcinogo/robot-front.git
cd robot-front
echo 'INSTALUJE NODEJS'
echo '----------------'
sudo apt-get install nodejs
echo 'INSTALUJE NPM'
echo '----------------'
sudo npm install
echo 'INSTALUJE ANGULAR CLI'
echo '----------------'
sudo npm install -g @angular/cli
echo 'STARTUJE APLIKACJE'
echo '----------------'
ng serve -o
~               
