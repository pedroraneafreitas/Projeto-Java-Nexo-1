projeto java da nexo
 
 Para funcionar precisa instalar o FFmpeg, que é usado no projeto para extrair o audio do video e a API Voks, usada para transcrever o audio

 1 -- instalar a choco
   A -- abrir o CMD pelo ADM e enviar esse comando 
       @"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin" 


    B-- Depois, instale o ffmpeg com o comando 
        choco install ffmpeg -y



2 -- Instalando a VOKS

    A-- Acessar o https://alphacephei.com/vosk/models e procurar pelo link de instalação "vosk-model-pt-fb-v0.1.1-20220516_2113", e colocar para instalar

    B-- apos a instalação do arquivo .zip, transferir ele para a pasta "C:" do computador e extrair o conteudo do zip


3-- após todos os passos, entrar no Application.properties, campo "spring.datasource.password=" coloque a senha do seu banco MySQL, e não se esqueça de quando for fazer um commit, deixar esse campo vazio

    