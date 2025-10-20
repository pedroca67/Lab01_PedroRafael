@echo off
echo Iniciando Teste da Parte 3 (Venda/Fantasma)...

echo Iniciando VendaWriter...
start cmd /k "java -cp build;lib/* VendaWriter"

:: Aguarda 2 segundos para o Writer iniciar
timeout /t 2 >nul

echo Iniciando VendaReader...
start cmd /k "java -cp build;lib/* VendaReader"